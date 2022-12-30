package com.lib.manage.service;

import java.util.Base64;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.config.JwtTokenProvider;
import com.lib.manage.constant.ErrorEnum;
import com.lib.manage.dto.AccountDto;
import com.lib.manage.dto.ChangePasswordForm;
import com.lib.manage.dto.response.JwtResponse;
import com.lib.manage.entity.Account;
import com.lib.manage.exception.LBRException;
import com.lib.manage.models.account.CustomAccountDetails;
import com.lib.manage.repository.AccountRepository;

import javassist.NotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtService;

	@Override
	public JwtResponse generateJwtToken(String Authorization, String accountName) throws UsernameNotFoundException, LBRException {
		String auth = new String(Base64.getDecoder().decode(Authorization));
		String aN= "";
		String pwd= "";
		if (Authorization==null||accountName==null||!auth.startsWith(accountName)) {
			throw new LBRException(ErrorEnum.VALUE_NOT_CORRECT, null);
		}else {
			aN = accountName;
			pwd = auth.substring(accountName.length()+1);
			// abc:xyz
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(aN, pwd));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtService.generateTokenLogin(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		CustomAccountDetails currentUser = (CustomAccountDetails) loadUserByUsername(userDetails.getUsername());
		return new JwtResponse(jwt, currentUser.getAccount().getId(), userDetails.getUsername(),
				userDetails.getAuthorities().toString());
	}

	@Override
	public CustomAccountDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
		// Kiểm tra xem user có tồn tại trong database không?
		Account account = accountRepository.findByAccountName(accountName);
		if (account == null) {
			throw new UsernameNotFoundException("Account not found with name: " + accountName);
		}
		return new CustomAccountDetails(account);
	}

	@Transactional
	public UserDetails loadUserById(String id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Account not found with id : " + id));

		return new CustomAccountDetails(account);
	}

	@Override
	public Account findByAccountName(String accountName) throws NotFoundException {
		return accountRepository.findByAccountName(accountName);
	}

	public AccountDto changePassword(Account account) {
		String password = account.getPassword();
		String encodePassword = passwordEncoder.encode(password);// Mã hóa pass của người dùng
		account.setPassword(encodePassword);
		account.setAccountType(account.getAccountType() == null || account.getAccountType().isBlank()
				|| account.getAccountType().isEmpty() ? "LIBRARIAN" : account.getAccountType());
		accountRepository.save(account);
		AccountDto dto = new AccountDto();
		dto.setAccountName(account.getAccountName());
		dto.setAccountType(account.getAccountType());
		dto.setId(account.getId());
		dto.setCreateDate(account.getCreateDate());
		dto.setModifyDate(account.getModifyDate());
		dto.setModifyAction("Change Password!");
		 return dto;
	}

	public boolean checkRegexPassword(String password) {
		String regex = "^(?=.*)(?=.*).{8,32}$"; // mật khẩu từ 8-32 ký tự
		return Pattern.matches(regex, password);
	}
	
	@Override
	public AccountDto changePassword(ChangePasswordForm changePasswordForm) throws NotFoundException, LBRException {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				changePasswordForm.getUsername(), changePasswordForm.getCurrentPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Account account = findByAccountName(changePasswordForm.getUsername());
		if (changePasswordForm.getCurrentPassword().equals(changePasswordForm.getPasswordForm().getPassword())
				|| !changePasswordForm.getPasswordForm().getPassword()
						.equals(changePasswordForm.getPasswordForm().getConfirmPassword())
				|| !checkRegexPassword(changePasswordForm.getPasswordForm().getPassword())
				|| !checkRegexPassword(changePasswordForm.getPasswordForm().getPassword())) {
			throw new LBRException(ErrorEnum.PASSWORD_INVALID, null);
		}
		account.setPassword(changePasswordForm.getPasswordForm().getConfirmPassword());
		return changePassword(account);
	}

//    private CustomAccountDetails createUser(String userName, String password, String role) {
//        
//    	return CustomAccountDetails.builder()
//          .withAccountName(userName)
//          .withPassword(password)
//          .withAccountType(role);
//    }
}

package com.lib.manage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.manage.config.JwtTokenService;
import com.lib.manage.config.TokenService;
import com.lib.manage.constant.AccountTypeEnum;
import com.lib.manage.constant.ErrorEnum;
import com.lib.manage.dto.AccountDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.account.CreateAccountRequest;
import com.lib.manage.dto.request.account.ResetPasswordRequest;
import com.lib.manage.dto.request.account.SearchAccountRequest;
import com.lib.manage.dto.request.account.UpdateAccountRequest;
import com.lib.manage.dto.request.account.UpdatePasswordRequest;
import com.lib.manage.dto.response.JwtResponse;
import com.lib.manage.entity.Account;
import com.lib.manage.exception.LBRException;
import com.lib.manage.models.account.CustomAccountDetails;
import com.lib.manage.models.account.SecurityConfigParam;
import com.lib.manage.repository.AccountRepository;
import com.lib.manage.util.SearchUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import javassist.NotFoundException;

@Service
public class AccountServiceImpl implements AccountService {
	private static final String PRINCIPAL = "principal";
	private AuthenticationManager authenticationManager;
	@Autowired
	private AccountRepository accountRepository;
//	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SecurityConfigParam securityParam;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private JwtTokenService jwtService;
//	@Autowired
	private JwtParser jwtParser;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public JwtResponse generateJwtToken(String Authorization, String accountName) throws Exception {
		if (Authorization != null && Authorization.startsWith("Basic ")) {
			Authorization.replace("Basic ", "");
		}
		String auth = new String(Base64.getDecoder().decode(Authorization));
		String aN = "";
		String pwd = "";
		if (Authorization == null || accountName == null || !auth.startsWith(accountName)) {
			throw new LBRException(ErrorEnum.VALUE_NOT_CORRECT, null);
		} else {
			aN = accountName;
			pwd = auth.substring(accountName.length() + 1);
			// abc:xyz
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(aN, pwd));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtService.generateToken(authentication, null, null);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		CustomAccountDetails currentUser = (CustomAccountDetails) loadUserByUsername(userDetails.getUsername());
		return new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(),
				userDetails.getAuthorities().toString());
	}

	@Override
	public CustomAccountDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
		// Kiểm tra xem user có tồn tại trong database không?
		Optional<Account> account = accountRepository.findByAccountName(accountName);
		if (!account.isPresent()) {
			throw new UsernameNotFoundException("Account not found with name: " + accountName);
		}
		return new CustomAccountDetails(account.get().getId(), accountName, account.get().getDisplayName(),
				account.get().getPassword(), !account.get().getIsDisable(), account.get().getIsDisable());
	}

	@Transactional
	public CustomAccountDetails loadUserById(String id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Account not found with id : " + id));
		return new CustomAccountDetails(account.getId(), account.getAccountName(), account.getDisplayName(),
				account.getPassword(), !account.getIsDisable(), account.getIsDisable());
	}
	
	@Override
	public Account findById(String id) throws LBRException {
		Optional<Account> oAcc = accountRepository.findById(id);
		if (!oAcc.isPresent()) {
	        throw new LBRException(ErrorEnum.NOT_FOUND,"Account with id: "+id+" is unavailable!");
	    }
		return oAcc.get();
	}
	

	@Override
	public Account findByAccountName(String accountName) throws NotFoundException {
		return accountRepository.findByAccountName(accountName).get();
	}

	public AccountDto changePassword(Account account) {
		String password = account.getPassword();
		String encodePassword = passwordEncoder.encode(password);// Mã hóa pass của người dùng
		account.setPassword(encodePassword);
		account.setAccountType(account.getAccountType() == null || account.getAccountType() == AccountTypeEnum.ADMIN
				? AccountTypeEnum.LIBRARIAN
				: account.getAccountType());
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

	@Override
	public AccountDto findAccountByAccountName(String accountName) throws LBRException {
		Optional<Account> account = accountRepository.findByAccountName(accountName);
		if (!account.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		}
		Account a = account.get();
		AccountDto dto = new AccountDto();
		dto.setAccountName(a.getAccountName());
		dto.setId(a.getId());
		dto.setIsDeleted(a.getIsDisable());
		dto.setAccountType(a.getAccountType());
		dto.setCreateDate(a.getCreateDate());
		dto.setModifyDate(a.getModifyDate());
		dto.setModifyAction(a.getModifyAction());
		return dto;
	}

	@Override
	public Account create(@Valid CreateAccountRequest accountRequest)
			throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
		Account u = new Account();
		u.setDisplayName(accountRequest.getDisplayName());
		u.setAccountName(accountRequest.getAccountName());
		u.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
		u.setAccountType(
				accountRequest.getAccountType() == null || accountRequest.getAccountType() == AccountTypeEnum.ADMIN
						? AccountTypeEnum.LIBRARIAN
						: accountRequest.getAccountType());
		u.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
		return accountRepository.save(u);
	}

	@Override
	public String deleteById(String id) throws LBRException {
		Optional<Account> oUser = accountRepository.findById(id);
		if (!oUser.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, id);
		} else {
			accountRepository.deleteById(id);
		}
		return id;
	}

	@Override
	public void setActive(String id, boolean isActive) throws LBRException {
		int count = accountRepository.updateIsActive(!isActive, id);
		if (count == 0) {
			throw new LBRException(ErrorEnum.NOT_FOUND, id );
		}
	}

	@Override
	public Page<AccountDto> advanceSearch(@Valid SearchAccountRequest searchRequest, Pageable pageable) {
		if (searchRequest != null) {
			List<Specification<Account>> specList = getAdvanceSearchSpecList(searchRequest);
			if (specList.size() > 0) {
				Specification<Account> spec = specList.get(0);
				for (int i = 1; i < specList.size(); i++) {
					spec = spec.and(specList.get(i));
				}
				Page<Account> pBI = accountRepository.findAll(spec, pageable);
				List<Account> ls = pBI.getContent();
				List<AccountDto> dtos = new ArrayList<>();
				for (Account bi : ls) {
					AccountDto bookInfoDto = new AccountDto();
					try {
						PropertyUtils.copyProperties(bookInfoDto, bi);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					dtos.add(bookInfoDto);
				}
				return new PageImpl<AccountDto>(dtos, pageable, pBI.getTotalElements());
			}
			return new PageImpl<>(null);
		}
		return findAllAccount(pageable);
	}

	private List<Specification<Account>> getAdvanceSearchSpecList(@Valid SearchAccountRequest s) {
		List<Specification<Account>> specList = new ArrayList<>();
		if (s.getAccountName() != null && !s.getAccountName().isEmpty()) {
			specList.add(SearchUtil.like("name", "%" + s.getAccountName() + "%"));
		}
		if (s.getIsActive() != null) {
			specList.add(SearchUtil.eq("isActive", s.getIsActive()));
		}
		if (s.getIds() != null && s.getIds().size() > 0) {
			if (s.getIds().size() == 1) {
				specList.add(SearchUtil.eq("Ids", s.getIds().get(0)));
			} else {
				specList.add(SearchUtil.in("Ids", s.getIds()));
			}
		}
		if (s.getAccountType() != null) {
			specList.add(SearchUtil.eq("accountType", s.getAccountType()));
		}
		return specList;
	}

	@Override
	public String updatePassword(@Valid UpdatePasswordRequest request) throws NotFoundException {
		Optional<Account> oAccount = accountRepository.findByAccountName(request.getUsername());
		if (!oAccount.isPresent()) {
			throw new NotFoundException("common.error.not-found");
		} else {
			String oldPwd = oAccount.get().getPassword();
			if (!passwordEncoder.matches(request.getCurrentPassword(), oldPwd)) {
				throw new NotFoundException("common.error.not-found");
			}
			String pwd = passwordEncoder.encode(request.getPasswordForm().getPassword());
			accountRepository.updatePassword(pwd, oAccount.get().getId());
			return "Change password account for " + oAccount.get().getDisplayName() + " is success";
		}
	}

	@Override
	public void resetPassword(@Valid ResetPasswordRequest request) throws NotFoundException {
		Optional<Account> oUser = accountRepository.findById(request.getAccountId());
		if (!oUser.isPresent()) {
			throw new NotFoundException("common.error.not-found");
		} else {
			// Update password
			String pwd = passwordEncoder.encode(request.getNewPassword());
			accountRepository.updatePassword(pwd, oUser.get().getId());
		}
	}

	@Override
	public String revokeToken(String token) throws NotFoundException {
		if (token == null || !token.startsWith(securityParam.getTokenPrefix())) {
			throw new NotFoundException("No authorization!");
		}
		token = token.substring(securityParam.getTokenPrefix().length() + 1);
		Claims claims = jwtParser.parseClaimsJws(token).getBody();
		String user = claims.getSubject();
		if (user == null || user.isEmpty()) {
			throw new NotFoundException("No authorization!");
		}
		Object authObj = claims.get(PRINCIPAL);
		CustomAccountDetails principal = objectMapper.convertValue(authObj, new TypeReference<CustomAccountDetails>() {
		});
		tokenService.revokeToken(principal.getId());
		return "Logout Success!";
	}

	@Override
	public Page<AccountDto> findAllAccount(Pageable pageable) {
		 Page<Account> pB = accountRepository.findAll(pageable);
		 List<Account> ls = pB.getContent();
		 List<AccountDto> dtos = new ArrayList<>();
			for (Account bi : ls) {
				AccountDto bookDto = new AccountDto();
				try {
					PropertyUtils.copyProperties(bookDto, bi);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				dtos.add(bookDto);
			}
			return new PageImpl<AccountDto>(dtos, pageable, pB.getTotalElements());
	}

	@Override
	public AccountDto update(String id, @Valid PatchRequest<UpdateAccountRequest> request) throws LBRException {
		Optional<Account> oBook = accountRepository.findById(id);
		if (!oBook.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				Account u = oBook.get();
				for (String fieldName : request.getUpdateFields()) {
					Object newValue = PropertyUtils.getProperty(request.getData(), fieldName);
					// set value of the bean
					PropertyUtils.setProperty(u, fieldName, newValue);
				}
				Account b = accountRepository.save(u);
				AccountDto dto = new AccountDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

}

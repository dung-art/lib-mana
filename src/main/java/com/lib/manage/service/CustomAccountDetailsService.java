package com.lib.manage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lib.manage.entity.Account;
import com.lib.manage.models.account.CustomAccountDetails;
import com.lib.manage.repository.AccountRepository;

public class CustomAccountDetailsService implements UserDetailsService {
  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> oUser = accountRepository.findByAccountName(username);
    if (oUser.isPresent()) {
    	Account u = oUser.get();
      CustomAccountDetails usr = new CustomAccountDetails(u.getId(), u.getAccountName(), u.getDisplayName(), u.getPassword(),
          !u.getIsDisable(), u.getIsDisable());
      usr.setAuthorities(getAuthorities(u));
      return usr;
    } else {
      throw new UsernameNotFoundException(username + " not found");
    }
  }

  private List<GrantedAuthority> getAuthorities(Account account) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(account.getAccountType().toString()));
    return authorities;
  }

}

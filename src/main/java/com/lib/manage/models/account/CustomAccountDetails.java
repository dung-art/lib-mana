package com.lib.manage.models.account;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lib.manage.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomAccountDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Account account;

    public static CustomAccountDetails builder() {
        return new CustomAccountDetails();
    }
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getAccountName();
    }
    
    
    public CustomAccountDetails withPassword(String password) {
         account.setPassword(password);
         return this;
    }

    
    public CustomAccountDetails withAccountName(String accountName) {
        account.setAccountName(accountName);
        return this;
    }
    
    public CustomAccountDetails withAccountType(String accountType) {
        account.setAccountType(accountType);
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !account.getIsDeleted();
    }
}
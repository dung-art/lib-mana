package com.lib.manage.models.account;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomAccountDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String id;
	  private String accountName;
	  private String displayName;
	  @JsonIgnore
	  private String password;
	  @Setter
	  @JsonIgnore
	  private boolean active;
	  @Setter
	  @JsonIgnore
	  private boolean locked;
	  @Setter
	  @JsonIgnore
	  private Collection<GrantedAuthority> authorities;

    public static CustomAccountDetails builder() {
        return new CustomAccountDetails();
    }
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Collection<GrantedAuthority> authorities =  (Collection<GrantedAuthority>) authentication.getAuthorities();
    	return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
      return active;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
      return !locked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public String getUsername() {
		return this.accountName;
	}
	  public CustomAccountDetails(String id, String accountName, String displayName, String password, boolean active,
		      boolean locked) {
		    super();
		    this.id = id;
		    this.accountName = accountName;
		    this.displayName = displayName;
		    this.password = password;
		    this.active = active;
		  }
}
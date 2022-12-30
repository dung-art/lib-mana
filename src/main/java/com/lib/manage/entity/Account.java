package com.lib.manage.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Account")
@Table(name = "lb_account")
public class Account extends EntityBase {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "account_type", nullable = false, insertable = true)
	@NotNull
	private String accountType;

	@Column(name = "account_name", nullable = false, insertable = true)
	@NotNull
    private String accountName;

	@Column(name = "password", nullable = false, insertable = true)
	@NotNull
    private String password;
}
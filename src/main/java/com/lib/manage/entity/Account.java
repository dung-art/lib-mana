package com.lib.manage.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lib.manage.constant.AccountTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Account")
@Table(name = "lb_account")
public class Account extends EntityBase {
	  public static final int PASSWORD_MAX_LENGTH = 256;
	  public static final int PASSWORD_MIN_LENGTH = 32;
	  public static final int DISPLAY_NAME_MAX_LENGTH = 100;
	  public static final int DISPLAY_NAME_MIN_LENGTH = 1;
	  public static final int FAIL_LOGIN_COUNT_MIN = 0;
	  public static final int FAIL_LOGIN_COUNT_MAX = 10;
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  @NotNull
	  @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
	  @Column(name = "display_name", nullable = false, length = DISPLAY_NAME_MAX_LENGTH)
	  private String displayName;
	
	@Column(name = "account_type", nullable = false, insertable = true)
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private AccountTypeEnum accountType;

	@Column(name = "account_name", nullable = false, insertable = true)
	@NotNull
    private String accountName;

	  @NotNull
	  @Size(max = PASSWORD_MAX_LENGTH, min = PASSWORD_MIN_LENGTH)
	  @JsonIgnore
	  @Column(name = "password", nullable = false, length = PASSWORD_MAX_LENGTH)
    private String password;
	  
	  @Min(FAIL_LOGIN_COUNT_MIN)
	  @Max(FAIL_LOGIN_COUNT_MAX)
	  @Column(name = "fail_login_count")
	  private Integer failLoginCount;
}
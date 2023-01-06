package com.lib.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lib.manage.constant.GenderEnum;
import com.lib.manage.constant.UserStatusEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "UserInfo")
@Table(name = "lb_user_info")
@NoArgsConstructor
public class UserInfo extends EntityBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int MAXSIZE_CODE = 12;
	private final static int MINSIZE_CODE = 12;
	private final static int MINSIZE_ADDRESS = 30;
	private final static int MAXSIZE_ADDRESS = 1000;
	private final static int MINSIZE = 2;
	private final static int MAXSIZE = 255;
	private final static int MINSIZE_DESCRIPTION = 50;
	private final static int MAXSIZE_DESCRIPTION = 6000;
//dinh dang user khach hang: CUST + ma user  
	// ma user la so 8 chu so

	@Column(name = "user_code", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_CODE, max = MAXSIZE_CODE)
	private String userCode;

	@Column(name = "user_name", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE, max = MAXSIZE)
	private String userName;

	@Column(name = "birth_date", insertable = true)
	@Size(min = MINSIZE)
	@NotNull
	private String birthDate;
	
	@Column(name = "gender", nullable = false, insertable = true,updatable = false)
//	@ValidGender
	private GenderEnum gender;// khoong theer sua
	
	@Column(name = "phone_number", nullable = false, insertable = true)
	private String phoneNumber;
	
	@Column(name = "email_address", nullable = false, insertable = true)
	private String emailAddress;
	
	@Column(name = "status", nullable = false, insertable = true)
	private UserStatusEnum status;
	
	@Column(name = "current_address", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_ADDRESS, max = MAXSIZE_ADDRESS)
	private String currentAddress;

	@Size(min = MINSIZE_DESCRIPTION, max = MAXSIZE_DESCRIPTION)
	@NotNull
	@Column(name = "description")
	private String description;

//	@PrePersist
//	public void onInsert() {
//		this.setUserAdd(this.getUserName());
//	}

}

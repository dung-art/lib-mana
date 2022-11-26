package com.lib.manage.user.Entity.CustomUser;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMap;
import com.lib.manage.common.Entity.EntityBase;
import com.lib.manage.common.Enum.GroupCustomUserEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JGlobalMap(excluded = { "MAXSIZE_CODE", "MINSIZE_CODE", "MINSIZE", "MAXSIZE", "isDelete", "amountSpent",
		"groupCustomUser", "userAdd", "dateAdd", "modifiedUser", "modifiedAction", "modifiedDate",
		"MINSIZE_DESCRIPTION", "MAXSIZE_DESCRIPTION", "MINSIZE_ADDRESS", "MAXSIZE_ADDRESS" ,"MINSIZE_ID","MAXSIZE_ID","id"})
@Getter
@Setter
@Entity(name = "CustomUsers")
@Table(name = "QLBH_custom_users")
@NoArgsConstructor
public class CustomUser extends EntityBase{
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

	@Column(name = "Custom_User_Code", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_CODE, max = MAXSIZE_CODE)
	private String customUserCode;

	@Column(name = "Full_Name", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String fullName;

	@Column(name = "User_Name", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE, max = MAXSIZE)
	private String userName;

	@Column(name = "Identify_Number", insertable = true)
	@Size(min = MINSIZE, max = MAXSIZE)
	private String identifyNumber;

	@Column(name = "Address", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_ADDRESS, max = MAXSIZE_ADDRESS)
	private String address;

	@Column(name = "Birth_Date", insertable = true)
	@Size(min = MINSIZE)
	@NotNull
	@JMap("birthDate")
	private LocalDateTime birthDate;

	@Column(name = "Password", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String password;

	@Column(name = "Path_Image_User", nullable = false, insertable = true)
	@Size(min = MINSIZE)
	private String pathImageUser;

	@Column(name = "Amount_Spent", nullable = false, insertable = true)
	@Size(min = MINSIZE)
	private float amountSpent = 0;

	@Column(name = "Group_Custom_User", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private GroupCustomUserEnum groupCustomUser = GroupCustomUserEnum.NORMAL_CUSTOMER;

	@Size(min = MINSIZE_DESCRIPTION, max = MAXSIZE_DESCRIPTION)
	@NotNull
	@Column(name = "description")
	private String description;

	@PrePersist
	public void onInsert() {
		this.setUserAdd(this.getUserName());
		this.setAmountSpent(0);
	}

	@PreUpdate
	public void updateGroupCustomer() {
		this.setModifiedUser(this.getUserName());
		if (this.getAmountSpent() >= 10000000) {
			this.setGroupCustomUser(GroupCustomUserEnum.LOYAL_CUSTOMER);
		}
		if (this.getAmountSpent() >= 100000000) {
			this.setGroupCustomUser(GroupCustomUserEnum.VIP_CUSTOMER);
		}
	}

}

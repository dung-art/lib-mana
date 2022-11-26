package com.lib.manage.user.Dto.CustomerUser;

import java.time.LocalDateTime;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.lib.manage.common.Enum.GroupCustomUserEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JGlobalMap
@Getter
@Setter
@NoArgsConstructor
public class CustomUserDto {
	private Boolean isDelete;
	private String userAdd;
	private LocalDateTime dateAdd;
	private LocalDateTime modifiedDate;
	private String modifiedUser;
	private String modifiedAction;
	private String customUserCode;
	private String fullName;
	private String userName;
	private String identifyNumber;
	private String address;
	private LocalDateTime birthDate;
	private String password;
	private String pathImageUser;
	private float amountSpent;
	private GroupCustomUserEnum groupCustomUser;
	private String description;
}

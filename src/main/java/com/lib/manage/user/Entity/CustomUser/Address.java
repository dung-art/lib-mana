package com.lib.manage.user.Entity.CustomUser;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Address")
@Table(name = "QLBH_address")
@NoArgsConstructor
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static int MAXSIZE_CODE = 12;
	private final static int MINSIZE_CODE = 12;
	private final static int MINSIZE = 2;
	private final static int MAXSIZE = 255;
	private final static int MINSIZE_ADDRESS = 30;
	private final static int MAXSIZE_ADDRESS = 1000;

	@Id
	@Column(name = "Id", nullable = false, insertable = true)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(min = MINSIZE, max = MAXSIZE)
	private String id;

	@Column(name = "Address", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_ADDRESS, max = MAXSIZE_ADDRESS)
	private String address;

	@Column(name = "Custom_User_Code", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_CODE, max = MAXSIZE_CODE)
	private String customUserCode;

	public Address(String address, String customUserCode) {
		this.address = address;
		this.customUserCode = customUserCode;
	}
}

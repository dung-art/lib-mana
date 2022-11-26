package com.lib.manage.user.Entity.Login;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Entity(name = "TryLogins")
@Table(name = "qlbh_try_logins")
@NoArgsConstructor
public class TryLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static int MINSIZE = 2;
	private final static int MAXSIZE = 255;

	@Id
	@Column(name = "Id", nullable = false, insertable = true)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(min = MINSIZE, max = MAXSIZE)
	private String id;

	@Column(name = "User_Name", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String userName;

	@Column(name = "Token", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String token;

	@Column(name = "Last_Used", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private LocalDateTime lastUser;
}

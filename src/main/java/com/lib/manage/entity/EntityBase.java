package com.lib.manage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntityBase implements Serializable {
	
	private static final int MINSIZE_ID = 36;
	private static final int MAXSIZE_ID = 36;
	@Id
	@GeneratedValue(generator = "GenerateUUID")
	@GenericGenerator(name = "GenerateUUID", strategy = "com.lib.manage.validator.GenerateUUID")
	@Column(name = "Id", updatable = false, nullable = false)
	@NotNull
	@Size(min = MINSIZE_ID, max = MAXSIZE_ID)
	private String id;

	private static final long serialVersionUID = 1L;
	@Column(name = "is_disable")
	private Boolean isDisable;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "modify_date")
	private LocalDateTime modifyDate;

	@Column(name = "modify_user")
	private String modifiedUser;

	@Column(name = "modify_action")
	private String modifyAction;

	@PrePersist
	public void preInsert() {
		LocalDateTime nowDateTime = LocalDateTime.now();
		this.createDate = nowDateTime;
		this.modifyDate = nowDateTime;
		this.createUser = SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PreUpdate
	public void preUpdate() {
		LocalDateTime nowDateTime = LocalDateTime.now();
		this.modifyDate = nowDateTime;
		this.modifiedUser = SecurityContextHolder.getContext().getAuthentication().getName();
	}
}

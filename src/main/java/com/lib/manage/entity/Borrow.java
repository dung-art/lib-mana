package com.lib.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Borrow")
@Table(name = "lb_borrow")
public class Borrow extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_code", nullable = false, insertable = true)
	@NotNull
	private String userCode;

	@Column(name = "book_id", insertable = true)
	@NotNull
	private String bookId;

	@Column(name = "borrow_date", insertable = true)
	@NotNull
	private String borrowDate;

	@Column(name = "borrow_time", insertable = true)
	@NotNull
	private String borrowTime;

	@Column(name = "borrow_end", insertable = true)
	@NotNull
	private String borrowEnd;

	@Column(name = "status", insertable = true)
	@NotNull
	private Boolean status;
}

package com.lib.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lib.manage.constant.BookConditionEnum;
import com.lib.manage.constant.BorrowStatusEnum;

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
	private String borrowDate; // ngày mượn
	
	@Column(name = "condition_borrow", insertable = true)
	@NotNull
	private BookConditionEnum conditionBorrow ;// tình trạng sách lúc mượn

	@Column(name = "borrow_end_compulsory", insertable = true)
	@NotNull
	private String borrowEndCompulsory; // ngày phải trả

	@Column(name = "borrow_end", insertable = true)
	private String borrowEnd; // ngày trả
	
	@Column(name = "condition_return", insertable = true)
	private BookConditionEnum conditionReturn ;// tình trạng sách lúc trả

	@Column(name = "status", insertable = true)
	@NotNull
	private BorrowStatusEnum status;
}

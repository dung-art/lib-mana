package com.lib.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "BookInfo")
@Table(name = "lb_book_info")
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "book_name", insertable = true)
	@NotNull
	private String bookName;

	@Column(name = "category", insertable = true)
	@NotNull
	private String category;

	@Column(name = "auth", insertable = true)
	@NotNull
	private String auth;

	@Column(name = "price", insertable = true)
	@NotNull
	private Float price;

	@Column(name = "book_image", insertable = true)
	@NotNull
	private String bookImage;
// nha xuat ban
	@Column(name = "publisher", insertable = true)
	@NotNull
	private String publisher;

	@Column(name = "description")
	private String description;
}

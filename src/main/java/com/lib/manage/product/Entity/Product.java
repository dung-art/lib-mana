package com.lib.manage.product.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.lib.manage.common.Entity.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JGlobalMap(excluded = { "MAXSIZE_CODE", "MINSIZE_CODE", "MINSIZE", "MINSIZE_DESCRIPTION", "MAXSIZE_DESCRIPTION",
		"isDelete", "userAdd", "dateAdd", "modifiedUser", "modifiedAction", "modifiedDate","MINSIZE_ID","MAXSIZE_ID","id" })
@Getter
@Setter
@Entity(name = "Products")
@Table(name = "QLBH_product")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int MAXSIZE_CODE = 36;
	private final static int MINSIZE_CODE = 10;
	private final static int MINSIZE = 1;
	private final static int MINSIZE_DESCRIPTION = 50;
	private final static int MAXSIZE_DESCRIPTION = 6000;

	@Column(name = "Product_Code", nullable = false, insertable = true)
	@NotNull
	@Size(min = MINSIZE_CODE, max = MAXSIZE_CODE)
	private String productCode;

	@Column(name = "Product_Name", insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String productName;

	@Column(name = "Product_Type", insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String productType;

	@Column(name = "Producer", insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String producer;

	@Column(name = "Price", insertable = true)
	@NotNull
	private Float price;

	@Column(name = "Path_Image", insertable = true)
	@NotNull
	@Size(min = MINSIZE)
	private String pathImage;

	@Column(name = "Is_Material", insertable = true)
	@NotNull
	private Boolean isMaterial;
	// g
	@Column(name = "Weight", insertable = true)
	@NotNull
	private String weight;
	// mm length x width
	@Column(name = "Size", insertable = true)
	@NotNull
	private String size;

	@Size(min = MINSIZE_DESCRIPTION, max = MAXSIZE_DESCRIPTION)
	@Column(name = "description")
	private String description;

	@PrePersist
	public void onIsMaterial() {
		if (!this.isMaterial) {
			this.weight = "0";
			this.size = "0";
		}
	}
}

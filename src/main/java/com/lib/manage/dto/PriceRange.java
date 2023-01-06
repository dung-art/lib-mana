package com.lib.manage.dto;

import com.lib.manage.validator.FieldLessThan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldLessThan(first = "from", second = "to")
public class PriceRange {
	private Float from;
	private Float to;
}

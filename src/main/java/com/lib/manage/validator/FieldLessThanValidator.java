package com.lib.manage.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

public class FieldLessThanValidator implements ConstraintValidator<FieldLessThan, Object> {
	private String firstFieldName;
	private String secondFieldName;

	public void initialize(final FieldLessThan constraintAnnotation) {
		this.firstFieldName = constraintAnnotation.first();
		this.secondFieldName = constraintAnnotation.second();
	}

	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Object firstObj = PropertyUtils.getProperty(value, this.firstFieldName);
			final Object secondObj = PropertyUtils.getProperty(value, this.secondFieldName);
			if (firstObj == null || secondObj == null) {
				return true;
			} else if (firstObj.getClass() != secondObj.getClass()) {
				return false;
			} else {
				if (firstObj instanceof LocalDateTime) {
					LocalDateTime c1 = (LocalDateTime) firstObj;
					LocalDateTime c2 = (LocalDateTime) secondObj;
					return c2.compareTo(c1) >= 0;
				} else if (firstObj instanceof Long || firstObj instanceof Integer) {
					Long c1 = (Long) firstObj;
					Long c2 = (Long) secondObj;
					return c2.compareTo(c1) >= 0;
				} else if (firstObj instanceof Double || firstObj instanceof Float) {
					Double c1 = (Double) firstObj;
					Double c2 = (Double) secondObj;
					return c2.compareTo(c1) >= 0;
				} else if (firstObj instanceof LocalDate) {
					LocalDate c1 = (LocalDate) firstObj;
					LocalDate c2 = (LocalDate) secondObj;
					return c2.compareTo(c1) >= 0;
				} else if (firstObj instanceof LocalTime) {
					LocalTime c1 = (LocalTime) firstObj;
					LocalTime c2 = (LocalTime) secondObj;
					return c2.compareTo(c1) >= 0;
				} else {
					return false;
				}
			}
		} catch (final Exception ex) {
			return false;
		}
	}
}

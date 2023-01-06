package com.lib.manage.dto;
import java.time.LocalDateTime;

import com.lib.manage.validator.FieldLessThan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldLessThan(first = "fromValue", second = "toValue")
public class DateRange {
  LocalDateTime fromValue;
  LocalDateTime toValue;
}

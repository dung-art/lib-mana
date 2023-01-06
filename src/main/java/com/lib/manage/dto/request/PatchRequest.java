package com.lib.manage.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lib.manage.validator.FieldExists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldExists(dataFieldName = "data", listFieldName = "updateFields")
public class PatchRequest<T> {
  @NotNull
  T data;

  @NotNull
  @Size(min = 1)
  List<String> updateFields;
}

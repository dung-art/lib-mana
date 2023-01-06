package com.lib.manage.dto.request.account;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchAccountRequest {
  String accountName;
  List<String> ids;
  String accountType;
  Boolean isActive;
}

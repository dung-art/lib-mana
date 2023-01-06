package com.lib.manage.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lib.manage.constant.Constants;
import com.lib.manage.constant.TokenTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Token")
@Table(name = "security_token")
@ToString(includeFieldNames = true, callSuper = true)
public class Token extends EntityBase {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static final int VALUE_MAX_LENGTH = 1024;
  private static final int VALUE_MIN_LENGTH = 32;
  @NotNull
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "token_type")
  private TokenTypeEnum tokenType;

  @NotNull
  @Size(max = VALUE_MAX_LENGTH, min = VALUE_MIN_LENGTH)
  @Column(name = "value", nullable = false, length = VALUE_MAX_LENGTH)
  private String value;

  @NotNull
  @Column(name = "affect_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime affectDate;

  @NotNull
  @Column(name = "expire_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime expireDate;

  @NotNull
  @Column(name = "account_id", nullable = false, length = Constants.ID_MAX_LENGTH)
  @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
  private String accountId;

  @JsonIgnore
  @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
      CascadeType.REFRESH }, targetEntity = Account.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", insertable = false, updatable = false, referencedColumnName = "id")
  private Account account;
}

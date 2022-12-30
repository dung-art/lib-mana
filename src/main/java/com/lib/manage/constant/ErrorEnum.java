package com.lib.manage.constant;
import lombok.Getter;

public enum ErrorEnum {
// @formatter:off
	VALIDATION_ERROR("1000", "validation-error"),
	DUPPLICATE_ENTITY("1001", "dupplicate-entity"),
	
	
	VALUE_NOT_CORRECT("1002", "the.passed.value.is.not.correct"),
	PASSWORD_INVALID("1003", "password.invalid"),
	NOT_FOUND("1004", "not.found")
	;
// @formatter:on

    @Getter
    private final String messageId;
    @Getter
    private final String code;

    private ErrorEnum(String code, String messageId) {
        this.messageId = messageId;
        this.code = code;
    }
}
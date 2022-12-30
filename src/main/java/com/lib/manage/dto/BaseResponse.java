package com.lib.manage.dto;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class BaseResponse {
    private int status;
    private long processDuration;
    private Date responseTime;
    @JsonInclude(Include.NON_NULL)
    private Date clientTime;
    private String clientMessageId;
}
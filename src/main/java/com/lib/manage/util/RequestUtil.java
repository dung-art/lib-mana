package com.lib.manage.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.lib.manage.dto.BaseResponse;
import com.lib.manage.dto.ErrorResponse;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.models.account.RequestContext;
import com.lib.manage.models.account.RequestContextHolder;



@Component
public class RequestUtil {
	public static <T> SuccessResponse<T> ok(T data) {
		SuccessResponse<T> ret = new SuccessResponse<T>(data);
		getResponseData(ret, 1);
		return ret;
	}

	public static ErrorResponse err(String errorCode, String errorMessage) {
		ErrorResponse ret = new ErrorResponse(errorCode, errorMessage);
		getResponseData(ret, 0);
		return ret;
	}

	private static void getResponseData(BaseResponse ret, int status) {
		Date responseTime = new Date();
		RequestContext ctx = RequestContextHolder.get();
		ret.setStatus(status);
		ret.setResponseTime(responseTime);
		ret.setProcessDuration(responseTime.getTime() - ctx.getReceivedTime());
		ret.setClientMessageId(ctx.getClientMessageId());
		ret.setClientTime(ctx.getClientTime());
	}
}

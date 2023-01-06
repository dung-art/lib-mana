package com.lib.manage.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.service.HomeService;
import com.lib.manage.util.RequestUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lbm/v1/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@ApiOperation(value = "API xem thống kê")
	@GetMapping("/dasboard")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> dasboard(LocalDateTime issueDate,LocalDateTime expiryDate) throws Exception {
		return RequestUtil.ok(homeService.getStatistics(issueDate,expiryDate));
	}
	
}

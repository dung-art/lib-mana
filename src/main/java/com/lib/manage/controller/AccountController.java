package com.lib.manage.controller;
import java.util.Optional;

import javax.validation.Valid;

import org.smartstruct.act.core.util.RequestUtil;
import org.smartstruct.act.model.dto.SuccessResponse;
import org.smartstruct.act.model.dto.request.LoginRequest;
import org.smartstruct.act.model.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.entity.Account;
import com.lib.manage.entity.User;
import com.lib.manage.service.AccountService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/lbm/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;



    @ApiOperation(value = "API đăng nhập")
    @GetMapping("/get-all")
    @ResponseBody
    public ResponseEntity<Iterable<User>> findAll() {
        Iterable<Account> users = accountService.findAllAccount();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable Long id) {
        Optional<User> userOptional = accountService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional, HttpStatus.OK);
    }

    @GetMapping("/lockOrUnlock/{id}")
    public ResponseEntity<Optional<User>> lockOrUnlockUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userOptional.get().setActive(!userOptional.get().isActive());
        userService.save(userOptional.get());
        return new ResponseEntity<>(userOptional, HttpStatus.OK);
    }

}
package com.lib.manage.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.manage.config.TokenService;
import com.lib.manage.constant.TokenTypeEnum;
import com.lib.manage.entity.Token;
import com.lib.manage.repository.TokenRepo;
@Service
public class TokenServiceImpl implements TokenService{
	  @Autowired
	  private TokenRepo tokenRepo;

	  @Override
	  public void saveToken(String accountId, TokenTypeEnum tokenType, String value, LocalDateTime affectDate,
	      LocalDateTime expireDate) {
	    Token t = new Token();
	    t.setIsDisable(false);
	    t.setAffectDate(affectDate);
	    t.setExpireDate(expireDate);
	    t.setTokenType(tokenType);
	    t.setAccountId(accountId);
	    t.setValue(value);
	    tokenRepo.save(t);
	  }

	  @Override
	  public boolean isTokenValid(String accountId, String value) {
	    Optional<Token> t = tokenRepo.findByAccountIdAndValue(accountId, value);
	    if (t.isPresent()) {
	      Token token = t.get();
	      LocalDateTime now = LocalDateTime.now();
	      return now.isAfter(token.getAffectDate()) && now.isBefore(token.getExpireDate());
	    }
	    return false;
	  }
	  @Override
	  public Boolean revokeToken(String accountId) {
		    tokenRepo.removeToken(accountId);
		    return true;
		  }

	
}

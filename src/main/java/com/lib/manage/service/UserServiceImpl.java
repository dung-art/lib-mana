package com.lib.manage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.lib.manage.constant.ErrorEnum;
import com.lib.manage.dto.UserInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.user.CreateUserRequest;
import com.lib.manage.dto.request.user.SearchUserInfoRequest;
import com.lib.manage.dto.request.user.UpdateUserInfoRequest;
import com.lib.manage.entity.UserInfo;
import com.lib.manage.exception.LBRException;
import com.lib.manage.repository.UserInfoRepository;
import com.lib.manage.util.SearchUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfoDto findById(String id) throws LBRException {
		Optional<UserInfo> oUser = userInfoRepository.findById(id);
		if (!oUser.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				UserInfo b = oUser.get();
				UserInfoDto dto = new UserInfoDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Page<UserInfoDto> findAll(Pageable pageable) {
		Page<UserInfo> pBI = userInfoRepository.findAll(pageable);
		List<UserInfo> ls = pBI.getContent();
		List<UserInfoDto> dtos = new ArrayList<>();
		for (UserInfo bi : ls) {
			UserInfoDto bookDto = new UserInfoDto();
			try {
				PropertyUtils.copyProperties(bookDto, bi);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			dtos.add(bookDto);
		}
		return new PageImpl<UserInfoDto>(dtos, pageable, pBI.getTotalElements());

	}

	@Override
	public String delete(List<String> ids) throws LBRException {
		findById(ids.get(0));
		userInfoRepository.deleteById(ids.get(0));
		return "Delete Success!";
	}

	@Override
	public UserInfoDto create(@Valid CreateUserRequest request) {
		try {
			UserInfo u = new UserInfo();
			PropertyUtils.copyProperties(u, request);
			UserInfo b = userInfoRepository.save(u);
			UserInfoDto dto = new UserInfoDto();
			PropertyUtils.copyProperties(dto, b);
			return dto;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserInfoDto update(String id, @Valid PatchRequest<UpdateUserInfoRequest> request) throws LBRException {
		Optional<UserInfo> oUser = userInfoRepository.findById(id);
		if (!oUser.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				UserInfo u = oUser.get();
				for (String fieldName : request.getUpdateFields()) {
					Object newValue = PropertyUtils.getProperty(request.getData(), fieldName);
					// set value of the bean
					PropertyUtils.setProperty(u, fieldName, newValue);
				}
				UserInfo b = userInfoRepository.save(u);
				UserInfoDto dto = new UserInfoDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Page<UserInfoDto> advanceSearch(@Valid SearchUserInfoRequest searchRequest, Pageable pageable) {
		if (searchRequest != null) {
			List<Specification<UserInfo>> specList = getAdvanceSearchSpecList(searchRequest);
			if (specList.size() > 0) {
				Specification<UserInfo> spec = specList.get(0);
				for (int i = 1; i < specList.size(); i++) {
					spec = spec.and(specList.get(i));
				}
				Page<UserInfo> pBI = userInfoRepository.findAll(spec, pageable);
				List<UserInfo> ls = pBI.getContent();
				List<UserInfoDto> dtos = new ArrayList<>();
				for (UserInfo bi : ls) {
					UserInfoDto bookInfoDto = new UserInfoDto();
					try {
						PropertyUtils.copyProperties(bookInfoDto, bi);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					dtos.add(bookInfoDto);
				}
				return new PageImpl<UserInfoDto>(dtos, pageable, pBI.getTotalElements());
			}
			return new PageImpl<>(null);
		}
		return findAll(pageable);
	}

	private List<Specification<UserInfo>> getAdvanceSearchSpecList(@Valid SearchUserInfoRequest s) {
		List<Specification<UserInfo>> specList = new ArrayList<>();
		// base
		if (s.getCreateUser() != null && s.getCreateUser().size() > 0) {
			specList.add(SearchUtil.in("createUser", s.getCreateUser()));
		}
		if (s.getModifiedUser() != null && s.getModifiedUser().size() > 0) {
			specList.add(SearchUtil.in("modifiedUser", s.getModifiedUser()));
		}
		if (s.getCreateDate() != null) {
			if (s.getCreateDate().getFromValue() != null) {
				specList.add(SearchUtil.ge("createdDate", s.getCreateDate().getFromValue()));
			}
			if (s.getCreateDate().getFromValue() != null) {
				specList.add(SearchUtil.lt("createdDate", s.getCreateDate().getToValue()));
			}
		}
		if (s.getModifyDate() != null) {
			if (s.getModifyDate().getFromValue() != null) {
				specList.add(SearchUtil.ge("modifyDate", s.getModifyDate().getFromValue()));
			}
			if (s.getModifyDate().getFromValue() != null) {
				specList.add(SearchUtil.lt("modifyDate", s.getModifyDate().getToValue()));
			}
		}
		// advance
		if (s.getUserName() != null && !s.getUserName().isEmpty()) {
			specList.add(SearchUtil.like("userName", "%" + s.getUserName() + "%"));
		}
		if (s.getUserCode() != null && !s.getUserCode().isEmpty()) {
			specList.add(SearchUtil.like("userCode", "%" + s.getUserCode() + "%"));
		}
		if (s.getBirthDate() != null) {
			if (s.getBirthDate().getFromValue() != null) {
				specList.add(SearchUtil.ge("birthDate", s.getBirthDate().getFromValue()));
			}
			if (s.getBirthDate().getFromValue() != null) {
				specList.add(SearchUtil.lt("birthDate", s.getBirthDate().getToValue()));
			}
		}
		if (s.getGender() != null && s.getGender().size() > 0) {
			specList.add(SearchUtil.in("gender", s.getGender()));
		}
		if (s.getPhoneNumber() != null && s.getPhoneNumber().isEmpty()) {
			specList.add(SearchUtil.like("phoneNumber", "%" + s.getPhoneNumber() + "%"));
		}
		if (s.getEmailAddress() != null && !s.getEmailAddress().isEmpty()) {
			specList.add(SearchUtil.like("emailAddress", "%" + s.getEmailAddress() + "%"));
		}
		if (s.getStatus() != null && s.getStatus().size() > 0) {
			specList.add(SearchUtil.in("status", s.getStatus()));
		}
		if (s.getCurrentAddress() != null && !s.getCurrentAddress().isEmpty()) {
			specList.add(SearchUtil.like("currentAddress", "%" + s.getCurrentAddress() + "%"));
		}
		return specList;
	}

	@Override
	public Page<UserInfoDto> violate(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}

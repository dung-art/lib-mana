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
import com.lib.manage.dto.BookInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookInfoRequest;
import com.lib.manage.dto.request.book.CreateBookInfoRequest;
import com.lib.manage.dto.request.book.UpdateBookRequest;
import com.lib.manage.entity.BookInfo;
import com.lib.manage.exception.LBRException;
import com.lib.manage.repository.BookInfoRepository;
import com.lib.manage.repository.BookRepository;
import com.lib.manage.util.SearchUtil;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookInfoRepository bookInfoRepository;
	@Autowired
	private BookRepository bookRepository;

	@Override
	public BookInfoDto create(CreateBookInfoRequest request) {
		try {
			BookInfo u = new BookInfo();
			PropertyUtils.copyProperties(u, request);
			BookInfo b = bookInfoRepository.save(u);
			BookInfoDto dto = new BookInfoDto();
			PropertyUtils.copyProperties(dto, b);
			return dto;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookInfoDto update(String id, PatchRequest<UpdateBookRequest> patchRequest) throws LBRException {
		Optional<BookInfo> oBook = bookInfoRepository.findById(id);
		if (!oBook.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				BookInfo u = oBook.get();
				for (String fieldName : patchRequest.getUpdateFields()) {
					Object newValue = PropertyUtils.getProperty(patchRequest.getData(), fieldName);
					// set value of the bean
					PropertyUtils.setProperty(u, fieldName, newValue);
				}
				BookInfo b = bookInfoRepository.save(u);
				BookInfoDto dto = new BookInfoDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String delete(String id) throws LBRException {
		findById(id);
		bookRepository.deleteByBookInfoId(id);
		bookInfoRepository.deleteById(id);
		return "Delete Success!";
	}

	@Override
	public Page<BookInfoDto> findAll(Pageable pageable) {
		Page<BookInfo> pBI = bookInfoRepository.findAll(pageable);
		List<BookInfo> ls = pBI.getContent();
		List<BookInfoDto> dtos = new ArrayList<>();
		for (BookInfo bi : ls) {
			BookInfoDto bookDto = new BookInfoDto();
			try {
				PropertyUtils.copyProperties(bookDto, bi);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			dtos.add(bookDto);
		}
		return new PageImpl<BookInfoDto>(dtos, pageable, pBI.getTotalElements());
	}

	@Override
	public BookInfoDto findById(String id) throws LBRException {
		Optional<BookInfo> oBook = bookInfoRepository.findById(id);
		if (!oBook.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				BookInfo b = oBook.get();
				BookInfoDto dto = new BookInfoDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Page<BookInfoDto> advanceSearch(@Valid AdvanceSearchBookInfoRequest searchRequest, Pageable pageable) {
		if (searchRequest != null) {
			List<Specification<BookInfo>> specList = getAdvanceSearchSpecList(searchRequest);
			if (specList.size() > 0) {
				Specification<BookInfo> spec = specList.get(0);
				for (int i = 1; i < specList.size(); i++) {
					spec = spec.and(specList.get(i));
				}
				Page<BookInfo> pBI = bookInfoRepository.findAll(spec, pageable);
				List<BookInfo> ls = pBI.getContent();
				List<BookInfoDto> dtos = new ArrayList<>();
				for (BookInfo bi : ls) {
					BookInfoDto bookInfoDto = new BookInfoDto();
					try {
						PropertyUtils.copyProperties(bookInfoDto, bi);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					dtos.add(bookInfoDto);
				}
				return new PageImpl<BookInfoDto>(dtos, pageable, pBI.getTotalElements());
			}
			return new PageImpl<>(null);
		}
		return findAll(pageable);
	}

	private List<Specification<BookInfo>> getAdvanceSearchSpecList(@Valid AdvanceSearchBookInfoRequest s) {
		List<Specification<BookInfo>> specList = new ArrayList<>();
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
		if (s.getBookName() != null && !s.getBookName().isEmpty()) {
			specList.add(SearchUtil.like("bookName", "%" + s.getBookName() + "%"));
		}
		if (s.getCategorys() != null && s.getCategorys().size() > 0) {
			specList.add(SearchUtil.in("category", s.getCategorys()));
		}
		if (s.getAuths() != null && s.getAuths().size() > 0) {
			specList.add(SearchUtil.in("auth", s.getAuths()));
		}
		if (s.getPublisher() != null && s.getPublisher().size() > 0) {
			specList.add(SearchUtil.in("publisher", s.getPublisher()));
		}
		if (s.getPrice() != null) {
			if (s.getCreateDate().getFromValue() != null) {
				specList.add(SearchUtil.ge("price", s.getPrice().getFrom()));
			}
			if (s.getCreateDate().getFromValue() != null) {
				specList.add(SearchUtil.lt("price", s.getPrice().getTo()));
			}
		}
		return specList;
	}

	@Override
	public Page<BookInfoDto> viewNewBook(Pageable pageable) {
		AdvanceSearchBookInfoRequest search = new AdvanceSearchBookInfoRequest();
//		advanceSearch()
		return null;
	}
}

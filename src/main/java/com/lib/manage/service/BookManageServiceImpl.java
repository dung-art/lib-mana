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
import com.lib.manage.dto.BookDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookRequest;
import com.lib.manage.dto.request.book.CreateBookRequest;
import com.lib.manage.dto.request.book.UpdateBookRequest;
import com.lib.manage.entity.Book;
import com.lib.manage.exception.LBRException;
import com.lib.manage.repository.BookRepository;
import com.lib.manage.util.SearchUtil;

@Service
public class BookManageServiceImpl implements BookManageService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public BookDto create(CreateBookRequest request) {
		try {
			Book u = new Book();
			PropertyUtils.copyProperties(u, request);
			Book b = bookRepository.save(u);
			BookDto dto = new BookDto();
			PropertyUtils.copyProperties(dto, b);
			return dto;
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookDto update(String id, PatchRequest<UpdateBookRequest> patchRequest) throws LBRException {
		Optional<Book> oBook = bookRepository.findById(id);
		if (!oBook.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				Book u = oBook.get();
				for (String fieldName : patchRequest.getUpdateFields()) {
					Object newValue = PropertyUtils.getProperty(patchRequest.getData(), fieldName);
					// set value of the bean
					PropertyUtils.setProperty(u, fieldName, newValue);
				}
				Book b = bookRepository.save(u);
				BookDto dto = new BookDto();
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
		bookRepository.deleteById(id);
		return "Delete Success!";
	}

	@Override
	public BookDto findById(String id) throws LBRException {
		Optional<Book> oBook = bookRepository.findById(id);
		if (!oBook.isPresent()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, null);
		} else {
			try {
				Book b = oBook.get();
				BookDto dto = new BookDto();
				PropertyUtils.copyProperties(dto, b);
				return dto;
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Page<BookDto> findAll(Pageable pageable) {
		 Page<Book> pB = bookRepository.findAll(pageable);
		 List<Book> ls = pB.getContent();
		 List<BookDto> dtos = new ArrayList<>();
			for (Book bi : ls) {
				BookDto bookDto = new BookDto();
				try {
					PropertyUtils.copyProperties(bookDto, bi);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				dtos.add(bookDto);
			}
			return new PageImpl<BookDto>(dtos, pageable, pB.getTotalElements());
	}

	@Override
	public Page<BookDto> advanceSearch(@Valid AdvanceSearchBookRequest searchRequest, Pageable pageable) {
		if (searchRequest != null) {
			List<Specification<Book>> specList = getAdvanceSearchSpecList(searchRequest);
			if (specList.size() > 0) {
				Specification<Book> spec = specList.get(0);
				for (int i = 1; i < specList.size(); i++) {
					spec = spec.and(specList.get(i));
				}
				Page<Book> pBI = bookRepository.findAll(spec, pageable);
				List<Book> ls = pBI.getContent();
				List<BookDto> dtos = new ArrayList<>();
				for (Book bi : ls) {
					BookDto bookInfoDto = new BookDto();
					try {
						PropertyUtils.copyProperties(bookInfoDto, bi);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						e.printStackTrace();
					}
					dtos.add(bookInfoDto);
				}
				return new PageImpl<BookDto>(dtos, pageable, pBI.getTotalElements());
			}
			return new PageImpl<>(null);
		}
		return findAll(pageable);
	}
	
	private List<Specification<Book>> getAdvanceSearchSpecList(@Valid AdvanceSearchBookRequest s) {
		List<Specification<Book>> specList = new ArrayList<>();
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
		if (s.getBookCondition() != null && s.getBookCondition().size() > 0) {
			specList.add(SearchUtil.in("bookCondition", s.getBookCondition()));
		}
		if (s.getStatus() != null && s.getStatus().size() > 0) {
			specList.add(SearchUtil.in("status", s.getStatus()));
		}
		return specList;
	}
	
}

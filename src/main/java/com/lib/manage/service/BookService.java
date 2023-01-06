package com.lib.manage.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lib.manage.dto.BookInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookInfoRequest;
import com.lib.manage.dto.request.book.CreateBookInfoRequest;
import com.lib.manage.dto.request.book.UpdateBookRequest;
import com.lib.manage.exception.LBRException;

public interface BookService {
	Page<BookInfoDto> findAll(Pageable pageable);

	BookInfoDto findById(String id) throws LBRException;

	Page<BookInfoDto> advanceSearch(@Valid AdvanceSearchBookInfoRequest searchRequest, Pageable pageable);

	Page<BookInfoDto> viewNewBook(Pageable pageable);

	BookInfoDto create(CreateBookInfoRequest request);

	BookInfoDto update(String id, PatchRequest<UpdateBookRequest> patchRequest) throws LBRException;

	String delete(String id) throws LBRException;
}

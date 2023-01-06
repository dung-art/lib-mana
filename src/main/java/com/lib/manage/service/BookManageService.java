package com.lib.manage.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lib.manage.dto.BookDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookRequest;
import com.lib.manage.dto.request.book.CreateBookRequest;
import com.lib.manage.dto.request.book.UpdateBookRequest;
import com.lib.manage.exception.LBRException;

public interface BookManageService {

	BookDto create(CreateBookRequest request);

	BookDto update(String id, PatchRequest<UpdateBookRequest> request) throws LBRException;

	Page<BookDto> findAll(Pageable pageable);

	BookDto findById(String id) throws LBRException;

	String delete(String id) throws LBRException;

	Page<BookDto> advanceSearch(@Valid AdvanceSearchBookRequest searchRequest, Pageable pageable);

}

package com.lib.manage.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lib.manage.dto.BaseResponse;

public class PageResponse<T> extends BaseResponse {
	 private Page<T> page;

	  public PageResponse(Page<T> page) {
	    this.page = page;
	  }

	  public int getNumber() {
	    return page.getNumber();
	  }

	  public int getSize() {
	    return page.getSize();
	  }

	  public int getNumberOfElements() {
	    return page.getNumberOfElements();
	  }

	  public List<T> getContent() {
	    return page.getContent();
	  }

	  public boolean hasContent() {
	    return page.hasContent();
	  }

	  public boolean isFirst() {
	    return page.isFirst();
	  }

	  public boolean isLast() {
	    return page.isLast();
	  }

	  public boolean hasNext() {
	    return page.hasNext();
	  }

	  public boolean hasPrevious() {
	    return page.hasPrevious();
	  }

	  public int getTotalPages() {
	    return page.getTotalPages();
	  }

	  public long getTotalElements() {
	    return page.getTotalElements();
	  }
}

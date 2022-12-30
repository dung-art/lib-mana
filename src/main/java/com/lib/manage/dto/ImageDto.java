package com.lib.manage.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JGlobalMap
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
	private String id;
	private String imageName;
	private byte[] imageData;
}

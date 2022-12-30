package com.lib.manage.dto.request;

import com.googlecode.jmapper.annotations.JMapConversion;
import com.lib.manage.validator.Convert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateImageRequest {
	private String imageCode;
	private String imageName;
	private String imageData;

	@JMapConversion(from = { "imageData" }, to = { "imageData" })
	public byte[] conversion(String pathImage) throws Exception {
		try {
			return Convert.ConvertImagetoByte(pathImage);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

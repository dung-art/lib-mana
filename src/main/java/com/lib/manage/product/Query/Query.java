package com.lib.manage.product.Query;

import com.lib.manage.product.Dto.AdvanceSearchProductRequest;

public class Query {
	public static String QueryLine(AdvanceSearchProductRequest request) {

		String queryString = "select * from QLBH_product ql  ";

		Boolean searchWay = true;
		if (request.getSearchWay() != null) {
			searchWay = request.getSearchWay();
		}

		String name = "";

		String productType = "";

		String producer = "";

		String price = "";

		String price1 = "";

		String isMaterial = "";

		if (!request.getName().isEmpty() && request.getName() != null) {
			name = " " + " ql.product_name like " + " " + "'" + "%" + request.getName() + "%" + "'" + " ";
		}
		if (request.getIsMaterial() != null) {
			isMaterial = " " + "ql.is_material = " + String.valueOf(request.getIsMaterial()) + " ";
		}
		if (!request.getProductType().isEmpty() && request.getProductType() != null) {
			String tp = null;
			int i = 0;
			for (String string : request.getProductType()) {
				i++;
				if (i == 1) {
					tp = "'" + string + "'";
				}
				if (i > 1) {
					tp = tp + "," + "'" + string + "'";
				}
			}
			String st = "(" + tp + ")";
			productType = " " + " ql.product_type in " + st + " ";
		}
		if (!request.getProducer().isEmpty() && request.getProducer() != null) {
			String tp = null;
			int i = 0;
			for (String string : request.getProducer()) {
				i++;
				if (i == 1) {
					tp = "'" + string + "'";
				}
				if (i > 1) {
					tp = tp + "," + "'" + string + "'";
				}
			}
			String st = "(" + tp + ")";
			producer = " " + " ql.producer in " + st + " ";
		}
		if (request.getPriceUp() != null) {
			price = " " + " ql.price < " + " " + request.getPriceUp() + " ";
		}
		if (request.getPriceDown() != null) {
			price1 = " " + " ql.price > " + " " + request.getPriceDown() + " ";
		}
		if ((request.getName().isEmpty() || request.getName() == null) && (request.getIsMaterial() == null)
				&& (request.getProductType().isEmpty() || request.getProductType() == null)
				&& (request.getProducer().isEmpty() || request.getProducer() == null) && (request.getPriceUp() == null)
				&& (request.getPriceDown() == null)) {
			String xString = "select * from QLBH_product ql";
			return xString;
		}

		if (searchWay) {
			if (!name.isEmpty() && (!isMaterial.isEmpty() || !productType.isEmpty() || !producer.isEmpty()
					|| !price.isEmpty() || !price1.isEmpty())) {
				name = name + " " + "and" + " ";
			}
			if (!isMaterial.isEmpty()
					&& (!productType.isEmpty() || !producer.isEmpty() || !price.isEmpty() || !price1.isEmpty())) {
				isMaterial = isMaterial + " " + "and" + " ";
			}
			if (!productType.isEmpty() && (!producer.isEmpty() || !price.isEmpty() || !price1.isEmpty())) {
				productType = productType + " " + "and" + " ";
			}
			if (!producer.isEmpty() && (!price.isEmpty() || !price1.isEmpty())) {
				producer = producer + " " + "and" + " ";
			}
			if (!price.isEmpty() && !price1.isEmpty()) {
				price = price + " " + "and" + " ";
			}
			// COMMON_SEARCH;
			queryString = queryString + " where " + name + isMaterial + productType + producer + price + price1;
		} else {
			if (!name.isEmpty() && (!isMaterial.isEmpty() || !productType.isEmpty() || !producer.isEmpty()
					|| !price.isEmpty() || !price1.isEmpty())) {
				name = name + " " + "or" + " ";
			}
			if (!isMaterial.isEmpty()
					&& (!productType.isEmpty() || !producer.isEmpty() || !price.isEmpty() || !price1.isEmpty())) {
				isMaterial = isMaterial + " " + "or" + " ";
			}
			if (!productType.isEmpty() && (!producer.isEmpty() || !price.isEmpty() || !price1.isEmpty())) {
				productType = productType + " " + "or" + " ";
			}
			if (!producer.isEmpty() && (!price.isEmpty() || !price1.isEmpty())) {
				producer = producer + " " + "or" + " ";
			}
			if (!price.isEmpty() && !price1.isEmpty()) {
				price = price + " " + "or" + " ";
			}
			queryString = queryString + " where " + name + isMaterial + productType + producer + price + price1;
		}
		return queryString;
	}
}

//package com.lib.manage.map;
//
//import com.lib.manage.dto.request.UpdateCustomUserRequest;
//import com.lib.manage.dto.request.UpdatePartnerUserRequest;
//import com.lib.manage.dto.request.UpdateSystemUserRequest;
//import com.lib.manage.entity.CustomUser;
//import com.lib.manage.entity.PartnerUser;
//import com.lib.manage.entity.SystemUser;
//import com.lib.manage.validator.Convert;
//
//public class MapUser {
//
//	public static SystemUser getSystemUserFromUpdateUSer(UpdateSystemUserRequest request, SystemUser systemUser) {
//		if (request == null) {
//			return null;
//		}
//		if (request.getIdentifyNumber() != null) {
//			systemUser.setIdentifyNumber(request.getIdentifyNumber());
//		}
//		if (request.getPathImage() != null) {
//			systemUser.setPathImage(request.getPathImage());
//		}
//		if (request.getGroupUser() != null) {
//			systemUser.setGroupUser(request.getGroupUser());
//		}
//		if (request.getRoleOriginSystemUser() != null) {
//			systemUser.setRoleOriginSystemUser(request.getRoleOriginSystemUser());
//		}
//		if (request.getDescription() != null) {
//			systemUser.setDescription(request.getDescription());
//		}
//		return systemUser;
//	}
//
//	public static PartnerUser getPartnerUserFromUpdateUSer(UpdatePartnerUserRequest request, PartnerUser partnerUser) {
//		if (request == null) {
//			return null;
//		}
//		if (request.getIdentifyNumber() != null) {
//			partnerUser.setIdentifyNumber(request.getIdentifyNumber());
//		}
//		if (request.getPathImageUser() != null) {
//			partnerUser.setPathImageUser(request.getPathImageUser());
//		}
//		if (request.getGroupPartnerUser() != null) {
//			partnerUser.setGroupPartnerUser(request.getGroupPartnerUser());
//		}
//		if (request.getRoleOriginPartnerUser() != null) {
//			partnerUser.setRoleOriginPartnerUser(request.getRoleOriginPartnerUser());
//		}
//		if (request.getDescription() != null) {
//			partnerUser.setDescription(request.getDescription());
//		}
//		return partnerUser;
//	}
//
//	public static CustomUser getCustomUserFromUpdateUSer(UpdateCustomUserRequest request, CustomUser customUser) {
//		if (request == null) {
//			return null;
//		}
//		if (request.getIdentifyNumber() != null) {
//			customUser.setIdentifyNumber(request.getIdentifyNumber());
//		}
//		if (request.getPathImageUser() != null) {
//			customUser.setPathImageUser(request.getPathImageUser());
//		}
//		if (request.getFullName() != null) {
//			customUser.setFullName(request.getFullName());
//		}
//		if (request.getAddress() != null) {
//			customUser.setAddress(request.getAddress());
//		}
//		if (request.getDescription() != null) {
//			customUser.setDescription(request.getDescription());
//		}
//		if (request.getBirthDate() != null) {
//			try {
//				customUser.setBirthDate(Convert.convertStringDateTimeToLocalDateTime(request.getBirthDate()));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if (request.getDescription() != null) {
//			customUser.setDescription(request.getDescription());
//		}
//		return customUser;
//	}
//
//}

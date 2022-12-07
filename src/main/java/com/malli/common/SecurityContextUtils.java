package com.malli.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class SecurityContextUtils {
	public static String getUserId() throws Exception{
		String userId=null;
		try{
			userId = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		}
		catch (ClassCastException e) {
			userId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			userId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			throw e;
		}
		return userId;
	}
}


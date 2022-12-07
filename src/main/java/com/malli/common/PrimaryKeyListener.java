package com.malli.common;


import java.lang.reflect.Field;
import java.security.SecureRandom;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import com.malli.common.StringLogic;
import com.malli.model.BaseEntity;

public class PrimaryKeyListener {
	@PrePersist
	public void preUpdate(BaseEntity entity) {
		// private field access
		try {
			Field f = getFieildByAnnotation(entity);
//			Field f = entity.getClass().getDeclaredField("schoolSubjectsId");
			f.setAccessible(true);
			Object pkvalue = f.get(entity);
			String pk = pkvalue == null ? null : pkvalue.toString(); 
			if(!StringLogic.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(pk))
				f.set(entity,smartPKey());
			f.setAccessible(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//entity.setSchoolSubjectsId(new Date().getTime() + "");
	}
	
	static Field getFieildByAnnotation(BaseEntity entity) throws IllegalArgumentException, IllegalAccessException{
		for(Field f:entity.getClass().getDeclaredFields()){
		  /**
		   * Ensure the RetentionPolicy of 'Annotation' is RUNTIME.
		   */
		   if(f.isAnnotationPresent(Id.class)){
		   return f;
		  } 
		 }
		for(Field f:entity.getClass().getSuperclass().getDeclaredFields()){
			  /**
			   * Ensure the RetentionPolicy of 'Annotation' is RUNTIME.
			   */
			   if(f.isAnnotationPresent(Id.class)){
			   return f;
			  } 
			 }
		 return null;
		}
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();

	static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	
	public static String smartPKey(){
		
		long unixTime = System.currentTimeMillis() / 10L;
		String pkey = randomString(4)+Long.toHexString(unixTime).toUpperCase()+randomString(4);

		return pkey;
	}
}

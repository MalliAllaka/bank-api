package com.malli.common;

import java.util.Random;

public class StringLogic {
	
	public StringLogic(String astring) {
		super();
	}



	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJava(  
			   final String string)  
			{  
			   return string != null && !string.isEmpty() && !string.trim().isEmpty();  
			}  
	public static String generateNdigitAlphaNumericCode(int n) {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = n;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
		return generatedString.toLowerCase();
	}

}

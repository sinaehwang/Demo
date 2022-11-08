package com.hsn.exam.demo.util;

public class Ut {

	public static boolean empty(Object object) {


		if(object ==null) { //널인경우 공백이니까 참
			
			return true;
		}
		
		if(object instanceof String == false ) { //문자가아닌경우 참
			return true;
		}
		
		String str = (String)object;

		return str.trim().length()==0; //위에 두경우외라면 object길이는 0이니까 공백참
		
	}

	public static String f(String string, Object...args) { //여러개의 인자를 받아 문자로 치환해주는함수

		return String.format(string, args);
	}
	
	

}

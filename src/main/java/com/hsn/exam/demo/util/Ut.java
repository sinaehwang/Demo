package com.hsn.exam.demo.util;

import java.text.SimpleDateFormat;

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

	public static String getFileExtTypeCodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		switch (ext) {
		case "jpeg":
		case "jpg":
		case "gif":
		case "png":
			return "img";
		case "mp4":
		case "avi":
		case "mov":
			return "video";
		case "mp3":
			return "audio";
		}

		return "etc";
	}

	public static String getFileExtFromFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		String ext = fileName.substring(pos + 1);

		return ext;
	}

	public static String getFileExtType2CodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		switch (ext) {
		case "jpeg":
		case "jpg":
			return "jpg";
		case "gif":
			return ext;
		case "png":
			return ext;
		case "mp4":
			return ext;
		case "mov":
			return ext;
		case "avi":
			return ext;
		case "mp3":
			return ext;
		}

		return "etc";
	}

	public static String getNowYearMonthDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM");

		String dateStr = format1.format(System.currentTimeMillis());

		return dateStr;
	}
	
	

}

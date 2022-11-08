 package com.hsn.exam.demo.vo;

import lombok.Getter;

public class ResultData {

	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Object data1;

	private ResultData() {// 외부에서 접근불가

	}

	public static ResultData from(String resultCode, String msg, Object data1) {
		// 함수화로만듬, 코드랑 메세지랑 데이터를 하나로 조합해서 리턴해줌, static 메소드 new인스턴스하지않아도 호출가능

		ResultData rd = new ResultData(); //Result로 리턴타입맞추기위해서

		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;

		return rd;

	}
	
	public static ResultData from(String resultCode, String msg) { //인자가 코드와 메세지만 들어올경우 데이터값을 null로넣어 위에 함수를 실행한다.


		return ResultData.from(resultCode, msg, null);

	}
	
	public static ResultData newData(ResultData rd, Object newData) {//기존보고서에서 새로운 데이터로만 변경하고싶을때
		
		return ResultData.from(rd.getResultCode(),rd.getMsg(),newData);
	}
	
	
	public boolean isSucess() { //성공코드일때의 성공코드정의
		
		return resultCode.startsWith("S-");
		
	}

	public boolean isFail() {//실패코드일때의 실패코드정의
		return resultCode.startsWith("F-");
	}
}

package com.hsn.exam.demo.vo;

import java.util.Map;

import com.hsn.exam.demo.util.Ut;

import lombok.Getter;

public class ResultData {

	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Map<String, Object> body;
	@Getter
	private Object data1;
	@Getter
	private String data1Name;
	@Getter
	private Object data2;
	@Getter
	private String data2Name;

	private ResultData() {// 외부에서 접근불가

	}

	public ResultData(String resultCode, String msg, Object... args) {
		this.resultCode = resultCode;
		this.msg = msg;
		this.body = Ut.mapOf(args);
	}

	public static ResultData from(String resultCode, String msg, String data1Name, Object data1) {
		// 함수화로만듬, 코드랑 메세지랑 데이터를 하나로 조합해서 리턴해줌, static 메소드 new인스턴스하지않아도 호출가능

		ResultData rd = new ResultData(); // Result로 리턴타입맞추기위해서

		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;

		return rd;

	}

	public static ResultData from(String resultCode, String msg) { // 인자가 코드와 메세지만 들어올경우 데이터값을 null로넣어 위에 함수를 실행한다.

		return ResultData.from(resultCode, msg, null, null);

	}

	public static ResultData newData(ResultData rd, String data1Name, Object newData) {// 기존보고서에서 새로운 데이터로만 변경하고싶을때

		return ResultData.from(rd.getResultCode(), rd.getMsg(), data1Name, newData);
	}

	public boolean isSucess() { // 성공코드일때의 성공코드정의

		return resultCode.startsWith("S-");

	}

	public boolean isFail() {// 실패코드일때의 실패코드정의
		return resultCode.startsWith("F-");
	}

	public void setData2(String dataName, Object data) {

		data2Name = dataName;

		data2 = data;

	}

}

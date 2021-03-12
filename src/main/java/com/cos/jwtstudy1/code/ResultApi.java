package com.cos.jwtstudy1.code;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.jwtstudy1.reponse.CommonResult;
import com.cos.jwtstudy1.reponse.ListResult;
import com.cos.jwtstudy1.reponse.SingleResult;

@Service
public class ResultApi {
	
	public enum Common  {
		SUCCESS(1, "성공"),
		FAIL(-1, "실패");
		
		int code;
		String msg;
		
		Common(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public String getMsg() {
			return this.msg;
		}
	}
	
	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<T>();
		
		result.setData(data);
		setSuccessResult(result);
		
		return result;
	}
	
	public <T> ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<T>();
		result.setList(list);
		setSuccessResult(result);
		
		return result;
	}
	
	public void setSuccessResult(CommonResult result) {
		result.setCode(ResultApi.Common.SUCCESS.getCode());
		result.setMsg(ResultApi.Common.SUCCESS.getMsg());
		result.setSuccess(Boolean.TRUE);
	}
	
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		
		return result;
	}
	
	public CommonResult getFailResult() {
		CommonResult result = new CommonResult();
		result.setCode(ResultApi.Common.FAIL.getCode());
		result.setMsg(ResultApi.Common.FAIL.getMsg());
		result.setSuccess(Boolean.FALSE);
		return result;
	}
	
	public CommonResult getFailResult(int code, String msg) {
		CommonResult result = new CommonResult();
		result.setCode(code);
		result.setMsg(msg);
		result.setSuccess(Boolean.FALSE);
		return result;
	}
	
}

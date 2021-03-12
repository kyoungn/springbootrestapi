package com.cos.jwtstudy1.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.jwtstudy1.advice.exception.AccessDeniedException;
import com.cos.jwtstudy1.advice.exception.AuthenticationEntryPointException;
import com.cos.jwtstudy1.advice.exception.LoginUserNotFoundException;
import com.cos.jwtstudy1.advice.exception.UserNotFoundException;
import com.cos.jwtstudy1.code.ResultApi;
import com.cos.jwtstudy1.reponse.CommonResult;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice //예외발생을 json형식으로 전송
@RequiredArgsConstructor
public class ExceptionAdvice {
	private final ResultApi api;
	private final MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return api.getFailResult(Integer.valueOf(getMessage("unKnow.code")), getMessage("unKnow.msg"));
    }
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
		System.out.println(" msssage code ::: " + getMessage("userNotFound.code"));
		// 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
		return api.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
	}
	
	@ExceptionHandler(LoginUserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult loginUserNotFoundException(HttpServletRequest request, LoginUserNotFoundException e) {
		System.out.println(" msssage code ::: " + getMessage("loginUserNotFound.code"));
		// 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
		return api.getFailResult(Integer.valueOf(getMessage("loginUserNotFound.code")), getMessage("loginUserNotFound.msg"));
	}
	

	@ExceptionHandler(AuthenticationEntryPointException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult authenticationEntryPointException(HttpServletRequest request, AuthenticationEntryPointException e) {
		System.out.println(" msssage code ::: " + getMessage("entryPointException.code"));
		// 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
		return api.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		System.out.println(" msssage code ::: " + getMessage("entryPointException.code"));
		// 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
		return api.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
	}
	
	private String getMessage(String code) {
		return getMessage(code, null);
	}
	
	// code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회.
	private String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}

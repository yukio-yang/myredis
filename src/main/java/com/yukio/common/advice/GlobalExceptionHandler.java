package com.yukio.common.advice;

import com.yukio.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yukio
 * @create 2022-02-08 15:19
 * 全局异常步捕捉
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public R exception(HttpServletRequest req, Exception e) {
		log.error("---exception Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
		return R.error(HttpStatus.BAD_REQUEST, "系统错误，请联系网站管理员！");
	}

	@ExceptionHandler(value = RuntimeException.class)
	public R runtimeException(HttpServletRequest req, RuntimeException e) {
		log.error("---runtimeException Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
		return R.error(HttpStatus.BAD_REQUEST, e.getMessage());
	}

//	/**
//	 * 处理Shiro权限拦截异常
//	 */
//	@ExceptionHandler(value = {AuthorizationException.class, UnauthorizedException.class})
//	public Result<Object> authorizationException(HttpServletRequest req, UnauthorizedException e) {
//		log.error("---authorizationException Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
//		return R.error(HttpStatus.BAD_REQUEST, e.getMessage());
//	}
}

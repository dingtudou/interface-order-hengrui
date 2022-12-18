package com.cimc.order.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class BDExceptionHandler {

	/*@ExceptionHandler(Exception.class)
	public RL handleBDException(BDException e) {
		e.printStackTrace();
		RL r = new RL();
		r.put("status", 500);
		r.put("message", e.getMessage());
		return r;
	}*/
}

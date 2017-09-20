package com.scienjus.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=NoHandlerFoundException.class)  
    public String noHandlerFoundExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
        return "404";  
    }  
}
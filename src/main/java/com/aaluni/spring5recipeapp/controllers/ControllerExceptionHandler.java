package com.aaluni.spring5recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {
	
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception ex) {
    	log.debug("Handling NumberformatException:" + ex.getMessage());
    	
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("400error");
    	mv.addObject("exception", ex);
    	return mv;
    }

}

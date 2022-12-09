package com.malli.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.malli.common.CommonController;
import com.malli.model.ApplicationConstants;
import com.malli.service.ApplicationConstantsService;

@RestController
@CrossOrigin
@RequestMapping("/applicationconstants/")
public class ApplicationConstantsController extends CommonController{
	
	@Autowired
	private ApplicationConstantsService applicationConstantsService;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<ApplicationConstants> findAll()  throws Exception{
		List<ApplicationConstants> applicationConstants = new ArrayList<ApplicationConstants>();
		try {
			applicationConstants = applicationConstantsService.findAll();
		} catch (Exception e) {
			throw new Exception("failed", e);
		}
		return applicationConstants;

	}
	
	

}

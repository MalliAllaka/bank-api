package com.malli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.malli.dao.ApplicationConstantsDAO;
import com.malli.model.ApplicationConstants;
import com.malli.model.Customer;

@Service
public class ApplicationConstantsService {
	
	@Autowired
	private ApplicationConstantsDAO applicationConstantsDAO;

	public List<ApplicationConstants> findAll()  throws Exception {
		List<ApplicationConstants> applicationConstants = new ArrayList<ApplicationConstants>();
		try {
			 applicationConstants  = applicationConstantsDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return applicationConstants;
	} 

}

package com.malli.common;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.malli.model.AuditFields;



public class AuditFieldListener {
	private static final Log log = LogFactory.getLog(AuditFieldListener.class);	

	@PrePersist
	public void persistAuditFields(AuditFields<Object> auditFields) {
		String userName = getUserName();
		auditFields.setCreatedBy(userName);
		auditFields.setCreatedOn(new Date());
		auditFields.setUpdatedBy(userName);
		auditFields.setUpdatedOn(new Date());
	}

	@PreUpdate
	public void updateAuditFields(AuditFields<Object> auditFields) {
		String userName = getUserName();
		auditFields.setUpdatedBy(userName);
		auditFields.setUpdatedOn(new Date());
	}

	public static String getUserName() {
		String userName = "unknown";
		try {
			userName = SecurityContextUtils.getUserId();
		} catch (Exception e) {
//			log.error("[Audit field listener]Exception getting user name from SecurityContextHolder! ");
		}
		return userName;
	}
}

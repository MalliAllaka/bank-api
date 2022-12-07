package com.malli.model;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.apache.poi.ss.formula.functions.T;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.malli.common.PrimaryKeyListener;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@MappedSuperclass
@JsonIdentityInfo(generator=JSOGGenerator.class) 
@EntityListeners(PrimaryKeyListener.class)
public class BaseEntity  extends AuditFields<T>{

}


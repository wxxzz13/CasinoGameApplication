package com.nagarro.adminPanel.dao;

import com.nagarro.adminPanel.dto.LoginServicesDTO;

public interface ValidateUserDao {

	public LoginServicesDTO validateUser(String customerID);

}
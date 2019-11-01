package com.nagarro.adminPanel.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.adminPanel.model.CustomerDetails;
import com.nagarro.adminPanel.services.UserListServices;

@Controller
public class UserListController {

	@Autowired
	UserListServices userListServices;

	@RequestMapping(value = "showUsers", method = RequestMethod.GET)
	public ModelAndView showUserList(HttpServletRequest request, HttpServletResponse response) {

		List<CustomerDetails> listOfUsers = new ArrayList<CustomerDetails>();

		ModelAndView modelAndView = null;

		listOfUsers = userListServices.fetchAllUsers();

		int userCounter = 0;

		modelAndView = new ModelAndView("UsersList");
		modelAndView.addObject("listOfUsers", listOfUsers);
		modelAndView.addObject("userCounter", userCounter);

		return modelAndView;
	}
}

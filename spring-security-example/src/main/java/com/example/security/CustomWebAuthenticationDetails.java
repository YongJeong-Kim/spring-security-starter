package com.example.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = 1L;
	
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);

        request.getSession().setAttribute("myfield", request.getParameter("myfield"));
    }
}
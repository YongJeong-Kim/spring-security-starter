package com.example.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.User;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		String myField = (String)request.getSession().getAttribute("myfield");
	    System.out.println("Another input Field : " + myField);
	    
		model.addAttribute("name", "ggggggggggggggggggg");
		return "home";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model, Principal principal, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User)auth.getPrincipal();
	    System.out.println(currentUser.getEmail());
	    System.out.println(request.getAttribute("comment"));
	    System.out.println((String) request.getSession().getAttribute("myfield"));
	    currentUser.setTest("modified test string.");
		model.addAttribute("name", principal.getName());
		return "hello";
	}
	
	@RequestMapping(value = "/hello2", method = RequestMethod.GET)
	public String hello2(Model model, Principal principal, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User)auth.getPrincipal();
	    System.out.println(currentUser.getEmail());
	    System.out.println(currentUser.getTest());
		model.addAttribute("name", principal.getName());
		return "hello2";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("name", "ggggggggggggggggggg");
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("prevPage", referrer);
		return "login";
	}
}

package com.itwill.lab05.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(name = "userSignOutController",urlPatterns = {"/user/signout"})
public class UserSignOutController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger log=LoggerFactory.getLogger(UserSignOutController.class);
	
	private final UserService userService = UserService.INSTANCE;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.removeAttribute("signedInUser");
		
		session.invalidate(); 
		
		String url=req.getContextPath()+"/user/signin";
		resp.sendRedirect(url);
	}
	
}

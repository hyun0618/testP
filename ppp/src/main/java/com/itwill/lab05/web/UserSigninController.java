package com.itwill.lab05.web;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.lab05.repository.User;
import com.itwill.lab05.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "userSigninController", urlPatterns = { "/user/signin" })
public class UserSigninController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UserSigninController.class);

	private final UserService userService = UserService.INSTANCE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("doGet()");
		req.getRequestDispatcher("/WEB-INF/views/user/signin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userid = req.getParameter("userid");
		String password = req.getParameter("password");

		User user = userService.signin(userid, password);

		String target = req.getParameter("target");

		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("signedInUser", user.getUserid());
			if (target == null || target.equals("")) {
				String url = req.getContextPath() + "/";
				resp.sendRedirect(url);
			} else {
				resp.sendRedirect(target);
			}

		} else {
			String url = req.getContextPath() + "/user/signin?result=f&target=" + URLEncoder.encode(target, "UTF-8");
			resp.sendRedirect(url);
		}
	}
}

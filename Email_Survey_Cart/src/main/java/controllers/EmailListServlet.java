package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;

import models.User;
import murach.data.*;

@WebServlet(urlPatterns = {"/emailList"})
public class EmailListServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8");
	    
        HttpSession session = req.getSession();
        
		String url = "/index.jsp";

		// get current action
		String action = req.getParameter("action");
		if (action == null) {
			action = "join"; // default action
		}

		// perform action and set URL to appropriate page
		if (action.equals("join")) {
			url = "/index.jsp"; // the "join" page
		} else if (action.equals("add")) {
			// get parameters from the request
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");
			
			//display currentyear scriptlet form footer.jsp
			GregorianCalendar currentDate = new GregorianCalendar();
		    int currentYear = currentDate.get(Calendar.YEAR);
		    
			// store data in User object
			User user = new User(firstName, lastName, email);
			session.setAttribute("user", user);
			// validate the parameters
			String message;
			if (firstName == null || lastName == null || email ==null ||
				firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
				message = "Please fill out all three text boxes.";
				url = "/index.jsp";
			}
			else {
				message = "";
				url = "/thanks.jsp";
			}
			
			// set User object in request object and set URL
			req.setAttribute("user", user);
			req.setAttribute("message", message);
			
			// set year as an attribute
			req.setAttribute("currentYear", currentYear);
		}
		
        // create the Date object and store it in the request
        Date currentDate = new Date();
        req.setAttribute("currentDate", currentDate);
		
        String path = getServletContext().getRealPath("/WEB-INF/EmailList0.txt");
        ArrayList<User> users = UserIOe.getUsers(path);
        session.setAttribute("users", users);
        
		// forward request and response objects to specified URL
		getServletContext().getRequestDispatcher(url).forward(req, resp);
		String email = req.getParameter("email");
		System.out.println("EmailListServlet email: " + email);
		log("email=" + email);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}

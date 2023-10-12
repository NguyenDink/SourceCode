package murach.download;

import javax.servlet.http.HttpServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.util.CookieUtil;
import models.User;
import murach.data.*;
import models.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class DownloadServlet extends HttpServlet{
	
	@Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAlbums";  // default action
        }
        // perform action and set URL to appropriate page
        String url = "/Chapter7/index.jsp";
        if (action.equals("viewAlbums")) {
            url = "/Chapter7/index.jsp";
        } else if (action.equals("checkUser")) {
            url = checkUser(request, response);
        } else if (action.equals("viewCookies")) {
            url = "/Chapter7/view_cookies.jsp";
        } else if (action.equals("deleteCookies")) {
            url = deleteCookies(request, response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
	
	@Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
        String action = request.getParameter("action");

        // perform action and set URL to appropriate page
        String url = "/Chapter7/index.jsp";
        if (action.equals("registerUser")) {
            url = registerUser(request, response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
	
    private String checkUser(HttpServletRequest request,
            HttpServletResponse response) {

        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        
        ServletContext sc = this.getServletContext();
        String productPath = sc.getRealPath("/WEB-INF/products.txt");
        Product product = ProductIO.getProduct(productCode, productPath);
        session.setAttribute("product", product);
        
        User user = (User) session.getAttribute("user");

        String url;
        // if User object doesn't exist, check email cookie
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String emailAddress = 
                CookieUtil.getCookieValue(cookies, "emailCookie");

            // if cookie doesn't exist, go to Registration page
            if (emailAddress == null || emailAddress.equals("")) {
                url = "/Chapter7/register.jsp";
            } 
            // if cookie exists, 
            // create User object and go to Downloads page
            else {
                String path = sc.getRealPath("/WEB-INF/EmailList.txt");
                user = UserIO.getUser(emailAddress, path);
                session.setAttribute("user", user);
                url = "/Chapter7/" + productCode + "_download.jsp";
            }
        } 
        // if User object exists, go to Downloads page
        else {
            url = "/Chapter7/" + productCode + "_download.jsp";
        }
        return url;
    }
    
    private String registerUser(HttpServletRequest request,
            HttpServletResponse response) {
	    
        // get the user data
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // store the data in a User object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // write the User object to a file
        ServletContext sc = getServletContext();
        String path = sc.getRealPath("/WEB-INF/EmailList.txt");
        UserIO.add(user, path);
        // store the User object as a session attribute
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // add a cookie that stores the user's email to browser
        Cookie c = new Cookie("userEmail", email);
        c.setMaxAge(60 * 60 * 24 * 365 * 3); // set age to 3 years
        c.setPath("/");                      // allow entire app to access it
        response.addCookie(c);
        
        // add a cookie that stores the user's as a cookie
        Cookie c2 = new Cookie("firstNameCookie", firstName);
        c2.setMaxAge(60 * 60 * 24 * 365 * 3); // set age to 2 years
        c2.setPath("/");                      // allow entire app to access it
        response.addCookie(c2);

        // create an return a URL for the appropriate Download page
        Product product = (Product) session.getAttribute("product");
        String url = "/Chapter7/" + product.getCode() + "_download.jsp";
        return url;
    }
    
    private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0); //delete the cookie
            cookie.setPath("/"); //allow the download application to access it
            response.addCookie(cookie);
        }
        String url = "/Chapter7/delete_cookies.jsp";
        return url;
    }
}


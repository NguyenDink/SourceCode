package murach.cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;

import models.*;
import murach.data.*;

public class CartServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext sc = getServletContext();

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "cart"; // default action
		}

		// perform action and set URL to appropriate page
		String url = "/Chapter7_7.3/index.jsp";
		if (action.equals("shop")) {
			url = "/Chapter7_7.3/index.jsp"; // the "index" page
		} else if (action.equals("cart")) {
			String productCode = request.getParameter("productCode");
			String quantityString = request.getParameter("quantity");

			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
			}

			// if the user enters a negative or invalid quantity,
			// the quantity is automatically reset to 1.
			int quantity;
			try {
				quantity = Integer.parseInt(quantityString);
				if (quantity < 0) {
					quantity = 1;
				}
			} catch (NumberFormatException nfe) {
				quantity = 1;
			}

			String path = sc.getRealPath("/WEB-INF/products.txt");
			Product product = ProductIO.getProduct(productCode, path);

			LineItem lineItem = new LineItem();
			lineItem.setProduct(product);
			lineItem.setQuantity(quantity);
			if (quantity > 0) {
				cart.addItem(lineItem);
			} else if (quantity == 0) {
				cart.removeItem(lineItem);
			}

			session.setAttribute("cart", cart);
			url = "/Chapter7_7.3/cart.jsp";
		} else if (action.equals("checkout")) {
			url = "/Chapter7_7.3/checkout.jsp";
		}

		sc.getRequestDispatcher(url).forward(request, response);
	}
	
	@Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }  
}

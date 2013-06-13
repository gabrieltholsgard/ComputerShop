package servlets;

/*
 * Shop.java
 *
 */
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

/**
 * 
 * @author Fredrik �lund, Olle Eriksson
 * @version 1.0
 */
public class ShopServlet extends HttpServlet {
	private static String showPage = null;
	private static String checkoutPage = null;
	private static String thankyouPage = null;
	private static String byePage = null;
	private static String profilePage = null;
	private static String userPage = null;
	private static String jdbcURL = null;
	private static String detailPage = null;
	private static String redirectPage = null;
	private BookListBean bookList = null;

	/**
	 * Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// look up our aliases for all pages, these are mapped
		// in web.xml

		showPage = config.getInitParameter("SHOW_PAGE");
		checkoutPage = config.getInitParameter("CHECKOUT_PAGE");
		thankyouPage = config.getInitParameter("THANKYOU_PAGE");
		byePage = config.getInitParameter("BYE_PAGE");
		profilePage = config.getInitParameter("PROFILE_PAGE");
		userPage = config.getInitParameter("USER_PAGE");
		detailPage = config.getInitParameter("DETAIL_PAGE");
		jdbcURL = config.getInitParameter("JDBC_URL");
		redirectPage = config.getInitParameter("CHECKOUT_REDIRECT_PAGE");

		// get the books from the database using a bean

		try {
			bookList = new BookListBean(jdbcURL);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		// servletContext is the same as scope Application
		// store the booklist in application scope

		ServletContext sc = getServletContext();
		sc.setAttribute("bookList", bookList);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {

		// this is the dispatcher in our application
		// all requests will go through it.

		// get access to the session and to the shoppingcart
		// Store the logged in username in the session
		// and get jdbc-URL provided in web.xml as init-parameter

		HttpSession sess = request.getSession();
		RequestDispatcher rd = null;
		ShoppingBean shoppingCart = getCart(request);
		sess.setAttribute("currentUser", request.getRemoteUser());
		sess.setAttribute("jdbcURL", jdbcURL);
		sess.setAttribute("usertype", "user"); // TODO -- NEW

		// check if we should turn on debug

		String debug = request.getParameter("debug");
		if (debug != null && debug.equals("on"))
			sess.setAttribute("debug", "on");
		else if (debug != null && debug.equals("off"))
			sess.removeAttribute("debug");

		// find out what to do based on the attribute "action"
		// no action or show

		/************************************************************/
		/* START SHOPPING PAGE */
		/************************************************************/
		if (request.getParameter("action") == null
				|| request.getParameter("action").equals("show")) {

			// A request dispatcher that's connected to the page.
			try {
				bookList.initBeans();
			} catch (Exception e) {
				throw new ServletException("Could not initializate beans.");
			}
			rd = request.getRequestDispatcher(showPage);
			rd.forward(request, response);
		/************************************************************/
		/* 				REFRESH										*/
		/************************************************************/
		} else if (request.getParameter("action").equals("refresh")) {
			try {
				bookList.initBeans();
			} catch (Exception e) {
				throw new ServletException("Could not initializate beans.");
			}
			rd = request.getRequestDispatcher(showPage);
			rd.forward(request, response);
		}

		// add a book to the shopping cart

		/************************************************************/
		/* ADD PRODUCT TO SHOPPING CART */
		/************************************************************/
		else if (request.getParameter("action").equals("add")) {

			// verify bookid and quantity

			if (request.getParameter("bookid") != null
					&& request.getParameter("quantity") != null) {
				BookBean bb = null;

				// search the book in our shop

				bb = bookList.getById(Integer.parseInt(request
						.getParameter("bookid")));
				if (bb == null) {
					throw new ServletException("The book is not in stock.");

				} else {

					// found, add it to the cart

					try {
						int q = Integer.parseInt(request
								.getParameter("quantity"));
						if (q <= 0)
							throw new NumberFormatException("Invalid quantity");
						shoppingCart.addBook(bb, q);
					} catch (NumberFormatException e) {
						throw new ServletException("Invalid quantity specified");
					}
				}
			}
			try {
				bookList.initBeans();
			} catch (Exception e) {
				throw new ServletException("Could not initializate beans.");
			}
			rd = request.getRequestDispatcher(showPage);
			rd.forward(request, response);
		}

		/************************************************************/
		/* REMOVE PRODUCT FROM SHOPPING CART */
		/************************************************************/
		else if (request.getParameter("action").equals("remove")) {
			if (request.getParameter("bookid") != null
					&& request.getParameter("quantity") != null) {
				try {
					int q = Integer.parseInt(request.getParameter("quantity"));
					if (q <= 0)
						throw new NumberFormatException("Illegal quantity");
					shoppingCart
							.removeBook(Integer.parseInt(request
									.getParameter("bookid")), q);
				} catch (NumberFormatException e) {
					throw new ServletException("Illegal quantity specified");
				}
			} else {
				throw new ServletException(
						"No bookid or quantity when removing book from cart");
			}
			try {
				bookList.initBeans();
			} catch (Exception e) {
				throw new ServletException("Could not initializate beans.");
			}
			rd = request.getRequestDispatcher(showPage);
			rd.forward(request, response);
		}

		// detailed information about a book

		/************************************************************/
		/* USER PRODUCT DETAILS */
		/************************************************************/
		else if (request.getParameter("action").equals("detail")) {
			if (request.getParameter("bookid") != null) {

				// find the book, store a reference in our request

				BookBean bb = bookList.getById(Integer.parseInt(request
						.getParameter("bookid")));
				request.setAttribute("book", bb);
			} else {
				throw new ServletException("No bookid when viewing detail");
			}
			rd = request.getRequestDispatcher(detailPage);
			rd.forward(request, response);
		}

		// make an order from our cart, empty the cart
		/************************************************************/
		/* MAKE ORDER FROM CART */
		/************************************************************/
		else if (request.getParameter("action").equals("save")) {

			// if we have a shoppingcart, verify that we have
			// valid userdata, then create an orderbean and
			// save the order in the database

			if (shoppingCart != null
					&& request.getParameter("shipping_name") != null
					&& request.getParameter("shipping_city") != null
					&& request.getParameter("shipping_zipcode") != null
					&& request.getParameter("shipping_address") != null) {
				OrderBean ob = new OrderBean(jdbcURL, shoppingCart, request
						.getParameter("shipping_name").trim(), request
						.getParameter("shipping_address").trim(), request
						.getParameter("shipping_zipcode").trim(), request
						.getParameter("shipping_city").trim());
				try {
					String check = ob.saveOrder();
					if (!check.equals(""))
						throw new ServletException(check, new Exception());
				} catch (Exception e) {
					throw new ServletException("Error saving order", e);
				}
			} else {
				throw new ServletException(
						"Not all parameters are present or no "
								+ " shopping cart when saving book");
			}
			rd = request.getRequestDispatcher(thankyouPage);
			rd.forward(request, response);
		}

		/************************************************************/
		/* CHECKOUT */
		/************************************************************/

		else if (request.getParameter("action").equals("checkout")) {
			if (sess.getAttribute("currentUser") != null) {

				// create an profile and populate it from the
				// database

				ProfileBean p = new ProfileBean(jdbcURL);
				try {
					p.populate((String) sess.getAttribute("currentUser"));
				} catch (Exception e) {
					throw new ServletException("Error loading profile", e);
				}
				sess.setAttribute("profile", p);

			}

			// redirect (not forward)

			response.sendRedirect(redirectPage);

		}

		/************************************************************/
		/* LOGOUT */
		/************************************************************/

		else if (request.getParameter("action").equals("logout")) {
			sess.invalidate();
			rd = request.getRequestDispatcher(byePage);
			rd.forward(request, response);
		}

		/************************************************************/
		/* PROFILE PAGE */
		/************************************************************/

		else if (request.getParameter("action").equals("profile")) {
			HashMap<String, Boolean> role = null;

			// create a profile object, fill it in from the database
			// also store all user roles in the map "role"

			ProfileBean p = new ProfileBean(jdbcURL);
			try {
				p.populate((String) sess.getAttribute("currentUser"));
				role = p.getRoles();
			} catch (Exception e) {
				throw new ServletException("Error loading profile", e);
			}
			sess.setAttribute("profile", p);

			// flag all roles that the user is associated with

			Set<String> k = role.keySet();
			Iterator<String> i = k.iterator();
			while (i.hasNext()) {
				String st = i.next();
				if (request.isUserInRole(st))
					role.put(st, true);
			}
			p.setRole(role);
			sess.setAttribute("roles", role);
			rd = request.getRequestDispatcher(profilePage);
			rd.forward(request, response);
		}

		/************************************************************/
		/* UPDATE PROFILE */
		/************************************************************/

		else if (request.getParameter("action").equals("profilechange")
				|| request.getParameter("action").equals("usercreate")) {
			ProfileBean pb = (ProfileBean) sess.getAttribute("profile");
			String u;
			if (request.getParameter("action").equals("profilechange"))
				u = (String) sess.getAttribute("currentUser");
			else
				u = request.getParameter("user");

			// get all data needed

			String p1 = request.getParameter("password");
			String p2 = request.getParameter("password2");
			String name = request.getParameter("name");
			String street = request.getParameter("street");
			String zip = request.getParameter("zip");
			String city = request.getParameter("city");
			String country = request.getParameter("country");

			pb.setUser(u);
			pb.setPassword(p1);
			pb.setName(name);
			pb.setStreet(street);
			pb.setZip(zip);
			pb.setCity(city);
			pb.setCountry(country);
			HashMap<String, Boolean> r = (HashMap<String, Boolean>) sess
					.getAttribute("roles");
			Set<String> k = r.keySet();
			Iterator<String> i = k.iterator();
			while (i.hasNext()) {
				String st = i.next();
				String res = request.getParameter(st);
				if (res != null)
					r.put(st, true);
				else
					r.put(st, false);
			}
			pb.setRole(r);

			// if this a new user, try to add him to the database

			if (request.getParameter("action").equals("usercreate")) {
				boolean b;

				// make sure the the username is not used already

				try {
					b = pb.testUser(u);
				} catch (Exception e) {
					throw new ServletException("Error loading user table", e);
				}
				if (b) {
					sess.setAttribute("passwordInvalid",
							"User name already in use");
					rd = request.getRequestDispatcher(userPage);
					rd.forward(request, response);

					// note that a return is needed here because forward
					// will not cause our servlet to stop execution, just
					// forward the request processing

					return;
				}
			}

			// now we know that we have a valid user name
			// validate all data,

			boolean b = profileValidate(request, sess);
			if (!b && request.getParameter("action").equals("profilechange")) {
				rd = request.getRequestDispatcher(profilePage);
				rd.forward(request, response);
			} else if (!b) {
				rd = request.getRequestDispatcher(userPage);
				rd.forward(request, response);
			}

			// validated OK, update the database

			else {

				ProfileUpdateBean pu = new ProfileUpdateBean(jdbcURL);
				if (request.getParameter("action").equals("profilechange")) {
					try {
						pu.setProfile(pb);
					} catch (Exception e) {
						throw new ServletException("Error saving profile", e);
					}
					try {
						bookList.initBeans();
					} catch (Exception e) {
						throw new ServletException("Could not initializate beans.");
					}
					rd = request.getRequestDispatcher(showPage);
					rd.forward(request, response);
				} else {
					try {
						pu.setUser(pb);
					} catch (Exception e) {
						throw new ServletException("Error saving profile", e);
					}
					response.sendRedirect(redirectPage);
				}
			}
		}

		/************************************************************/
		/* CREATE USER */
		/************************************************************/

		else if (request.getParameter("action").equals("newuser")) {
			ProfileBean p = new ProfileBean(jdbcURL);
			try {
				HashMap<String, Boolean> role = p.getRoles();
				sess.setAttribute("roles", role);
			} catch (Exception e) {
				throw new ServletException("Error loading profile", e);
			}

			sess.setAttribute("profile", p);

			rd = request.getRequestDispatcher(userPage);
			rd.forward(request, response);
		}
	}

	// valide a profile

	private boolean profileValidate(HttpServletRequest request, HttpSession sess) {

		// use the attribute "passwordInvalid" as error messages

		sess.setAttribute("passwordInvalid", null);
		String u;

		// get all data

		if (request.getParameter("action").equals("profilechange"))
			u = (String) sess.getAttribute("currentUser");
		else
			u = request.getParameter("user");
		String p1 = request.getParameter("password");
		String p2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String street = request.getParameter("street");
		String zip = request.getParameter("zip");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		HashMap<String, Boolean> r = (HashMap<String, Boolean>) sess
				.getAttribute("roles");
		Set<String> k = r.keySet();
		int count = 0;
		Iterator<String> i = k.iterator();
		while (i.hasNext()) {
			String st = request.getParameter(i.next());
			if (st != null)
				count++;
		}

		// validate

		if (count == 0) {
			sess.setAttribute("passwordInvalid",
					"You must select at least one role");
			return false;
		} else if (u == null || u.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"User name must not be empty, retry!");
			return false;

		}
		if (!request.isUserInRole("admin")
				&& request.getParameter("admin") != null) {
			sess.setAttribute("passwordInvalid",
					"You must be in role admin to set role admin");
			return false;
		}
		if (!request.isUserInRole("manager")
				&& request.getParameter("manager") != null) {
			sess.setAttribute("passwordInvalid",
					"You must be in role manager to set role manager");
			return false;
		}
		if (!request.isUserInRole("manager-gui")
				&& request.getParameter("manager-gui") != null) {
			sess.setAttribute("passwordInvalid",
					"You must be in role manager-gui to set role manager-gui");
			return false;
		}
		if (!request.isUserInRole("admim-gui")
				&& request.getParameter("admim-gui") != null) {
			sess.setAttribute("passwordInvalid",
					"You must be in role admin-gui to set role admin-gui");
			return false;
		}
		if (!request.isUserInRole("manager-script")
				&& request.getParameter("manager-script") != null) {
			sess.setAttribute("passwordInvalid",
					"You must be in role manager-script to set role manager-script");
			return false;
		}
		if (p1 == null || p2 == null || p1.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"Password must not be empty, retry!");
			return false;

		} else if (!(p1.equals(p2))) {
			sess.setAttribute("passwordInvalid",
					"Passwords do not match, retry!");
			return false;
		} else if (name == null || name.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"Name must not be empty, retry!");
			return false;
		} else if (street == null || street.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"Street must no be empty, retry!");
			return false;
		} else if (zip == null || zip.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"Zip code must not be empty, retry!");
			return false;
		} else if (city == null || city.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"City must not be empty, retry!");
			return false;
		} else if (country == null || country.length() < 1) {
			sess.setAttribute("passwordInvalid",
					"County must not be empty, retry!");
			return false;
		}

		// validation OK

		return true;
	}

	// get the shoppingcart, create it if needed

	private ShoppingBean getCart(HttpServletRequest request) {
		HttpSession se = null;
		se = request.getSession();
		ShoppingBean sb = null;
		sb = (ShoppingBean) se.getAttribute("shoppingCart");
		if (sb == null) {
			sb = new ShoppingBean();
			se.setAttribute("shoppingCart", sb);
		}

		return sb;
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "The main BookShop";
	}
}

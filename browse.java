
// import Java libraries
import java.io.*;
import java.net.URL;
import java.util.Properties;

// import mail service libraries
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// import Servlet libraries
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebServlet("/browse")
public class browse extends HttpServlet {
	private static final long serialVersionUID = 2L;

	// **** setting for local ****/
	private static String LoginServlet = "http://localhost:8080/CS4640/login";
	private static String LogoutServlet = "http://localhost:8080/CS4640/logout";
	private static String BrowseServlet = "http://localhost:8080/CS4640/browse";

	private static String q1_Servlet = "http://localhost:8080/cs4640/question1";

	private static String classWebsite = "http://www.cs.virginia.edu/upsorn/cs4640/";

	// a data file containing username and password
	// note: this is a simple login information without encryption.
	// In reality, credential must be encrypted for security purpose
	public static String user_info = "/Applications/apache/webapps/cs4640/WEB-INF/data/user-info.txt";

	public static String browse_info = "/Applications/apache/webapps/cs4640/WEB-INF/data/browse-info.txt";

	// doPost() tells doGet() when the login is invalid.
	private static boolean invalidID = false;

	private String user;

	/**
	 * ***************************************************** Overrides
	 * HttpServlet's doGet(). prints the login form.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		user = (String) session.getAttribute("UserID");

		// URL rewriting
		// user = request.getParameter("uid");

		// if the survey servlet is accessed without logging in,
		// redirect to login servlet
		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		PrintHead(out);
		PrintBody(out);
		print_data("/Applications/apache/webapps/cs4640/WEB-INF/data/game_list.xml", response);
		//PrintBottom(out);

		out.close();
		

	}

	/*******************************************************
	 * Overrides HttpServlet's doPost().
	 * 
	 * // assume an account will locked after 3 failed attempts // write code to
	 * check and handle this business logic // (optional)
	 ********************************************************* 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response); // if an HTTP POST request comes in, simple
									// redraw the page by calling doGet()
	}

	public void PrintHead(PrintWriter out) {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
				+ " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> ");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");

		out.println("<head>");
		out.println("   <meta http-equiv=\"content-type\" content=\"text/xhtml; charset=utf-8\">");
		out.println("   <title>CS4640 Session example</title>");

		out.println("   <style>");
		out.println("      body, html {");
		out.println("         margin: 10 auto;");
		out.println("         padding: 10;");
		out.println("         color: white;");
		out.println("         background-color: #060CE9;");
		out.println(
				"         font-family: 'Lucida Grande',Verdana,Helvetica,Arial,Geneva,'Bitstream Vera Sans',Sans-Serif;");
		out.println("         font-size: 12px;");
		out.println("      }");

		out.println("      input[type=text], textarea {");
		out.println("         border: 1px solid #cccccc;");
		out.println("         font: 11px Verdana;");
		out.println("         color: black;");
		out.println("         line-height: 1.2em;");
		out.println("      }");

		out.println("      #top {");
		out.println("         padding-top: 12px;");
		out.println("         padding-left: 10px;");
		out.println("         padding-right: 4px;");
		out.println("         float: left;");
		out.println("         text-align: justify;");
		out.println("         width: 90%;");
		out.println("     	}");

		out.println("      #main {");
		out.println("         padding-top: 12px;");
		out.println("         padding-left: 10px;");
		out.println("         padding-right:4px;");
		out.println("         float: left;");
		out.println("         width: 90%;");
		out.println("         text-align: center;");
		out.println("      }");

		out.println("      #note {");
		out.println("         font: 10px Verdana;");
		out.println("         color: red;");
		out.println("      }");

		out.println("      .errorMsg {");
		out.println("         background-color: #eeeeee;");
		out.println("         width: 100%; ");
		out.println("      }");

		out.println("      .list {");
		out.println("         width: 90%;");
		out.println("         background-color: #eeeeee;");
		out.println("      }");

		out.println("   </style>");
		out.println("</head>");
	}

	public void PrintBody(PrintWriter out) {
		out.println("<body >");

		out.println(
				"  <table width=\"25%\" align=\"right\" bgcolor=\"#E0E0E0\" border=\"0\" cellspacing=\"2\" cellpadding=\"5\"");
		out.println("    <tr>");
		out.println("      <td align=\"right\">UserID:  " + user + "</td>");
		out.println("      <td>");
		out.println("        <form action=\"" + LogoutServlet + "\" method=\"post\">");
		out.println("          <input type=\"submit\" value=\"Logout\" "
				+ " onMouseOver=\"Tip('Logout from the survey. <br /> Thank you " + user + ".')\" "
				+ " onMouseOut=\"UnTip()\"></input>");
		out.println("        </form>");
		out.println("      </td>");
		out.println("    </tr>");
		out.println("  </table>");

		out.println("  <br /><br />");

		out.println("  <center><h1>Jeopardy</h1></center>");


	}
/*
	public void PrintBottom(PrintWriter out) {
		out.println("<br />");
		out.println("<hr />");
		out.println("<br />");
		out.println("<a href=\"" + classWebsite + "\">CS4640 &mdash; class website</a>");
		out.println("<br />");

		out.println("</body>");
		out.println("</html>");
	}
	*/
	
	private void print_data(String fname, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("TEXT/HTML");
		PrintWriter out = response.getWriter();

		try {
			Document doc = create_DOM_from_file(fname);

			Node root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			
			for (int i = 1; i < nList.getLength(); i++) {
				Node nd = nList.item(i);
				out.println("Game Name: " + nd.getNodeName());
				
				Node u = nd.getFirstChild();
				Element euser = (Element) u;
				String userstring = euser.getTextContent();
				out.println(userstring);
				if (userstring.equals(user)) {
					out.println("<input type='button' name='update' value='update' type='submit' onClick='session('editgame')="+nd.getNodeName()+""
							+ ";window.location.href='localhost:8080/cs4640/jeopardy' </button>");
					out.println("<input type='button' name='delete' value='delete' type='submit' </button>");
					out.println("<input type='button' name='play' value='play' type='submit' </button>");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// create a DOM object
	private Document create_DOM_from_file(String fname) throws Exception {
		try {
			File datafile = new File(fname);
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
			Document doc = dbuilder.parse(datafile);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}

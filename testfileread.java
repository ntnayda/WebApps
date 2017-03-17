package examples;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.net.*;

//Import Java Libraries
import java.io.*;

@WebServlet("/jeopardy")
public class testfileread extends HttpServlet
{
   public void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();

      out.println ("<HTML>");
      out.println ("<HEAD>");
      out.println ("<TITLE>A simple servlet program</TITLE>");
      out.println ("</HEAD>");

      out.println ("<BODY>");
      out.println ("<CENTER>");

      out.println ("<FORM METHOD=\"post\" ACTION=\"http://localhost:8080/cs4640/jeopardy\">");
      out.println ("<P>");
      out.println ("Please enter four integer values between 1 and 24 inclusive.");
      out.println ("This program will return all expressions using the four operators");
      out.println ("+, -, * and / that use each value exactly once and that have the result 24.");
      out.println ("There may be a lot of what looks like duplicates,");
      out.println ("but they are actually different ways of rearranging the numbers.");
      out.println ("(This program can actually handle all integers.)");
      out.println ("");
      out.println ("<P>");
      out.println ("<TABLE CELLSPACING=0 CELLPADDING=5 BORDER=1 ALIGN=center>");
      out.println ("");

      URL url = new URL("http://plato.cs.virginia.edu/~ntn4jg/jeopardy/jeopardy_output.txt");
      InputStream is = url.openStream();
      String line;
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      br.readLine();
      int count = 0;
      while ((line = br.readLine()) != null) {
                  String qstring = line.substring(14);
                  String[] items = qstring.split(";");
                  String question = "";
                  String answer = "";
                  String qtype = "";
                  for(String item: items) {

                        String[] subitem = item.split(":");
                        if(subitem[0].equals("Question")) {
                              question = subitem[1];
                        }
                        if(subitem[0].equals("Correct_Answer")) {
                              answer = subitem[1];
                        }
                        if(subitem[0].equals("question_type")) {
                              qtype = subitem[1];
                        }

                  }
            if(!question.equals("")) {
            if(qtype.equals("mc")) {
                  String fullquestion = question + ": ";
                  out.println("<tr><td>Question: " + question + "</br>" );
                  for(String item: items) {
                        String[] subitem = item.split(":");
                        if(subitem[0].substring(0,6).equals("Answer")) {
                              out.println(subitem[0] + ": " + subitem[1] + "</br>");
                              fullquestion = fullquestion + subitem[0] + ": " + subitem[1] + " ";

                        }
                  }
                  out.println("Answer: " + answer);
                  out.println("<input name=\"question"+count+"\" type=\"hidden\" value=\""+fullquestion+"\">");
                  out.println("<input name=\"answer"+count+"\" type=\"hidden\" value=\""+answer+"\">");
                  out.println("</td><td>row: <input name=\"row"+count+"\" type=\"text\" size=\"3\"></td><td>column: <input name=\"column"+count+"\" type=\"text\" size=\"3\"></td><td>score: <input name=\"score"+count+"\" type=\"text\" size=\"5\"></td><tr>");
                  count++;
            }
            else if(qtype.equals("sata")) {
                  String fullquestion = question + "(select all that apply): ";
                  out.println("<tr><td>Question: " + question + " (select all that apply)</br>" );
                  for(String item: items) {
                        String[] subitem = item.split(":");
                        if(subitem[0].substring(0,6).equals("Answer")) {
                              out.println(subitem[0] + ": " + subitem[1] + "</br>");
                              fullquestion = fullquestion + subitem[0] + ": " + subitem[1] + " ";
                        }
                  }
                  out.println("<input name=\"question"+count+"\" type=\"hidden\" value=\""+fullquestion+"\">");
                  out.println("<input name=\"answer"+count+"\" type=\"hidden\" value=\""+answer+"\">");
                  out.println("Answer: " + answer);
                  out.println("</td><td>row: <input name=\"row"+count+"\" type=\"text\" size=\"3\"></td><td>column: <input name=\"column"+count+"\" type=\"text\" size=\"3\"></td><td>score: <input name=\"score"+count+"\" type=\"text\" size=\"8\"></td><tr>");
                  count++;
            }
            else {
                  out.println("<tr><td>Question: " + question + "</br>");
                  out.println("Answer: "+answer +"</td>");
                  out.println("<input name=\"question"+count+"\" type=\"hidden\" value=\""+question+"\">");
                  out.println("<input name=\"answer"+count+"\" type=\"hidden\" value=\""+answer+"\">");
                  
                  out.println("<td>row: <input name=\"row"+count+"\" type=\"text\" size=\"3\"></td><td>column: <input name=\"column"+count+"\" type=\"text\" size=\"3\"></td><td>score: <input name=\"score"+count+"\" type=\"text\" size=\"8\"></td><tr>");
                  count++;
            }
            
      }

      }

      out.println("<input name=\"count\" type=\"hidden\" value=\""+count+"\">");
      out.println (" </TABLE>");
      out.println ("");
      out.println (" <TABLE CELLSPACING=0 CELLPADDING=0 BORDER=0 ALIGN=center WIDTH=\"50%\">");
      out.println ("  <TR ALIGN=center>");
      out.println ("   <TD><INPUT NAME=\"submit\" TYPE=\"submit\" value=\"Create Game\"></TD>");
      out.println ("   <TD><INPUT NAME=\"moreq\" TYPE=\"button\" value=\"Add more questions\"");
      out.println ("              onClick=\"location.href='http://plato.cs.virginia.edu/~ntn4jg/jeopardy/assignment3.php';\"></TD>");
      out.println ("  </TR>");
      out.println (" </TABLE>");
      out.println ("");
      out.println ("</FORM>");
      out.println ("");
      out.println ("</CENTER>");
      out.println ("</BODY>");

      out.println ("</HTML>");

      out.close ();
   }
public void doPost (HttpServletRequest req, HttpServletResponse res)
   throws ServletException, IOException
{
      res.setContentType ("TEXT/HTML");
      PrintWriter out = res.getWriter ();

      String countstring = req.getParameter ("count");
      int count = Integer.parseInt(countstring);
      try{
    PrintWriter writer = new PrintWriter("jeopardy_game_data.txt", "UTF-8");
    for(int i=0; i<count;i++) {
            String outputstring = req.getParameter("question"+i);
      outputstring += ";" + req.getParameter("answer"+i) + ";";
      outputstring += req.getParameter("row"+i) + ";";
      outputstring += req.getParameter("column"+i) + ";";
      outputstring += req.getParameter("score" +i) +";";
      writer.println(outputstring);
      }writer.close();
} catch (IOException e) {
   out.println(e);
}
int row = 0;
int column = 0;
     for(int i=0; i<count;i++) {
      int currentrow = Integer.parseInt(req.getParameter("row"+i));
      int currentcol = Integer.parseInt(req.getParameter("column"+i));
      if(currentrow>row){
            row=currentrow;
      }
      if(currentcol>column){
            column=currentcol;
      }
     } 

     out.println ("<HTML>");
      out.println ("<HEAD>");
      out.println ("<TITLE>A simple servlet program</TITLE>");
      out.println ("</HEAD>");

      out.println ("<BODY>");
      out.println ("<CENTER>");

      out.println ("<FORM METHOD=\"post\" ACTION=\"http://localhost:8080/cs4640/jeopardy\">");

      out.println ("<TABLE CELLSPACING=0 CELLPADDING=50 BORDER=1 ALIGN=center>");
      out.println ("");
      int newcount=0;
      int currentrow=1;
      int currentcol=1;

            while(currentrow<=row) {
                  out.println("<tr>");
                  while(currentcol<=column) {
                        for(int i=0;i<count;i++) {
                              int currentrow2 = Integer.parseInt(req.getParameter("row"+i));
                              int currentcol2 = Integer.parseInt(req.getParameter("column"+i));
                              if(currentrow2==currentrow && currentcol2==currentcol) {
                                    out.println("<td>"+req.getParameter("score"+i)+"</td>");
                              }
                        }
                        currentcol++;
                  }
                  currentrow++;
                  currentcol=1;
                  out.println("</tr>");
            }

      out.println("</table>");
      out.println ("");
      out.println("<button onclick=\"window.history.back();\">Edit</button>");
      out.println ("</CENTER>");
      out.println ("</BODY>");

      out.println ("</HTML>");

      out.close ();
}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L4;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
    //request.getParameter()to dread 
    //request.getSession()
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        // Retrieves the current session, if one doesn't exist it creates it
        HttpSession session = request.getSession(true);
        
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            Boolean signedIn = false;
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
           
                    while (rs.next()) {
                        if(username.equals(rs.getString("username")) && password.equals(rs.getString("password")) ){
                            signedIn = true;
                            session.setAttribute("ub", new UserBean(username,password));
                            UserBean ub = (UserBean)session.getAttribute("ub");

                            //Vidare processandet av req o respons objektet sker nu i jsp -sidan 
                            //Detta syns ej i browsern direkt
                            RequestDispatcher rd = request.getRequestDispatcher("Test-jsp.jsp");
                            rd.forward(request, response); 
                            break;
                        }
                    }
                    if(!signedIn){
                        out.print("WRONG USERNAME OR PASSWORD TRY AGAIN"); 
                        request.getRequestDispatcher("login.html").include(request, response); 
                    }
            
                /* 
                //if it's a new game, create it
                if(session.isNew()){
                    session.setAttribute("game", new Game());
                }
                String g = request.getParameter("guess");
                Integer guess = Integer.parseInt(g);
                Game game = (Game)session.getAttribute("game");
                game.newGuess(guess);
                RequestDispatcher rd = request.getRequestDispatcher("Game.jsp");
                rd.forward(request, response); 
                */
        }
        catch(Exception e){
            out.println(e.getMessage());
        }
    }
}

/**
 *
 * @author Amanda
 
public class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}*/

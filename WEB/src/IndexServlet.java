import service.IEmployee;
import service.ILogin;
import utils.ServicesLocator;
import utils.ServicesLocatorException;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Manfred on 04/11/2014.
 */
public class IndexServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("coucoupost");
        HttpSession session = request.getSession(true);
        session.setAttribute("currentSessionUser", request.getParameter("username"));
        response.sendRedirect("index.jsp");
    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // get employees list
        try {
            ILogin serviceEmployee = (ILogin) ServicesLocator.getInstance().getRemoteInterface("ServiceLogin");
            System.out.println(serviceEmployee.getEmployees());
            request.setAttribute("employees", serviceEmployee.getEmployees());
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }
        System.out.println("coucou");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

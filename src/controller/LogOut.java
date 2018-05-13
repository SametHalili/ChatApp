package controller;

import domain.Person;
import domain.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOut extends RequestHandler
{

    @Override
    public String handleRequest(HttpServletRequest request,
                                HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        Person currPerson = (Person) request.getSession().getAttribute("user");
        super.getPersonService().getPerson(currPerson.getUserId()).setUserStatus(UserStatus.OFFLINE);
        session.invalidate();
        return "index.jsp";
    }

}

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Chat extends RequestHandler
{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        if(!(session.getAttribute("user") == null))
        {
            return "chat.jsp";
        }
        return "index.jsp";
    }
}

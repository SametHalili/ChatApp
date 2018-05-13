package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Person;
import domain.UserStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AsyncController")
public class AsyncController extends Controller
{
    public AsyncController()
    {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handelRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handelRequest(request, response);
    }

    public void handelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");

        if(action != null)
            switch (action)
            {
                case "changeStatus":
                    changeStatus(request, response);
                    break;
                case "getUserStatus":
                    getStatus(request, response);
                    break;
                case "getUser":
                    getUser(request, response);
                    break;
                case "getFriends":
                    getFriends(request, response);
                    break;
                case "addFriend":
                    addFriend(request, response);
                    break;
            }
    }

    private void addFriend(HttpServletRequest request, HttpServletResponse response)
    {
        String username1 = request.getParameter("username1");
        String username2 = request.getParameter("username2");
        if(!(username1 == null || username2 == null)
                || !(username1.isEmpty() || username2.isEmpty())
                || !(username1.equals(username2)))
        {
            if(super.getModel().getPersonExists(username2)
                    && !super.getModel().getPerson(username1).friendInFriendListAlreadyExists(username2))
            {
                System.out.println("test");
                super.getModel().addPersonToFriend(username1, super.getModel().getPerson(username2));
                super.getModel().addPersonToFriend(username2, super.getModel().getPerson(username1));
            }
        }
    }

    private void getFriends(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if(request.getParameter("username") != null)
        {
            List<Person> friendList = super.getModel().getFriendsList(request.getParameter("username"));
            String friendJson = this.toJSON(friendList);
            response.setContentType("application/json");
            response.getWriter().write(friendJson);
        }
    }

    public String toJSON (Object object) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String userId = request.getParameter("username");
        if(!(super.getModel().getPerson(userId) == null) || !userId.isEmpty())
        {
            String userJSON = this.toJSON(super.getModel().getPerson(userId));
            response.setContentType("application/json");
            response.getWriter().write(userJSON);
        }
    }

    private void getStatus(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if(request.getParameter("username") != null)
        {
            UserStatus userStatusT = super.getModel().getPerson(request.getParameter("username")).getUserStatus();
            String statusJSON = this.toJSON(userStatusT);
            response.setContentType("application/json");
            response.getWriter().write(statusJSON);
        }
        else
        {
            Person person = (Person) request.getSession().getAttribute("user");
            UserStatus userStatus = super.getModel().getPerson(person.getUserId()).getUserStatus();
            String statusJSON = this.toJSON(userStatus);
            response.setContentType("application/json");
            response.getWriter().write(statusJSON);
        }

    }

    private void changeStatus(HttpServletRequest request, HttpServletResponse response)
    {
        if(request.getParameter("username") != null)
        {
            UserStatus newStatus = UserStatus.strToStatus(request.getParameter("newStatus"));
            super.getModel().getPerson(request.getParameter("username")).setUserStatus(newStatus);
        }
        else
        {
            Person person = (Person) request.getSession().getAttribute("user");
            UserStatus newStatus = UserStatus.strToStatus(request.getParameter("newStatus"));
            super.getModel().getPerson(person.getUserId()).setUserStatus(newStatus);
        }
    }
}

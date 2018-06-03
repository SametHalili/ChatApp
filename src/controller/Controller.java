package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;

@WebServlet("/Controller")
public class Controller extends HttpServlet
{
    private PersonService model = new PersonService();
    private ChatConversationsListService chatModel = new ChatConversationsListService();
    private ControllerFactory controllerFactory = new ControllerFactory();

    public Controller()
    {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }


    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getParameter("asyncAction") == null)
        {
            String action = request.getParameter("action");
            String destination = "index.jsp";
            if (action != null)
            {
                RequestHandler handler;
                try
                {
                    handler = controllerFactory.getController(action, model);
                    destination = handler.handleRequest(request, response);
                }
                catch (NotAuthorizedException exc)
                {
                    List<String> errors = new ArrayList<String>();
                    errors.add(exc.getMessage());
                    request.setAttribute("errors", errors);
                    destination = "index.jsp";
                }
            }
            RequestDispatcher view = request.getRequestDispatcher(destination);
            view.forward(request, response);
        }
        else
        {
            handelRequest(request, response);
        }

    }

    public void handelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("asyncAction");

        if (action != null)
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
                case "getAllUsers":
                    getAllUser(request, response);
                    break;
                case "getFriends":
                    getFriends(request, response);
                    break;
                case "addFriend":
                    addFriend(request, response);
                    break;
                case "updateUser":
                    putUser(request, response);
                    break;
                case "chatConversationSend":
                    chatConversationSend(request, response);
                    break;
                case "chatConversationGet":
                    chatConversationGet(request, response);
                    break;
                case "chatConversationGetAll":
                    chatConversationGetAll(request, response);
                    break;
            }
    }

    public String toJSON(Object object) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void putUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String JSONdata = "";
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null)
        {
            JSONdata += line;
        }

        JsonNode root = mapper.readTree(JSONdata);
        String username = removeQuotationMarks(root, "userId");
        String firstName = removeQuotationMarks(root, "firstName");
        String lastName = removeQuotationMarks(root, "lastName");

        Person updPerson = model.getPerson(username);
        updPerson.setFirstName(firstName);
        updPerson.setLastName(lastName);

        model.updatePersons(updPerson);
    }


    private String removeQuotationMarks(JsonNode node, String path)
    {
        String temp = node.path(path).toString();
        StringBuilder sb = new StringBuilder(temp);
        sb.deleteCharAt(temp.length() - 1);
        sb.deleteCharAt(0);

        return sb.toString();
    }

    private void addFriend(HttpServletRequest request, HttpServletResponse response)
    {
        String username1 = request.getParameter("username1").trim();
        String username2 = request.getParameter("username2").trim();
        if(!username1.equals(username2))
        {
            if (!(username1 == null || username2 == null) ||
                    !(username1.isEmpty() || username2.isEmpty()))
            {
                if (model.getPersonExists(username2)
                        && !model.getPerson(username1).friendInFriendListAlreadyExists(username2))
                {
                    model.addPersonToFriend(username1, model.getPerson(username2));
                    model.addPersonToFriend(username2, model.getPerson(username1));
                }
            }
        }
    }

    private void getFriends(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (request.getParameter("username") != null)
        {
            List<Person> friendList = model.getFriendsList(request.getParameter("username"));
            String friendJson = this.toJSON(friendList);
            response.setContentType("application/json");
            response.getWriter().write(friendJson);
        }
    }


    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String userId = request.getParameter("username");
        if (!(model.getPerson(userId) == null) || !userId.isEmpty())
        {
            String userJSON = this.toJSON(model.getPerson(userId));
            response.setContentType("application/json");
            response.getWriter().write(userJSON);
        }
    }

    private void getAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String userJSON = this.toJSON(model.getPersons());
        response.setContentType("application/json");
        response.getWriter().write(userJSON);
    }

    private void getStatus(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (request.getParameter("username") != null)
        {
            UserStatus userStatusT = model.getPerson(request.getParameter("username")).getUserStatus();
            String statusJSON = this.toJSON(userStatusT);
            response.setContentType("application/json");
            response.getWriter().write(statusJSON);
        }
        else
        {
            Person person = (Person) request.getSession().getAttribute("user");
            UserStatus userStatus = model.getPerson(person.getUserId()).getUserStatus();
            String statusJSON = this.toJSON(userStatus);
            response.setContentType("application/json");
            response.getWriter().write(statusJSON);
        }

    }

    private void changeStatus(HttpServletRequest request, HttpServletResponse response)
    {
        if (request.getParameter("username") != null)
        {
            UserStatus newStatus = UserStatus.strToStatus(request.getParameter("newStatus"));
            model.getPerson(request.getParameter("username")).setUserStatus(newStatus);
        }
        else
        {
            Person person = (Person) request.getSession().getAttribute("user");
            UserStatus newStatus = UserStatus.strToStatus(request.getParameter("newStatus"));
            model.getPerson(person.getUserId()).setUserStatus(newStatus);
        }
    }

    private void chatConversationSend(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession s = request.getSession();

        Person person = (Person) s.getAttribute("user");
        String userId1 = person.getUserId();
        String userId2 = request.getParameter("userId");
        String msg = request.getParameter("msg");

        try
        {
            chatModel.getChatConversationList().getChatConversation(userId1, userId2).addMsg(new Message(userId1, msg));
        }
        catch(DomainException e)
        {
            System.out.println("Conversation did not exist, creating new one");
            chatModel.getChatConversationList().addChatConversation(new ChatConversation(userId1, userId2));
            chatModel.getChatConversationList().getChatConversation(userId1, userId2).addMsg(new Message(userId1, msg));
        }
    }

    private void chatConversationGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession s = request.getSession();

        Person person = (Person) s.getAttribute("user");
        String userId = person.getUserId();
        ChatConversationsList chatConvs = chatModel.getChatConversationListById(userId);

        String convJSON = toJSON(chatConvs);
        response.setContentType("application/json");
        convJSON = convJSON.substring(0, convJSON.length() - 1);
        convJSON+=",\"loggedin\":\""+userId+"\"}";
        response.getWriter().write(convJSON);
    }

    private void chatConversationGetAll(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String convJSON = toJSON(chatModel.getChatConversationList());
        response.setContentType("application/json");
        response.getWriter().write(convJSON);
    }
}

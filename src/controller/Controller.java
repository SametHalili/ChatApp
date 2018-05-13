package controller;

import domain.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Controller extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private PersonService model = new PersonService();

    public Controller()
    {
        super();
    }

    public PersonService getModel()
    {
        return model;
    }

    protected abstract void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException;
    protected abstract void doPost(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException;
}

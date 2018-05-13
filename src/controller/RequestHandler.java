package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.PersonService;
import domain.Person;
import domain.UserStatus;

public abstract class RequestHandler
{

    private PersonService personService;

    public abstract String handleRequest(HttpServletRequest request, HttpServletResponse response);

    public void setModel(PersonService personService)
    {
        this.personService = personService;
    }

    public PersonService getPersonService()
    {
        return personService;
    }

    protected boolean isFromUserWithRole(HttpServletRequest request, UserStatus userStatus)
    {
        Person person = (Person) request.getSession().getAttribute("user");
        if (person != null && person.getUserStatus().equals(userStatus))
        {
            return true;
        }
        return false;
    }

}

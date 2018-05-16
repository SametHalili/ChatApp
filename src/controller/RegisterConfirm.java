package controller;

import domain.Person;
import domain.PersonService;
import domain.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RegisterConfirm extends RequestHandler
{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    {
        String destination = "index.jsp";
        List<String> errors = new ArrayList<String>();

        String username = request.getParameter("username");
        if (username == null || username.isEmpty())
        {
            errors.add("No username given");
        }

        String password = request.getParameter("password");
        if (password == null || password.isEmpty())
        {
            errors.add("No password given");
        }

        String passwordConf = request.getParameter("passwordConfirmation");
        if (passwordConf == null || password.isEmpty())
        {
            errors.add("Your password confirmation is empty");
        }
        else if(!password.equals(passwordConf))
        {
            errors.add("Your passwords are not the same");
        }


        String firstName = request.getParameter("firstName");
        if (firstName == null || firstName.isEmpty())
        {
            errors.add("No first name given");
        }

        String lastName = request.getParameter("lastName");
        if (lastName == null || lastName.isEmpty())
        {
            errors.add("No last name given");
        }

        String email = request.getParameter("email");
        if (email == null || email.isEmpty())
        {
            errors.add("No email given");
        }
        else
        {
            Person testP = new Person();
            try
            {
                testP.setEmail(email);
            }
            catch (IllegalArgumentException e)
            {
                errors.add("Incorrect email");
            }
        }

        String geslacht = request.getParameter("geslacht");
        if (geslacht == null || geslacht.isEmpty())
        {
            errors.add("No gender given");
        }

        String leeftijdStr = request.getParameter("leeftijd");
        int leeftijd = 0;
        if (leeftijdStr == null || leeftijdStr.isEmpty())
        {
            errors.add("No age given");
        }
        else
        {
            leeftijd = Integer.parseInt(leeftijdStr);
            if(leeftijd <= 0)
            {
                errors.add("Incorrect age");
            }
        }


        if (errors.size() == 0)
        {
            Person person;
            if (!super.getPersonService().getPersonExists(username))
            {
                person = new Person(username, password, firstName, lastName, email, geslacht, leeftijd, UserStatus.OFFLINE);
                super.getPersonService().addPerson(person);
            }
            else
            {
                errors.add("User already exists");
            }
        }

        if (errors.size() > 0)
        {
            request.setAttribute("errors", errors);
            return new Register().handleRequest(request, response);
        }
        else
        {
            request.setAttribute("success", "You have created your account!");
        }

        return new Chat().handleRequest(request, response);
    }
}

package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Person;
import domain.UserStatus;

public class PersonRepositoryStub implements PersonRepository
{
    private Map<String, Person> persons = new HashMap<String, Person>();

    public PersonRepositoryStub()
    {
        Person administrator = new Person("bibAdmin", "t", "Bib", "Liothekaris",
                "bib@ucll.be", "man", 25, UserStatus.OFFLINE);
        add(administrator);
        Person jan = new Person("jan123", "t", "Jan", "Janssens",
                "jan@ucll.be" , "man", 19, UserStatus.OFFLINE);
        add(jan);
        Person an = new Person("an555", "t", "An", "Cornelissen",
                "an@ucll.be", "vrouw", 20, UserStatus.OFFLINE);
        add(an);
    }

    public Person get(String personId)
    {
        if (personId == null)
        {
            throw new IllegalArgumentException("No id given");
        }
        return persons.get(personId);
    }

    public List<Person> getAll()
    {
        return new ArrayList<Person>(persons.values());
    }

    public void add(Person person)
    {
        if (person == null)
        {
            throw new IllegalArgumentException("No person given");
        }
        if (persons.containsKey(person.getUserId()))
        {
            throw new IllegalArgumentException("User already exists");
        }
        persons.put(person.getUserId(), person);
    }

    public void update(Person person)
    {
        if (person == null)
        {
            throw new IllegalArgumentException("No person given");
        }
        persons.put(person.getUserId(), person);
    }

    public void delete(String personId)
    {
        if (personId == null)
        {
            throw new IllegalArgumentException("No id given");
        }
        persons.remove(personId);
    }

    public Person getAuthenticatedUser(String username, String password)
    {
        Person person = get(username);

        if (person != null && person.isCorrectPassword(password))
        {
            return person;
        }
        else
        {
            return null;
        }
    }
}

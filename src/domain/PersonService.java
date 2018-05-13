package domain;

import java.util.List;

import db.PersonRepository;
import db.PersonRepositoryStub;

public class PersonService
{
    private PersonRepository personRepository = new PersonRepositoryStub();

    public PersonService()
    {
    }

    public Person getPerson(String personId)
    {
        return getPersonRepository().get(personId);
    }

    public boolean getPersonExists(String personId)
    {
        if(getPerson(personId) == null || personId.isEmpty())
        {
            return false;
        }
        return true;
    }

    public List<Person> getPersons()
    {
        return getPersonRepository().getAll();
    }

    public void addPerson(Person person)
    {
        getPersonRepository().add(person);
    }

    public void updatePersons(Person person)
    {
        getPersonRepository().update(person);
    }

    public void deletePerson(String id)
    {
        getPersonRepository().delete(id);
    }

    public Person getAuthenticatedUser(String username, String password)
    {
        return getPersonRepository().getAuthenticatedUser(username, password);
    }

    public List<Person> getFriendsList(String userId)
    {
        return getPerson(userId).getFriendList();
    }

    public void addPersonToFriend(String userId, Person newFriend)
    {
        this.getPerson(userId).getFriendList().add(newFriend);;
    }

    private PersonRepository getPersonRepository()
    {
        return personRepository;
    }
}

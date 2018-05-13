package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Person
{
    private String userId;
    private String password;
    private String salt;
    private String firstName;
    private String lastName;
    private UserStatus userStatus;
    @JsonIgnore
    private List<Person> friendList = new ArrayList<>();


    public Person(String userId, String password, String firstName,
                  String lastName, UserStatus userStatus)
    {
        setUserId(userId);
        setHashedPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setUserStatus(userStatus);
    }

    public Person(String userId, String password, String salt,
                  String firstName, String lastName, UserStatus userStatus)
    {
        setUserId(userId);
        setPassword(password);
        setSalt(salt);
        setFirstName(firstName);
        setLastName(lastName);
        setUserStatus(userStatus);
    }

    public Person()
    {
    }

    public UserStatus getUserStatus()
    {
        return this.userStatus;
    }

    public String getStatusStr()
    {
        return this.userStatus.getUserStatus();
    }

    public void setUserStatus(UserStatus userStatus)
    {
        this.userStatus = userStatus;
    }


    public void setUserId(String userId)
    {
        if (userId.isEmpty())
        {
            throw new IllegalArgumentException("No id given");
        }
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isCorrectPassword(String password)
    {
        if (password.isEmpty())
        {
            throw new IllegalArgumentException("No password given");
        }
        return getPassword().equals(hashPassword(password, getSalt()));
    }

    public void setPassword(String password)
    {
        if (password.isEmpty())
        {
            throw new IllegalArgumentException("No password given");
        }
        this.password = password;
    }

    public void setHashedPassword(String password)
    {
        if (password.isEmpty())
        {
            throw new IllegalArgumentException("No password given");
        }
        this.password = hashPassword(password);
    }

    private String hashPassword(String password)
    {
        SecureRandom random = new SecureRandom();
        byte[] seed = random.generateSeed(20);

        String salt = new BigInteger(1, seed).toString(16);
        this.setSalt(salt);

        return hashPassword(password, salt);
    }

    private String hashPassword(String password, String seed)
    {
        String hashedPassword = null;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(salt.getBytes("UTF-8"));
            crypt.update(password.getBytes("UTF-8"));
            hashedPassword = new BigInteger(1, crypt.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new DomainException(e.getMessage(), e);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new DomainException(e.getMessage(), e);
        }
        return hashedPassword;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getSalt()
    {
        return salt;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        if (firstName.isEmpty())
        {
            throw new IllegalArgumentException("No firstname given");
        }
        this.firstName = firstName;// firstName;

    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        if (lastName.isEmpty())
        {
            throw new IllegalArgumentException("No last name given");
        }
        this.lastName = lastName;
    }

    public List<Person> getFriendList()
    {
        return friendList;
    }

    public Person getFriend(String userId)
    {
        Person actualFriend = null;
        for (Person friend: friendList)
        {
            if(friend.getUserId().equals(userId))
            {
                actualFriend = friend;
            }
        }
        return actualFriend;
    }

    public boolean friendInFriendListAlreadyExists(String userId)
    {
        if(this.getFriend(userId) == null || userId == null || userId.isEmpty())
            return false;
        return true;
    }

    public void addFriend(Person person)
    {
        friendList.add(person);
    }
}

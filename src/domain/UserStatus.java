package domain;

public enum UserStatus
{
    ONLINE("online"), OFFLINE("offline"), AWAY("away"), CUSTOM("");

    private String userStatus;

    UserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }

    UserStatus()
    {

    }

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }

    public void customStatus(String custom)
    {
        CUSTOM.setUserStatus(custom);
    }

    public static UserStatus strToStatus(String userStatus)
    {
        UserStatus status;
        if(userStatus == null || userStatus.isEmpty())
            return ONLINE;
        switch(userStatus.toUpperCase())
        {
            case("ONLINE"):
                status = ONLINE;
                status.setUserStatus(userStatus);
                break;
            case("OFFLINE"):
                status = OFFLINE;
                status.setUserStatus(userStatus);
                break;
            case("AWAY"):
                status = AWAY;
                status.setUserStatus(userStatus);
                break;
            default:
                status = CUSTOM;
                status.setUserStatus(userStatus);
        }
        return status;
    }
}

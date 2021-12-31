package hu.gemesi.taskmaker.common.rest.header;

public class ProjectHeader implements IProjectHeader{
    private String user;
    private String sessionToken;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}

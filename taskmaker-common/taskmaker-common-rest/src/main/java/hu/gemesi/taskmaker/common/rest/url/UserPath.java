package hu.gemesi.taskmaker.common.rest.url;

public interface UserPath {
    String USER_SERVICE = "userService";
    String AUTH = "/auth";
    String LOGIN = "/login";
    String LOGOUT = "/logout";
    String REFRESH = "/refresh";

    String USER_SERVICE_AUTH = USER_SERVICE + AUTH;
}

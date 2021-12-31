package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.common.rest.exception.BaseException;
import hu.gemesi.taskmaker.common.rest.url.UserPath;
import hu.gemesi.taskmaker.dto.common.common.BaseResponseElement;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginRequest;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginResponse;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(UserPath.USER_SERVICE_AUTH)
public interface IUserAuthRest {

    @Path(UserPath.LOGIN)
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    LoginResponse postLogin(LoginRequest loginRequest) throws BaseException;

    @Path(UserPath.REFRESH)
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    LoginResponse getRefreshToken() throws BaseException;

    @Path(UserPath.LOGOUT)
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponseElement getLogout() throws BaseException;
}

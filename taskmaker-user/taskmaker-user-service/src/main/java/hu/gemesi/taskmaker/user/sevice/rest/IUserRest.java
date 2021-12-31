package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.user.user.GroupResponse;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListResponse;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsResponse;
import hu.gemesi.taskmaker.dto.user.user.UserGroupListResponse;
import hu.gemesi.taskmaker.dto.user.user.UserLoginRequest;
import hu.gemesi.taskmaker.dto.user.user.UserModifyRequest;
import hu.gemesi.taskmaker.dto.user.user.UserPasswordModifyRequest;
import hu.gemesi.taskmaker.dto.user.user.UserPermissionRequest;
import hu.gemesi.taskmaker.dto.user.user.UserRegistrationRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rest/userService/user")
public interface IUserRest {

    @Path("/registration")
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse postCreateNewUser(UserRegistrationRequest userRegistrationRequest);

    @Path("/login")
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse postLogin(UserLoginRequest userLoginRequest);

    @Path("/termsOFConditions")
    @GET
    @Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
    Response getTermsOfConditions();

    @Path("/modify/data")
    @PUT
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse putModifyUser(UserModifyRequest userModifyRequest);

    @Path("/modify/password")
    @PUT
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse putModifyUserPassword(UserPasswordModifyRequest userPasswordModifyRequest);

    @Path("/checkPermission")
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse postCheckUserPermissions(UserPermissionRequest userPermissionRequest);

    @Path("/checkLogin/{username}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse getCheckLogin(@PathParam("username") String username);

    @Path("/logout/{username}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse getLogout(@PathParam("username") String username);

    @Path("/checkUserName/{username}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse getUserNameIsTaken(@PathParam("username") String username);

    @Path("/myGroups/{username}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    UserGroupListResponse getGroups(@PathParam("username") String username);

    @Path("/myGroups/exist/{username}/{groupName}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    GroupResponse getSpecificGroup(@PathParam("username") String username, @PathParam("groupName") String groupName);

    @Path("/information/{username}")
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    UserDetailsResponse getUserInformation(@PathParam("username") String username);

    @Path("/findUsers/{username}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8")
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    UserDetailsListResponse getUsers(@PathParam("username") String user, @QueryParam("username") String username,
            @QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname, @QueryParam("birthday") String birthday);

    @Path("/deleteUser/{deleteUsername}/{executorUsername}")
    @DELETE
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse deleteUser(@PathParam("deleteUsername") String deleteUsername, @PathParam("executorUsername") String executorUsername);

    @Path("/remove/users/{loggedInUser}/{removingUser}")
    @DELETE
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse deleteUsersFromSystem(@PathParam("loggedInUser") String loggedInUser, @PathParam("removingUser") String removingUser);
}

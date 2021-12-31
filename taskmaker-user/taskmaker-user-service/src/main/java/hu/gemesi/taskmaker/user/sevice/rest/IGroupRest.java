package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.user.user.AddedUserResponse;
import hu.gemesi.taskmaker.dto.user.user.AddingUserRequestType;
import hu.gemesi.taskmaker.dto.user.user.GenerateGroupRequest;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListResponse;

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

@Path("rest/userService/group")
public interface IGroupRest {

    @Path("/generate")
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse postGenerateGroup(GenerateGroupRequest generateGroupRequest);

    @Path("/finish/{groupName}/{username]")
    @PUT
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse postSetInactiveGroup(@PathParam("groupName") String groupName, @PathParam("username") String username);

    @Path("/add/users")
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    AddedUserResponse postAddUsersToGroup(AddingUserRequestType addingUserRequest);

    @Path("/remove/{loggedInUser}/{group}/{removingUser}")
    @DELETE
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    BaseResponse deleteUserFromGroup(@PathParam("loggedInUser") String loggedInUser, @PathParam("group") String group,
            @PathParam("removingUser") String removingUser);

    @Path("/add/{username}/{groupName}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8")
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
    UserDetailsListResponse getUsersNotInGroup(@PathParam("username") String user, @PathParam("groupName") String groupName,
                                               @QueryParam("username") String username, @QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname,
                                               @QueryParam("birthday") String birthday);

}

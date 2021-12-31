package hu.gemesi.taskmaker.matcher.service.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.PointListResponse;
import hu.gemesi.taskmaker.dto.common.user.AddingUserRequest;
import hu.gemesi.taskmaker.dto.common.user.AddingUserRequestType;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/internal/task")
public interface ITaskInternalRest {

    @Path("/checkTask/active/{taskName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse getCheckActiveTask(@PathParam("taskName") String taskName);

    @Path("/checkTask/exist/{taskName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse getCheckTaskExists(@PathParam("taskName") String taskName);

    @Path("/checkTask/solution/added/{taskName}/{username}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse getCheckTaskAddedSolution(@PathParam("taskName") String taskName, @PathParam("username") String username);

    @Path("/result/points/{user}/{groupName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    PointListResponse getAllResultPointsByGroup(@PathParam("user") String user, @PathParam("groupName") String groupName);

    @Path("/add/users")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse postAddUserToCreatedTask(AddingUserRequestType addingUserRequest);

    @Path("/remove/user/{removerUser}/{removingUser}/{groupName}")
    @DELETE
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse deleteUserFromCreatedTask(@PathParam("removerUser") String removerUser, @PathParam("removingUser") String removingUser, @PathParam("groupName") String groupName);

}

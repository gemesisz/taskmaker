package hu.gemesi.taskmaker.matcher.service.rest;


import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.CreateTaskRequest;
import hu.gemesi.taskmaker.dto.common.matcher.ResultUploadRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SaveResultRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SolutionResponse;
import hu.gemesi.taskmaker.dto.common.matcher.TaskListResponse;
import hu.gemesi.taskmaker.dto.common.matcher.TaskResponse;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/task")
public interface ITaskRest {

    @Path("/create")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse postCreateTask(CreateTaskRequest createTaskRequest);

    @Path("/uploadResult")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse postUploadFile(ResultUploadRequest resultUploadRequest);

    @Path("/result/save")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse postSaveResult(@Valid SaveResultRequest saveResultRequest);

    /*@Path("/get/{taskName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    TaskResponse getTask(@PathParam("taskName") String taskName);*/

    @Path("/findByGroupName/{username}/{groupName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    TaskListResponse getTaskByGroupName(@PathParam("username") String username, @PathParam("groupName") String groupName);

    @Path("/result/{task}/{username}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    SolutionResponse getResults(@PathParam("task") String task, @PathParam("username") String username);

    @Path("/solution/file/{group}/{task}/{filePath}")
    @GET
    @Produces(value = {MediaType.APPLICATION_OCTET_STREAM})
    Response getSolutionFile(@PathParam("group") String group, @PathParam("task") String task, @PathParam("filePath") String filePath);

    @Path("/remove/solution/{removerUser}/{taskName}")
    @DELETE
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    BaseResponse deleteSolution(@PathParam("removerUser") String removerUser, @PathParam("taskName") String taskName);
}

package hu.gemesi.taskmaker.matcher.service.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.CreateTaskRequest;
import hu.gemesi.taskmaker.dto.common.matcher.ResultUploadRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SaveResultRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SolutionResponse;
import hu.gemesi.taskmaker.dto.common.matcher.TaskListResponse;
import hu.gemesi.taskmaker.matcher.service.action.TaskAction;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Model
public class TaskRest implements ITaskRest {

    @Inject
    private TaskAction taskAction;

    @Override
    public BaseResponse postCreateTask(CreateTaskRequest createTaskRequest) {
        return taskAction.createTask(createTaskRequest);
    }

    @Override
    public BaseResponse postUploadFile(ResultUploadRequest resultUploadRequest) {
        return taskAction.uploadResult(resultUploadRequest);
    }

    @Override
    public BaseResponse postSaveResult(SaveResultRequest saveResultRequest) {
        return taskAction.saveResult(saveResultRequest);
    }


    /*@Override
    public TaskResponse getTask(String taskName) {
        return taskAction.getTask(taskName);
    }*/

    @Override
    public TaskListResponse getTaskByGroupName(String username, String groupName) {
        return taskAction.getTaskByGroup(username, groupName);
    }

    @Override
    public SolutionResponse getResults(String task, String username) {
        return taskAction.getSolution(task, username);
    }

    @Override
    public Response getSolutionFile(String group, String task, String filePath) {
        return taskAction.getSolutionFile(group, task, filePath);
    }

    @Override
    public BaseResponse deleteSolution(String removerUser, String taskName) {
        return taskAction.deleteSolution(removerUser, taskName);
    }
}

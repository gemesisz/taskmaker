package hu.gemesi.taskmaker.matcher.service.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.PointListResponse;
import hu.gemesi.taskmaker.dto.common.user.AddingUserRequestType;
import hu.gemesi.taskmaker.matcher.service.action.TaskInternalAction;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class TaskInternalRest implements ITaskInternalRest{

    @Inject
    private TaskInternalAction taskInternalAction;

    @Override
    public BaseResponse getCheckActiveTask(String taskName) {
        return taskInternalAction.checkActiveTask(taskName);
    }

    @Override
    public BaseResponse getCheckTaskExists(String taskName) {
        return taskInternalAction.checkTaskExist(taskName);
    }

    @Override
    public BaseResponse getCheckTaskAddedSolution(String taskName, String username) {
        return taskInternalAction.checkTaskSolutionAdded(taskName, username);
    }

    @Override
    public PointListResponse getAllResultPointsByGroup(String user, String groupName) {
        return taskInternalAction.getPointsForUser(user, groupName);
    }

    @Override
    public BaseResponse postAddUserToCreatedTask(AddingUserRequestType addingUserRequest) {
        return taskInternalAction.addUsersToTask(addingUserRequest);
    }

    @Override
    public BaseResponse deleteUserFromCreatedTask(String removerUser, String removingUser, String groupName) {
        return taskInternalAction.removeUserFromTask(removerUser, removingUser, groupName);
    }
}

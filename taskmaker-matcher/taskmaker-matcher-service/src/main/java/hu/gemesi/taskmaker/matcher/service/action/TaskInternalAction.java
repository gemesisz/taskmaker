package hu.gemesi.taskmaker.matcher.service.action;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.common.model.task.Task;
import hu.gemesi.taskmaker.common.model.task.TaskAssignment;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.PointListResponse;
import hu.gemesi.taskmaker.dto.common.matcher.PointsType;
import hu.gemesi.taskmaker.dto.common.user.AddingUserRequestType;
import hu.gemesi.taskmaker.dto.common.user.AddingUserType;
import hu.gemesi.taskmaker.matcher.service.service.TaskAssignmentService;
import hu.gemesi.taskmaker.matcher.service.service.TaskService;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskInternalAction extends BaseAction {

    @Inject
    private TaskService taskService;

    @Inject
    private TaskAssignmentService taskAssignmentService;

    @Inject
    private UserLoginHelper userLoginHelper;

    public BaseResponse checkTaskExist(String taskName) {
        Task task = taskService.findByName(URLDecoder.decode(taskName, StandardCharsets.UTF_8));
        if (task == null) {
            return createInvalidResponse();
        }
        return createOKResponse();
    }

    public BaseResponse checkActiveTask(String taskName) {
        Task task = taskService.findByName(URLDecoder.decode(taskName, StandardCharsets.UTF_8));
        if (task == null || task.getDeadline().compareTo(LocalDateTime.now()) < 0) {
            return createInvalidResponse();
        }
        return createOKResponse();
    }

    public PointListResponse getPointsForUser(String loggedInUser, String groupName) {
        var response = new PointListResponse();
        if (userLoginHelper.isLoggedIn(loggedInUser)) {
            Group group = taskService.findByGroupName(groupName);
            for (Grouping grouping : group.getGroupingList()) {
                List<TaskAssignment> taskAssignmentList = taskAssignmentService.findByGroupAndUser(group, grouping.getUser());
                int point = 0;
                for (TaskAssignment taskAssignment : taskAssignmentList) {
                    point += taskAssignment.getPoint();
                }
                response.getPointInfo().add(new PointsType().withUsername(grouping.getUser().getUsername()).withPoint(point));
            }
            return response.withResult(createOKResponse().getResult());
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }

    @Transactional
    public BaseResponse addUsersToTask(AddingUserRequestType addingUserRequest) {
        var response = new BaseResponse();
        if (userLoginHelper.isLoggedIn(addingUserRequest.getUsername())) {
            Group group = taskService.findByGroupName(addingUserRequest.getGroupName());
            CustomerUser adder = taskService.findByUserName(addingUserRequest.getUsername());
            if (group.getAdmin().equals(adder)) {
                List<TaskAssignment> taskAssignmentList = taskAssignmentService.findByGroup(group);
                List<Task> taskList = new ArrayList<>();
                for (TaskAssignment taskAssignment : taskAssignmentList) {
                    if (!taskList.contains(taskAssignment.getTask())) {
                        taskList.add(taskAssignment.getTask());
                    }
                }
                for (Task task : taskList) {
                    for (AddingUserType addingUserType : addingUserRequest.getUserList().getUser()) {
                        System.out.println(addingUserType.getUsername());
                        var taskAssignment = new TaskAssignment();
                        taskAssignment.setTask(task);
                        taskAssignment.setGroup(group);
                        taskAssignment.setUser(taskService.findByUserName(addingUserType.getUsername()));
                        taskAssignmentService.save(taskAssignment);
                    }
                }
                return response.withResult(createOKResponse().getResult());
            }
            return response.withResult(createInvalidResponse().getResult());
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }

    @Transactional
    public BaseResponse removeUserFromTask(String removerUser, String removingUser, String groupName) {
        var response = new BaseResponse();
        if (userLoginHelper.isLoggedIn(removerUser)) {
            Group group = taskService.findByGroupName(groupName);
            CustomerUser remover = taskService.findByUserName(removerUser);
            if (group.getAdmin().equals(remover)) {
                CustomerUser removing = taskService.findByUserName(removingUser);
                List<TaskAssignment> taskAssignmentList = taskAssignmentService.findByGroupAndUser(group, removing);
                for (TaskAssignment taskAssignment : taskAssignmentList) {
                    if (taskAssignment.getFileName() != null && !taskAssignment.getFileName().isEmpty()) {
                        File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + taskAssignment.getFileName());
                        file.delete();
                    }
                    taskAssignmentService.delete(taskAssignment);
                }
                return response.withResult(createOKResponse().getResult());
            }
            return response.withResult(createInvalidResponse().getResult());
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }

    public BaseResponse checkTaskSolutionAdded(String taskName, String username) {
        taskName = URLDecoder.decode(taskName, StandardCharsets.UTF_8);
        username = URLDecoder.decode(username, StandardCharsets.UTF_8);
        if (!userLoginHelper.isLoggedIn(username)) {
            return createNotPermittedResponse();
        }
        Task task = taskService.findByName(taskName);
        CustomerUser customerUser = taskService.findByUserName(username);
        for (TaskAssignment taskAssignment : task.getTaskAssignmentList()) {
            if (taskAssignment.getUser().equals(customerUser)) {
                if (taskAssignment.getFileName() != null || taskAssignment.getOptionalSolution() != null) {
                    return createOKResponse();
                }
            }
        }
        return createNotPermittedResponse();
    }
}

package hu.gemesi.taskmaker.matcher.service.action;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.common.model.task.Task;
import hu.gemesi.taskmaker.common.model.task.TaskAssignment;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.model.user.UserRole;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.matcher.CreateTaskRequest;
import hu.gemesi.taskmaker.dto.common.matcher.ResultUploadRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SaveResultRequest;
import hu.gemesi.taskmaker.dto.common.matcher.SolutionResponse;
import hu.gemesi.taskmaker.dto.common.matcher.SolutionType;
import hu.gemesi.taskmaker.dto.common.matcher.TaskListResponse;
import hu.gemesi.taskmaker.dto.common.matcher.TaskListResponseType;
import hu.gemesi.taskmaker.matcher.service.convert.ModelToType;
import hu.gemesi.taskmaker.matcher.service.service.TaskAssignmentService;
import hu.gemesi.taskmaker.matcher.service.service.TaskService;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskAction extends BaseAction {

    @Inject
    private TaskService taskService;

    @Inject
    private TaskAssignmentService taskAssignmentService;

    @Inject
    private UserLoginHelper userLoginHelper;

    @Transactional
    public BaseResponse createTask(CreateTaskRequest createTaskRequest) {
        CustomerUser customerUser = taskService.findByUserName(createTaskRequest.getUsername());
        Group group = taskService.findByGroupName(createTaskRequest.getGroupName());
        if (customerUser == null || group == null) {
            return createInvalidResponse();
        }
        if ((customerUser.getRole() != UserRole.ADMIN && customerUser.getRole() != UserRole.GROUP_ADMIN) || !userLoginHelper.isLoggedIn(customerUser.getUsername())) {
            return createNotPermittedResponse();
        }
        var task = new Task();
        if (customerUser.equals(group.getAdmin())) {
            task.setName(createTaskRequest.getTaskName());
            task.setDescription(createTaskRequest.getDescription());
            task.setPoint(createTaskRequest.getPoint());
            task.setDeadline(createTaskRequest.getDeadline().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            task.setCreator(customerUser);
            Task savedTask = taskService.save(task);
            var taskAssignment = new TaskAssignment();
            taskAssignment.setTask(savedTask);
            taskAssignment.setGroup(group);
            taskAssignment.setUser(group.getAdmin());
            for (Grouping grouping : group.getGroupingList()) {
                var assignment = new TaskAssignment();
                assignment.setGroup(group);
                assignment.setUser(grouping.getUser());
                assignment.setTask(savedTask);
                taskAssignmentService.save(assignment);
            }
            return createOKResponse();
        }
        return createInvalidResponse();

    }

    /*public TaskResponse getTask(String taskName) {
        Task task = taskService.findByName(taskName);
        var response = new TaskResponse();
        if (task == null) {
            return response.withResult(createInvalidResponse().getResult());
        }
        return response.withResult(createOKResponse().getResult()).withTask(ModelToType.modelToType(task));
    }*/

    public TaskListResponse getTaskByGroup(String username, String groupName) {
        CustomerUser customerUser = taskService.findByUserName(username);
        Group group = taskService.findByGroupName(groupName);
        var response = new TaskListResponse();
        if (customerUser == null || group == null) {
            return response.withResult(createInvalidResponse().getResult());
        }
        if (!userLoginHelper.isLoggedIn(customerUser.getUsername())) {
            return response.withResult(createNotPermittedResponse().getResult());
        }
        List<TaskAssignment> taskAssignment = taskAssignmentService.findByGroup(group);
        var taskList = new TaskListResponseType();
        var taskAdded = new ArrayList<Task>();
        if (customerUser.equals(group.getAdmin())) {
            for (TaskAssignment assignment : taskAssignment) {
                Task task = assignment.getTask();
                if (!taskAdded.contains(task)) {
                    taskList.getTaskList().add(ModelToType.modelToType(task));
                    taskAdded.add(task);
                }
            }
        } else {
            for (TaskAssignment assignment : taskAssignment) {
                Task task = assignment.getTask();
                if (!taskAdded.contains(task) && assignment.getUser().equals(customerUser)) {
                    taskList.getTaskList().add(ModelToType.modelToType(task, assignment));
                    taskAdded.add(task);
                }
            }
        }
        return response.withResult(createOKResponse().getResult()).withTaskList(taskList);
    }


    @Transactional
    public BaseResponse uploadResult(ResultUploadRequest resultUploadRequest) {
        CustomerUser customerUser = taskService.findByUserName(resultUploadRequest.getUsername());
        Task task = taskService.findByName(resultUploadRequest.getTaskName());
        if (customerUser == null || task == null) {
            return createInvalidResponse();
        }

        TaskAssignment taskAssign = null;
        for (TaskAssignment taskAssignment : task.getTaskAssignmentList()) {
            if (taskAssignment.getGroup().getName().equals(resultUploadRequest.getGroupName()) && taskAssignment.getUser().equals(customerUser)) {
                if (taskAssign != null) {
                    return createInvalidResponse();
                }
                taskAssign = taskAssignment;
            }
        }
        if (taskAssign == null) {
            return createInvalidResponse();
        }
        if (taskAssign.getPoint() != 0 || (taskAssign.getResultComment() != null && !taskAssign.getResultComment().isEmpty())) {
            taskAssign.setPoint(0);
            taskAssign.setResultComment(null);
        }
        if (resultUploadRequest.getFileResult().getFileName() != null || !resultUploadRequest.getFileResult().getFileName().isEmpty() ||
                resultUploadRequest.getFileResult().getData() != null || resultUploadRequest.getFileResult().getData().length != 0 ) {
            if (taskAssign.getFileName() != null && !taskAssign.getFileName().isEmpty()) {
                File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + taskAssign.getFileName());
                file.delete();
            }
            taskAssign.setOptionalSolution(resultUploadRequest.getOptionalResult());
            var path = resultUploadRequest.getGroupName() + "/" + task.getName() + "/" + customerUser.getUsername() + "_" + resultUploadRequest.getFileResult().getFileName();
            var file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + path);
            try {
                FileUtils.writeByteArrayToFile(file, resultUploadRequest.getFileResult().getData());
            } catch (IOException ioException) {
                return createResponse("UNSUPPORTED_FILE", 500, "Error while saving the file!");
            }
            taskAssign.setFileName(path);
        }
        taskAssignmentService.save(taskAssign);
        return createOKResponse();
    }

    public SolutionResponse getSolution(String task, String username) {
        var response = new SolutionResponse();
        if (userLoginHelper.isLoggedIn(username)) {
            Task taskEntity = taskService.findByName(task);
            if (task == null) {
                return response.withResult(createInvalidResponse().getResult());
            }
            for (TaskAssignment taskAssignment : taskEntity.getTaskAssignmentList()) {
                String fullPath = "C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + taskAssignment.getFileName();
                response.withSolutionItem(new SolutionType().withSolution(taskAssignment.getOptionalSolution()).withFullPath(fullPath)
                        .withFileName(taskAssignment.getFileName()).withUsername(taskAssignment.getUser().getUsername()));
            }
            return response.withResult(createOKResponse().getResult());
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }

    public Response getSolutionFile(String group, String task, String filePath) {
        String path = group + "/" + task + "/" + filePath;
        File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + path);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                 .header("Content-Disposition", "attachment; filename=\"" + filePath + "\"").build();
    }

    @Transactional
    public BaseResponse saveResult(SaveResultRequest saveResultRequest) {
        if (userLoginHelper.isLoggedIn(saveResultRequest.getUsername())) {
            Task task = taskService.findByName(saveResultRequest.getTask());
            for (TaskAssignment taskAssignment : task.getTaskAssignmentList()) {
                if (taskAssignment.getUser().equals(taskService.findByUserName(saveResultRequest.getUser()))) {
                    if (saveResultRequest.getPoints() <= task.getPoint()) {
                        taskAssignment.setPoint(saveResultRequest.getPoints());
                        taskAssignment.setResultComment(saveResultRequest.getResultComment());
                        taskAssignmentService.save(taskAssignment);
                    }
                }
            }
            return createOKResponse();
        }
        return createNotPermittedResponse();
    }

    @Transactional
    public BaseResponse deleteSolution(String removerUser, String taskName) {
       if (userLoginHelper.isLoggedIn(removerUser)) {
           Task task = taskService.findByName(taskName);
           CustomerUser customerUser = taskService.findByUserName(removerUser);
           if (task == null) {
               return createInvalidResponse();
           }
           if (customerUser.getRole() != UserRole.TASK_SOLVER) {
               return createNotPermittedResponse();
           }
           for (TaskAssignment taskAssignment : task.getTaskAssignmentList()) {
               if (taskAssignment.getUser().equals(customerUser) && taskAssignment.getFileName() != null && !taskAssignment.getFileName().isEmpty()) {
                   File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/Tasks/" + taskAssignment.getFileName());
                   file.delete();
                   taskAssignment.setOptionalSolution(null);
                   taskAssignment.setFileName(null);
                   taskAssignment.setPoint(0);
                   taskAssignment.setResultComment(null);
                   taskAssignmentService.save(taskAssignment);
                   return createOKResponse();
               }
           }
       }
       return createNotPermittedResponse();
    }
}

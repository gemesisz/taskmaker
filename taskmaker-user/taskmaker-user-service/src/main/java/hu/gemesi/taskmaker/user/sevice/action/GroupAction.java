package hu.gemesi.taskmaker.user.sevice.action;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import hu.gemesi.taskmaker.common.rest.client.MatcherClient;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.user.user.AddedUserResponse;
import hu.gemesi.taskmaker.dto.user.user.AddingUserRequestType;
import hu.gemesi.taskmaker.dto.user.user.AddingUserType;
import hu.gemesi.taskmaker.dto.user.user.GenerateGroupRequest;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListResponse;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListType;
import hu.gemesi.taskmaker.dto.user.user.UserFullDetailsListType;
import hu.gemesi.taskmaker.user.sevice.convert.ModelToType;
import hu.gemesi.taskmaker.user.sevice.helper.FindUserHelper;
import hu.gemesi.taskmaker.user.sevice.service.GroupService;
import hu.gemesi.taskmaker.user.sevice.service.GroupingService;
import hu.gemesi.taskmaker.user.sevice.service.UserService;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class GroupAction extends BaseAction {

    @Inject
    private GroupService groupService;

    @Inject
    private UserService userService;

    @Inject
    private GroupingService groupingService;

    @Inject
    private UserLoginHelper userLoginHelper;

    @Inject
    private FindUserHelper findUserHelper;

    @Inject
    private ModelToType modelToType;

    @Inject
    private MatcherClient matcherClient;

    @Transactional
    public BaseResponse postGenerateGroup(GenerateGroupRequest generateGroupRequest) {
        var group = new Group();
        CustomerUser admin = userService.findByUserName(generateGroupRequest.getCustomerUser());
        if (admin == null) {
            return createInvalidResponse();
        }
        List<Group> allGroup = groupService.findByAdmin(admin);
        for (Group usedGroup : allGroup) {
            if (usedGroup.isActive()) {
                return createNotPermittedResponse();
            }
        }
        group.setAdmin(admin);
        group.setName(generateGroupRequest.getGroupName());
        Group savedGroup = groupService.save(group);
        List<CustomerUser> savedCustomerUser = new ArrayList<>();
        List<CustomerUser> okCustomerUser = userService.findByRole(1);
        for (int i = 0; savedCustomerUser.size() < 10 && i < okCustomerUser.size(); i++) {
            var user = okCustomerUser.get(i);
            /*if (user.getRole() == UserRole.TASK_SOLVER) {
                boolean usedUser = true;
                for (Grouping grouping : user.getGroupingList()) {
                    if (grouping.getGroup().isActive()) {
                        usedUser = false;
                        break;
                    }
                }
                if (usedUser) {
                    savedCustomerUser.add(user);
                }
            }*/
        }
        if (savedCustomerUser.size() == 0) {
            return createResponse("ALL_USER_BUSY", 402, "Currently there are no user who can join the group!");
        }

        for (CustomerUser customerUser : savedCustomerUser) {
            var grouping = new Grouping();
            grouping.setGroup(savedGroup);
            grouping.setUser(customerUser);
            groupingService.save(grouping);
        }
        return createOKResponse();
    }

    @Transactional
    public BaseResponse putSetInactiveGroup(String groupName, String username) {
        Group group = groupService.findByName(groupName);
        if (userLoginHelper.isLoggedIn(username)) {
            if (group == null) {
                return createInvalidResponse();
            }
            if (!group.getAdmin().equals(userService.findByUserName(username))) {
                return createNotPermittedResponse();
            }
            group.setActive(false);
            groupService.save(group);
            return createOKResponse();

        }
        return createNotPermittedResponse();
    }

    @Transactional
    public AddedUserResponse postAddUsersToGroup(AddingUserRequestType addingUserRequest) {
        var response = new AddedUserResponse();
        if(userLoginHelper.isLoggedIn(addingUserRequest.getUsername())) {
            Group group = groupService.findByName(addingUserRequest.getGroupName());
            CustomerUser adder = userService.findByUserName(addingUserRequest.getUsername());
            if (group == null) {
                return response.withResult(createInvalidResponse().getResult());
            }
            /*if (!group.getAdmin().equals(adder) || (adder.getRole() != UserRole.GROUP_ADMIN && adder.getRole() != UserRole.ADMIN)) {
                return response.withResult(createNotPermittedResponse().getResult());
            }*/
            response.withUserDetails(new UserDetailsListType());
            List<CustomerUser> addedCustomerUsers = new ArrayList<>();
            try {
                HttpResponse httpResponse = matcherClient.postAddUsersToTask(addingUserRequest);
                String entity = EntityUtils.toString(httpResponse.getEntity());
            } catch (Exception e) {
                return response.withResult(createResponse("ADDING_ERROR", 500, "Error occurred while adding task to the users!").getResult());
            }
            for (AddingUserType addingUserType: addingUserRequest.getCustomerUserList().getCustomerUser()) {
                CustomerUser customerUser = userService.findByUserName(addingUserType.getUsername());
                if (!addedCustomerUsers.contains(customerUser)) {
                    boolean isAdded = false;
                    for (Grouping grouping: group.getGroupingList()) {
                        if (grouping.getUser().equals(customerUser)) {
                            isAdded = true;
                            break;
                        }
                    }
                    if (!isAdded) {
                        var grouping = new Grouping();
                        grouping.setUser(customerUser);
                        grouping.setGroup(group);
                        groupingService.save(grouping);
                        response.getUserDetails().getCustomerUserList().add(modelToType.modelToType(customerUser));
                        addedCustomerUsers.add(customerUser);
                    }
                }
            }
        }

        return response.withResult(createOKResponse().getResult());
    }


    @Transactional
    public BaseResponse deleteUserFromGroup(String loggedInUser, String groupName, String removingUser) {
        var response = new BaseResponse();
        if(userLoginHelper.isLoggedIn(loggedInUser)) {
            Group group = groupService.findByName(groupName);
            CustomerUser remover = userService.findByUserName(loggedInUser);
            CustomerUser customerUser = userService.findByUserName(removingUser);
            if (customerUser == null || group == null) {
                return response.withResult(createInvalidResponse().getResult());
            }
           /* if (!group.getAdmin().equals(remover) || (remover.getRole() != UserRole.GROUP_ADMIN && remover.getRole() != UserRole.ADMIN)) {
                return response.withResult(createNotPermittedResponse().getResult());
            }*/
            try {
                HttpResponse httpResponse = matcherClient.deleteUserFromTask(loggedInUser, removingUser, groupName);
                String entity = EntityUtils.toString(httpResponse.getEntity());
            } catch (Exception e) {
                return response.withResult(createResponse("DELETE_ERROR", 500, "Error occurred while deleting the user's task!").getResult());
            }
            for (Grouping grouping : group.getGroupingList()) {
                if (grouping.getGroup().equals(group)) {
                    if (grouping.getUser().equals(customerUser)) {
                        groupingService.delete(grouping);
                        break;
                    }
                }
            }
        }
        return response.withResult(createOKResponse().getResult());
    }

    public UserDetailsListResponse getUsersNotInGroup(String user, String groupName, String username, String firstname, String lastname, String birthday) {
        var response = new UserDetailsListResponse();
        var userFullDetailsListType = new UserFullDetailsListType();
        if (userLoginHelper.isLoggedIn(user)) {
            try {
                List<CustomerUser> customerUserList = findUserHelper.findUsers(username, firstname, lastname, birthday);
                response.setResult(createOKResponse().getResult());
                for (CustomerUser foundCustomerUser : customerUserList) {
                    boolean addable = true;
                    for (Grouping grouping: foundCustomerUser.getGroupingList()) {
                        if (grouping.getGroup().isActive()) {
                            addable = false;
                            break;
                        }
                    }
                    /*if (foundCustomerUser.getRole() == UserRole.TASK_SOLVER && addable) {
                        userFullDetailsListType.withUserList(modelToType.modelToFullType(foundCustomerUser));
                    }*/
                }
            } catch (DateTimeParseException dateTimeParseException) {
                response.setResult(createResponse("INVALID_DATE", 200, dateTimeParseException.getLocalizedMessage()).getResult());
            }
            return response.withResult(createOKResponse().getResult()).withUserDetailsList(userFullDetailsListType);
        }
        return response.withResult(createNotPermittedResponse().getResult()).withUserDetailsList(userFullDetailsListType);
    }
}

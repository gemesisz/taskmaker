package hu.gemesi.taskmaker.user.sevice.action;

import com.google.common.hash.Hashing;
import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.model.user.SecurityUser;
import hu.gemesi.taskmaker.common.model.user.UserRole;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import hu.gemesi.taskmaker.common.rest.client.MatcherClient;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.user.user.GroupResponse;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListResponse;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsResponse;
import hu.gemesi.taskmaker.dto.user.user.UserFullDetailsListType;
import hu.gemesi.taskmaker.dto.user.user.UserGroupListResponse;
import hu.gemesi.taskmaker.dto.user.user.UserLoginRequest;
import hu.gemesi.taskmaker.dto.user.user.UserModifyRequest;
import hu.gemesi.taskmaker.dto.user.user.UserPasswordModifyRequest;
import hu.gemesi.taskmaker.dto.user.user.UserPermissionRequest;
import hu.gemesi.taskmaker.dto.user.user.UserRegistrationRequest;
import hu.gemesi.taskmaker.rest.service.PermissionService;
import hu.gemesi.taskmaker.user.sevice.convert.ModelToType;
import hu.gemesi.taskmaker.user.sevice.helper.FindUserHelper;
import hu.gemesi.taskmaker.user.sevice.helper.UserPermissionHelper;
import hu.gemesi.taskmaker.user.sevice.service.GroupService;
import hu.gemesi.taskmaker.user.sevice.service.GroupingService;
import hu.gemesi.taskmaker.user.sevice.service.SecurityUserService;
import hu.gemesi.taskmaker.user.sevice.service.UserService;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UserAction extends BaseAction {

    @Inject
    private UserService userService;

    @Inject
    private GroupService groupService;

    @Inject
    private UserPermissionHelper userPermissionHelper;

    @Inject
    private UserLoginHelper userLoginHelper;

    @Inject
    private FindUserHelper findUserHelper;

    @Inject
    private ModelToType modelToType;

    @Inject
    private GroupingService groupingService;

    @Inject
    private MatcherClient matcherClient;

    @Inject
    private PermissionService permissionService;

    @Inject
    private SecurityUserService securityUserService;

    @Transactional
    public BaseResponse createUser(UserRegistrationRequest userRegistrationRequest) {
        if (userService.findByUserName(userRegistrationRequest.getUsername()) != null) {
            return createResponse("INVALID_USERNAME", 500, "The specified username already exists!");
        }
        var user = new CustomerUser();
        user.setUsername(userRegistrationRequest.getUsername());
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setBirthday(userRegistrationRequest.getBirthday());
        user = userService.save(user);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCustomerUser(user);
        securityUser.setUsername(securityUser.getUsername());
        securityUser.setPassword(securityUser.getPassword());
        securityUser.setUserRole(UserRole.valueOf(userRegistrationRequest.getRole().name()));
        securityUserService.save(securityUser);
        return createOKResponse();
    }

    public BaseResponse login(UserLoginRequest userLoginRequest) {
        /*var user = userService.findByUserName(userLoginRequest.getUsername());
        if (user == null) {
            return createResponse("INVALID_USERNAME", 500, "The specified username is not exists!");
        }
        if (user.getHashedPassword().equals(hashPassword(userLoginRequest.getPassword()))) {
            userLoginHelper.login(userLoginRequest.getUsername());
            return createOKResponse();
        }*/
        // return Response.status(500).entity(createResponse("INVALID_PASSWORD", 500, "The password is incorrect!")).build();
        return createResponse("INVALID_PASSWORD", 500, "The password is incorrect!");
    }

    public BaseResponse logout(String username) {
        var user = userService.findByUserName(username);
        if (user == null) {
            return createResponse("INVALID_USERNAME", 500, "The specified username is not exists!");
        }
        userLoginHelper.logout(user.getUsername());
        return createOKResponse();
    }

    protected String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public BaseResponse getUserNameIsTaken(String username) {
        CustomerUser customerUser = userService.findByUserName(username);
        if (customerUser == null) {
            return createOKResponse();
        }
        return createResponse("INVALID_USERNAME", 500, "The specified username already exists!");
    }

    @Transactional
    public BaseResponse deleteUser(String username, String executorUsername) {
        var executor = userService.findByUserName(executorUsername);
        /*if (executor == null || executor.getRole() != UserRole.ADMIN) {
            return createNotPermittedResponse();
        }
        var user = userService.findByUserName(username);
        if (user == null) {
            return createResponse("INVALID_USERNAME", 500, "The specified username is not exists!");
        }
        userService.delete(user);*/
        return createOKResponse();
    }

    public UserGroupListResponse getGroups(String username) {
        CustomerUser customerUser = userService.findByUserName(username);
        var response = new UserGroupListResponse();
        response.setResult(createOKResponse().getResult());
        response.withGroupList(new ArrayList<>());
        try {
            for (Grouping grouping : customerUser.getGroupingList()) {
                response.withGroupList(modelToType.modelToType(grouping.getGroup(), username));
            }
            List<Group> allGroup = groupService.findByAdmin(customerUser);
            for (Group usedGroup : allGroup) {
                response.getGroupList().add(modelToType.modelToType(usedGroup, username));
            }
            return response;
        } catch (Exception e) {
            return response.withResult((createResponse("PARSE_ERROR", 500, "Error occurred while parsing the groups!").getResult()));
        }
    }

    public UserDetailsResponse getInformationAboutUser(String username) {
        CustomerUser customerUser = userService.findByUserName(username);
        var response = new UserDetailsResponse();
        if (customerUser == null || !userLoginHelper.isLoggedIn(customerUser.getUsername())) {
            return response.withResult(createNotPermittedResponse().getResult());
        }
        return response.withResult(createOKResponse().getResult()).withUserDetails(modelToType.modelToFullType(customerUser));
    }

    public boolean checkUserLogin(String username) {
        CustomerUser customerUser = userService.findByUserName(username);
        return checkUserLogin(customerUser);
    }

    protected boolean checkUserLogin(CustomerUser customerUser) {
        return userLoginHelper.isLoggedIn(customerUser.getUsername());
    }

    public UserDetailsListResponse findUsers(String user, String username, String firstname, String lastname, String birthday) {
        var response = new UserDetailsListResponse();
        var userFullDetailsListType = new UserFullDetailsListType();
        if (!userLoginHelper.isLoggedIn(user)) {
            return response.withResult(createNotPermittedResponse().getResult()).withUserDetailsList(userFullDetailsListType);
        }
        try {
            List<CustomerUser> customerUserList = findUserHelper.findUsers(username, firstname, lastname, birthday);
            response.setResult(createOKResponse().getResult());
            for (CustomerUser foundCustomerUser : customerUserList) {
                userFullDetailsListType.withCustomerUserList(modelToType.modelToFullType(foundCustomerUser));
            }
        } catch (DateTimeParseException dateTimeParseException) {
            response.setResult(createResponse("INVALID_DATE", 200, dateTimeParseException.getLocalizedMessage()).getResult());
        }
        return response.withUserDetailsList(userFullDetailsListType);
    }

    public BaseResponse checkUserPermission(UserPermissionRequest userPermissionRequest) {
        CustomerUser customerUser = userService.findByUserName(userPermissionRequest.getUsername());
        if (customerUser == null) {
            return createInvalidResponse();
        }
        if (!userPermissionHelper.checkPermission(userPermissionRequest.getPermission(), customerUser)) {
            return createNotPermittedResponse();
        }
        return createOKResponse();
    }

    public BaseResponse checkLogin(String username) {
        if (checkUserLogin(username)) {
            return createOKResponse();
        }
        return createInvalidResponse();
    }

    @Transactional
    public BaseResponse modifyUser(UserModifyRequest userModifyRequest) {
        if (userLoginHelper.isLoggedIn(userModifyRequest.getUsername())) {
            CustomerUser customerUser = userService.findByUserName(userModifyRequest.getUsername());
            if (customerUser == null) {
                return createInvalidResponse();
            }
            customerUser.setFirstName(userModifyRequest.getFirstName());
            customerUser.setLastName(userModifyRequest.getLastName());
            customerUser.setBirthday(userModifyRequest.getBirthday());
            userService.save(customerUser);
            return createOKResponse();
        }
        return createNotPermittedResponse();
    }

    @Transactional
    public BaseResponse modifyUserPassword(UserPasswordModifyRequest userPasswordModifyRequest) {
        if (userLoginHelper.isLoggedIn(userPasswordModifyRequest.getUsername())) {
            CustomerUser customerUser = userService.findByUserName(userPasswordModifyRequest.getUsername());
            if (customerUser == null) {
                return createInvalidResponse();
            }
            /*if (customerUser.getHashedPassword().equals(hashPassword(userPasswordModifyRequest.getOldPassword()))) {
                customerUser.setHashedPassword(hashPassword(userPasswordModifyRequest.getNewPassword()));
                userService.save(customerUser);
                return createOKResponse();
            }*/
            return createInvalidResponse();
        }
        return createNotPermittedResponse();
    }

    @Transactional
    public BaseResponse removeUsers(String loggedInUser, String removingUserName) {
        CustomerUser customerUser = userService.findByUserName(loggedInUser);
        if (customerUser == null) {
            return createInvalidResponse();
        }
        /*if (userLoginHelper.isLoggedIn(loggedInUser) && customerUser.getRole() == UserRole.ADMIN) {
            CustomerUser removingCustomerUser = userService.findByUserName(removingUserName);
            if (removingCustomerUser == null) {
                return createInvalidResponse();
            }
            for (Grouping grouping : removingCustomerUser.getGroupingList()) {
                try {
                    HttpResponse httpResponse = matcherClient.deleteUserFromTask(loggedInUser, grouping.getGroup().getName(), removingUserName);
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return createResponse("DELETE_ERROR", 500, "Error occurred while deleting the user's task!");
                }
                groupingService.delete(grouping);
            }
            userService.delete(removingCustomerUser);
            return createOKResponse();
        }*/
        return createNotPermittedResponse();
    }

    public Response getTermsOfConditions() {
        File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/DocumentResources/TermsOfConditions.pdf");
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + "TermsOfConditions.pdf" + "\"").build();
    }

    public GroupResponse getGroup(String username, String groupName) {
        var response = new GroupResponse();
        if (userLoginHelper.isLoggedIn(username)) {
            Group group = groupService.findByName(groupName);
            if (group == null) {
                return response.withResult(createInvalidResponse().getResult());
            }
            try {
                response.withGroup(modelToType.modelToType(group, username));
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return response.withResult((createResponse("PARSE_ERROR", 500, "Error occurred while parsing the group!").getResult()));
            }
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }
}

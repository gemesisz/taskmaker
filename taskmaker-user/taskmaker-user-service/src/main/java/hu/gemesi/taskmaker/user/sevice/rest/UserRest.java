package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.common.security.qualifier.Auth;
import hu.gemesi.taskmaker.common.security.role.AuthRole;
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
import hu.gemesi.taskmaker.user.sevice.action.UserAction;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Model
public class UserRest implements IUserRest {

    @Inject
    private UserAction userAction;

    @Override
    public BaseResponse postCreateNewUser(UserRegistrationRequest userRegistrationRequest) {
        return userAction.createUser(userRegistrationRequest);
    }

    @Override
    public BaseResponse postLogin(UserLoginRequest userLoginRequest) {
        return userAction.login(userLoginRequest);
    }

    @Override
    public Response getTermsOfConditions() {
        return userAction.getTermsOfConditions();
    }

    @Override
    public BaseResponse putModifyUser(UserModifyRequest userModifyRequest) {
        return userAction.modifyUser(userModifyRequest);
    }

    @Override
    public BaseResponse putModifyUserPassword(UserPasswordModifyRequest userPasswordModifyRequest) {
        return userAction.modifyUserPassword(userPasswordModifyRequest);
    }

    @Override
    public BaseResponse getCheckLogin(String username) {
        return userAction.checkLogin(username);
    }

    @Override
    public BaseResponse postCheckUserPermissions(UserPermissionRequest userPermissionRequest) {
        return userAction.checkUserPermission(userPermissionRequest);
    }

    @Override
    public BaseResponse getLogout(String username) {
        return userAction.logout(username);
    }

    @Override
    public BaseResponse getUserNameIsTaken(String username) {
        return userAction.getUserNameIsTaken(username);
    }

    @Override
    public BaseResponse deleteUser(String deleteUsername, String executorUsername) {
        return userAction.deleteUser(deleteUsername, executorUsername);
    }

    @Override
    @Auth(role = AuthRole.TASK_SOLVER)
    public UserGroupListResponse getGroups(String username) {
        return userAction.getGroups(username);
    }

    @Override
    public GroupResponse getSpecificGroup(String username, String groupName) {
        return userAction.getGroup(username, groupName);
    }

    @Override
    public UserDetailsResponse getUserInformation(String username) {
        return userAction.getInformationAboutUser(username);
    }

    @Override
    public UserDetailsListResponse getUsers(String user, String username, String firstname, String lastname, String birthday) {
        return userAction.findUsers(user, username, firstname, lastname, birthday);
    }

    @Override
    public BaseResponse deleteUsersFromSystem(String loggedInUser, String removingUser) {
        return userAction.removeUsers(loggedInUser, removingUser);
    }
}

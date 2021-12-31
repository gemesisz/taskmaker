package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.user.user.AddedUserResponse;
import hu.gemesi.taskmaker.dto.user.user.AddingUserRequestType;
import hu.gemesi.taskmaker.dto.user.user.GenerateGroupRequest;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListResponse;
import hu.gemesi.taskmaker.user.sevice.action.GroupAction;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class GroupRest implements IGroupRest {

    @Inject
    private GroupAction groupAction;

    @Override
    public BaseResponse postGenerateGroup(GenerateGroupRequest generateGroupRequest) {
        return groupAction.postGenerateGroup(generateGroupRequest);
    }

    @Override
    public BaseResponse postSetInactiveGroup(String groupName, String username) {
        return groupAction.putSetInactiveGroup(groupName, username);
    }

    @Override
    public AddedUserResponse postAddUsersToGroup(AddingUserRequestType addingUserRequest) {
        return groupAction.postAddUsersToGroup(addingUserRequest);
    }

    @Override
    public BaseResponse deleteUserFromGroup(String loggedInUser, String group, String removingUser) {
        return groupAction.deleteUserFromGroup(loggedInUser, group, removingUser);
    }

    @Override
    public UserDetailsListResponse getUsersNotInGroup(String user, String groupName, String username, String firstname, String lastname,
                                                      String birthday) {
        return groupAction.getUsersNotInGroup(user, groupName, username, firstname, lastname, birthday);
    }
}

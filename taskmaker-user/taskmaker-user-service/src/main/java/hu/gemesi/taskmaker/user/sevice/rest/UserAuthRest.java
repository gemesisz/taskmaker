package hu.gemesi.taskmaker.user.sevice.rest;

import hu.gemesi.taskmaker.common.rest.exception.BaseException;
import hu.gemesi.taskmaker.common.rest.header.ProjectHeader;
import hu.gemesi.taskmaker.common.security.qualifier.Auth;
import hu.gemesi.taskmaker.common.security.role.AuthRole;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.common.BaseResponseElement;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginRequest;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginResponse;
import hu.gemesi.taskmaker.user.sevice.action.UserAuthAction;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UserAuthRest implements IUserAuthRest {

    @Inject
    private UserAuthAction userAuthAction;

    @Inject
    private ProjectHeader projectHeader;

    @Override
    public LoginResponse postLogin(LoginRequest loginRequest) throws BaseException {
        return userAuthAction.postLogin(loginRequest);
    }

    @Override
    @Auth(role = AuthRole.ALL)
    public LoginResponse getRefreshToken() throws BaseException  {
        return userAuthAction.getRefreshToken(projectHeader.getUser(), projectHeader.getSessionToken());
    }

    @Override
    @Auth(role = AuthRole.ALL)
    public BaseResponseElement getLogout() throws BaseException  {
        return userAuthAction.getLogout(projectHeader.getUser(), projectHeader.getSessionToken());
    }
}

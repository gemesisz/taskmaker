package hu.gemesi.taskmaker.user.sevice.action;

import hu.gemesi.taskmaker.common.dto.enums.FaultType;
import hu.gemesi.taskmaker.common.model.user.SecurityUser;
import hu.gemesi.taskmaker.common.model.user.UserSession;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import hu.gemesi.taskmaker.common.rest.exception.BaseException;
import hu.gemesi.taskmaker.common.rest.exception.BusinessException;
import hu.gemesi.taskmaker.common.rest.service.UserSessionService;
import hu.gemesi.taskmaker.dto.common.common.BaseResponseElement;
import hu.gemesi.taskmaker.dto.common.common.RoleType;
import hu.gemesi.taskmaker.dto.common.common.SessionDataType;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginRequest;
import hu.gemesi.taskmaker.dto.userauth.userauth.LoginResponse;
import hu.gemesi.taskmaker.user.sevice.service.SecurityUserService;
import hu.gemesi.taskmaker.user.sevice.service.UserService;
import hu.icellmobilsoft.coffee.module.redis.annotation.RedisConnection;
import hu.icellmobilsoft.coffee.module.redis.service.RedisService;
import hu.icellmobilsoft.coffee.tool.utils.string.PasswordUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UserAuthAction extends BaseAction {

    @Inject
    @RedisConnection(configKey = "")
    private RedisService redisService;

    @Inject
    private UserService userService;

    @Inject
    private UserSessionService userSessionService;

    @Inject
    private SecurityUserService securityUserService;

    @Transactional
    public LoginResponse postLogin(LoginRequest loginRequest) throws BaseException {
        if (loginRequest == null) {
            throw newInvalidParameterException("loginRequest is missing!");
        }
        String password = PasswordUtil.encodeString(loginRequest.getPassword());
        SecurityUser securityUser = securityUserService.findByUsernameAndPassword(loginRequest.getUsername(), password)
                .orElseThrow(() -> new BusinessException(FaultType.INVALID_PASSWORD_OR_USER,
                        MessageFormat.format("Invalid username [{0}] or password [{1}]", loginRequest.getUsername(), password)));
        var response = new LoginResponse();
        OffsetDateTime now = OffsetDateTime.now();
        SessionDataType sessionDataType = createSession(securityUser, now);
        response.withSessionData(sessionDataType);
        createUserSession(securityUser, sessionDataType, now);
        handleSuccessResultType(response, loginRequest);
        return response;
    }

    @Transactional
    public LoginResponse getRefreshToken(String username, String sessionToken) throws BaseException {
        if (StringUtils.isAnyBlank(username, sessionToken)) {
            throw newInvalidParameterException("Username or sessionToken is missing!");
        }
        SecurityUser securityUser = securityUserService.findByUsername(username)
                .orElseThrow(() -> new BusinessException(FaultType.USER_NOT_EXISTS, MessageFormat.format("Invalid username [{0}]", username)));
        var response = new LoginResponse();
        OffsetDateTime now = OffsetDateTime.now();
        SessionDataType sessionDataType = createSession(securityUser, now);
        response.withSessionData(sessionDataType);
        createUserSession(securityUser, sessionDataType, now);
        handleSuccessResultType(response);
        return response;
    }

    @Transactional
    public BaseResponseElement getLogout(String username, String sessionToken) throws BaseException {
        if (StringUtils.isAnyBlank(username, sessionToken)) {
            throw newInvalidParameterException("Username or sessionToken is missing!");
        }
        SecurityUser securityUser = securityUserService.findByUsername(username)
                .orElseThrow(() -> new BusinessException(FaultType.USER_NOT_EXISTS, MessageFormat.format("Invalid username [{0}]", username)));
        closeSession(securityUser);
        expiryRedis(sessionToken, username);
        var response = new BaseResponseElement();
        handleSuccessResultType(response);
        return response;
    }

    private void expiryRedis(String sessionToken, String username) throws BaseException {
        try {
            redisService.expireRedisData(sessionToken, 0);
            redisService.expireRedisData(username, 0);
        } catch (hu.icellmobilsoft.coffee.dto.exception.BaseException e) {
            throw new BusinessException(FaultType.USER_NOT_EXISTS, e.getMessage());
        }
    }

    private void closeSession(SecurityUser securityUser) {
        List<UserSession> userSessions = userSessionService.findByUsername(securityUser.getUsername());
        for (UserSession userSession : userSessions) {
            userSession.setLoggedIn(false);
            userSession.setLastLogin(userSession.getActualLogin());
            userSessionService.save(userSession);
        }
    }

    private SessionDataType createSession(SecurityUser securityUser, OffsetDateTime now) throws BaseException {
        var sessionDataType = new SessionDataType();
        String sessionToken = RandomStringUtils.random(26);
        OffsetDateTime sessionTokenExpiry = now.plus(30L, ChronoUnit.MINUTES);
        OffsetDateTime refreshTokenExpiry = now.plus(12L, ChronoUnit.HOURS);
        String refreshToken = RandomStringUtils.random(26);
        sessionDataType.setSessionToken(sessionToken);
        sessionDataType.setRefreshToken(refreshToken);
        sessionDataType.setSessionEnd(sessionTokenExpiry);
        sessionDataType.setRefreshTokenExpiry(refreshTokenExpiry);
        sessionDataType.setUserId(securityUser.getUsername());
        sessionDataType.setRole(RoleType.fromValue(securityUser.getUserRole().name()));
        try {
            redisService.setRedisData(securityUser.getUsername(), 30L * 60, sessionDataType.getSessionToken());
            redisService.setRedisData(sessionToken, 30L * 60, sessionDataType);
        } catch (hu.icellmobilsoft.coffee.dto.exception.BaseException e) {
            throw new BusinessException(FaultType.USER_NOT_EXISTS, e.getMessage());
        }
        return sessionDataType;
    }

    private void createUserSession(SecurityUser securityUser, SessionDataType sessionDataType, OffsetDateTime now) {
        var userSession = new UserSession();
        userSession.setCustomerUser(securityUser.getCustomerUser());
        userSession.setSessionToken(sessionDataType.getSessionToken());
        userSession.setActualLogin(now.toLocalDateTime());
        userSession.setRefreshToken(sessionDataType.getRefreshToken());
        userSession.setSessionTokenExpiry(sessionDataType.getSessionEnd());
        userSession.setRefreshTokenExpiry(sessionDataType.getRefreshTokenExpiry());
        userSession.setLoggedIn(true);
        userSessionService.save(userSession);
    }
}

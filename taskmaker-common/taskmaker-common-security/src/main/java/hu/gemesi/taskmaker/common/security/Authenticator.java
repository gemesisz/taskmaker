package hu.gemesi.taskmaker.common.security;

import hu.gemesi.taskmaker.common.rest.header.ProjectHeader;
import hu.gemesi.taskmaker.common.security.dto.CurrentUserData;
import hu.gemesi.taskmaker.common.security.qualifier.Auth;
import hu.gemesi.taskmaker.common.security.redis.AuthRedisService;
import hu.gemesi.taskmaker.common.security.role.AuthRole;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

@Model
public class Authenticator {

    @Inject
    private CurrentUserData currentUserData;

    @Inject
    private ProjectHeader projectHeader;

    @Inject
    private AuthRedisService redisService;

    public boolean checkLoginOnly(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return processParameters(invocationContext, manager);
    }

    public boolean authenticateCheck(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return processParameters(invocationContext, manager);
    }

    private boolean processParameters(InvocationContext invocationContext, BeanManager manager) throws BaseException {
        Method method = invocationContext.getMethod();
        if (StringUtils.isBlank(projectHeader.getUser())) {
            // TODO create AccessDeniedException
        }
        currentUserData.setSessionData(redisService.authenticate(projectHeader.getSessionToken(), projectHeader.getUser()).getSessionData());
        Auth auth = method.getAnnotation(Auth.class);
        return auth.role() == AuthRole.valueOf(currentUserData.getSessionData().getRole().value())
                || (auth.role() == AuthRole.ALL && currentUserData.getSessionData().getRole() != null);
    }
}

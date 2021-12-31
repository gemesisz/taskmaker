package hu.gemesi.taskmaker.common.security;

import hu.gemesi.taskmaker.common.security.qualifier.Auth;
import hu.gemesi.taskmaker.common.security.role.AuthRole;
import org.apache.deltaspike.security.api.authorization.Secures;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;

@Model
public class AuthenticatorResolver {

    @Inject
    private Authenticator iAuthenticator;

    @Secures
    @Auth(role = AuthRole.ALL)
    public boolean checkLoginOnly(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return iAuthenticator.checkLoginOnly(invocationContext, manager);
    }

    @Secures
    @Auth(role = AuthRole.TASK_SOLVER)
    public boolean authenticateSolverCheck(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return iAuthenticator.authenticateCheck(invocationContext, manager);
    }

    @Secures
    @Auth(role = AuthRole.GROUP_ADMIN)
    public boolean authenticateGroupAdminCheck(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return iAuthenticator.authenticateCheck(invocationContext, manager);
    }

    @Secures
    @Auth(role = AuthRole.ADMIN)
    public boolean authenticateAdminCheck(InvocationContext invocationContext, BeanManager manager) throws Exception {
        return iAuthenticator.authenticateCheck(invocationContext, manager);
    }


}

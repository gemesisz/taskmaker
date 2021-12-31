package hu.gemesi.taskmaker.common.rest.userLogin;

import hu.gemesi.taskmaker.common.model.user.UserSession;
import hu.gemesi.taskmaker.common.rest.service.UserSessionService;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class UserLoginHelper {

    @Inject
    private UserSessionService userSessionService;

    @Transactional
    public void login(String userName) {
        UserSession userSession = userSessionService.findByUserName(userName);
        if (userSession == null) {
            userSession = new UserSession();
            //userSession.setUsername(userName);
        }
        userSession.setLoggedIn(true);
        userSession.setActualLogin(LocalDateTime.now());
        userSessionService.save(userSession);
    }

    @Transactional
    public boolean isLoggedIn(String userName) {
        UserSession userSession = userSessionService.findByUserName(userName);
        if (userSession == null) {
            return false;
        }
        return userSession.isLoggedIn();
    }

    @Transactional
    public void logout(String userName) {
        UserSession userSession = userSessionService.findByUserName(userName);
        if (userSession == null) {
            return;
        }
        userSession.setLoggedIn(false);
        userSession.setLastLogin(userSession.getActualLogin());
        userSession.setActualLogin(null);
        userSessionService.save(userSession);
    }
}

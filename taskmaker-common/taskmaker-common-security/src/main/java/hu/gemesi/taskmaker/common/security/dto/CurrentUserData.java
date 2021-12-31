package hu.gemesi.taskmaker.common.security.dto;

import hu.gemesi.taskmaker.dto.common.common.SessionDataType;

import javax.enterprise.inject.Model;
import java.io.Serializable;

@Model
public class CurrentUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    private SessionDataType sessionDataType;

    public SessionDataType getSessionData() {
        if (sessionDataType == null) {
            // TODO create AccessDeniedException
        }
        return sessionDataType;
    }

    public void setSessionData(SessionDataType sessionDataType) {
        this.sessionDataType = sessionDataType;
    }
}

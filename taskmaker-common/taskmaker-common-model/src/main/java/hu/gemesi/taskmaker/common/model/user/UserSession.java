package hu.gemesi.taskmaker.common.model.user;

import hu.gemesi.taskmaker.model.base.AbstractEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_session")
@Vetoed
public class UserSession extends AbstractEntity {

    @Column(name = "username")
    private CustomerUser customerUser;

    @Column(name = "logged_in")
    private boolean loggedIn;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "actual_login")
    private LocalDateTime actualLogin;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiry")
    private OffsetDateTime refreshTokenExpiry;

    @Column(name = "session_token_expiry")
    private OffsetDateTime sessionTokenExpiry;

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getActualLogin() {
        return actualLogin;
    }

    public void setActualLogin(LocalDateTime actualLogin) {
        this.actualLogin = actualLogin;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public OffsetDateTime getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(OffsetDateTime refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public OffsetDateTime getSessionTokenExpiry() {
        return sessionTokenExpiry;
    }

    public void setSessionTokenExpiry(OffsetDateTime sessionTokenExpiry) {
        this.sessionTokenExpiry = sessionTokenExpiry;
    }
}

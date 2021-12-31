package hu.gemesi.taskmaker.user.sevice.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.user.SecurityUser;
import hu.gemesi.taskmaker.user.sevice.repository.SecurityUserRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.Optional;

@Model
public class SecurityUserService extends BaseService<SecurityUser> {

    @Inject
    private SecurityUserRepository securityUserRepository;

    public Optional<SecurityUser> findByUsernameAndPassword(String username, String password) {
        return securityUserRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<SecurityUser> findByUsername(String username) {
        return securityUserRepository.findByUsername(username);
    }
}

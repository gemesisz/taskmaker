package hu.gemesi.taskmaker.user.sevice.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.user.sevice.repository.UserRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Model
public class UserService extends BaseService<CustomerUser> {

    @Inject
    private UserRepository userRepository;

    public CustomerUser findByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<CustomerUser> findByRole(int role) {
        return userRepository.findByRole(role);
    }

    public List<CustomerUser> findLikeUsername(String username) {
        return userRepository.findLikeUsername(likeParameter(username));
    }

    public List<CustomerUser> findLikeFirstname(String firstname) {
        return userRepository.findLikeFirstName(likeParameter(firstname));
    }

    public List<CustomerUser> findLikeLastname(String lastname) {
        return userRepository.findLikeLastName(likeParameter(lastname));
    }

    public List<CustomerUser> findByBirthday(LocalDate birthday) {
        return userRepository.findByBirthday(birthday);
    }

}

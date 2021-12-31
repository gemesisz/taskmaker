package hu.gemesi.taskmaker.user.sevice.helper;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.user.sevice.service.UserService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FindUserHelper {

    @Inject
    private UserService userService;

    public List<CustomerUser> findUsers(String username, String firstname, String lastname, String birthday) throws DateTimeParseException {
        List<CustomerUser> customerUserList = new ArrayList<>();
        if (username != null) {
            customerUserList.addAll(userService.findLikeUsername(username));
        }
        List<CustomerUser> firstnameCustomerUserList = new ArrayList<>();
        if (firstname != null && !firstname.equals("")) {
            firstnameCustomerUserList = userService.findLikeFirstname(firstname);
            for (CustomerUser foundCustomerUser : firstnameCustomerUserList) {
                if (!customerUserList.contains(foundCustomerUser)) {
                    customerUserList.add(foundCustomerUser);
                }
            }
            if (firstnameCustomerUserList.size() != 0) {
                List<CustomerUser> finalFirstnameCustomerUserList = firstnameCustomerUserList;
                customerUserList.removeIf(foundUser -> !finalFirstnameCustomerUserList.contains(foundUser));
            }

        }
        List<CustomerUser> lastnameCustomerUserList = new ArrayList<>();
        if (lastname != null && !lastname.equals("")) {
            lastnameCustomerUserList = userService.findLikeLastname(lastname);
            for (CustomerUser foundCustomerUser : lastnameCustomerUserList) {
                if (!customerUserList.contains(foundCustomerUser)) {
                    customerUserList.add(foundCustomerUser);
                }
            }
            if (lastnameCustomerUserList.size() != 0) {
                List<CustomerUser> finalLastnameCustomerUserList = lastnameCustomerUserList;
                customerUserList.removeIf(foundUser -> !finalLastnameCustomerUserList.contains(foundUser));
            }
            if (firstnameCustomerUserList.size() != 0) {
                List<CustomerUser> finalFirstnameCustomerUserList1 = firstnameCustomerUserList;
                customerUserList.removeIf(foundUser -> !finalFirstnameCustomerUserList1.contains(foundUser));
            }

        }
        if (birthday != null) {
            List<CustomerUser> birthdayCustomerUserList = userService.findByBirthday(LocalDate.parse(birthday));
            for (CustomerUser foundCustomerUser : birthdayCustomerUserList) {
                if (!customerUserList.contains(foundCustomerUser)) {
                    customerUserList.add(foundCustomerUser);
                }
            }
            if (lastnameCustomerUserList.size() != 0) {
                List<CustomerUser> finalLastnameCustomerUserList1 = lastnameCustomerUserList;
                customerUserList.removeIf(foundUser -> !finalLastnameCustomerUserList1.contains(foundUser));
            }
            if (firstnameCustomerUserList.size() != 0) {
                List<CustomerUser> finalFirstnameCustomerUserList1 = firstnameCustomerUserList;
                customerUserList.removeIf(foundUser -> !finalFirstnameCustomerUserList1.contains(foundUser));
            }
            if (birthdayCustomerUserList.size() != 0) {
                customerUserList.removeIf(foundUser -> !birthdayCustomerUserList.contains(foundUser));
            }
        }
        return customerUserList;
    }
}

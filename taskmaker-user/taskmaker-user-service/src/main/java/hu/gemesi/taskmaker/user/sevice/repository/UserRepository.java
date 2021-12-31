package hu.gemesi.taskmaker.user.sevice.repository;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends EntityRepository<String, CustomerUser> {

    Optional<CustomerUser> findByUsername(String username);

    List<CustomerUser> findByRole(int role);

    @Query("FROM User WHERE username LIKE ?1")
    List<CustomerUser> findLikeUsername(String username);

    @Query("FROM User WHERE firstName LIKE ?1")
    List<CustomerUser> findLikeFirstName(String firstName);

    @Query("FROM User WHERE lastName LIKE ?1")
    List<CustomerUser> findLikeLastName(String lastName);

    List<CustomerUser> findByBirthday(LocalDate birthday);
}

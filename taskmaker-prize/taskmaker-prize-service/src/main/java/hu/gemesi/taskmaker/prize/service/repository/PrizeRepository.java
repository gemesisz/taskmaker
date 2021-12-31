package hu.gemesi.taskmaker.prize.service.repository;

import hu.gemesi.taskmaker.common.model.prize.Prize;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;

import java.util.List;

public interface PrizeRepository extends EntityRepository<Prize, String> {

    List<Prize> findByUser(CustomerUser customerUser);
}

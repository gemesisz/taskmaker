package hu.gemesi.taskmaker.prize.service.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.prize.Prize;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.prize.service.repository.PrizeRepository;
import hu.gemesi.taskmaker.prize.service.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class PrizeService extends BaseService<Prize> {

    @Inject
    private PrizeRepository prizeRepository;

    @Inject
    private UserRepository userRepository;

    public List<Prize> findByUsername(String username) {
        Optional<CustomerUser> user = userRepository.findByUsername(username);
        return user.map(prizeRepository::findByUser).orElse(null);
    }
}

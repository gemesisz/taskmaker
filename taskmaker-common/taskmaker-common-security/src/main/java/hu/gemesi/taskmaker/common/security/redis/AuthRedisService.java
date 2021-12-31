package hu.gemesi.taskmaker.common.security.redis;

import hu.gemesi.taskmaker.dto.common.common.RedisUserAuthenticationType;
import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.AccessDeniedException;
import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.module.redis.annotation.RedisConnection;
import hu.icellmobilsoft.coffee.module.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

@Model
public class AuthRedisService {

    private static final long serialVersionUID = 1L;

    @Inject
    @ThisLogger
    private AppLogger log;

    @Inject
    @RedisConnection(configKey = "")
    private RedisService redisService;

    /**
     * Authenticate by sessionToken and userLogin.
     *
     * @param sessionToken
     * @param userLogin
     * @return RedisUserAuthenticationType
     * @throws BaseException
     */
    public RedisUserAuthenticationType authenticate(String sessionToken, String userLogin) throws BaseException {
        RedisUserAuthenticationType type;
        try {
            type = redisService.getRedisData(sessionToken, RedisUserAuthenticationType.class);
        } catch (BONotFoundException e) {
            log.debug("Redis data not found for sessionToken: [{0}]", sessionToken);
            throw new AccessDeniedException(generateAccessDeniedExceptionMessage(sessionToken, userLogin));
        }
        if (type == null || !StringUtils.equalsIgnoreCase(type.getSessionId(), sessionToken)
                || !StringUtils.equalsIgnoreCase(type.getSessionData().getUserId(), userLogin)) {
            log.debug("Redis data not valid for sessionToken: [{0}] and userLogin: [{1}]", sessionToken, userLogin);
            throw new AccessDeniedException(generateAccessDeniedExceptionMessage(sessionToken, userLogin));
        }
        return type;
    }

    private String generateAccessDeniedExceptionMessage(String sessionToken, String userLogin) {
        return MessageFormat.format("Access denied with sessionToken: [{0}] and userLogin [{1}], invalid user or session!", sessionToken, userLogin);
    }

    /**
     * Kijelentkezi a sessionToken és a refreshToken kulcsokat a Redisből
     * 
     * @param sessionToken
     * @return
     * @throws BaseException
     */
    public RedisUserAuthenticationType logout(String sessionToken) throws BaseException {
        try {
            RedisUserAuthenticationType type = redisService.getRedisData(sessionToken, RedisUserAuthenticationType.class);
            redisService.removeRedisData(sessionToken);
            return type;
        } catch (BONotFoundException e) {
            log.debug(e.getLocalizedMessage());
        }
        return null;
    }

    public void logoutAll(String userId) throws BaseException {
        List<String> redisData = redisService.listRedisData(userId);
        if (redisData != null && !redisData.isEmpty()) {
            redisService.removeAllRedisData(redisData);
            redisService.removeRedisData(userId);
        }
    }
}

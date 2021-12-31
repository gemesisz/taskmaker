package hu.gemesi.taskmaker.common.rest.exception;

import hu.gemesi.taskmaker.common.dto.enums.FaultType;

public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(FaultType.INVALID_REQUEST, message);
    }

    public BusinessException(FaultType faultType, String message) {
        super(faultType, message);
    }

    public BusinessException(FaultType faultType, Exception e, String message) {
        super(faultType, e, message);
    }
}

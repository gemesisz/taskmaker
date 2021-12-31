package hu.gemesi.taskmaker.common.rest.exception;

import hu.gemesi.taskmaker.common.dto.enums.FaultType;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.common.BaseResponseType;

public class BaseException extends Exception{

    private static final long serialVersionUID = 1L;
    private FaultType faultType;

    private BaseResponseType.Result result;

    public BaseException(BaseResponse baseResponse) {
        this(baseResponse.getResult());
    }

    public BaseException(String message) {
        this(FaultType.OPERATION_FAILED, message);
    }

    public BaseException(FaultType faultType, String message) {
        super(message);
        this.faultType = faultType;
    }

    public BaseException(FaultType faultType, Exception e, String message) {
        super(message, e);
        this.faultType = faultType;
    }

    public BaseException(BaseResponseType.Result result) {
        this.result = result;
    }

    public BaseResponseType.Result getResult() {
        return result;
    }

    public void setResult(BaseResponseType.Result result) {
        this.result = result;
    }

    public FaultType getFaultType() {
        return faultType;
    }
}

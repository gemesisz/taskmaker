package hu.gemesi.taskmaker.common.rest.action;

import hu.gemesi.taskmaker.common.dto.enums.FaultType;
import hu.gemesi.taskmaker.common.rest.exception.BaseException;
import hu.gemesi.taskmaker.common.util.random.RandomUtil;
import hu.gemesi.taskmaker.dto.common.common.BaseRequestType;
import hu.gemesi.taskmaker.dto.common.common.BaseResponse;
import hu.gemesi.taskmaker.dto.common.common.BaseResponseType;
import hu.gemesi.taskmaker.dto.common.common.BaseResponsesType;
import hu.gemesi.taskmaker.dto.common.common.ContextType;
import hu.gemesi.taskmaker.dto.common.common.FuncCode;

import java.time.OffsetDateTime;

public abstract class BaseAction {

    public ContextType createContext() {
        ContextType contextType = new ContextType();
        contextType.setRequestId(RandomUtil.generateId());
        contextType.setTimestamp(OffsetDateTime.now());
        return contextType;
    }

    public void handleSuccessResultType(BaseResponsesType baseResponsesType) {
        handleSuccessResultType(baseResponsesType, null);
    }

    public void handleSuccessResultType(BaseResponsesType baseResponsesType, BaseRequestType baseRequestType) {
        if (baseRequestType != null) {
            baseResponsesType.setContext(baseRequestType.getContext());
        }
        if (baseResponsesType.getContext() == null) {
            baseResponsesType.setContext(createContext());
        }
        baseResponsesType.setFuncCode(FuncCode.OK);
    }

    protected BaseResponse createOKResponse() {
        return createResponse("OK", 200, "The operation was successful!");
    }

    protected BaseResponse createInvalidResponse() {
        return createResponse("INVALID_PARAMS", 403, "The parameters are invalid!");
    }

    protected BaseResponse createResponse(String errorCode, int statusCode, String msg) {
        return new BaseResponse().withResult(new BaseResponseType.Result().withMessage(msg).withStatusCode(statusCode).withErrorCode(errorCode));
    }

    protected BaseResponse createNotPermittedResponse() {
        return createResponse("NOT_PERMITTED", 403, "Your user does not have access to the method!");
    }

    protected BaseException newInvalidParameterException(String message) {
        return new BaseException(FaultType.INVALID_PARAMETERS, message);
    }
}

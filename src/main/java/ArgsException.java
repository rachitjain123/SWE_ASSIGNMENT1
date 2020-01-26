import java.util.Map;

public class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = null;
    private ErrorCode errorCode = ErrorCode.OK;

    public ArgsException() {}

    public ArgsException(String message) {super(message);}

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    char getErrorArgumentId() {
        return this.errorArgumentId;
    }

    String getErrorParameter() {
        return this.errorParameter;
    }

    ErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * @return message string of the corresponding errorCode
     */
    String errorMessage() {
        ErrorMessage errorMessage = new ErrorMessage(errorArgumentId, errorParameter);
        Map<ErrorCode, String> errorCodeMessageMap = errorMessage.getErrorMessages();

        if(!errorCodeMessageMap.containsKey(errorCode))
            throw new IllegalStateException("Unexpected value: " + errorCode);
        return errorCodeMessageMap.get(errorCode);

    }
}

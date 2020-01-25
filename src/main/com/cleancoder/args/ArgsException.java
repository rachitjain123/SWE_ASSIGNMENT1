package main.com.cleancoder.args;

public class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = null;
    private ErrorCode errorCode = ErrorCode.OK;
    ArgsException(ErrorCode invalidArgumentFormat, char elementId, String message) {super(message);}
    ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    ArgsException(ErrorCode errorCode, char errorArgumentId) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
    }
    public char getErrorArgumentId() {
        return errorArgumentId;
    }
    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }
    public String getErrorParameter() {
        return errorParameter;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public String errorMessage() {
        switch (errorCode) {
            case OK:
                return "TILT: Should not get here.";
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected.", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c.", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c.", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%c.", errorArgumentId);
            case INVALID_ARGUMENT_NAME:
                return String.format("'%c' is not a valid argument name.", errorArgumentId);
            case INVALID_ARGUMENT_FORMAT:
                return String.format("'%s' is not a valid argument format.", errorParameter);
            case MISSING_MAP:
                return String.format("Could not find map string for -%c.", errorArgumentId);
            case MALFORMED_MAP:
                return String.format("Map string for -%c is not of form k1:v1,k2:v2...", errorArgumentId);
            default:
                throw new IllegalStateException("Unexpected value: " + errorCode);
        }
    }

}

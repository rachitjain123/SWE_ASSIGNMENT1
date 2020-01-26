import java.util.HashMap;
import java.util.Map;

/**
 * Function of this class is to get all error messages map
 * with respect to errorArgumentId, errorParameter
 */
class ErrorMessage {
    private char errorArgumentId;
    private String errorParameter;

    ErrorMessage(char errorArgumentId, String errorParameter){
        this.errorArgumentId = errorArgumentId;
        this. errorParameter = errorParameter;
    }

    /**
     * @return errorCodeMessageMap, map of error code and the respective messages
     * with argument id and errorParameter
     * A More scalable approach
     */
    Map<ErrorCode, String> getErrorMessages() {

        Map<ErrorCode, String> errorCodeMessageMap = new HashMap<>();
        errorCodeMessageMap.put(ErrorCode.OK, "TILT: Should not get here.");
        errorCodeMessageMap.put(ErrorCode.UNEXPECTED_ARGUMENT, String.format("Argument -%c unexpected.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.MISSING_STRING, String.format("Could not find string parameter for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.ILLEGAL_ACCESS, String.format("Trying to illegally get access of a class for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_CAST, String.format("Invalid casting of a class to another class for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_INSTANTIATION, String.format("Invalid instantiation of a class for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_INTEGER, String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter));
        errorCodeMessageMap.put(ErrorCode.MISSING_INTEGER, String.format("Could not find integer parameter for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_DOUBLE, String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter));
        errorCodeMessageMap.put(ErrorCode.MISSING_DOUBLE, String.format("Could not find double parameter for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_ARGUMENT_NAME, String.format("'%c' is not a valid argument name.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.INVALID_ARGUMENT_FORMAT, String.format("'%s' is not a valid argument format.", errorParameter));
        errorCodeMessageMap.put(ErrorCode.MISSING_MAP, String.format("Could not find map string for -%c.", errorArgumentId));
        errorCodeMessageMap.put(ErrorCode.MALFORMED_MAP, String.format("Map string for -%c is not of form k1:v1,k2:v2...", errorArgumentId));
        return errorCodeMessageMap;

    }
}

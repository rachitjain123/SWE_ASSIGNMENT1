import java.util.HashMap;
import java.util.Map;

class ErrorMessage {
    private char errorArgumentId;
    private String errorParameter;

    ErrorMessage(char errorArgumentId, String errorParameter){
        this.errorArgumentId = errorArgumentId;
        this. errorParameter = errorParameter;
    }

    Map<ErrorCode, String> getErrorMessages() {

        Map<ErrorCode, String> codeStringMap = new HashMap<>();
        codeStringMap.put(ErrorCode.OK, "TILT: Should not get here.");
        codeStringMap.put(ErrorCode.UNEXPECTED_ARGUMENT, String.format("Argument -%c unexpected.", errorArgumentId));
        codeStringMap.put(ErrorCode.MISSING_STRING, String.format("Could not find string parameter for -%c.", errorArgumentId));
        codeStringMap.put(ErrorCode.INVALID_INTEGER, String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter));
        codeStringMap.put(ErrorCode.MISSING_INTEGER, String.format("Could not find integer parameter for -%c.", errorArgumentId));
        codeStringMap.put(ErrorCode.INVALID_DOUBLE, String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter));
        codeStringMap.put(ErrorCode.MISSING_DOUBLE, String.format("Could not find double parameter for -%c.", errorArgumentId));
        codeStringMap.put(ErrorCode.INVALID_ARGUMENT_NAME, String.format("'%c' is not a valid argument name.", errorArgumentId));
        codeStringMap.put(ErrorCode.INVALID_ARGUMENT_FORMAT, String.format("'%s' is not a valid argument format.", errorParameter));
        codeStringMap.put(ErrorCode.MISSING_MAP, String.format("Could not find map string for -%c.", errorArgumentId));
        codeStringMap.put(ErrorCode.MALFORMED_MAP, String.format("Map string for -%c is not of form k1:v1,k2:v2...", errorArgumentId));
        return codeStringMap;
    }
}

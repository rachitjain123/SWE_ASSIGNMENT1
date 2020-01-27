import junit.framework.TestCase;

public class ArgsExceptionTest extends TestCase {

    public void testUnexpectedMessage() {
        ArgsException e = new ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, 'x', null);
        assertEquals("Argument -x unexpected.", e.errorMessage());
    }

    public void testMissingStringMessage() {
        ArgsException e = new ArgsException(ErrorCode.MISSING_STRING, 'x', null);
        assertEquals("Could not find string parameter for -x.", e.errorMessage());
    }

    public void testInvalidIntegerMessage() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_INTEGER, 'x', "Forty two");
        assertEquals("Argument -x expects an integer but was 'Forty two'.", e.errorMessage());
    }

    public void testMissingIntegerMessage() {
        ArgsException e = new ArgsException(ErrorCode.MISSING_INTEGER, 'x', null);
        assertEquals("Could not find integer parameter for -x.", e.errorMessage());
    }

    public void testInvalidDoubleMessage() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_DOUBLE, 'x', "Forty two");
        assertEquals("Argument -x expects a double but was 'Forty two'.", e.errorMessage());
    }

    public void testMissingDoubleMessage() {
        ArgsException e = new ArgsException(ErrorCode.MISSING_DOUBLE, 'x', null);
        assertEquals("Could not find double parameter for -x.", e.errorMessage());
    }

    public void testMissingMapMessage() {
        ArgsException e = new ArgsException(ErrorCode.MISSING_MAP, 'x', null);
        assertEquals("Could not find map string for -x.", e.errorMessage());
    }

    public void testMalformedMapMessage() {
        ArgsException e = new ArgsException(ErrorCode.MALFORMED_MAP, 'x', null);
        assertEquals("Map string for -x is not of form k1:v1,k2:v2...", e.errorMessage());
    }

    public void testInvalidArgumentName() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, '#', null);
        assertEquals("'#' is not a valid argument name.", e.errorMessage());
    }

    public void testInvalidFormat() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, 'x', "$");
        assertEquals("'$' is not a valid argument format.", e.errorMessage());
    }

    public void testInvalidInstantiation() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_INSTANTIATION, 'x', null);
        assertEquals("Invalid instantiation of a class for -x.", e.errorMessage());
    }

    public void testInvalidCast() {
        ArgsException e = new ArgsException(ErrorCode.INVALID_CAST, 'y', null);
        assertEquals("Invalid casting of a class to another class for -y.", e.errorMessage());
    }

    public void testIllegalAccess() {
        ArgsException e = new ArgsException(ErrorCode.ILLEGAL_ACCESS, 'x', null);
        assertEquals("Trying to illegally get access of a class for -x.", e.errorMessage());
    }

}

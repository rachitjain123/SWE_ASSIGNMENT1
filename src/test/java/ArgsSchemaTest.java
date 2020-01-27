import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArgsSchemaTest {

    @Test
    public void testCreateWithNoSchemaOrArguments() throws Exception {
        Args args = new Args("", new String[0]);
        assertEquals(0, args.nextArgument());
    }


    @Test
    public void testWithNoSchemaButWithOneArgumentErrorCode() {
        try {
            new Args("", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
        }
    }

    @Test
    public void testWithNoSchemaButWithOneArgumentErrorArgumentId() {
        try {
            new Args("", new String[]{"-y"});
            fail();
        } catch (ArgsException e) {
            assertEquals('y', e.getErrorArgumentId());
        }
    }

    @Test
    public void testWithNoSchemaButWithMultipleArgumentsErrorCode() {
        try {
            new Args("", new String[]{"-x", "-y"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
        }
    }

    @Test
    public void testWithNoSchemaButWithMultipleArgumentsErrorArgument() {
        try {
            new Args("", new String[]{"-x", "-y"});
            fail();
        } catch (ArgsException e) {
            assertEquals('x', e.getErrorArgumentId());
        }
    }

    @Test
    public void testNonLetterSchemaErrorCode() {
        try {
            new Args("*", new String[]{});
            fail("Args constructor should have thrown exception");
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_ARGUMENT_NAME, e.getErrorCode());
        }
    }

    @Test
    public void testNonLetterSchemaErrorArgument() {
        try {
            new Args("*", new String[]{});
            fail("Args constructor should have thrown exception");
        } catch (ArgsException e) {
            assertEquals('*', e.getErrorArgumentId());
        }
    }

    @Test
    public void testInvalidArgumentFormatErrorCode() {
        try {
            new Args("f~", new String[]{});
            fail("Args constructor should have throws exception");
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_ARGUMENT_FORMAT, e.getErrorCode());
        }
    }

    @Test
    public void testInvalidArgumentFormatErrorArgument() {
        try {
            new Args("f~", new String[]{});
            fail("Args constructor should have throws exception");
        } catch (ArgsException e) {
            assertEquals('f', e.getErrorArgumentId());
        }
    }


}

package test.com.cleancoder.args;


import main.com.cleancoder.args.Args;
import main.com.cleancoder.args.ArgsException;
import main.com.cleancoder.args.ErrorCode;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArgsTest {

    @Test
    public void testCreateWithNoSchemaOrArguments() throws Exception {
        Args args = new Args("", new String[0]);
        assertEquals(0, args.nextArgument());
    }

    @Test
    public void testWithNoSchemaButWithOneArgument() throws Exception {
        try {
            new Args("", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
            assertEquals('x', e.getErrorArgumentId());
        }
    }
}

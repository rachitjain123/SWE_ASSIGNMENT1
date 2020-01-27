
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;


public class ArgsTest {

    @Test
    public void testSimpleBooleanPresent() throws Exception {
        Args args = new Args("x", new String[]{"-x"});
        assertTrue(args.getBoolean('x'));
    }

    @Test
    public void testBooleanArgumentIndex() throws Exception {
        Args args = new Args("y", new String[]{"-y"});
        assertEquals(1, args.nextArgument());
    }

    @Test
    public void testArgumentPresent() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertTrue(args.hasArgument('x'));
    }

    @Test
    public void testSpacesInFormat() throws Exception {
        Args args = new Args("x, y", new String[]{"-xy"});
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('x'), true, "Argument x")
                .and(args.hasArgument('y'), true, "Argument y");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());
    }

    @Test
    public void testSimpleStringPresent() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertEquals("param", args.getString('x'));
    }

    @Test
    public void testStringArgumentIndex() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertEquals(2, args.nextArgument());
    }

    @Test
    public void testMissingStringArgument() {
        try {
            new Args("x*", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.MISSING_STRING, e.getErrorCode());
        }
    }


    @Test
    public void testSimpleIntPresent() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertTrue(args.hasArgument('x'));
    }

    @Test
    public void testIntArgument() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertEquals(42, args.getInt('x'));
    }
    @Test
    public void testIntArgumentIndex() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertEquals(2, args.nextArgument());
    }



    @Test
    public void testSimpleIntPresentDiffArg() throws Exception {
        Args args = new Args("y#", new String[]{"-y", "42"});
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('y'), true, "Has argument")
                .and(42, args.getInt('y'),"Argument Parameter y");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());
    }

    @Test
    public void testInvalidIntegerErrorCode() {
        try {
            new Args("x#", new String[]{"-x", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_INTEGER, e.getErrorCode());
        }
    }

    @Test
    public void testInvalidIntegerErrorParameter() {
        try {
            new Args("y#", new String[]{"-y", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals("Forty two", e.getErrorParameter());
        }
    }

    @Test
    public void testMissingInteger() {
        try {
            new Args("x#", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.MISSING_INTEGER, e.getErrorCode());
        }
    }


    @Test
    public void testSimpleDoublePresent() throws Exception {
        Args args = new Args("x##", new String[]{"-x", "42.3"});
        assertTrue(args.hasArgument('x'));
    }

    @Test
    public void testInvalidSimpleDouble() {
        try {
            Args args = new Args("x##", new String[]{"-x", "42.3 4"});
            args.getDouble('x');
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_DOUBLE, e.getErrorCode());
        }
    }

    @Test
    public void testSimpleDoubleArgument() throws Exception {
        Args args = new Args("y##", new String[]{"-y", "42.3"});
        assertEquals(42.3, args.getDouble('y'), .001);
    }

    @Test
    public void testInvalidDoubleErrorCode() {
        try {
            new Args("x##", new String[]{"-x", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_DOUBLE, e.getErrorCode());
        }
    }

    @Test
    public void testInvalidDoubleErrorParameter() {
        try {
            new Args("y##", new String[]{"-y", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals("Forty two", e.getErrorParameter());
        }
    }

    @Test
    public void testMissingDouble() {
        try {
            new Args("x##", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.MISSING_DOUBLE, e.getErrorCode());
        }
    }

    @Test
    public void testStringArrayLength() throws Exception {
        Args args = new Args("x[*]", new String[]{"-x", "alpha"});
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('x'), true, "Argument x")
                .and(args.getStringArray('x').length, 1, "Length of string array");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());
    }

    @Test
    public void testStringArrayValue() throws Exception {
        Args args = new Args("y[*]", new String[]{"-y", "alpha"});

        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('y'), true, "Argument y")
                .and(args.getStringArray('y')[0], "alpha", "Array[0]");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());

    }

    @Test
    public void testMissingStringArrayElement() {
        try {
            new Args("x[*]", new String[] {"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.MISSING_STRING,e.getErrorCode());
        }
    }

    @Test
    public void testManyStringArraySize() throws Exception {
        Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta", "-x", "gamma"});
        String[] result = args.getStringArray('x');
        assertEquals(3, result.length);
    }

    @Test
    public void manyStringArrayElementValues() throws Exception {
        Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta", "-x", "gamma"});
        String[] result = args.getStringArray('x');
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and("alpha", result[0], "1st Element")
                .and("beta", result[1], "2nd Element")
                .and("gamma", result[2], "3rd element");

        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());

    }

    @Test
    public void testMapArgument() throws Exception {
        Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});
        assertTrue(args.hasArgument('f'));
    }

    @Test
    public void testMapValues() throws Exception {
        Args args = new Args("g&", new String[] {"-g", "key1:val1,key2:val2"});
        Map argsMap = args.getMap('g');
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and("val1", argsMap.get("key1"), "key1")
                .and("val2", argsMap.get("key2"), "key2");
        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());
    }

    @Test
    public void malFormedMapArgument() {
        try {
            new Args("f&", new String[]{"-f", "key1:val1,key2"});
            fail();
        }
        catch (ArgsException e) {
            assertEquals(ErrorCode.MALFORMED_MAP, e.getErrorCode());
        }
    }

    @Test
    public void oneMapArgument() throws Exception {
        Args args = new Args("f&", new String[] {"-f", "key1:val1"});
        Map map = args.getMap('f');
        assertEquals("val1", map.get("key1"));
    }

    @Test
    public void testExtraArgumentsTypeCheck() throws Exception {
        Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertTrue(args.getBoolean('x'));
    }

    @Test
    public void testExtraArgumentString() throws Exception {
        Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertEquals("alpha", args.getString('y'));
    }

    @Test
    public void testExtraArgumentIndex() throws Exception {
        Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertEquals(3, args.nextArgument());
    }

    @Test
    public void testExtraArgumentsThatLookLikeFlags() throws Exception {
        Args args = new Args("x,y", new String[]{"-x", "alpha", "-y", "beta"});
        EqualsBuilder equalsBuilder = new EqualsBuilder()
                .and(args.hasArgument('x'), true, "Argument x")
                .and(args.hasArgument('y'), false, "Argument y")
                .and(args.getBoolean('x'), true, "Argument x")
                .and(args.getBoolean('y'), false, "Argument y ");

        assertTrue(equalsBuilder.getMessage(), equalsBuilder.result());

    }

    @Test
    public void testExtraArgumentsThatLookLikeFlagsIndexCheck() throws Exception {
        Args args = new Args("x,y", new String[]{"-x", "alpha", "-y", "beta"});
        assertEquals(1, args.nextArgument());
    }

    /**
     * Integer type but was casting as boolean type
     */
    @Test
    public void testInvalidCast() {
        try {
            Args args = new Args("x#", new String[]{"-x", "42"});
            args.getBoolean('x');
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_CAST, e.getErrorCode());
        }
    }

    
}

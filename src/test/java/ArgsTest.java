
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class ArgsTest {



    @Test
    public void testSimpleBooleanPresent() throws Exception {
        Args args = new Args("x", new String[]{"-x"});
        assertTrue(args.getBoolean('x'));
        assertEquals(1, args.nextArgument());
    }

    @Test
    public void testSimpleStringPresent() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertTrue(args.hasArgument('x'));
        assertEquals("param", args.getString('x'));
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
    public void testSpacesInFormat() throws Exception {
        Args args = new Args("x, y", new String[]{"-xy"});
        assertTrue(args.hasArgument('x'));
        assertTrue(args.hasArgument('y'));
        assertEquals(1, args.nextArgument());
    }

    @Test
    public void testSimpleIntPresent() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertTrue(args.hasArgument('x'));
        assertEquals(42, args.getInt('x'));
        assertEquals(2, args.nextArgument());
    }

    @Test
    public void testSimpleIntPresentDiffArg() throws Exception {
        Args args = new Args("y#", new String[]{"-y", "42"});
        assertTrue(args.hasArgument('y'));
        assertEquals(42, args.getInt('y'));
        assertEquals(2, args.nextArgument());
    }

    @Test
    public void testInvalidInteger() {
        try {
            new Args("x#", new String[]{"-x", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_INTEGER, e.getErrorCode());
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
        assertEquals(42.3, args.getDouble('x'), .001);
    }

    @Test
    public void testInvalidDouble() {
        try {
            new Args("x##", new String[]{"-x", "Forty two"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_DOUBLE, e.getErrorCode());
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
    public void testStringArray() throws Exception {
        Args args = new Args("x[*]", new String[]{"-x", "alpha"});
        assertTrue(args.hasArgument('x'));
        String[] result = args.getStringArray('x');

        assertEquals(1, result.length);
        assertEquals("alpha", result[0]);
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
    public void manyStringArrayElements() throws Exception {
        Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta", "-x", "gamma"});
        assertTrue(args.hasArgument('x'));
        String[] result = args.getStringArray('x');
        assertEquals(3, result.length);
        assertEquals("alpha", result[0]);
        assertEquals("beta", result[1]);
        assertEquals("gamma", result[2]);
    }

    @Test
    public void MapArgument() throws Exception {
        Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});
        assertTrue(args.hasArgument('f'));
        Map map = args.getMap('f');
        assertEquals("val1", map.get("key1"));
        assertEquals("val2", map.get("key2"));
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
        assertTrue(args.hasArgument('f'));
        Map map = args.getMap('f');
        assertEquals("val1", map.get("key1"));
    }

    @Test
    public void testExtraArguments() throws Exception {
        Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertTrue(args.getBoolean('x'));
        assertEquals("alpha", args.getString('y'));
        assertEquals(3, args.nextArgument());
    }

    @Test
    public void testExtraArgumentsThatLookLikeFlags() throws Exception {
        Args args = new Args("x,y", new String[]{"-x", "alpha", "-y", "beta"});
        assertTrue(args.hasArgument('x'));
        assertFalse(args.hasArgument('y'));
        assertTrue(args.getBoolean('x'));
        assertFalse(args.getBoolean('y'));
        assertEquals(1, args.nextArgument());
    }
}

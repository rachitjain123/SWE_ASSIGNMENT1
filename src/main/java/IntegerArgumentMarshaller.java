import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaller extends AbstractArgumentMarshaller<Integer> {

    private int intValue;

    /**
     * @param currentArgument setting the current iterator value
     */
    @Override
    public void setValue(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_INTEGER, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_INTEGER, parameter);
        }
    }

    public Integer getValue() {
        return intValue;
    }

    /**
     * @return default value if IntegerArgumentMarshaller does not contain the element id
     */
    public Integer getDefaultValue() {
        return 0;
    }
}

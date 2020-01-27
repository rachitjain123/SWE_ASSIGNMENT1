import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleArgumentMarshaller extends AbstractArgumentMarshaller<Double> {
    private double doubleValue;

    DoubleArgumentMarshaller() {
    }

    /**
     * @param currentArgument setting the current iterator value
     */
    @Override
    public void setValue(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_DOUBLE, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_DOUBLE, parameter);
        }
    }

    public Double getValue() {
        return doubleValue;
    }

    /**
     * @return default value if DoubleArgumentMarshaller does not contain the element id
     */
    public Double getDefaultValue() {
        return 0.0;
    }

}


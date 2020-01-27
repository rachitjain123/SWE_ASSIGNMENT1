import java.util.Iterator;

public class BooleanArgumentMarshaller extends AbstractArgumentMarshaller<Boolean> {
    private boolean booleanValue;

    BooleanArgumentMarshaller() {
        booleanValue = false;
    }

    /**
     * @param currentArgument setting the current iterator value
     */
    @Override
    public void setValue(Iterator<String> currentArgument) {
        booleanValue = true;
    }

    public Boolean getValue() {
        return booleanValue;
    }

    /**
     * @return default value if BooleanArgumentMarshaller does not contain the element id
     */
    @Override
    public Boolean getDefaultValue() {
        return false;
    }
}

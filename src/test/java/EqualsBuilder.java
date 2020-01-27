import java.text.MessageFormat;

/**
 * This class tells the reason of assertion even when the asserions are anded
 * For example: expected "Field2" but we got "Field1" in argument x
 */
class EqualsBuilder {
    private boolean result = true;
    private String text = "";

    /**
     *
     * @param expected Expected object
     * @param actual Actual Object
     * @param msg Message when expected != actual
     * @return EqualsBuilder object
     */
    EqualsBuilder and(final Object expected, final Object actual, final String msg) {
        result = (result && actual != null && expected != null) && expected.equals(actual);
        if (!result && text.length() < 1) {
            text = MessageFormat.format("expected:<[{0}]> but was <[{1}]> failed at {2}", expected, actual, msg);
        }
        return this;
    }

    boolean result() {
        return result;
    }

    String getMessage() {
        return text;
    }
}
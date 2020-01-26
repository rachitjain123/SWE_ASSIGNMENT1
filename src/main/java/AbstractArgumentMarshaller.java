/**
 * @param <T> describes the type parameter the AbstractArgumentMarshaller implements
 *            Generic Abstract class giving interface of various Argument Marshaller
 */
public abstract class AbstractArgumentMarshaller<T> implements ArgumentMarshaller<T> {
    private T value;

    AbstractArgumentMarshaller() {
        value = getDefaultValue();
    }

    public T getValue() {
        return value;
    }
}

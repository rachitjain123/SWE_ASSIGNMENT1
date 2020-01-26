/**
 *
 * @param <T> describes the type parameter the AbstractArgumentMarshaler implements
 */
public abstract class AbstractArgumentMarshaler<T> implements ArgumentMarshaler<T> {
    private T value;
    AbstractArgumentMarshaler() {
        value = getDefaultValue();
    }
    public T getValue() { return value; }
}

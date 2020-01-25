public abstract class AbstractArgumentMarshaler<T> implements ArgumentMarshaler<T> {
    private T value;
    AbstractArgumentMarshaler() {
        value = getDefaultValue();
    }
    public T getValue() { return value; }
}

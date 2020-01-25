package main.com.cleancoder.args;

public abstract class AbstractArgumentMarshaler<T> implements ArgumentMarshaler<T> {
    protected T value;
    public AbstractArgumentMarshaler() {
        value = getDefaultValue();
    }
    public T getValue() { return value; }
}

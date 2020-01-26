import java.util.Iterator;

/**
 * @param <T> generic type parameter
 *            interface implemented in subclasses in various data types argument marshaller
 */
public interface ArgumentMarshaller<T> {

    void set(Iterator<String> currentArgument) throws ArgsException;

    T getValue();

    T getDefaultValue();
}

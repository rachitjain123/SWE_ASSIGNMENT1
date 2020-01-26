import java.util.Map;

class ProcessArgumentMarshaller {
    private Map<Character, ArgumentMarshaller> marshaller;

    ProcessArgumentMarshaller(Map<Character, ArgumentMarshaller> marshaller) {
        this.marshaller = marshaller;
    }

    /**
     * @param marshallerClass Type of class ArgumentMarsheller
     * @param arg             argument id
     * @param <S>             type parameter
     * @param <T>             type parameter
     * @return casted output of marshallerClass to the type parameter if arg present
     * else returns default value of marshaller class
     */
    <S, T extends ArgumentMarshaller<S>> S process(Class<T> marshallerClass, char arg) throws ArgsException {
        if (marshaller.containsKey(arg)) {
            try {
                marshallerClass.cast(marshaller.get(arg));
                return marshallerClass.cast(marshaller.get(arg)).getValue();
            } catch (ClassCastException e) {
                throw new ArgsException(ErrorCode.INVALID_CAST);
            }
        }
        try {
            return marshallerClass.newInstance().getDefaultValue();
        } catch (InstantiationException e1) {
            throw new ArgsException(ErrorCode.INVALID_INSTANTIATION);
        } catch (IllegalAccessException e1) {
            throw new ArgsException(ErrorCode.ILLEGAL_ACCESS);
        }
    }
}

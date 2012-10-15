package pl.polidea.interceptor;

import java.lang.reflect.Array;

public class ProxyUtils {
    public static <T> T[] copyOfRange(final T[] original, final int start, final int end) {
        final int originalLength = original.length; // For exception priority
                                                    // compatibility.
        if (start > end) {
            throw new IllegalArgumentException();
        }
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        final int resultLength = end - start;
        final int copyLength = Math.min(resultLength, originalLength - start);
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance(original.getClass().getComponentType(), resultLength);
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }
}

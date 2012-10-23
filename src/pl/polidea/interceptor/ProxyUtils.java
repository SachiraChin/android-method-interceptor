package pl.polidea.interceptor;

import java.lang.reflect.Array;

/**
 * Class contains copyOfRange static method form java.util.Arrays because in
 * Android it's available since API 9.
 * 
 * @author Przemek Jakubczyk
 * 
 */
public class ProxyUtils {
    /**
     * Copy from java.util.Arrays.copyOfRange . Android support this method
     * since API 9.
     * 
     * @param original
     * @param start
     * @param end
     * @return
     */
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

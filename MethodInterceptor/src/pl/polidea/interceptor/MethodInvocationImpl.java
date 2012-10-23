package pl.polidea.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 
 * Class is used as wrapper for invoking method. Holds real object, method under
 * invocation and its arguments.
 * 
 * @author Przemek Jakubczyk
 * 
 */
public class MethodInvocationImpl implements MethodInvocation {

    final Method method;
    final Object obj;
    final Object[] args;

    public MethodInvocationImpl(final Method method, final Object obj, final Object[] args) {
        this.method = method;
        this.obj = obj;
        this.args = args;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object getDelegate() {
        return obj;
    }

    @Override
    public Object[] getMethodArgs() {
        return args;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((obj == null) ? 0 : obj.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodInvocationImpl other = (MethodInvocationImpl) obj;
        if (!Arrays.equals(args, other.args)) {
            return false;
        }
        if (method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!method.equals(other.method)) {
            return false;
        }
        if (this.obj == null) {
            if (other.obj != null) {
                return false;
            }
        } else if (!this.obj.equals(other.obj)) {
            return false;
        }
        return true;
    }

}

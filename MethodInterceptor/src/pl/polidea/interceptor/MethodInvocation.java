package pl.polidea.interceptor;

import java.lang.reflect.Method;

public interface MethodInvocation {

    Method getMethod();

    Object getDelegate();

    Object[] getMethodArgs();
}

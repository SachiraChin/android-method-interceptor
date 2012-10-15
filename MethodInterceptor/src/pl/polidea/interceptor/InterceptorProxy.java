package pl.polidea.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class InterceptorProxy implements InvocationHandler {
    Object obj;
    InterceptorChain chain;

    public InterceptorProxy(final Object o, final InterceptorFilter... filter) {
        obj = o;
        chain = new InterceptorChain(filter[0], ProxyUtils.copyOfRange(filter, 1, filter.length));
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return chain.advise(new MethodInvocationImpl(method, obj, args));
    }
}
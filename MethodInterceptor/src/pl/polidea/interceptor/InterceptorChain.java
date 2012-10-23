package pl.polidea.interceptor;

import java.lang.reflect.InvocationTargetException;

/**
 * The Class InterceptorChain handles operations on implemented class.
 */
public class InterceptorChain {

    InterceptorFilter filter;
    InterceptorChain chain;

    public InterceptorChain(final InterceptorFilter advisor, final InterceptorChain a) {
        this.filter = advisor;
        this.chain = a;
    }

    public InterceptorChain() {
        this.filter = new InterceptorFilter() {

            @Override
            public Object perform(final MethodInvocation invocation, final InterceptorChain chain) throws Throwable {
                try {
                    return invocation.getMethod().invoke(invocation.getDelegate(), invocation.getMethodArgs());
                } catch (final InvocationTargetException e) {
                    throw e.getCause();
                }
            }

            @Override
            public boolean applies(final MethodInvocation invocation) {
                return true;
            }
        };

    }

    public InterceptorChain(final InterceptorFilter first, final InterceptorFilter... advisors) {
        this.filter = first;
        if (advisors.length > 0) {
            chain = new InterceptorChain(advisors[0], ProxyUtils.copyOfRange(advisors, 1, advisors.length));
        } else {
            chain = new InterceptorChain();
        }
    }

    /**
     * Method checks if current filter applies and if true invokes filter
     * perform method.
     * 
     * @param invocation
     * @return value of invoked method
     * @throws Throwable
     */
    public Object advise(final MethodInvocation invocation) throws Throwable {
        final boolean applies = filter.applies(invocation);
        if (applies) {
            return filter.perform(invocation, chain);
        } else {
            return this.chain.advise(invocation);
        }
    }
}

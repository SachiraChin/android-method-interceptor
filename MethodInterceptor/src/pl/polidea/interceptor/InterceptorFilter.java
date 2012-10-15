package pl.polidea.interceptor;


public interface InterceptorFilter {

    Object perform(MethodInvocation invocation, InterceptorChain chain) throws Throwable;

    boolean applies(MethodInvocation invocation);
}

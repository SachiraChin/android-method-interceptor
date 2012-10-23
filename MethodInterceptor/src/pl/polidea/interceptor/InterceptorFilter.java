package pl.polidea.interceptor;

/**
 * Interface used by Interceptor Module.
 * 
 * @author Przemek Jakubczyk
 * 
 */
public interface InterceptorFilter {

    /**
     * Method wraps inkoved method with chaining if available.
     * 
     * @param invocation
     * @param chain
     * @return real method result
     * @throws Throwable
     */
    Object perform(MethodInvocation invocation, InterceptorChain chain) throws Throwable;

    /**
     * Method is used to check if given filter will be used in invocation chain.
     * 
     * @param invocation
     * @return
     */
    boolean applies(MethodInvocation invocation);
}

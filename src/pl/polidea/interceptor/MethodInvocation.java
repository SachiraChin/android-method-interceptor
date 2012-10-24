package pl.polidea.interceptor;

import java.lang.reflect.Method;

/**
 * 
 * @author Przemek Jakubczyk
 * 
 */
public interface MethodInvocation {

    /**
     * Method used for invocation.
     * 
     * @return
     */
    Method getMethod();

    /**
     * Object from which method is called.
     * 
     * @return
     */
    Object getDelegate();

    /**
     * Method args.
     * 
     * @return
     */
    Object[] getMethodArgs();
}

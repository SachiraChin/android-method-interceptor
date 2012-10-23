package pl.polidea.interceptor;

import java.lang.reflect.Proxy;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * The Class InterceptorProvider.
 * 
 * @param <I>
 *            the generic type
 * @param <C>
 *            the generic type implementing I interface
 */
public class InterceptorProvider<I, C extends I> implements Provider<I> {
    @Inject Injector injector;
    private final Class<C> clazz;
    private final Class<I> iface;
    private final Class< ? extends InterceptorFilter>[] filterClasses;

    public InterceptorProvider(final Class<I> iface, final Class<C> clazz,
            final Class< ? extends InterceptorFilter>... filterClasses) {
        this.iface = iface;
        this.clazz = clazz;
        this.filterClasses = filterClasses;
    }

    @SuppressWarnings("unchecked")
    @Override
    public I get() {
        final InterceptorFilter[] filters = new InterceptorFilter[filterClasses.length];
        for (int i = 0; i < filterClasses.length; ++i) {
            filters[i] = injector.getInstance(filterClasses[i]);
        }
        final ClassLoader classLoader = iface.getClassLoader();
        final Class< ? >[] interfaces = new Class< ? >[] { iface };
        final Object instance = injector.getInstance(clazz);
        final InterceptorProxy proxyClass = new InterceptorProxy(instance, filters);
        return (I) Proxy.newProxyInstance(classLoader, interfaces, proxyClass);
    }

}

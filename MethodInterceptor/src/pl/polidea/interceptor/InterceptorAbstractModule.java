package pl.polidea.interceptor;

import com.google.inject.AbstractModule;

public abstract class InterceptorAbstractModule extends AbstractModule {

    public <I, C extends I> void bindWithFilters(final Class<I> iface, final Class<C> clazz,
            final Class< ? extends InterceptorFilter>... filterClasses) {
        bind(iface).toProvider(new InterceptorProvider<I, C>(iface, clazz, filterClasses));
    }
}

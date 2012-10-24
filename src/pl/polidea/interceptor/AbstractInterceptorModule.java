package pl.polidea.interceptor;

import com.google.inject.AbstractModule;

/**
 * Class extends RoboGuice {@link com.google.inject.AbstractModule}
 * AbstarctModule.
 * 
 * @author Przemek Jakubczyk
 * 
 */
public abstract class AbstractInterceptorModule extends AbstractModule {

    /**
     * Method binding interface with class using filters. Filters are placed and
     * launched in chain structure.
     * 
     * @param iface
     * @param clazz
     * @param filterClasses
     */
    public <I, C extends I> void bindWithFilters(final Class<I> iface, final Class<C> clazz,
            final Class< ? extends InterceptorFilter>... filterClasses) {
        bind(iface).toProvider(new InterceptorProvider<I, C>(iface, clazz, filterClasses));
    }
}

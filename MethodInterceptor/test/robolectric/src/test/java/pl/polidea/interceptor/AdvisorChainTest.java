package pl.polidea.interceptor;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AdvisorChainTest {
    InterceptorFilter advisor1 = mock(InterceptorFilter.class);
    InterceptorFilter advisor2 = mock(InterceptorFilter.class);
    InterceptorFilter advisor3 = mock(InterceptorFilter.class);
    InterceptorFilter advisor4 = mock(InterceptorFilter.class);
    InterceptorFilter advisor5 = mock(InterceptorFilter.class);

    final Object object = new Object();
    Method method;
    private MethodInvocationImpl invocationImpl;

    Object method() {
        return object;
    }

    @Before
    public void setup() throws SecurityException, NoSuchMethodException {
        reset(advisor1, advisor2, advisor3, advisor4, advisor5);
        method = getClass().getDeclaredMethod("method", new Class[] {});
        invocationImpl = new MethodInvocationImpl(method, this, null);
    }

    @Test
    public void testSimpleChain() throws Throwable {
        // given
        final InterceptorChain chain = new InterceptorChain(advisor1, new InterceptorChain());
        when(advisor1.applies(invocationImpl)).thenReturn(true);
        // when
        chain.advise(invocationImpl);

        // then
        verify(advisor1, times(1)).perform(eq(invocationImpl), eq(chain.chain));
    }

    @Test
    public void testChainWithManyAdvisors() throws Throwable {
        // given
        final InterceptorFilter advisor = new InterceptorFilter() {

            @Override
            public Object perform(final MethodInvocation invocation, final InterceptorChain chain) throws Throwable {
                return chain.advise(invocation);
            }

            @Override
            public boolean applies(final MethodInvocation invocation) {
                return true;
            }
        };
        final InterceptorChain chain = new InterceptorChain(advisor, new InterceptorChain());
        final InterceptorChain chain2 = new InterceptorChain(advisor2, chain);
        when(advisor2.applies(invocationImpl)).thenReturn(true);

        // when
        chain2.advise(invocationImpl);

        // then
        verify(advisor2, times(1)).perform(eq(invocationImpl), any(InterceptorChain.class));
    }

    @Test
    public void testChainMethodsGoesInCorrectOrder() throws Throwable {
        // given
        final InterceptorFilter advisor = new InterceptorFilter() {

            @Override
            public Object perform(final MethodInvocation invocation, final InterceptorChain chain) throws Throwable {
                return chain.advise(invocation);
            }

            @Override
            public boolean applies(final MethodInvocation invocation) {
                return true;
            }
        };
        final InterceptorFilter first = spy(advisor);
        final InterceptorFilter second = spy(advisor);
        final InterceptorFilter third = spy(advisor);
        final InterceptorFilter fourth = spy(advisor);
        final InOrder inOrder = inOrder(first, second, third, fourth);

        final InterceptorChain chain = new InterceptorChain(first, second, third, fourth);

        // when
        chain.advise(invocationImpl);

        // then
        inOrder.verify(first).perform(eq(invocationImpl), any(InterceptorChain.class));
        inOrder.verify(second).perform(eq(invocationImpl), any(InterceptorChain.class));
        inOrder.verify(third).perform(eq(invocationImpl), any(InterceptorChain.class));
        inOrder.verify(fourth).perform(eq(invocationImpl), any(InterceptorChain.class));
    }
}

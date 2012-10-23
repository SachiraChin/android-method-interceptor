package pl.polidea.interceptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class InterceptorProxyTest {

    final Object object = new Object();
    Method method;
    private MethodInvocationImpl invocationImpl;

    Object method() {
        return object;
    }

    @Before
    public void setup() throws SecurityException, NoSuchMethodException {
        method = InterceptorProxyTest.class.getDeclaredMethod("method", new Class[] {});
        invocationImpl = new MethodInvocationImpl(method, this, null);
    }

    @Test
    public void testSimpleMethodInvocation() throws Throwable {
        // given
        final InterceptorProxy proxy = new InterceptorProxy(this, mock(InterceptorFilter.class));

        // when
        final Object o = proxy.invoke(null, method, null);
        // then
        assertEquals(object, o);
    }

    @Test
    public void testAdvisorMethodApplyTrue() throws Throwable {
        // given
        final InterceptorFilter advisor = mock(InterceptorFilter.class);
        when(advisor.applies(invocationImpl)).thenReturn(true);
        final InterceptorProxy proxy = new InterceptorProxy(this, advisor);

        // when
        proxy.invoke(null, method, null);

        // then
        verify(advisor, times(1)).applies(eq(invocationImpl));
        verify(advisor, times(1)).perform(eq(invocationImpl), any(InterceptorChain.class));
    }

    @Test
    public void testAdvisorMethodApplyFalse() throws Throwable {
        // given
        final InterceptorFilter advisor = mock(InterceptorFilter.class);
        when(advisor.applies(invocationImpl)).thenReturn(false);
        final InterceptorProxy proxy = new InterceptorProxy(this, advisor);

        // when
        proxy.invoke(null, method, null);

        // then
        verify(advisor, times(0)).perform(eq(invocationImpl), any(InterceptorChain.class));
    }

    @Test
    public void testApplingTwoAdvisors() throws Throwable {
        // given
        final InterceptorFilter advisorA = mock(InterceptorFilter.class);
        final InterceptorFilter advisorB = mock(InterceptorFilter.class);
        when(advisorA.applies(invocationImpl)).thenReturn(true);
        when(advisorB.applies(invocationImpl)).thenReturn(true);
        final InterceptorProxy proxy = new InterceptorProxy(this, advisorA, advisorB);
        // when
        proxy.invoke(null, method, null);

        // then
        verify(advisorA, times(1)).perform(eq(invocationImpl), any(InterceptorChain.class));
        // verify(advisorB, times(1)).perform(method, this, null);
    }

    @Test
    public void testApplingTwoAdvisorsWhenOneDoesntApply() throws Throwable {
        // given
        final InterceptorFilter advisorA = mock(InterceptorFilter.class);
        final InterceptorFilter advisorB = mock(InterceptorFilter.class);
        when(advisorA.applies(invocationImpl)).thenReturn(true);
        when(advisorB.applies(invocationImpl)).thenReturn(false);
        final InterceptorProxy proxy = new InterceptorProxy(this, advisorA, advisorB);
        // when
        proxy.invoke(null, method, null);

        // then
        verify(advisorA, times(1)).perform(eq(invocationImpl), any(InterceptorChain.class));
        verify(advisorB, times(0)).perform(eq(invocationImpl), any(InterceptorChain.class));
    }
}

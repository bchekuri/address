package org.innovate.address.interceptor;


import org.innovate.address.constant.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith({MockitoExtension.class})
public class LoggingInterceptorTest {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptorTest.class);

    private LoggingInterceptor loggingInterceptor;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Object handler;

    @Mock
    private ModelAndView modelAndView;


    @BeforeEach
    public void setup() throws Exception {
        loggingInterceptor = new LoggingInterceptor();
    }

    @Test
    public void testPreHandleWithClientIPInHeader() throws Exception {
        Mockito.doReturn("10.10.10.10").when(httpServletRequest).getHeader(Constants.X_FORWARDED_FOR);
        Mockito.doReturn("address").when(httpServletRequest).getContextPath();
        loggingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getHeader(Constants.X_FORWARDED_FOR);
        Mockito.verify(httpServletRequest, Mockito.times(0)).getRemoteAddr();
        Assertions.assertEquals(MDC.get(Constants.CONTEXT_ID), "address");
    }

    @Test
    public void testPreHandleWithNoClientIPInHeader() throws Exception {
        Mockito.doReturn(null).when(httpServletRequest).getHeader(Constants.X_FORWARDED_FOR);
        loggingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getHeader(Constants.X_FORWARDED_FOR);
        Mockito.verify(httpServletRequest, Mockito.times(1)).getRemoteAddr();
    }

    @Test
    public void testPostHandle() throws Exception {
        Mockito.doReturn("address").when(httpServletRequest).getContextPath();
        loggingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);
        loggingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);
        Assertions.assertNull(MDC.get(Constants.CONTEXT_ID));
    }

}

package org.innovate.address.filter;

import org.innovate.address.config.CorrelationIdPropertyConfig;
import org.innovate.address.constant.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@ExtendWith({MockitoExtension.class})
public class CorrelationIdFilterTest {

    private CorrelationIdFilter correlationIdFilter;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterConfig filterConfig;

    @BeforeEach
    public void setup() {
        correlationIdFilter = new CorrelationIdFilter();
    }


    @Test
    public void testDoFilter() throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), "test-correlation-id");
            }
        };
        Mockito.doReturn("test-correlation-id").when(httpServletRequest)
                .getHeader(Constants.CORRELATION_ID_HEADER);
        CorrelationIdPropertyConfig correlationIdPropertyConfig = new CorrelationIdPropertyConfig();
        ReflectionTestUtils.setField(correlationIdFilter, "correlationIdPropertyConfig", correlationIdPropertyConfig);
        correlationIdFilter.init(filterConfig);
        correlationIdFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        correlationIdFilter.destroy();
        Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), null);
    }

    @Test
    public void testCorrelationIdGeneration() throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                Assertions.assertNotNull(MDC.get(Constants.CORRELATION_ID_HEADER));
            }
        };
        Mockito.doReturn(null).when(httpServletRequest)
                .getHeader(Constants.CORRELATION_ID_HEADER);
        CorrelationIdPropertyConfig correlationIdPropertyConfig = new CorrelationIdPropertyConfig();
        ReflectionTestUtils.setField(correlationIdFilter, "correlationIdPropertyConfig", correlationIdPropertyConfig);
        correlationIdFilter.init(filterConfig);
        correlationIdFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        correlationIdFilter.destroy();
        Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), null);
    }


    @Test
    public void testCorrelationIdExcluded() throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                Assertions.assertNull(MDC.get(Constants.CORRELATION_ID_HEADER));
            }
        };
        Mockito.doReturn(null).when(httpServletRequest)
                .getHeader(Constants.CORRELATION_ID_HEADER);
        Mockito.doReturn("/admin/health").when(httpServletRequest)
                .getRequestURI();
        CorrelationIdPropertyConfig correlationIdPropertyConfig = new CorrelationIdPropertyConfig();
        correlationIdPropertyConfig.setExclusions(Arrays.asList("/admin/health"));
        ReflectionTestUtils.setField(correlationIdFilter, "correlationIdPropertyConfig", correlationIdPropertyConfig);
        correlationIdFilter.init(filterConfig);
        correlationIdFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        correlationIdFilter.destroy();
        Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), null);
    }

    @Test
    public void testCorrelationIdLoggedForNotExcludedURLs() throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                Assertions.assertNotNull(MDC.get(Constants.CORRELATION_ID_HEADER));
                Assertions.assertNotEquals(MDC.get(Constants.CORRELATION_ID_HEADER), "test-correlation-id");
            }
        };
        Mockito.doReturn(null).when(httpServletRequest)
                .getHeader(Constants.CORRELATION_ID_HEADER);
        Mockito.doReturn("/address/convert").when(httpServletRequest)
                .getRequestURI();
        CorrelationIdPropertyConfig correlationIdPropertyConfig = new CorrelationIdPropertyConfig();
        correlationIdPropertyConfig.setExclusions(Arrays.asList("/admin/health"));
        ReflectionTestUtils.setField(correlationIdFilter, "correlationIdPropertyConfig", correlationIdPropertyConfig);
        correlationIdFilter.init(filterConfig);
        correlationIdFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        correlationIdFilter.destroy();
        Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), null);
    }

    @Test
    public void testCorrelationIdNotExcludedIfPresentInRequest() throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), "test-correlation-id-1");
            }
        };
        Mockito.doReturn("test-correlation-id-1").when(httpServletRequest)
                .getHeader(Constants.CORRELATION_ID_HEADER);
        Mockito.doReturn("/admin/health").when(httpServletRequest)
                .getRequestURI();
        CorrelationIdPropertyConfig correlationIdPropertyConfig = new CorrelationIdPropertyConfig();
        correlationIdPropertyConfig.setExclusions(Arrays.asList("/admin/health"));
        ReflectionTestUtils.setField(correlationIdFilter, "correlationIdPropertyConfig", correlationIdPropertyConfig);
        correlationIdFilter.init(filterConfig);
        correlationIdFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        correlationIdFilter.destroy();
        Assertions.assertEquals(MDC.get(Constants.CORRELATION_ID_HEADER), null);
    }

}

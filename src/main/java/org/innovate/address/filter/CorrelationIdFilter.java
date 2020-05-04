package org.innovate.address.filter;


import org.innovate.address.config.CorrelationIdPropertyConfig;
import org.innovate.address.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CorrelationIdFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Autowired
    private CorrelationIdPropertyConfig correlationIdPropertyConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("CorrelationId doFilter");
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String correlationId = httpServletRequest.getHeader(Constants.CORRELATION_ID_HEADER);
        boolean isPathExcludedForCorrelationId = this.isPathExcludedForCorrelationId(httpServletRequest);
        try {
            if(null == correlationId && !isPathExcludedForCorrelationId) {
                correlationId = UUID.randomUUID().toString();
                LOG.debug("{} is not present in client request, generating new id: {}",Constants.CORRELATION_ID_HEADER,
                        correlationId);
            } else if(isPathExcludedForCorrelationId) {
                LOG.debug("Context Path {} is excluded from {} id", httpServletRequest.getContextPath(),
                        Constants.CORRELATION_ID_HEADER);
            }
            MDC.put(Constants.CORRELATION_ID_HEADER, correlationId);
            LOG.debug("Added {} to logging context", Constants.CORRELATION_ID_HEADER);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(Constants.CORRELATION_ID_HEADER);
            LOG.debug("Removed {} from logging context", Constants.CORRELATION_ID_HEADER);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isPathExcludedForCorrelationId(HttpServletRequest httpServletRequest) {
        List<String> excludes = correlationIdPropertyConfig.getExclusions();
        for(String exclude: excludes) {
            if(exclude.equalsIgnoreCase(httpServletRequest.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}

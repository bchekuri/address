package org.innovate.address.interceptor;

import org.innovate.address.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BChekuri
 */
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        LOG.debug("Interceptor Logger - preHandle");
        String ip = getRemoteAddress(request);
        LOG.info("CLIENT IP: {}", ip);
        MDC.put(Constants.CONTEXT_ID, request.getContextPath());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        LOG.debug("Interceptor Logger - postHandle");
        MDC.remove(Constants.CONTEXT_ID);
        super.postHandle(request, response, handler, modelAndView);
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader(Constants.X_FORWARDED_FOR);
        if (null != ipFromHeader && ipFromHeader.length() > 0) {
            LOG.debug("IP from proxy - {} : {}", Constants.X_FORWARDED_FOR, ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}

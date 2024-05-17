package com.micasa.notificationservice.interceptor;

import com.micasa.notificationservice.constants.NotificationConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * This class intercepts each request and sets correlationId to logs.
 */
@Slf4j
public class LoggingRequestInterceptor implements HandlerInterceptor
{
    //Variable name that will be used in logs.
    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    /**
     * This intercepts request before handling.
     * Structure:
     *  1. Look for correlationId header.
     *      If present
     *          1.1 Set in logs against CORRELATION_ID_LOG_VAR_NAME.
     *      Else
     *          1.1 Generate new correlationId.
     *          1.2 Set in logs against CORRELATION_ID_LOG_VAR_NAME.
     * @param request : request object.
     * @param response : response object.
     * @param handler : handler object.
     * @return : true/false
     * @throws Exception : Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String correlationId = request.getHeader(NotificationConstants.CORRELATION_ID_HEADER);
        if(null == correlationId)
        {
            correlationId = UUID.randomUUID().toString();
            log.info("No correlation-id present in headers, generated new correlation-id: {}",correlationId);
        }
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * Perform some operation after request handling is done.
     * @param request : request object.
     * @param response : response object.
     * @param handler : handler object.
     * @param modelAndView : model/view.
     * @throws Exception : Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * Perform operation after response is sent.
     * @param request : request object.
     * @param response : response object.
     * @param handler : handler object.
     * @param ex : exception object.
     * @throws Exception : Exception.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

package com.microservices.gateway.config.exception;

import com.microservices.gateway.exception.WebException;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalExceptionAttributes extends DefaultErrorAttributes {

    private static final String EXCEPTION = "exception";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String ERROR_DESCRIPTION = "error_description";
    private static final String EXCEPTION_TYPE = "exception_type";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(request, options);
        addErrorDetails(errorAttributes, request);
        return errorAttributes;
    }

    private void addErrorDetails(Map<String, Object> errorAttributes, ServerRequest request) {
        var exception = getError(request);
        errorAttributes.put(EXCEPTION, exception.getClass().getSimpleName());
        if (exception instanceof final WebException webException) {
            errorAttributes.put(ERROR, webException.getMessage());
            errorAttributes.put(STATUS, webException.getHttpStatus().value());
            errorAttributes.put(ERROR_DESCRIPTION, StringUtils.isNotEmpty(webException.getErrorDescription()) ?
                    webException.getErrorDescription() : StringUtils.EMPTY);
            errorAttributes.put(EXCEPTION_TYPE, webException.getExceptionType());
        }
    }
}

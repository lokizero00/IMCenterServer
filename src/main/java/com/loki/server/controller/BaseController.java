package com.loki.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BaseController {
	static final String ERROR_MESSAGES = "errors";

    static RedirectAttributes redirectAttributes;
    static HttpServletRequest httpServletRequest;

    protected static void addErrorMessage(final String message) {
        RedirectAttributes redirectAttributes = getRedirectAttributes();
        if (redirectAttributes != null) {
            final Map<String, ?> map = redirectAttributes.getFlashAttributes();
            if (map.containsKey(ERROR_MESSAGES)) {
                final List<String> messages = new ArrayList<String>((List<String>) map.get(ERROR_MESSAGES));
                messages.add(message);
                redirectAttributes.addFlashAttribute(ERROR_MESSAGES, messages);
            } else {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGES, Collections.singletonList(message));
            }
        }
    }
    
    protected static void addRedirectParam(final String key,final Object value) {
    		RedirectAttributes redirectAttributes = getRedirectAttributes();
    		if (redirectAttributes != null) {
    			redirectAttributes.addFlashAttribute(key,value);
    		}
    }
    
    protected static void addRequestErrorMessage(final String message) {
    		HttpServletRequest httpServletRequest=getHttpServletRequest();
    		if(httpServletRequest!=null) {
    			httpServletRequest.setAttribute(ERROR_MESSAGES, "错误："+message);
    		}
    }

    public static RedirectAttributes getRedirectAttributes() {
        return redirectAttributes;
    }

	public static void setRedirectAttributes(RedirectAttributes redirectAttributes) {
		BaseController.redirectAttributes = redirectAttributes;
	}

	public static HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public static void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		BaseController.httpServletRequest = httpServletRequest;
	}
}

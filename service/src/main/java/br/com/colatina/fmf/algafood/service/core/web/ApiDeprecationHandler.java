package br.com.colatina.fmf.algafood.service.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getRequestURI().startsWith("/api/v1/")) {
			response.addHeader("X-AlgaFood-Deprecated",
					"This version of the API is deprecated and will be removed in the future. Please use the most recent version.");
		}

		return true;
	}
}

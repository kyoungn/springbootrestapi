package com.cos.jwtstudy1.config.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cos.jwtstudy1.code.helper.GeneralConstant;
import com.google.common.collect.ImmutableList;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsWebConfig implements Filter{

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest  request  = HttpServletRequest.class.cast (servletRequest);
        HttpServletResponse response = HttpServletResponse.class.cast(servletResponse);

        String origin  = GeneralConstant.ASTERISK;
        String headers = String.join(GeneralConstant.COMMA, ImmutableList.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        String methods = String.join(GeneralConstant.COMMA, ImmutableList.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(), HttpMethod.HEAD.name()));

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,  origin );
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, methods);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, headers);

        if(StringUtils.containsIgnoreCase(RequestMethod.OPTIONS.name(), request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        System.out.println("CorsWebConfig ------------------------->>>>");
        chain.doFilter(servletRequest, servletResponse);
		
	}

}

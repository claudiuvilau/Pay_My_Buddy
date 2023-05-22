package com.openclassrooms.pay_my_buddy.model;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestClass {

    private HttpServletRequest request;

    public RequestClass() {
    }

    public RequestClass(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String requestParameter(String parameterHtml) {

        return request.getParameter(parameterHtml);
    }

}
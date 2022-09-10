package com.spring.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class RootRestController {

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @RequestMapping("/")
    public  void redirectToSwagger(HttpServletResponse response) throws IOException{
        response.sendRedirect(this.servletContextPath + "/swagger-ui/index.html");
    }

}

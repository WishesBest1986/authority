package com.neusoft.app.web.taglibs;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义标签的数据传输对象（这里的传输并非网络、分页之间的传输，而是自定义标签与具体标签处理类的对象传输）
 *
 * Created by admin on 2015/4/9.
 */
public class TagDTO implements Serializable {
    private WebApplicationContext springContext;
    private ServletContext servletContext = null;
    private Map<String, String> properties = new HashMap<String, String>();

    public TagDTO(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public WebApplicationContext getSpringContext() {
        return springContext;
    }

    public void setSpringContext(WebApplicationContext springContext) {
        this.springContext = springContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }
}

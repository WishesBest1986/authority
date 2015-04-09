package com.neusoft.app.web.taglibs;

import com.neusoft.app.web.taglibs.builder.MenuTagBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;

/**
 * 系统首界面左栏导航菜单自定义标签
 * 该类继承RequestContextAwareTag，主要用于获取WebApplicationContext
 *
 * Created by admin on 2015/4/9.
 */
public class MenuTag extends RequestContextAwareTag {
    private WebApplicationContext springContext;
    private ServletContext servletContext = null;

    @Override
    protected int doStartTagInternal() throws Exception {
        servletContext = pageContext.getServletContext();
        springContext = getRequestContext().getWebApplicationContext();

        JspWriter writer = pageContext.getOut();
        if (springContext == null) {
            writer.write("获取菜单项失败");
        } else {
            TagDTO dto = new TagDTO(servletContext);
            dto.setSpringContext(springContext);
            MenuTagBuilder builder = springContext.getBean(MenuTagBuilder.class);
            writer.write(builder.build(dto));
        }

        return 0;
    }
}

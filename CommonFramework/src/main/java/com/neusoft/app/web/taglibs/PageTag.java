package com.neusoft.app.web.taglibs;

import com.neusoft.app.web.taglibs.builder.PageTagBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Created by admin on 2015/4/9.
 */
public class PageTag extends RequestContextAwareTag {
    private String totalRecords;
    private String totalPages;
    private String curPage;
    private String exportUrl;
    private String lookup;
    private WebApplicationContext springContext;
    private ServletContext servletContext = null;

    @Override
    protected int doStartTagInternal() throws Exception {
        servletContext = pageContext.getServletContext();
        springContext = getRequestContext().getWebApplicationContext();

        JspWriter writer = pageContext.getOut();
        try {
            TagDTO dto = new TagDTO(servletContext);
            dto.setProperty(PageTagBuilder.TOTAL_RECORDS, totalRecords);
            dto.setProperty(PageTagBuilder.TOTAL_PAGES, totalPages);
            dto.setProperty(PageTagBuilder.CUR_PAGE, curPage);
            dto.setProperty(PageTagBuilder.EXPORT_URL, exportUrl);
            dto.setProperty(PageTagBuilder.LOOKUP, lookup);

            PageTagBuilder builder = springContext.getBean(PageTagBuilder.class);
            writer.write(builder.build(dto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }
}

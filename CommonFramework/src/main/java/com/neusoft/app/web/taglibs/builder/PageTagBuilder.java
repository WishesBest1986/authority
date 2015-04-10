package com.neusoft.app.web.taglibs.builder;

import com.neusoft.app.web.taglibs.TagDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2015/4/9.
 */
@Component
public class PageTagBuilder implements TagBuilder {
    public static final String TOTAL_RECORDS = "totalRecords";
    public static final String TOTAL_PAGES = "totalPages";
    public static final String CUR_PAGE = "curPage";
    public static final String EXPORT_URL = "exportUrl";
    public static final String LOOKUP = "lookup";

    private String totalRecords;
    private String totalPages;
    private String curPage;
    private String exportUrl;
    private String lookup;

    @Override
    public String build(TagDTO dto) {
        this.totalRecords = dto.getProperty(TOTAL_RECORDS);
        this.totalPages = dto.getProperty(TOTAL_PAGES);
        this.curPage = dto.getProperty(CUR_PAGE);
        this.exportUrl = dto.getProperty(EXPORT_URL);
        this.lookup = dto.getProperty(LOOKUP);

        StringBuffer content = new StringBuffer();
        content.append(buildPageMessage());
        content.append(buildPageLink());
        if (lookup == null || lookup.equals("")) {
            content.append(buildPageButton());
        }
        return wrapPageContent(content.toString());
    }

    private String buildPageMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td><div align='left'>");
        sb.append("总共<font color='red'>");
        sb.append(totalRecords);
        sb.append("</font>条记录&nbsp; 共<font color='red'>");
        sb.append(totalPages);
        sb.append("</font>页&nbsp; 当前所在第<font color='red'>");
        sb.append(curPage);
        sb.append("</font>页");
        sb.append("</div></td>");
        return sb.toString();
    }

    private String buildPageLink() {
        int prePage = 0, nextPage = 0, pageNo = 1, totalPage = 1;
        try {
            pageNo = Integer.parseInt(curPage);
            totalPage = Integer.parseInt(totalPages);
            prePage = pageNo - 1;
            nextPage = pageNo + 1;
            prePage = prePage <= 0 ? 1 : prePage;
            nextPage = nextPage >= totalPage ? totalPage : nextPage;
        } catch (Exception e) {

        }

        StringBuffer sb = new StringBuffer();
        sb.append("<td><div class='class1_ur4'>");
        sb.append("<a href='javascript:jumpPage(1)' style='TEXT-DECORATION:none'>首页</a>&nbsp;&nbsp;");
        sb.append("<a href='javascript:jumpPage(");
        sb.append(prePage);
        sb.append(")' style='TEXT-DECORATION:none'>上一页</a>&nbsp;&nbsp;");
        sb.append("<a href='javascript:jumpPage(");
        sb.append(nextPage);
        sb.append(")' style='TEXT-DECORATION:none'>下一页</a>&nbsp;&nbsp;");
        sb.append("<a href='javascript:jumpPage(");
        sb.append(totalPage);
        sb.append(")' style='TEXT-DECORATION:none'>末页</a>");
        return sb.toString();
    }

    private String buildPageButton() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td><input type='hidden' name='lastpage' value='");
        sb.append(totalPages);
        sb.append("'/>转至第");
        sb.append("<input maxLength=5 name='jumppage' size=2 value='");
        sb.append("'/>");
        sb.append("页 &nbsp;&nbsp;");
        sb.append("<input type='button' name='pagesubmit' ");
        sb.append("onclick='return gotoPage()' class='button_70px' ");
        sb.append("value='跳转'/></td>");
        sb.append("<td>");
        if (StringUtils.isNotEmpty(exportUrl)) {
            sb.append("<input type='button' name='genExcel' class='button_100px' value='保存成Excel文件' onclick=\"exportExcel('");
            sb.append(exportUrl);
            sb.append("')\">");
        }
        sb.append("</td>");
        return sb.toString();
    }

    private String wrapPageContent(String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr><td class='td_table_bottom' colspan='8'>");
        sb.append("<table width='800' border='0' cellpadding='0' cellspacing='0'><tr>");
        sb.append(content);
        sb.append("</tr></table></td></tr>");
        return sb.toString();
    }
}

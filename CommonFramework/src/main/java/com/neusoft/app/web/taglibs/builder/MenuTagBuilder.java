package com.neusoft.app.web.taglibs.builder;

import com.neusoft.app.web.taglibs.TagDTO;
import com.neusoft.framework.security.entity.Menu;
import com.neusoft.framework.security.service.MenuService;
import com.neusoft.framework.security.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by admin on 2015/4/9.
 */
@Component
public class MenuTagBuilder implements TagBuilder {
    private WebApplicationContext springContext;
    private ServletContext servletContext = null;

    @Override
    public String build(TagDTO dto) {
        this.servletContext = dto.getServletContext();
        this.springContext = dto.getSpringContext();

        StringBuffer buffer = new StringBuffer();
        List<Menu> menus = getAllowedAccessMenu();
        Map<Long, List<Menu>> menuMap = buildMenuTreeMap(menus);
        buildMenuTreeFolder(buffer, menuMap, Menu.ROOT_MENU);
        return buffer.toString();
    }

    private Map<Long, List<Menu>> buildMenuTreeMap(List<Menu> menus) {
        Map<Long, List<Menu>> menuMap = new TreeMap<Long, List<Menu>>();
        for (Menu menu : menus) {
            // 判断是否有上一级菜单，如果有，则添加到上一级菜单的Map中去 如果没有上一级菜单，把该菜单作为根节点
            Long parentMenuId = menu.getParentMenu() == null ? Menu.ROOT_MENU : menu.getParentMenu().getId();
            if (!menuMap.containsKey(parentMenuId)) {
                List<Menu> subMenus = new ArrayList<Menu>();
                subMenus.add(menu);
                menuMap.put(parentMenuId, subMenus);
            } else {
                List<Menu> subMenus = menuMap.get(parentMenuId);
                subMenus.add(menu);
                menuMap.put(parentMenuId, subMenus);
            }
        }
        return menuMap;
    }

    private List<Menu> getAllowedAccessMenu() {
        MenuService menuService = springContext.getBean(MenuService.class);
        return menuService.getAllowedAccessMenu(ShiroUtils.getUserId());
    }

    private void buildMenuTreeFolder(StringBuffer buffer, Map<Long, List<Menu>> menuMap, Long menuId) {
        List<Menu> treeFolders = menuMap.get(menuId);
        if (treeFolders == null) {
            return;
        }

        for (Menu menu : treeFolders) {
            List<Menu> treeNodes = menuMap.get(menu.getId());
            if ((treeNodes == null || treeNodes.isEmpty()) && StringUtils.isEmpty(menu.getDescription())) {
                continue;
            }
            buffer.append("<dl>");
            buffer.append("<dt id='sidebar_goods_manage'><i class='pngFix'></i>");
            buffer.append(menu.getName());
            buffer.append("</dt>");
            buffer.append("<dd>");
            buffer.append("<ul>");
            buildMenuTreeNode(buffer, treeNodes);
            buffer.append("</ul>");
            buffer.append("</dd>");
            buffer.append("</dl>");
        }
    }

    private void buildMenuTreeNode(StringBuffer buffer, List<Menu> treeNodes) {
        if (treeNodes == null) {
            return;
        }
        for (Menu menu : treeNodes) {
            buffer.append("<li>");
            buffer.append("<a href='");
            buffer.append(servletContext.getContextPath());
            buffer.append(menu.getDescription());
            buffer.append("' target='mainFrame' ");
            buffer.append(">");
            buffer.append(menu.getName());
            buffer.append("</a>");
            buffer.append("</li>");
        }
    }
}

package com.neusoft.framework.security.web;

import com.neusoft.framework.orm.Page;
import com.neusoft.framework.orm.PropertyFilter;
import com.neusoft.framework.security.entity.User;
import com.neusoft.framework.security.service.RoleService;
import com.neusoft.framework.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2015/4/10.
 */
@Controller
@RequestMapping(value = "/security/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<User> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrderBy(Page.ASC);
        }
        page = userService.findPage(page, filters);
        model.addAttribute("page", page);
        return "security/userList";
    }
}

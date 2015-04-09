package com.neusoft.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by admin on 2015/3/30.
 */
@Controller
public class PlatformController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "system/index";
    }
}

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

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String top() {
        return "system/top";
    }

    @RequestMapping(value = "/left", method = RequestMethod.GET)
    public String left() {
        return "system/left";
    }

    @RequestMapping(value = "/middle", method = RequestMethod.GET)
    public String middle() {
        return "system/middle";
    }

    @RequestMapping(value = "/right", method = RequestMethod.GET)
    public String right() {
        return "system/right";
    }
}

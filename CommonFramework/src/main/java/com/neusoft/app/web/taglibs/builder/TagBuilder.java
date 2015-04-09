package com.neusoft.app.web.taglibs.builder;

import com.neusoft.app.web.taglibs.TagDTO;

/**
 * Created by admin on 2015/4/9.
 */
public interface TagBuilder {
    /**
     * 根据标签变量构建控件元素
     * @param dto 标签之间数据传输对象，包括标签属性、值以及常用的上下文
     * @return
     */
    public String build(TagDTO dto);
}

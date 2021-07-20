package com.ldy.myblog.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class AccountSessionManager  extends DefaultWebSessionManager {
    /**
     * 头信息中具有sessionId
     * 请求头：Authorization：sessionId
     * 指定 sessionId
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 获取请求头 Authorization 中的数据
        String id = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(id)) {
            // 如果没有携带，生成新的 sessionId
            return super.getSessionId(request,response);
        } else {
            // 返回 sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,"header"); // sessionId 的来源
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id); // 具体的 sessionId 是什么
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE); // sessionId 是需要验证
            return id;
        }
    }
}

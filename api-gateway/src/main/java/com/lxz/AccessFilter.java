package com.lxz;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/7/28 0028.
 */
public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getHeader("accessToken");
        HttpServletResponse response = ctx.getResponse();
        String url = request.getServletPath();
        if (accessToken == null) {
            try {
                ctx.setSendZuulResponse(false);
                response.setContentType("application/json; charset=utf8");
                response.setStatus(500);
                response.getWriter().write("{\"code\":500" +",\"msg\":\"" +"请先登录系统"+"\"}");
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
/*            throw new RuntimeException();*/
        }
        return null;
    }
}

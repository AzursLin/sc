package com.lxz;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018/7/28 0028.
 */
public class ErrorFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(ErrorFilter.class);
    @Override
    public String filterType() {
        return "error";
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
        try {
            RequestContext context = RequestContext.getCurrentContext();
/*            ZuulException exception = this.findZuulException(context.getThrowable());*/
            logger.error("进入系统异常拦截", 500);

            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json; charset=utf8");
            response.setStatus(500);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("{\"code\":500" +",\"msg\":\"" +"网关异常错误"+"\"}");;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(writer!=null){
                    writer.close();
                }
            }

        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }

        return null;

    }

/*    ZuulException findZuulException(Throwable throwable) {
        if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause().getCause();
        } else if (ZuulException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause();
        } else {
            return ZuulException.class.isInstance(throwable) ? (ZuulException) throwable : new ZuulException(throwable, 500, (String) null);
        }
    }*/

    }

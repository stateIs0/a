package com.ipzoe.light.component.filter;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Admin;
import com.ipzoe.light.component.TokenComponent;
import com.ipzoe.light.repository.AccountRepository;
import com.ipzoe.light.repository.AdminRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 身份过滤器.
 * 通过Header中的X-Auth-Token项, jwt校验.
 * <p>
 * 此过滤器需要在跨域过滤器过滤后执行.
 */
@Component
@Order(3)
public class AuthenticationTokenFilter extends GenericFilterBean {
    private Log log = LogFactory.getLog(getClass());

    @Value("${eva.token.header}")
    private String tokenHeader;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenComponent tokenComponent;

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        // 不过滤OPTIONS请求
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(req, res);
            return;
        }

        // 对登录,swagger不过滤
        if (!request.getServletPath().startsWith("/api/account")
                && !request.getServletPath().startsWith("/api/f")
                && !request.getServletPath().startsWith("/api/l")
                && !request.getServletPath().startsWith("/api/l")
                && !request.getServletPath().startsWith("/admin")
                ) {
            chain.doFilter(req, res);
            return;
        }

        // X-Auth-Token携带确认
        final String authHeader = request.getHeader(tokenHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        final String token = authHeader.substring(7);
        // 根据用户不同的访问路径获取它们的账号
        if (request.getServletPath().startsWith("/api")) {
            // 前台账号ID 1000以后
            Account account = accountRepository.selectByPrimaryKey(Long.parseLong(tokenComponent.getUsernameFromToken(token)));
            if (account == null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            } else {
                req.setAttribute("account", account);
            }
        }
        if (request.getServletPath().startsWith("/admin")) {
            // 后台账号ID 1000 以内
            Admin admin = adminRepository.selectByPrimaryKey(Long.parseLong(tokenComponent.getUsernameFromToken(token)));
            if (admin == null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            } else {
                req.setAttribute("admin", admin);
            }
        }

        chain.doFilter(req, res);
    }
}

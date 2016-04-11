package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



/*
 *  1)	编写名为 EncodingFilter.java 的过滤器;
	2)	要求 过滤器设置的字符集可通过参数配置;
 */

public class EncodingFilter implements Filter{
	String encode="UTF-8";
	
	
	public void destroy() {
		//System.out.println("EncodingFilter destroy()");
		
	}

	
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		//拦截到请求后 进行调用业务方法
		
		req.setCharacterEncoding(encode);
		resp.setCharacterEncoding(encode);
		//System.out.println("拦截啦----------");
		
		filterChain.doFilter(req, resp);
		//System.out.println("放行------------");
		
		
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
		
		//获得配置的参数    web.xml里设置的encode
		encode=filterConfig.getInitParameter("encode");
	//	System.out.println("init EncodingFilter"+encode);  //服务开启的时候
		
		
		
	}
	
	

}

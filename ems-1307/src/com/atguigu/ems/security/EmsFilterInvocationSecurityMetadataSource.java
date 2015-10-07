package com.atguigu.ems.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Resource;
import com.atguigu.ems.web.services.ResourceService;


/**
 * 自定义的资源和对应的权限信息
 * @author Administrator
 *
 */
@Component
public class EmsFilterInvocationSecurityMetadataSource implements
		FactoryBean<DefaultFilterInvocationSecurityMetadataSource> {
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public DefaultFilterInvocationSecurityMetadataSource getObject()
			throws Exception {
		
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = 
														new LinkedHashMap<>();
		
		//获得被保护的URL
		List<Resource> resources = resourceService.getAll(true);
		
		for (Resource resource : resources) {
			String path = resource.getUrl();
			//获得对应权限的集合
			Set<Authority> authorities = resource.getAuthorities();
			
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(path);
			
			Collection<ConfigAttribute> attributes = new HashSet<>();
			
			for (Authority authority : authorities) {
				//获取权限名称（框架用的权限名称是以ROLe_开头的）
				String name = authority.getName();
				SecurityConfig securityConfig = new SecurityConfig(name);
				attributes.add(securityConfig);
				
			}
			requestMap.put(matcher, attributes);
			
		}
		
		DefaultFilterInvocationSecurityMetadataSource metadataSource = 
				new DefaultFilterInvocationSecurityMetadataSource(requestMap);
		

		return metadataSource;
	}

	@Override
	public Class<?> getObjectType() {
		return DefaultFilterInvocationSecurityMetadataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

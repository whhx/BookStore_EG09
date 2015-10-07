package com.atguigu.ems.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;


/**
 * 把自定义的 DefaultFilterInvocationSecurityMetadataSource Bean 赋给 
	FilterSecurityInterceptor Bean 的 securityMetadataSource 属性
 * @author Administrator
 *
 */
@Component
public class EmsSecurityBeanPostProcessor implements BeanPostProcessor {

	private FilterSecurityInterceptor filterSecurityInterceptor;
	private DefaultFilterInvocationSecurityMetadataSource metadataSource;
	private boolean isSetter = false;
	
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof FilterSecurityInterceptor){
			this.filterSecurityInterceptor = (FilterSecurityInterceptor) bean;
		}
		if(bean instanceof EmsFilterInvocationSecurityMetadataSource){
			EmsFilterInvocationSecurityMetadataSource factory = (EmsFilterInvocationSecurityMetadataSource) bean;
			try {
				this.metadataSource = factory.getObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(this.filterSecurityInterceptor != null 
				&& this.metadataSource != null
				&& !this.isSetter){
			this.filterSecurityInterceptor.setSecurityMetadataSource(metadataSource);
			this.isSetter = true;
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

}

package com.base.common.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements BeanFactoryPostProcessor {

	/** Spring应用上下文环境 */
	private static ConfigurableListableBeanFactory beanFactory;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanUtils.beanFactory = beanFactory;
	}

	/**
	 * 获取类型为requiredType的对象
	 *
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 *
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		T result = (T) beanFactory.getBean(clz);
		return result;
	}

	/**
	 * 获取aop代理对象
	 * 
	 * @param invoker
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}
}

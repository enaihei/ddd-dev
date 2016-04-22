/*
 * 修订记录：
 * aiping.yuan 16:22 创建
 */
package com.ddd.adk.domain;

import com.ddd.adk.common.exception.DomainException;
import com.ddd.adk.domain.model.DomainObject;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Optional;

/**
 * 领域工厂
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public class DomainFactory implements ApplicationContextAware {

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    private Map<Class<DomainObject>, DomainObjectRegister> domainObjectRegisterMap = Maps.newConcurrentMap();

    public <T extends DomainObject> T newInstance(Class<T> domainOjbectClazz, boolean isAutowire) {
        DomainObject domainObject = create(domainOjbectClazz).build();
        if (isAutowire) {
            autowireCapableBeanFactory.autowireBeanProperties(domainObject, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            autowireCapableBeanFactory.initializeBean(domainObject, "domainObject");
        }
        return (T) domainObject;
    }


    private DomainObjectRegister create(Class<? extends DomainObject> domainOjbectClazz) {
        Optional<DomainObjectRegister> domainObjectRegister = Optional.ofNullable(
                domainObjectRegisterMap.get(domainOjbectClazz));
        if (domainObjectRegister.isPresent()) {
            return domainObjectRegister.get();
        }
        throw new DomainException("无法构建实体对象");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }
}

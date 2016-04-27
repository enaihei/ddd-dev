/*
 * 修订记录：
 * aiping.yuan 16:22 创建
 */
package com.ddd.adk.domain;

import com.ddd.adk.common.exception.DomainException;
import com.ddd.adk.domain.model.DomainObject;
import com.google.common.collect.Maps;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 领域工厂
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public class DomainFactory implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(DomainFactory.class);

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


    private DomainObjectRegister create(Class<? extends DomainObject> domainObjectClazz) {
        Optional<DomainObjectRegister> domainObjectRegister = Optional.ofNullable(
                domainObjectRegisterMap.get(domainObjectClazz));
        if (domainObjectRegister.isPresent()) {
            return domainObjectRegister.get();
        }
        domainObjectRegister = Optional.ofNullable(newCreate(domainObjectClazz));
        if (!domainObjectRegister.isPresent()) {
            throw new DomainException("无法构建实体对象");
        }
        DomainObjectRegister _domainObjectRegister = domainObjectRegister.get();
        domainObjectRegisterMap.put((Class<DomainObject>) domainObjectClazz, _domainObjectRegister);
        if (logger.isDebugEnabled()) {
            logger.debug("构建 DomainObject{}->DomainObjectRegister{}", domainObjectClazz, _domainObjectRegister);
        }
        return _domainObjectRegister;
    }

    private DomainObjectRegister newCreate(Class<? extends DomainObject> domainObjectClazz) {
        String method = createMethod(domainObjectClazz.getName());
        ClassPool classPool = new ClassPool(true);
        ClassPath classPath = new ClassClassPath(this.getClass());
        classPool.insertClassPath(classPath);
        CtClass ctClass = classPool.makeClass(createClassName(DomainObjectRegister.class));
        try {
            ctClass.addMethod(CtNewMethod.make(method, ctClass));
        } catch (CannotCompileException e) {
            logger.error("动态编译加入方法出现错误 supperClass={}, method={}", DomainObjectRegister.class, method, e);
        }
        return newInstance(ctClass);
    }

    private static <T> T newInstance(CtClass ctClass) {
        T targetClass = null;
        try {
            targetClass = (T) ctClass.toClass().newInstance();
        } catch (InstantiationException | IllegalAccessException |CannotCompileException e) {
            logger.error("动态编译构建出现错误 supperClass={}, ctClass={}", DomainObjectRegister.class, ctClass, e);
        }
        return targetClass;
    }

    private static String createClassName(Class<?> clazz) {
        StringBuilder className = new StringBuilder("com.ddd.adk.dcompile")
                .append(clazz.getSimpleName())
                .append(UUID.randomUUID().toString().replaceAll("-", ""));
        return className.toString();
    }

    private static String createMethod(String targetClassName){
        StringBuilder createMethod = new StringBuilder();
        createMethod
            .append("public com.ddd.adk.domain.model.DomainObject build() {\n")
            .append("\t")
            .append(targetClassName).append(" domainObject = new ").append(targetClassName).append("();\n")
            .append("\t")
            .append("return domainObject;\n")
            .append("\n}");
        return createMethod.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }
}

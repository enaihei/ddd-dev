/*
 * 修订记录：
 * aiping.yuan 18:20 创建
 */
package com.ddd.adk.domain;

import com.ddd.adk.domain.model.DomainObject;

/**
 * 实体寄存器
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public abstract class DomainObjectRegister {

    protected DomainFactory domainFactory;


    protected abstract DomainObject build();

}

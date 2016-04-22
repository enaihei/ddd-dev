/*
 * 修订记录：
 * aiping.yuan 17:23 创建
 */
package com.ddd.adk.domain.model;

import com.ddd.adk.domain.DomainFactory;

/**
 * 领域定义
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public interface Definition {

    /**
     * 领域工厂
     * @return
     */
    DomainFactory domainFactory();
}

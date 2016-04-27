/*
 * 修订记录：
 * aiping.yuan 17:49 创建
 */
package com.ddd.adk.domain.model;

import com.ddd.adk.domain.DomainFactory;

/**
 * 实体对象
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public class DomainObject extends AbstractDomain implements Definition {

    @Override
    public DomainFactory domainFactory() {
        return null;
    }
}

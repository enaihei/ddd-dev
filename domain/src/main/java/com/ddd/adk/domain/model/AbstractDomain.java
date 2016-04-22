/*
 * 修订记录：
 * aiping.yuan 17:49 创建
 */
package com.ddd.adk.domain.model;

import org.springframework.beans.BeanUtils;

/**
 * 领域抽象转换（提供基础能力）
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public abstract class AbstractDomain implements Domain {

    @Override
    public <DTO> void copyTOForm(DTO form, String... ignore) {
        BeanUtils.copyProperties(form, this, ignore);
    }

    @Override
    public <DTO> void copyTODto(DTO dto, String... ignore) {
        BeanUtils.copyProperties(this, dto, ignore);
    }
}

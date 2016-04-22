/*
 * 修订记录：
 * aiping.yuan 17:41 创建
 */
package com.ddd.adk.domain.model;

import java.io.Serializable;

/**
 * 领域接口定义
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public interface Domain extends Serializable {

    <DTO> void copyTOForm(DTO form, String... ignore);

    <DTO> void copyTODto(DTO dto, String... ignore);
}

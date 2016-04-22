/*
 * 修订记录：
 * aiping.yuan 18:37 创建
 */
package com.ddd.adk.common.exception;

/**
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 525024489353500367L;

    public DomainException(String msg){
        super(msg);
    }

    public DomainException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public DomainException(Throwable throwable) {
        super(throwable);
    }


}

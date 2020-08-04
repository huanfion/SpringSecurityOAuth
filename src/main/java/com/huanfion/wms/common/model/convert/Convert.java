package com.huanfion.wms.common.model.convert;


import com.huanfion.wms.common.framework.BeanConverter;

import java.io.Serializable;

/**
 * @author huanfion
 * @version 1.0
 * @date 2019/8/22 17:02
 */
public class Convert implements Serializable {
    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T convert(Class<T> clazz) {
        return BeanConverter.convert(clazz, this);
    }
}

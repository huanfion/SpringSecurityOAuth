package com.huanfion.wms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建时间
     */
    private final String createDate = "createdate";
    /**
     * 修改时间
     */
    private final String modifyDate = "modifydate";
    /**
     * 创建者ID
     */
    private final String createId = "createid";

    /**
     * 修改者ID
     */
    private final String modifyId = "modifyid";

    /**
     * 创建者 名称
     */
    private final String creator="creator";
    /**
     * 修改者名称
     */
    private final String modifier="modifier";
    @Override
    public void insertFill(MetaObject metaObject) {

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}

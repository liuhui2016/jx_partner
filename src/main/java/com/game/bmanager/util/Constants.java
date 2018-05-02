package com.game.bmanager.util;

import com.game.util.PropertiesUtil;

public class Constants {

    /** 合作客户编码：DOMAIN_PARTNER = "c_paner" */
    public static final String DOMAIN_PARTNER = "c_paner";

    /** 商务编码：DOMAIN_BUSINESS = "c_business" */
    public static final String DOMAIN_BUSINESS = "c_business";

    public static final String CONSTANT_FILENAME = "config.properties";

    /*
     * properties 常量对象
     */
    public static final PropertiesUtil CONS_PROPERTIES = new PropertiesUtil(CONSTANT_FILENAME);

    public static final String JUMP_URL = CONS_PROPERTIES.getValue("JUMP_URL");
}
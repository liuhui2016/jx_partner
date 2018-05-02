package com.game.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class PreferencesPlaceholderConfigurer extends org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        String url = props.getProperty("jdbc.url");

        try {
            // 解密jdbc.password属性值，并重新设置
            props.setProperty("jdbc.username", Des.decryptDES(username));
            props.setProperty("jdbc.password", Des.decryptDES(password));
            props.setProperty("jdbc.url", Des.decryptDES(url));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.processProperties(beanFactory, props);
    }
}

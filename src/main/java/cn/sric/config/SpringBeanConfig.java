package cn.sric.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author sunchuanchuan
 * @version V1.0
 * @date 2020/11/27
 * @package cn.sric.common
 * @description
 **/
@Component
public class SpringBeanConfig implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanConfig.applicationContext == null) {
            SpringBeanConfig.applicationContext = applicationContext;
        }
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名字获取bean
     *
     * @param name bean的名字
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);

    }

    /**
     * 根据bean的类型来注入bean
     *
     * @param clazz 类型
     * @param <T>   类型
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名字和类型来获取bean
     *
     * @param name  bean name
     * @param clazz 类型
     * @param <T>   类型
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }


}

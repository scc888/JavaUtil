package cn.sric.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 功能说明：mybatis-plus注册配置
 * 功能描述：分页插件
 * 如果分页不管用的话，添加这个设置就可以了
 *
 * @author sric
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 启动分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
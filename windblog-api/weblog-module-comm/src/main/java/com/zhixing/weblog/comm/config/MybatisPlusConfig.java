package com.zhixing.weblog.comm.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig 配置文件
 */
@Configuration
// 扫描接口存放位置
@MapperScan("com.zhixing.weblog.comm.domain.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 批量插入 SQL 注解器
     */
    @Bean
    public InsertBatchSqlInjector insertBatchSqlInjector(){
        return  new InsertBatchSqlInjector();
    }
}

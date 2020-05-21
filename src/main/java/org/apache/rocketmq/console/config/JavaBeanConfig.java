package org.apache.rocketmq.console.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * 采用JavaConfig方式管理Bean<br>
 * 注意：因为MapperScannerConfigurer对象需要SqlSessionFactory对象的创建，所以添加@AutoConfigureAfter注解
 * 
 * @author YuanZhiQiang
 */
@Configuration
@AutoConfigureAfter(SqlSessionFactory.class)
public class JavaBeanConfig {

	/**
	 * Mybatis Mapper文件包名。
	 */
	private static final String MYBATIS_MAPPER_PACKAGE = "org.apache.rocketmq.console.dao";

	/**
	 * 配置Mybatis动态代理DAO接口
	 * 
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(MYBATIS_MAPPER_PACKAGE);
		return mapperScannerConfigurer;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}


}
package org.apache.rocketmq.console.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.rocketmq.console.support.IntEnumHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import javax.sql.DataSource;

/**
 * 采用JavaConfig方式管理Bean<br>
 * 注意：因为MapperScannerConfigurer对象需要SqlSessionFactory对象的创建，所以添加@AutoConfigureAfter注解
 * 
 * @author YuanZhiQiang
 */
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class JavaBeanConfig {

	private String configLocation;

	private String mapperLocations;


	/**
	 * 创建SqlSessionFactory ，修改默认的枚举类处理器
	 *
	 * @return org.apache.ibatis.session.SqlSessionFactory
	 * @author lijiangtao
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		//数据源
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//配置文件
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setConfigLocation(resourcePatternResolver.getResource(configLocation));
		sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));

		//修改默认枚举类型处理器
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
		typeHandlerRegistry.setDefaultEnumTypeHandler(IntEnumHandler.class);

		return sqlSessionFactory;
	}


	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}
}
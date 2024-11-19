package com.dkb.urlshortner.urlshortnerservice.configuration.datasource;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class TransactionRoutingDataSourceConfiguration {

    @Bean
    public DataSource routingDataSource(
            @Qualifier("readDataSource") DataSource readDataSource,
            @Qualifier("writeDataSource") DataSource writeDataSource) {

        TransactionRoutingDataSource dataSource = new TransactionRoutingDataSource();

        final var dataSoourceMap = Map.<Object, Object>of(
                DataSourceType.READ, readDataSource,
                DataSourceType.WRITE, writeDataSource);

        dataSource.setTargetDataSources(dataSoourceMap);
        dataSource.afterPropertiesSet();
        return new LazyConnectionDataSourceProxy(dataSource);
    }
}

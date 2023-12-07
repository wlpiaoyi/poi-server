package com.icss.poie.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * 配置mongodb 事务
 * 增加此类 此外需要事务的方法上增加 // @Transactional(transactionManager = "mongoTransactionManager") 开启事务
 */
@Slf4j
@Configuration
public class MongoConfig {

    @Bean("mongoTransactionManager")
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * 转换类配置
     *
     * @return 转换类
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDbFactory, MongoMappingContext mongoMappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        //不保存 _class 属性到mongo
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

}

/*

#停止mongo
systemctl stop mongod
systemctl status mongod

#备份配置文件
cp -a  /etc/mongod.conf /etc/mongod.conf.bk

#编辑配置文件
vim  mongod.cfg
#添加以下内容
replication:
  oplogSizeMB: 2000
  replSetName: rs0
  enableMajorityReadConcern: true

#保存
#启动mongo
systemctl start mongod
systemctl status mongod


##初始化副本
mongosh
mongosh> rs.initiate( { _id: "rs0", version: 1, members: [ { _id: 0, host: "localhost:27016" } ] })
mongosh> rs.status


 */

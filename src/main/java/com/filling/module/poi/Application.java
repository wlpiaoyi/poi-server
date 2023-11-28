package com.filling.module.poi;

import com.filling.Fv;
import com.filling.module.poi.domain.entity.BaseEntity;
import com.filling.module.poi.domain.entity.BaseMongoEntity;
import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/14 17:40
 * {@code @version:}:       1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.filling.module.poi"
})
@ComponentScan(basePackages = {"com.filling.module.poi"})
@MapperScan("com.filling.module.poi")
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application implements ApplicationContextAware {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(BaseEntity.ZONE));
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Data
    static class User extends BaseMongoEntity {
        private String name;
        private int age;
        private boolean sex;
    }

    @Data
    static class User1 extends User{
        private String p1;
    }
    @Data
    static class User2 extends User{
        private String p2;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
//        List<Map<String, Fv>> temps = new ArrayList<>();
//        temps.add(new HashMap(){{
//            put("h1", Fv.builder().v("v1").r(1).c(0));
//            put("h2", Fv.builder().v(201).r(1).c(1));
//            put("h3", Fv.builder().v(true).r(1).c(2));
//        }});
//        temps.add(new HashMap(){{
//            put("h1", Fv.builder().v("v2").r(2).c(0));
//            put("h2", Fv.builder().v(202).r(2).c(1));
//            put("h3", Fv.builder().v(false).r(2).c(2));
//        }});
//        temps.add(new HashMap(){{
//            put("h1", Fv.builder().v("v3").r(3).c(0));
//            put("h2", Fv.builder().v(203).r(3).c(1));
//            put("h3", Fv.builder().v(false).r(3).c(2));
//        }});
//        mongoTemplate.insert(temps, "poi_form_test_01");
//
//        Query queryCondition = new Query();
//
//        Criteria criteria = Criteria.where("h1.v").is("v1");
//        queryCondition.addCriteria(criteria);
//        List<Map> res = mongoTemplate.find(queryCondition, Map.class, "poi_form_test_01");
//        System.out.println();

//        User1 user1 = new User1();
//        user1.setId(ObjectId.get());
//        user1.setName("u1" + StringUtils.getUUID32().substring(0, 5));
//        user1.setAge(new Random().nextInt() % 50);
//        user1.setSex(true);
//        user1.setP1("p1");
//        user1.setCreateTime(new Date());
//        user1.setUpdateTime(new Date());
////        user1.setIsDeleted(0);
//        mongoTemplate.insert(user1);
//        User2 user2 = new User2();
//        user1.setId(ObjectId.get());
//        user2.setName("u2" + StringUtils.getUUID32().substring(0, 5));
//        user2.setAge(new Random().nextInt() % 50);
//        user2.setSex(true);
//        user2.setP2("p2");
//        user2.setCreateTime(new Date());
//        user2.setUpdateTime(new Date());
////        user2.setIsDeleted(0);
//        mongoTemplate.insert(user2);
//        // 拼装具体查询信息
//        List<AggregationOperation> operations = new ArrayList<>();
//
//        operations.add(context -> new Document("$group",
//                new Document("_id", "any")
//                        .append("user1",
//                                new Document("$push", "$$ROOT"))));
//        operations.add(context -> new Document("$lookup",
//                new Document("from", "user2")
//                        .append("localField", "invalidField")
//                        .append("foreignField", "testField")
//                        .append("as", "user2")));
//        operations.add(context -> new Document("$project",
//                new Document("_id", 0L)
//                        .append("allValue",
//                                new Document("$setUnion", Arrays.asList("$user1", "$user2")))));
//        operations.add(context -> new Document("$unwind", "$allValue"));
//        operations.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, "allValue.createTime")));
//
//        Aggregation aggregation = Aggregation.newAggregation(operations);
//
//        //查询、并获取结果
//        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "user1", Map.class);


        System.out.println();
    }
}

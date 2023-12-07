package com.icss.poie;

import com.icss.poie.framework.common.tools.PackageUtils;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.biz.excel.domain.entity.SheetData;
import com.mongodb.client.ListIndexesIterable;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.Indexed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ApplicationInitializer {

	/**
	 * 同步Mongo索引
	 * @param mongoTemplate
	 */
	public static void synMongoDocumentIndex(MongoTemplate mongoTemplate){
		PackageUtils.iteratorClazz("com.filling.module.poi", clazz -> {
			org.springframework.data.mongodb.core.mapping.Document document = clazz.getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class);
			if(document == null){
				return;
			}
			ApplicationInitializer.synMongoDocumentIndex(clazz, document.collection(), mongoTemplate);
		});

		for (int i = 1; i <= 12; i ++){
			String collection = SheetData.cellDataCollectionName(i);
			ApplicationInitializer.synMongoDocumentIndex(CellData.class, collection, mongoTemplate);
		}
	}

	private static void synMongoDocumentIndex(Class<?> entityClazz, String collection, MongoTemplate mongoTemplate){
		ListIndexesIterable<Document> indexList = mongoTemplate
				.getCollection(collection).listIndexes();
		List<String> indexFds = new ArrayList<>();
		for (Document indexDoc : indexList) {
			Object key = indexDoc.get("key");
			if(key == null){
				continue;
			}
			if(!(key instanceof Document)){
				continue;
			}
			Document keyDocument = (Document) key;
			if(keyDocument.containsKey("_id")){
				continue;
			}
			indexFds.addAll(keyDocument.keySet());
		}
		Field[] fields = entityClazz.getDeclaredFields();
		Map<String, Indexed> indexedMap = new HashMap<>();
		if(ValueUtils.isNotBlank(fields)){
			for (Field field : fields){
				Indexed indexed = field.getAnnotation(Indexed.class);
				if(indexed == null){
					continue;
				}
				if(indexFds.contains(field.getName())){
					continue;
				}
				indexedMap.put(field.getName(), indexed);
			}
		}
		for (Map.Entry<String, Indexed> entry : indexedMap.entrySet()){
			Index index = new Index();
			switch (entry.getValue().direction()){
				case ASCENDING:{
					index.on(entry.getKey(), Sort.Direction.ASC);
				}
				break;
				case DESCENDING:{
					index.on(entry.getKey(), Sort.Direction.DESC);
				}
			}
			if(ValueUtils.isNotBlank(entry.getValue().name())){
				index.named(entry.getValue().name());
			}else{
				index.named(collection + ".idx." + entry.getKey())
						.on(entry.getKey(), Sort.Direction.ASC);
			}
			if(entry.getValue().background()){
				index.background();
			}
			if(entry.getValue().unique()){
				index.unique();
			}
			String resultStr = mongoTemplate.indexOps(collection).ensureIndex(index);
			log.info("add mongo index result:{}", resultStr);
//			String resultStr = mongoTemplate.getCollection(collection)
//					.createIndex(new Document(entry.getKey(), "hashed"),
//							new IndexOptions().background(entry.getValue().background())
//									.unique(entry.getValue().unique())
//									.name(collection + ".idx." + entry.getKey()));
		}
	}
}

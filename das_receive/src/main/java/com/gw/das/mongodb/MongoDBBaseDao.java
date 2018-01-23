package com.gw.das.mongodb;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;


/**
 * 摘要：MongdDB基本数据接口类
 * @author Gavin.guo
 * @date  2015-04-07
 */
@Repository(value = "mongoDBBaseDao")
	
public class MongoDBBaseDao {
	@Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;
	
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 自增序列
	 * @param seq
	 * @return
	 */
	public String getNextSeqId(IdSeq seq){
		String name=seq.name();
		//自增序列 id，从 1开始
	    Update update = new Update();
		update.inc("seq", 1);
		//返回新自增序列 id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		SequenceId seqId =mongoTemplate.findAndModify(new Query(Criteria.where("id").is(name)), update, options, SequenceId.class);
		Long startNum=seq.getStartNum();
		Long seqNo=startNum;
		if(seqId != null) {
			seqNo=seqId.getSeq();
		}else{
			SequenceId newSeq=new SequenceId();
			newSeq.setId(name);
			newSeq.setSeq(startNum);
			mongoTemplate.insert(newSeq); 
		}
		String date = new SimpleDateFormat("yyMMdd").format(new Date());
		char[] charArray=IdSeq.charArray;
		long jobNo = seqNo % (charArray.length * startNum);
		int charArrayIndex = (int)(jobNo / startNum);
		StringBuilder sb = new StringBuilder()
			.append(seq.getPrefix()).append(date)
			.append(charArray[charArrayIndex])
			.append(String.valueOf(jobNo % startNum + startNum).substring(1));
		return sb.toString();
	}
	
	/**
	 * 功能：获取自增长的seq
	 */
	public Long getIncSeq(IdSeq seq){
		String name=seq.name();
		//自增序列 id，从 1开始
	    Update update = new Update();
		update.inc("seq", 1);
		//返回新自增序列 id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		SequenceId seqId = mongoTemplate.findAndModify(new Query(Criteria.where("id").is(name)), update, options, SequenceId.class);
		Long startNum=seq.getStartNum();
		Long seqNo=startNum;
		if(seqId != null) {
			seqNo = seqId.getSeq();
		}else{
			SequenceId newSeq=new SequenceId();
			newSeq.setId(name);
			newSeq.setSeq(startNum);
			mongoTemplate.insert(newSeq);
		}
		return seqNo;
	}
	
    /** 
     * 功能：根据主键id-->获取对象
     * @return T 对象
     */
    public <T> T findById(Class<T> entityClass, String id) {  
        return this.mongoTemplate.findById(id, entityClass);  
    }
    
    /** 
     * 功能：根据查询条件-->获取对象
     * @param entityClass 实体class
     * @param query       查询对象
     */
    public <T> T findOne(Class<T> entityClass, Query query) {  
         return this.mongoTemplate.findOne(query, entityClass);
    }
  
    /** 
     * 功能：根据查询条件-->获取对象
     * @param entityClass 实体class
     * @param query    查询对象
     * @param excludeFields 排除字段  
     */
    public <T> T findOneExclude(Class<T> entityClass, Query query,String ...excludeFields) {
    	 org.springframework.data.mongodb.core.query.Field field=query.fields();
    	 for(String fieldKey:excludeFields){
    		 field.exclude(fieldKey);
    	 }
         return this.mongoTemplate.findOne(query, entityClass);
    }
    
    /** 
     * 功能：根据查询条件-->获取对象
     * @param entityClass 实体class
     * @param query    查询对象
     * @param includeFields 包含字段  
     */
    public <T> T findOneInclude(Class<T> entityClass, Query query,String ...includeFields) {
    	 org.springframework.data.mongodb.core.query.Field field=query.fields();
    	 for(String fieldKey:includeFields){
    		 field.include(fieldKey);
    	 }
         return this.mongoTemplate.findOne(query, entityClass);
    }
    
	 /**
     * 功能：根据查询条件-->查询记录列表
     * @param entityClass 对象类型 
     * @param query  查询条件
     * @param includeFields 包含字段  
     */
    public <T> List<T> findListInclude(Class<T> entityClass, Query query,String ...includeFields) {
    	 org.springframework.data.mongodb.core.query.Field field=query.fields();
    	 for(String fieldKey:includeFields){
    		 field.include(fieldKey);
    	 }
        return this.mongoTemplate.find(query, entityClass);
    }
    
    /**
     * 功能：根据查询条件-->查询记录列表
     * @param entityClass 对象类型 
     * @param query  查询条件
     * @param excludeFields 排除字段  
     */
    public <T> List<T> findListExclude(Class<T> entityClass, Query query,String ...excludeFields) {
    	 org.springframework.data.mongodb.core.query.Field field=query.fields();
    	 for(String fieldKey:excludeFields){
    		 field.exclude(fieldKey);
    	 }
         return this.mongoTemplate.find(query, entityClass);
    } 
    
    /** 
     * 功能：根据类-->获取全部的对象列表 
     * @param entityClass  返回类型
     * @return List<T> 返回对象列表 
     */  
    public <T> List<T> findAll(Class<T> entityClass) {
        return this.mongoTemplate.findAll(entityClass);
    }  
  
    /** 
     * 功能：删除一个对象 
     * @param obj  要删除的Mongo对象 
     */  
    public void remove(Object obj) {  
        this.mongoTemplate.remove(obj);  
    }
    
    /** 
     * 功能：删除一个对象 
     * @param obj  要删除的Mongo对象 
     */  
    public <T> void remove(Query query, Class<T> entityClass) { 
        this.mongoTemplate.remove(query, entityClass);
    }
    
    /** 
     * 功能：删除一个对象 
     * @param obj  要删除的Mongo对象 
     */  
    public <T> void remove(Query query, String collection) { 
        this.mongoTemplate.remove(query, collection);
    }
  
    /**
     * 批量软删除(即把isDel字段更新为0)
     * @param ids
     * @return
     */
	public <T> boolean softDelete(Class<T> entityClass,Object[] ids){
		WriteResult wr=this.mongoTemplate.updateMulti(Query.query(Criteria.where("id").in(ids)), Update.update("isDel", 1),entityClass);
		return wr!=null&&wr.getN()>0;
	}
    
    /** 
     * 功能:插入单个对象 
     * @param obj  要添加的Mongo对象
     */
    public void add(Object obj) {
    	if(obj instanceof MongodbBaseModel){
    		Date currDate=new Date();
    		MongodbBaseModel baseModel=(MongodbBaseModel)obj;
    		baseModel.setCreateDate(currDate);
    		baseModel.setUpdateDate(currDate);
    	}
    	this.mongoTemplate.insert(obj);  
    }
    
    /**
     * 功能：批量插入对象
     * @param objectsToSave
     */
    public void batchAdd(Collection<? extends Object> collections){
    	this.mongoTemplate.insertAll(collections);
    }
  
    /**
     * 功能：更新对象
     * @param query
     * @param update
     * @param entityClass
     */
    public <T> void update(T t){
		if(t instanceof MongodbBaseModel){
			MongodbBaseModel baseModel=(MongodbBaseModel)t;
			baseModel.setUpdateDate(new Date());
		}
	    this.mongoTemplate.save(t);
    }
    
    /**
     * 功能：更新对象
     * @param query
     * @param update
     * @param entityClass
     */
    public <T> void update(Query query,T t){
    	Update update = this.buildBaseUpdate(t);
        this.mongoTemplate.updateFirst(query, update, t.getClass());
    }
    
    /**
     * 功能：更新对象
     * @param query
     * @param update
     * @param documentCls
     */
    public <T> void update(Query query, Update update, Class<T> documentCls){
        this.mongoTemplate.updateFirst(query, update, documentCls);
    }
    
    /**
     * 功能：更新对象(如果传入的值为null,则删除该字段,否则更新改字段)
     */
    public <T> void updateFirst(Query query,T t){
    	if(t instanceof MongodbBaseModel){
    		MongodbBaseModel baseModel=(MongodbBaseModel)t;
			baseModel.setUpdateDate(new Date());
		}
    	Update update = buildBaseUpdate(t);
    	this.mongoTemplate.updateFirst(query, update, t.getClass());
    }
    
    /**
     * 功能：构造更新的Update
     * @param t
     * @return
     */
    private <T> Update buildBaseUpdate(T t) {
    	Update update = new Update();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
            	if(!field.isAnnotationPresent(Transient.class)){
            		Object value = field.get(t);
    	            if (value != null) {
    	                update.set(field.getName(), value);
    	            }
            	}
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
        return update;  
    }
    
    /**
     * 功能：批量更新
     * @param <T>
     * @param query 查询条件
     * @param update 更新字段对象
     * @param entityClass 更新实体
     */
    public <T> void batchUpdate(Query query,Update update,Class<T> entityClass){
    	this.mongoTemplate.updateMulti(query, update, entityClass);
    }
  
    /** 
     * 功能：查询并分页 
     * @param entityClass  对象类型 
     * @param query  查询条件
     * @param page  分页
     */  
    public <T> List<T> findList(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria) {  
    	List<Order> orderList = new ArrayList<Order>();
		HashMap<String, SortDirection> sortMap = dCriteria.getOrderbyMap();
		if(sortMap != null){
			String keyStr="";
			for(Entry<String, SortDirection> entry : sortMap.entrySet()){
				keyStr=entry.getKey();
				if(keyStr.contains("[0]")){//调整多级文档格式的字段
					keyStr=keyStr.replaceAll("\\[0\\]", "");
				}
				Order order = new Order(SortDirection.ASC.equals(entry.getValue()) ? Direction.ASC : Direction.DESC,keyStr);
				orderList.add(order);
			}
			query.with(new Sort(orderList));
		}
    	query.skip((dCriteria.getPageNo() - 1) * dCriteria.getPageSize()).limit(dCriteria.getPageSize());
        return this.mongoTemplate.find(query, entityClass);
    }
    
    /** 
     * 功能：查询并分页 
     * @param entityClass  对象类型 
     * @param query  查询条件
     * @param page  分页
     */  
    public <T> List<T> findListInclude(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria, String ...includeFields) {
    	org.springframework.data.mongodb.core.query.Field field = query.fields();
    	for(String fieldKey:includeFields){
    		 field.include(fieldKey);
    	}
    	return this.findList(entityClass, query, dCriteria);
    }
    
    /** 
     * 功能：分页查询
     * @param entityClass  对象类型 
     * @param query  查询条件
     * @param page  分页
     */  
    public <T> Page<T> findPage(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria) {  
    	Page<T> page=new Page<T>();
    	page.setTotalSize(this.count(entityClass,query).intValue());
    	page.addAll(this.findList(entityClass,query,dCriteria));
    	page.setPageNo(dCriteria.getPageNo());
    	page.setPageSize(dCriteria.getPageSize());
        return page;
    }
    
    /**
     * 分页查询
     * @param entityClass
     * @param query
     * @param dCriteria
     * @param includeFields
     * @return
     */
    public <T> Page<T> findPageInclude(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria,String ...includeFields) {  
    	Page<T> page=new Page<T>();
    	org.springframework.data.mongodb.core.query.Field field=query.fields();
    	for(String fieldKey:includeFields){
    		 field.include(fieldKey);
    	}
    	page.setTotalSize(this.count(entityClass,query).intValue());
    	page.addAll(this.findList(entityClass,query,dCriteria));
        return page;  
    }
    
    /**
     * 分页查询
     * @param entityClass
     * @param query
     * @param dCriteria
     * @param includeFields
     * @param excludeFields
     * @return
     */
    public <T> Page<T> findPage(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria,String[] includeFields, String[] excludeFields) {  
    	Page<T> page=new Page<T>();
    	org.springframework.data.mongodb.core.query.Field field=query.fields();
    	for(String fieldKey:includeFields){
    		 field.include(fieldKey);
    	}
    	for(String fieldKey:excludeFields){
    		field.exclude(fieldKey);
    	}
    	page.setTotalSize(this.count(entityClass,query).intValue());
    	page.addAll(this.findList(entityClass,query,dCriteria));
        return page;  
    }
    
    /**
     * 功能：根据查询条件-->查询记录列表
     * @param entityClass 对象类型 
     * @param query  查询条件
     */
    public <T> List<T> findList(Class<T> entityClass, Query query) {
        return this.mongoTemplate.find(query, entityClass);
    }
  
    /** 
     * 功能：根据查询条件查询某个对象的总数
     * @param entityClass  查询对象 
     * @param query  查询条件
     */  
    public <T> Long count(Class<T> entityClass, Query query) {  
        return this.mongoTemplate.count(query, entityClass);  
    }  
      
    /** 
     * 功能：批量插入 
     * @param entityClass 对象类 
     * @param collection  要插入的对象集合
     */  
    public <T> void addCollection(Class<T> entityClass, Collection<T> collection){
        this.mongoTemplate.insert(collection, entityClass);  
    }
  
    public MongoTemplate getMongoTemplate() {  
        return mongoTemplate;  
    }  
}

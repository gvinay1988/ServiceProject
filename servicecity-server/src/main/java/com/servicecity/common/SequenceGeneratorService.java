package com.servicecity.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Yedukondalu K        
 *
 */

@Service
public class SequenceGeneratorService {

	private MongoTemplate mongoTemplate;

	@Autowired
	public SequenceGeneratorService(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}
	public long generateSequence(String _id){
		Query query = new Query(Criteria.where("_id").is(_id));
		Update update = new Update().inc("sequence", 1);
		DatabaseSequence databaseSequence = mongoTemplate.findAndModify(query, update, DatabaseSequence.class); // return old Counter object
		return databaseSequence.getSequence();
	}

}

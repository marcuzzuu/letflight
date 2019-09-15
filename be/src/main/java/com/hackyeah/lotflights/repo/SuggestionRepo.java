package com.hackyeah.lotflights.repo;

import com.hackyeah.lotflights.model.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SuggestionRepo
{
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public List<Suggestion> findSuggestionsById(final String id)
    {
        final Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        return this.mongoTemplate.find(query,Suggestion.class);
    }
}

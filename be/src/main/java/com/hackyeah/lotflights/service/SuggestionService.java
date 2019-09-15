package com.hackyeah.lotflights.service;

import com.hackyeah.lotflights.model.Suggestion;
import com.hackyeah.lotflights.repo.SuggestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SuggestionService
{
    @Autowired
    private SuggestionRepo suggestionRepo;
    
    public List<Suggestion> getSuggestionsBasedOnIata(final String iata)
    {
        if (!StringUtils.isEmpty(iata))
        {
            return this.suggestionRepo.findSuggestionsById(iata);
        }
        return Collections.emptyList();
    }
}

package com.example.jobfinder.service.impl;

import org.springframework.stereotype.Service;

import com.example.jobfinder.exception.JsonProcessException;
import com.example.jobfinder.service.JsonReaderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonReaderServiceImpl implements JsonReaderService<Object> {

    @Override
    public <U> U readValue(String content, Class<U> valueType) {
        try {
            return new ObjectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonProcessException(e);
        }
    }
}

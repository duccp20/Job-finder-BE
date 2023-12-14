package com.example.jobfinder.service;

public interface JsonReaderService<T> {
    <U> U readValue(String content, Class<U> valueType);
}

package com.gdc.diary.mapper;

public interface Mapper<TSource, TDestination> {
    TDestination map(TSource source);
}

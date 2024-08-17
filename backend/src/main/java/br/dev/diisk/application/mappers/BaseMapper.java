package br.dev.diisk.application.mappers;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import jakarta.annotation.PostConstruct;

public abstract class BaseMapper<S, T> implements Function<S, T> {

    private Class<T> targetType;
    private Class<S> sourceType;
    
    private final ModelMapper mapper;

    @SuppressWarnings("unchecked")
    public BaseMapper(ModelMapper mapper) {
        this.sourceType = (Class<S>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.targetType = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        TypeMap<S, T> typeMap = mapper.createTypeMap(sourceType, targetType);
        configureMapping(typeMap);
    }

    protected void configureMapping(TypeMap<S, T> typeMap){

    }

    protected void doComplexMap(S source, T target){

    }
    
    @Override
    public T apply(S source) {
        T target = mapper.map(source, targetType);
        doComplexMap(source, target);
        return target;
    }

    public void update(S source, T target) {
        mapper.map(source, target);
    }

    public List<T> mapList(Collection<S> source) {
        return source.stream().map(this).collect(Collectors.toList());
    }

    public Set<T> mapSet(Collection<S> source) {
        return source.stream().map(this).collect(Collectors.toSet());
    }
}

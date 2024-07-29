package br.dev.diisk.application.mappers;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;

public abstract class BaseMapper<S, T> implements Function<S, T> {

    private Class<T> targetType;
    private Class<S> sourceType;

    @Autowired
    private ModelMapper mapper;

    public BaseMapper(Class<S> sourceType, Class<T> targetType) {
        this.targetType = targetType;
        this.sourceType = sourceType;
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

    public List<T> mapList(Collection<S> source) {
        var test = source.stream().map(this).collect(Collectors.toList());
        return test;
    }

    public Set<T> mapSet(Collection<S> source) {
        var test = source.stream().map(this).collect(Collectors.toSet());
        return test;
    }
}

package br.dev.diisk.infra.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class CustomMapperService extends ModelMapper {

    public CustomMapperService() {
        getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
    }

    public <T, S> List<T> mapList(Collection<S> source, Function<S, T> mapFunc) {
        return source.stream().map(mapFunc).collect(Collectors.toList());
    }

    public <T, S> Set<T> mapSet(Collection<S> source, Function<S, T> mapFunc) {
        return source.stream().map(mapFunc).collect(Collectors.toSet());
    }


}

package com.example.demo.mapper;

import com.example.demo.dto.VolDTO;
import com.example.demo.entity.Vol;
import org.mapstruct.Mapper;
import com.example.demo.entity.Avion;
import org.mapstruct.Mapping;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VolMapper {
    
    @Mapping(source = "avions", target = "avionIds", qualifiedByName = "avionsToIds")
    @Mapping(source = "aeroportDepart.id", target = "aeroportDepartId")
    @Mapping(source = "aeroportArrivee.id", target = "aeroportArriveeId")
    VolDTO toDto(Vol entity);
    
    @Mapping(target = "avions", ignore = true)
    @Mapping(target = "aeroportDepart", ignore = true)
    @Mapping(target = "aeroportArrivee", ignore = true)
    Vol toEntity(VolDTO dto);

    @Named("avionsToIds")
    default List<Long> avionsToIds(Set<Avion> avions) {
        return avions.stream().map(Avion::getId).collect(Collectors.toList());
    }
}
package com.nqbao.project.service;

import com.nqbao.project.exception.DuplicateIdException;
import com.nqbao.project.exception.MissingRequiredParameter;
import com.nqbao.project.exception.NotFoundException;
import lombok.Getter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class GenericService<T, ID, DTO> {

    private final JpaRepository<T, ID> repository;
    @Getter
    private final String cacheName;

    private final Function<T, DTO> entityToDtoMapper;
    private final Function<DTO, T> dtoToEntityMapper;

    private T convertToEntity(DTO dto) {
        return dtoToEntityMapper.apply(dto);
    }

    private DTO convertToDTO(T t) {
        return entityToDtoMapper.apply(t);
    }

    public GenericService(JpaRepository<T, ID> jpaRepository, String cacheName, Function<T, DTO> entityToDtoMapper, Function<DTO, T> dtoToEntityMapper) {
        this.repository = jpaRepository;
        this.cacheName = cacheName;
        this.entityToDtoMapper = entityToDtoMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
    }


    @Cacheable(cacheResolver = "dynamicCacheResolver", key = "#id")
    public Optional<DTO> findById(ID id) {
        return repository.findById(id).map(entityToDtoMapper);
    }

    public DTO save(DTO dto) {
        T entity = convertToEntity(dto);
        T savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    @CacheEvict(cacheResolver = "dynamicCacheResolver", key = "#id", beforeInvocation = true)
    public void deleteById(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Can not find item with id = " + id);
        }
    }

    @Cacheable(cacheResolver = "dynamicCacheResolver")
    public List<DTO> findAll() {
        return repository.findAll().stream().map(entityToDtoMapper).collect(Collectors.toList());
    }

    @CachePut(cacheResolver = "dynamicCacheResolver", key = "#id")
    public DTO updateById(ID id, DTO item) throws NoSuchFieldException, IllegalAccessException {
        DTO oldItem = findById(id).orElseThrow(() -> new NotFoundException("Can not find item with id = " + id));
        Class<?> clazz = item.getClass();
        List<String> checkFieldList = getClassPropertiesWithoutId(clazz);

        for (String fieldName : checkFieldList) {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(item);
            if (fieldValue == null) {
                throw new MissingRequiredParameter("Please fill " + fieldName);
            }
            field.set(oldItem, fieldValue);
        }
        save(oldItem);
        return oldItem;
    }

    public <T> List<String> getClassPropertiesWithoutId(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).map(Field::getName).filter(name -> !name.equals("id")).toList();
    }

    public DTO create(DTO itemDTO) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = itemDTO.getClass();
        List<String> checkFiedlList = getClassPropertiesWithoutId(clazz);
        Field fieldId = clazz.getDeclaredField("id");
        fieldId.setAccessible(true);

        ID id = (ID) fieldId.get(itemDTO);
        if (findById(id).isPresent()) {
            throw new DuplicateIdException("id " + id + "already exists.");
        }
        for (String fieldName : checkFiedlList) {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(itemDTO);
            if (fieldValue == null) {
                throw new MissingRequiredParameter("Please fill " + fieldName);
            }
        }
//        T entity = convertToEntity(itemDTO);
        DTO savedEntity = save(itemDTO);
        return savedEntity;
    }

}

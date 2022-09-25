package uz.darico.base.service;

import lombok.AllArgsConstructor;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.base.repository.BaseRepository;
import uz.darico.base.validator.BaseValidator;

@AllArgsConstructor
public abstract class AbstractService<R extends BaseRepository,
        V extends BaseValidator,
        M extends BaseMapper> implements BaseService {
    public R repository;
    public V validator;
    public M mapper;
}

package uz.darico.base.controller;

import lombok.AllArgsConstructor;
import uz.darico.base.service.BaseService;

@AllArgsConstructor
public abstract class AbstractController<S extends BaseService> implements BaseController {
    public S service;
}

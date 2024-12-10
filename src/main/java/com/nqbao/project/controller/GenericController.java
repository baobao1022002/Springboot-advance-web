package com.nqbao.project.controller;
import com.nqbao.project.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public class GenericController<T, ID, DTO > {

    private final GenericService<T, ID, DTO> service;


    public GenericController(GenericService<T, ID, DTO> service) {
        this.service = service;
    }

    @Operation(summary = "Get all", description = "API get all")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DTO> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Get by id", description = "API get by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTO getById(@PathVariable(name = "id") ID id) {
        return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can not find with id " + id));
    }

    @Operation(summary = "Delete by id", description = "API delete by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") ID id) {
        service.deleteById(id);
    }


    @Operation(summary = "Update by id", description = "API update by id")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTO updateById(@PathVariable(name = "id") ID id, @Valid @RequestBody DTO item) throws NoSuchFieldException, IllegalAccessException {
        return service.updateById(id, item);
    }

    @Operation(summary = "create new ", description = "API create new")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DTO create(@Valid @RequestBody DTO item) throws NoSuchFieldException, IllegalAccessException {
        return service.create(item);
    }


}

package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.table.TableDto;
import com.example.restaurantmanagement.service.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/table")
@CrossOrigin("*")
public class TableController {

    private final TableService tableService;

    @GetMapping
    public List<TableDto> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/{tableId}")
    public TableDto getTableById(@PathVariable String tableId) {
        return tableService.getTableById(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTable(@RequestBody @Valid TableDto tableDto) {
        tableService.createTable(tableDto);
    }

    @PutMapping("/{tableId}")
    public void updateTable(@PathVariable String tableId, @RequestBody @Valid TableDto tableDto) {
        tableService.updateTable(tableId, tableDto);
    }

    @DeleteMapping("/{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable String tableId){
        tableService.deleteTable(tableId);
    }
}

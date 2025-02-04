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
public class TableController {

    private final TableService tableService;

    @GetMapping
    public List<TableDto> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/{table-id}")
    public TableDto getTableById(@PathVariable("table-id") String tableId) {
        return tableService.getTableById(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTable(@RequestBody @Valid TableDto tableDto) {
        tableService.createTable(tableDto);
    }

    @PutMapping("/{table-id}")
    public void updateTable(@PathVariable("table-id") String tableId, @RequestBody @Valid TableDto tableDto) {
        tableService.updateTable(tableId, tableDto);
    }

    @DeleteMapping("/{table-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable("table-id") String tableId){
        tableService.deleteTable(tableId);
    }
}

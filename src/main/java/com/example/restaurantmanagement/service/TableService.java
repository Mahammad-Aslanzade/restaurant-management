package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.dao.repository.TableRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.TableMapper;
import com.example.restaurantmanagement.model.table.TableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TableService {

    private final TableMapper tableMapper;
    private final TableRepository tableRepository;

    public List<TableDto> getAllTables() {
        log.info("ACTION.getAllTables.start");
        List<TableEntity> tableEntityList = tableRepository.findAll();
        List<TableDto> tableDtoList = tableMapper.listToDto(tableEntityList);
        log.info("ACTION.getAllTables.end");
        return tableDtoList;
    }

    public TableEntity getTableEntity(String tableId) {
        return tableRepository.findById(tableId).orElseThrow(() ->
                new NotFoundException(
                        ExceptionDetails.TABLE_NOT_FOUND.message(),
                        ExceptionDetails.TABLE_NOT_FOUND.createLogMessage("getTableEntity", tableId)
                )
        );
    }

    public TableDto getTableById(String tableId) {
        log.info("ACTION.getTableById.start id : {}", tableId);
        TableEntity tableEntity = getTableEntity(tableId);
        TableDto tableDto = tableMapper.mapToDto(tableEntity);
        log.info("ACTION.getTableById.end id : {}", tableId);
        return tableDto;
    }

    public void throwIfTableExist(Integer tableNo) {
        if (tableRepository.findByNo(tableNo).isPresent()){
            throw new AlreadyExistException(
                    ExceptionDetails.TABLE_IS_ALREADY_EXIST.message(),
                    ExceptionDetails.TABLE_IS_ALREADY_EXIST.createLogMessage("checkNoIsValid", "no", tableNo.toString())
            );
        }
    }

    public void createTable(TableDto tableDto) {
        log.info("ACTION.createTable.start requestBody : {}", tableDto);
        throwIfTableExist(tableDto.getNo());
        TableEntity table = tableMapper.mapToEntity(tableDto);
        tableRepository.save(table);
        log.info("ACTION.createTable.end requestBody : {}", tableDto);
    }

    public void updateTable(String tableId, TableDto tableDto) {
        log.info("ACTION.updateTable.start id : {} |  requestBody : {}", tableId, tableDto);
        TableEntity oldTable = getTableEntity(tableId);
        //If table number change , it will check is it valid table no
        if(!oldTable.getNo().equals(tableDto.getNo())){
            throwIfTableExist(tableDto.getNo());
        }
        //
        TableEntity updatedTable = tableMapper.mapToEntity(tableDto);
        updatedTable.setId(oldTable.getId());
        tableRepository.save(updatedTable);
        log.info("ACTION.updateTable.end id : {} |  requestBody : {}", tableId, tableDto);
    }

    public void deleteTable(String tableId) {
        log.info("ACTION.deleteTable.start tableId : {}", tableId);
        TableEntity table = getTableEntity(tableId);
        tableRepository.delete(table);
        log.info("ACTION.deleteTable.end tableId : {}", tableId);
    }

}

package com.example.restaurantmanagement.model.table;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private String id;
    @Min(0)
    private Integer no;
    @Min(0)
    private Integer floor;
    private Integer capacity;
    private Boolean cigarette;
    private Boolean windowView;
}

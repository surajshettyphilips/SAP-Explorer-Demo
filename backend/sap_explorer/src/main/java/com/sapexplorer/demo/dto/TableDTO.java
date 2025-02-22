package com.sapexplorer.demo.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.Getter;

@Data
@Getter
@Builder
@AllArgsConstructor
public class TableDTO {

    private String tableName;
    private String name;
    private String description;

   
}

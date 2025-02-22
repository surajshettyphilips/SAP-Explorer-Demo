package com.sapexplorer.demo.models;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Table {
  
    
    private String tableName;
    private String name;
    private String description;

    
    
}

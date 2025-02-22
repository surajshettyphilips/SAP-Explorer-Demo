package com.sapexplorer.demo.services;


import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import com.sapexplorer.demo.dto.TableDTO;
import reactor.core.publisher.Mono;

@Service
public class PostgresService {
    
    private final DatabaseClient tables;
    PostgresService(DatabaseClient tables){
        this.tables=tables;
    }
public Mono<Long> saveTableDetails(TableDTO tableDetails){
    System.out.println(tableDetails.getDescription());
    String sql = "INSERT INTO sapschema.saptable (tablename, name,description) VALUES ( '"+ tableDetails.getTableName() + "', '" + tableDetails.getName() +"', '"+tableDetails.getDescription()+"') "; 
    return tables.sql(sql).fetch().rowsUpdated();
}


    
}

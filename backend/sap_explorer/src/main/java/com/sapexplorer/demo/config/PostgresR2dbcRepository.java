package com.sapexplorer.demo.config;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.sapexplorer.demo.dto.TableDTO;

public interface  PostgresR2dbcRepository extends R2dbcRepository<TableDTO,Long>{
    
}

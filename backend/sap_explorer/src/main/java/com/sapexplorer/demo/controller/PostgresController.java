package com.sapexplorer.demo.controller;




import com.sapexplorer.demo.dto.TableDTO;
import com.sapexplorer.demo.models.ResponseData;
import com.sapexplorer.demo.services.PostgresService;
import com.sapexplorer.demo.services.ReactiveReadLines;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.r2dbc.core.DatabaseClient;

import org.springframework.web.bind.annotation.*;






@RestController
@RequestMapping("/postgres")

public class PostgresController {
    @Autowired
    private PostgresService reposervice;
    private final DatabaseClient tables;

    @Autowired
    private ReactiveReadLines FileReadLineService;
    PostgresController(DatabaseClient tables){
        this.tables=tables;
    }
    @GetMapping("/ping") 
    public Mono<ResponseData> ping(){
        
            return Mono.just(new ResponseData("Pong"));
        
    }
    @GetMapping("/all/tables") 
    public Flux<HashMap<String, String>> getTableList(){
        try{
            //return tables.sql("SELECT *  FROM sapschema.saptable").map(row->row.get("name",String.class)).all();
           return tables.sql("SELECT *  FROM sapschema.saptable")
                .fetch()
                .all() // Returns a Flux of Rows
                .flatMap(row -> {  // Transform each Row to a Map
                    HashMap<String, String> map = new HashMap<String, String>();
                    
                    map.put((String)row.get("name"), (String)row.get("description"));
                    
                    return Mono.just(map); // Emit each map as a single item in the Flux
                });
        }catch(Exception e){
            return Flux.empty();
        }
    }

    @GetMapping("/tabledetails/{id}")
    public Flux<TableDTO> getTableByName(@PathVariable String id) {
        try{
        System.out.println("------------------------------"+id);
        
        //HashMap<String,String> result = new HashMap<String,String>();
        return tables.sql("SELECT * FROM sapschema.saptable WHERE tablename = :id")
                .bind("id", id)
                .map((row, metadata) -> new TableDTO(
                        row.get("tablename", String.class),
                        row.get("name", String.class),  
                        row.get("description", String.class) 
                ))
                .all();
                
        //return tables.sql("SELECT * FROM sapschema.saptable where tablename= :id").bind("id",id).map(row->result.put(row.get("name",String.class),row.get("description",String.class))).all();
        }catch(Exception e){
            e.printStackTrace();
            return Flux.empty();
        }
    }

@PostMapping("/upload-files")
Mono uploadFileWithoutEntity(@RequestPart("files") Flux<FilePart> filePartFlux) {
    return filePartFlux.flatMap(file ->       FileReadLineService.getLines(file).skip(1).flatMap(str->{
        System.out.println("usave to db");
        String[] line = str.split(",");
        if(line.length>0){
            String tableName=line[0];
            String name=line[1];
            System.out.println(tableName);
            String description = line[line.length-1];
            description=description.replaceAll("'","");
            System.out.println(description);
            TableDTO dto = TableDTO.builder().tableName(tableName).name(name).description(description).build();
            reposervice.saveTableDetails(dto).subscribe();
            System.out.println("reposervice"+System.currentTimeMillis());
           }
           return Mono.empty();
          
    }).subscribeOn(Schedulers.boundedElastic()))
      .then(Mono.just("SAP data saved successfully"))
      .onErrorResume(error -> Mono.just("Error uploading files"));
}

    @PostMapping(value ="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<ResponseData> uploadFile(@RequestPart("file") Flux<FilePart> file)  {
      System.out.println("uploadFile   "+file);
      FileReadLineService.getLines(file).skip(1).flatMap(str->{
            System.out.println("uploadFile2");
            String[] line = str.split(",");
            if(line.length>0){
                String tableName=line[0];
                String name=line[1];
                System.out.println(tableName);
                String description = line[line.length-1];
                description=description.replaceAll("'","");
                System.out.println(description);
                TableDTO dto = TableDTO.builder().tableName(tableName).name(name).description(description).build();
                reposervice.saveTableDetails(dto).subscribe();
                System.out.println("reposervice"+System.currentTimeMillis());
               }
               return Mono.empty();
              
        }).subscribeOn(Schedulers.boundedElastic());
        return Flux.just(new ResponseData("Table Updated Successfully...."));
    }
    
    
}

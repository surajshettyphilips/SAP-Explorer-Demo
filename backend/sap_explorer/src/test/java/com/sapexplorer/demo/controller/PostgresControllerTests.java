package com.sapexplorer.demo.controller;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


import com.sapexplorer.demo.services.PostgresService;
import com.sapexplorer.demo.services.ReactiveReadLines;



import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PostgresControllerTests {

    @Autowired
    private WebTestClient webTestClient;
    @Mock
    private PostgresService reposervice;
    @Mock
    private ReactiveReadLines realinebean;

    @InjectMocks
    private PostgresController controller; // Your controller
    @Test
    public void shouldReturnGreeting() {
        webTestClient
                .get()
                .uri("/postgres/ping")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("{\"message\":\"Pong\"}");
    }
    @Test
    void testMultipartFileUploadAsFlux() {
  
        // Test data
        String fileContent = "TABNAME,FIELDNAME,AS4LOCAL,AS4VERS,POSITION,KEYFLAG,MANDATORY,ROLLNAME,CHECKTABLE,ADMINFIELD,INTTYPE,INTLEN,REFTABLE,PRECFIELD,REFFIELD,CONROUT,NOTNULL,DATATYPE,LENG,DECIMALS,DOMNAME,SHLPORIGIN,TABLETYPE,DEPTH,COMPTYPE,REFTYPE,LANGUFLAG,DBPOSITION,ANONYMOUS,OUTPUTSTYLE,DDTEXT\n VBAP,MANDT,A,0,1,X, ,MANDT,T000,0,C,3, , , , ,X,CLNT,3,0,MANDT,P, ,0,E, , ,0, ,0,Client\n";
        byte[] fileBytes = fileContent.getBytes(StandardCharsets.UTF_8);

        // Create a MultipartBodyBuilder
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(fileBytes)); // Use appropriate media type

       
        // Perform the POST request with multipart data
        webTestClient.post()
                .uri("/postgres/upload") // Your upload endpoint
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isOk(); // Expect 200 OK
                




    }
}
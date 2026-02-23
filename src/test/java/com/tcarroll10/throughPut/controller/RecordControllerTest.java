package com.tcarroll10.throughPut.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tcarroll10.throughPut.service.RecordService;
import com.tcarroll10.throughPut.to.AccountingResult;

@WebMvcTest(RecordController.class)
class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecordService recordService;

    @Test
    void createRecord_returnsCreated() throws Exception {
        AccountingResult result = new AccountingResult("1", "test-payload", 1000L);
        when(recordService.save(any(AccountingResult.class))).thenReturn(result);

        mockMvc.perform(post("/api/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":"1","payload":"test-payload","timestamp":1000}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.payload").value("test-payload"))
                .andExpect(jsonPath("$.timestamp").value(1000));
    }

    @Test
    void createRecords_batch_returnsCreated() throws Exception {
        when(recordService.saveAll(anyList())).thenReturn("test-batch-id");

        mockMvc.perform(post("/api/records/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {"id":"1","payload":"payload-1","timestamp":1000},
                                  {"id":"2","payload":"payload-2","timestamp":2000}
                                ]
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchId").value("test-batch-id"))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.records[0].id").value("1"))
                .andExpect(jsonPath("$.records[1].id").value("2"));
    }
}

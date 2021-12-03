package com.fastcampus.investment.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("채점용 테스트")
class ApisTest {
    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("채점 01. 투자 상품 조회시 모집기간에 해당되는 상품만 조회(10점)")
    void _01_findAllOpenProductsApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].title", is("You can be elon musk")))
                .andExpect(jsonPath("$.data[0].totalInvestAmount", is(100000000)))
                .andExpect(jsonPath("$.data[0].investedCount", is(0)))
                .andExpect(jsonPath("$.data[0].investedAmount", is(0)))
                .andExpect(jsonPath("$.data[0].startedAt", is(LocalDate.now().minusDays(1L).toString())))
                .andExpect(jsonPath("$.data[0].finishedAt", is(LocalDate.now().plusDays(1L).toString())))
                .andExpect(jsonPath("$.data[1].id", is(4)))
                .andExpect(jsonPath("$.data[1].title", is("TOBE-RICH of Warren Buffett")))
                .andExpect(jsonPath("$.data[1].totalInvestAmount", is(600000000)))
                .andExpect(jsonPath("$.data[1].investedCount", is(0)))
                .andExpect(jsonPath("$.data[1].investedAmount", is(0)))
                .andExpect(jsonPath("$.data[1].startedAt", is(LocalDate.now().minusDays(2L).toString())))
                .andExpect(jsonPath("$.data[1].finishedAt", is(LocalDate.now().plusDays(2L).toString())))
        ;
    }

    @Test
    @DisplayName("채점 02. 투자하기 성공(15점)")
    void _02_creatInvestmentApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/investment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 1)
                        .param("productId", "1")
                        .param("investAmount", "10000")
        );

        result.andDo(print())
                .andExpect(status().isOk())
        ;

        ResultActions after = mockMvc.perform(
                get("/api/investment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 1)
        );

        after.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].product").exists())
                .andExpect(jsonPath("$.data[0].product.id", is(1)))
                .andExpect(jsonPath("$.data[0].product.title", is("You can be elon musk")))
                .andExpect(jsonPath("$.data[0].product.totalInvestAmount", is(100000000)))
                .andExpect(jsonPath("$.data[0].investedAmount", is(10000)))
                .andExpect(jsonPath("$.data[0].status", is("INVESTED")))
                .andExpect(jsonPath("$.data[0].investedAt").exists())
        ;
    }

    @Test
    @DisplayName("채점 03. 이미 부자라 투자 실패(15점)")
    void _03_creatInvestmentApiFailTest() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/investment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 2)
                        .param("productId", "1")
                        .param("investAmount", "100000000000")
        );

        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.status", is("FAIL")))
        ;

        ResultActions after = mockMvc.perform(
                get("/api/investment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 2)
        );
        after.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("$.data[0].id", is(2)))
                .andExpect(jsonPath("$.data[0].product").exists())
                .andExpect(jsonPath("$.data[0].product.id", is(1)))
                .andExpect(jsonPath("$.data[0].product.title", is("You can be elon musk")))
                .andExpect(jsonPath("$.data[0].product.totalInvestAmount", is(100000000)))
                .andExpect(jsonPath("$.data[0].investedAmount", is(100000000000L)))
                .andExpect(jsonPath("$.data[0].status", is("FAIL")))
                .andExpect(jsonPath("$.data[0].investedAt").exists())
        ;
    }

    @Test
    @DisplayName("채점 04. 개미의 투자 취소(20점)")
    void _04_cancelInvestmentApiTest() throws Exception {
        ResultActions result = mockMvc.perform(
                put("/api/investment/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 1)
                        .param("status", "CANCELED")
        );

        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
        ;

        ResultActions after = mockMvc.perform(
                get("/api/investment")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", 1)
        );

        after.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].product").exists())
                .andExpect(jsonPath("$.data[0].product.id", is(1)))
                .andExpect(jsonPath("$.data[0].product.title", is("You can be elon musk")))
                .andExpect(jsonPath("$.data[0].product.totalInvestAmount", is(100000000)))
                .andExpect(jsonPath("$.data[0].investedAmount", is(10000)))
                .andExpect(jsonPath("$.data[0].status", is("CANCELED")))
                .andExpect(jsonPath("$.data[0].investedAt").exists())
        ;
    }
}
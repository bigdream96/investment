DROP TABLE IF EXISTS INVESTMENTS CASCADE;
DROP TABLE IF EXISTS PRODUCTS CASCADE;

CREATE TABLE PRODUCTS
(
    ID                     BIGINT       NOT NULL AUTO_INCREMENT, --ID
    TITLE                  VARCHAR(100) NOT NULL,                --상품명
    TOTAL_INVESTING_AMOUNT BIGINT       NOT NULL,                --총 투자 모집금액
    STARTED_AT             DATETIME     NOT NULL,                --투자시작일시
    FINISHED_AT            DATETIME     NOT NULL,                --투자종료일시
    PRIMARY KEY (ID)
);

CREATE TABLE INVESTMENTS
(
    ID                     BIGINT AUTO_INCREMENT PRIMARY KEY,   --ID
    USER_ID                BIGINT,
    PRODUCTS_ID            BIGINT,                              --상품ID
    INVESTED_AMOUNT        BIGINT,                              --투자금액
    STATUS                 VARCHAR(20)  NOT NULL,               --투자상태
    INVESTED_AT            DATETIME     NOT NULL,               --투자일시
    FOREIGN KEY (PRODUCTS_ID) REFERENCES PRODUCTS(ID)
);
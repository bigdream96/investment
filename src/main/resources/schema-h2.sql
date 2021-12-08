DROP TABLE IF EXISTS investments CASCADE;
DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products
(
    id                     bigint       NOT NULL AUTO_INCREMENT, --id
    title                  varchar(100) NOT NULL,                --상품명
    total_investing_amount bigint       NOT NULL,                --총 투자 모집금액
    started_at             datetime     NOT NULL,                --투자시작일시
    finished_at            datetime     NOT NULL,                --투자종료일시
    PRIMARY KEY (id)
);

CREATE TABLE investments
(
    id                     bigint auto_increment primary key,   --id
    user_id                bigint,
    products_id            bigint,                              --상품id
    invested_amount        bigint,                              --투자금액
    status                 varchar(20)  not null,               --투자상태
    invested_at            datetime     not null,               --투자일시
    foreign key (products_id) references products(id)
);
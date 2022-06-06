create table "market"
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    constraint pk_market primary key (id)
);

create table "market_books"
(
    market_id BIGINT NOT NULL,
    books_id  BIGINT NOT NULL,
    constraint FK_MARKETBOOKITEM_ON_MARKET foreign key (market_id) references market(id),
    constraint FK_MARKETBOOKITEM_ON_BOOKITEM foreign key (books_id) references book_item(id)
)
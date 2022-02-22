DROP TABLE IF EXISTS public.customer CASCADE;
CREATE TABLE public.customer
(
    id           serial  NOT NULL PRIMARY KEY,
    first_name varchar(100),
    last_name         varchar(100),
    email           varchar(150),
    password varchar(300),
    balance decimal,
    registration_date timestamp NOT NULL
);

ALTER TABLE IF EXISTS ONLY public.transfer DROP CONSTRAINT IF EXISTS fk_customer_id CASCADE;

DROP TABLE IF EXISTS public.transfer CASCADE;
CREATE TABLE public.transfer
(
    id          serial  NOT NULL PRIMARY KEY,
    customer_id integer,
    transfer_sender_name varChar(200),
    transfer_money decimal
);

ALTER TABLE ONLY public.transfer
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES public.customer(id);

ALTER TABLE IF EXISTS ONLY public.transaction DROP CONSTRAINT IF EXISTS fk_customer_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.transaction DROP CONSTRAINT IF EXISTS fk_transfer_id CASCADE;

DROP TABLE IF EXISTS public.transaction CASCADE;
CREATE TABLE public.transaction
(
    id          serial  NOT NULL PRIMARY KEY,
    customer_id integer,
    transfer_id integer,
    transaction_type varChar(150),
    money decimal
);

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk_transfer_id FOREIGN KEY (transfer_id) REFERENCES public.transfer(id);
ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES public.customer(id);




INSERT INTO public.customer VALUES (1, 'Oakley', 'Burns', 'burns.oakley@gmail.com', '1234', 3000, CURRENT_DATE);
INSERT INTO public.customer VALUES (2, 'Chase', 'Price', 'price.chase@gmail.com', '1234', 3000, CURRENT_DATE);
INSERT INTO public.customer VALUES (3, 'Harris', 'Reynolds','reynolds.harris@gmail.com', '1234', 3000, CURRENT_DATE);
INSERT INTO public.customer VALUES (4, 'Spencer', 'Hayes', 'hayes.spence@gmail.com',  '1234', 3000, CURRENT_DATE);
INSERT INTO public.customer VALUES (5, 'Teddy', 'Hill', 'hill.tedd@gmail.com', '1234', 3000, CURRENT_DATE);
SELECT pg_catalog.setval('customer_id_seq', 5, true);
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account VALUES (2, 'lst0n3her0@gmail.com', 'Рифдар Нигматьянов', '', NULL, 'ADMIN', 'ACTIVE');
INSERT INTO public.account VALUES (3, 'assasinnyx1111@gmail.com', 'assasinnyx1111@gmail.com', '$2a$10$qYc2pxEVfTbGXyLBYswjmOacdRX.HE1ka9DvAlJDboML.ywWYokG2', NULL, 'ADMIN', 'ACTIVE');
INSERT INTO public.account VALUES (4, 'korneick34@gmail.com', 'Корней Корнеич', '', NULL, 'USER', 'ACTIVE');
INSERT INTO public.account VALUES (1, 'assasinnyx99@gmail.com', 'handsome', '', NULL, 'USER', 'ACTIVE');
INSERT INTO public.account VALUES (6, 'assasinnyx999999@gmail.com', 'assasinnyx999999@gmail.com', '$2a$10$cNhnERknB3zOsyi5BExznehIPOWDgcPUdvKB9KS/BCMmrFldnciTC', NULL, 'USER', 'ACTIVE');
INSERT INTO public.account VALUES (7, 'assasinnyx888@gmail.com', 'assasinnyx888@gmail.com', '$2a$10$CSWqQUz7WKMgZ8Qbkqg3GOdg5YcuZ1rr3GKPRRmz8.71f8nBtIRP2', NULL, 'USER', 'ACTIVE');
INSERT INTO public.account VALUES (5, 'lrifdar@gmail.com', 'Рифдар Нигматьянов', '', NULL, 'USER', 'ACTIVE');


--
-- Data for Name: basic_open_id_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.basic_open_id_user VALUES ('113550568275318881610', 'assasinnyx99@gmail.com', 'handsome', 1);
INSERT INTO public.basic_open_id_user VALUES ('107037067312502730004', 'lst0n3her0@gmail.com', 'Рифдар Нигматьянов', 2);
INSERT INTO public.basic_open_id_user VALUES ('115014790757773601996', 'korneick34@gmail.com', 'Корней Корнеич', 4);
INSERT INTO public.basic_open_id_user VALUES ('103335550731915694749', 'lrifdar@gmail.com', 'Рифдар Нигматьянов', 5);


--
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chats VALUES (1, 1, 'ACTIVE');
INSERT INTO public.chats VALUES (3, 3, 'ACTIVE');
INSERT INTO public.chats VALUES (2, 2, 'ACTIVE');
INSERT INTO public.chats VALUES (2, 1, 'ACTIVE');
INSERT INTO public.chats VALUES (1, 2, 'ACTIVE');
INSERT INTO public.chats VALUES (1, 3, 'ACTIVE');
INSERT INTO public.chats VALUES (3, 1, 'ACTIVE');
INSERT INTO public.chats VALUES (4, 4, 'ACTIVE');
INSERT INTO public.chats VALUES (1, 4, 'ACTIVE');
INSERT INTO public.chats VALUES (4, 1, 'ACTIVE');
INSERT INTO public.chats VALUES (5, 5, 'ACTIVE');
INSERT INTO public.chats VALUES (6, 6, 'ACTIVE');
INSERT INTO public.chats VALUES (7, 7, 'ACTIVE');
INSERT INTO public.chats VALUES (6, 7, 'ACTIVE');
INSERT INTO public.chats VALUES (7, 6, 'ACTIVE');


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.message VALUES (53, 2, 1, 'ипавиваиавиавиав', NULL, '2021-05-20 22:58:55.186');
INSERT INTO public.message VALUES (54, 1, 4, 'Привет!Я по тебе скучаю', NULL, '2021-05-25 20:39:47.409');
INSERT INTO public.message VALUES (55, 4, 1, 'Аз!', NULL, '2021-05-25 20:40:05.851');
INSERT INTO public.message VALUES (56, 1, 4, 'Привет!Початимся?', NULL, '2021-05-25 20:50:42.095');
INSERT INTO public.message VALUES (57, NULL, NULL, 'Congratulations!You are one of the first beta testers!I hope there are no bugs in this application, it''s not for nothing that we uploaded it to production', NULL, '2021-05-25 21:09:39.976');
INSERT INTO public.message VALUES (58, 6, 6, 'Congratulations!You are one of the first beta testers!I hope there are no bugs in this application, it''s not for nothing that we uploaded it to production', NULL, '2021-05-25 21:12:18.359');
INSERT INTO public.message VALUES (59, 7, 7, 'Congratulations!You are one of the first beta testers!I hope there are no bugs in this application, it''s not for nothing that we uploaded it to production', NULL, '2021-05-25 21:13:12.4');
INSERT INTO public.message VALUES (60, 7, 6, 'Привет!', NULL, '2021-05-25 21:13:47.184');
INSERT INTO public.message VALUES (61, 6, 7, 'ПКпк!', NULL, '2021-05-25 21:14:00.23');
INSERT INTO public.message VALUES (50, 2, 1, 'Привет! Я отправлю тебе это сообщени!', NULL, '2021-05-20 22:56:45.002');
INSERT INTO public.message VALUES (51, 1, 2, 'https://www.youtube.com/watch?v=E_y-VGW9FPQ&t=2687s', NULL, '2021-05-20 22:57:17.146');
INSERT INTO public.message VALUES (52, 1, 2, 'кпкпкуп', NULL, '2021-05-20 22:58:28.584');


--
-- Data for Name: technical_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.technical_info VALUES (1, 'CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (2, 'CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (3, 'NONE_CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (4, 'CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (5, 'CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (6, 'NONE_CONFIRMED', false, NULL);
INSERT INTO public.technical_info VALUES (7, 'NONE_CONFIRMED', false, NULL);


--
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_id_seq', 7, true);


--
-- Name: message_id_pk_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.message_id_pk_seq', 61, true);


--
-- Name: technical_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.technical_info_id_seq', 7, true);


--
-- PostgreSQL database dump complete
--


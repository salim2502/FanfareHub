--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: appartenir; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.appartenir (
    nomfanfaron character varying(50) NOT NULL,
    nom character varying(50) NOT NULL
);


ALTER TABLE public.appartenir OWNER TO groupe7;

--
-- Name: evenement; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.evenement (
    id integer NOT NULL,
    nom character varying(100),
    horodatage timestamp without time zone,
    duree integer,
    lieu character varying(100),
    description text,
    nomfanfaron character varying(50)
);


ALTER TABLE public.evenement OWNER TO groupe7;

--
-- Name: evenement_id_seq; Type: SEQUENCE; Schema: public; Owner: groupe7
--

CREATE SEQUENCE public.evenement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.evenement_id_seq OWNER TO groupe7;

--
-- Name: evenement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: groupe7
--

ALTER SEQUENCE public.evenement_id_seq OWNED BY public.evenement.id;


--
-- Name: fanfarons; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.fanfarons (
    nomfanfaron character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    motdepasse character varying(100) NOT NULL,
    nom character varying(50),
    prenom character varying(50),
    genre character varying(10),
    contraintealimentaire text,
    createdat timestamp without time zone,
    lastconnection timestamp without time zone,
    isadmin boolean
);


ALTER TABLE public.fanfarons OWNER TO groupe7;

--
-- Name: groupecommission; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.groupecommission (
    commission character varying(50) NOT NULL
);


ALTER TABLE public.groupecommission OWNER TO groupe7;

--
-- Name: impliquer; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.impliquer (
    nomfanfaron character varying(50) NOT NULL,
    commission character varying(50) NOT NULL
);


ALTER TABLE public.impliquer OWNER TO groupe7;

--
-- Name: pupitre; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.pupitre (
    nom character varying(50) NOT NULL
);


ALTER TABLE public.pupitre OWNER TO groupe7;

--
-- Name: sinscrire; Type: TABLE; Schema: public; Owner: groupe7
--

CREATE TABLE public.sinscrire (
    nomfanfaron character varying(50) NOT NULL,
    id integer NOT NULL,
    nompupitre character varying(50),
    statut character varying(20)
);


ALTER TABLE public.sinscrire OWNER TO groupe7;

--
-- Name: evenement id; Type: DEFAULT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.evenement ALTER COLUMN id SET DEFAULT nextval('public.evenement_id_seq'::regclass);


--
-- Data for Name: appartenir; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.appartenir (nomfanfaron, nom) FROM stdin;
soly	Trompette
\.


--
-- Data for Name: evenement; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.evenement (id, nom, horodatage, duree, lieu, description, nomfanfaron) FROM stdin;
4	Soiree jazz	2025-06-20 20:00:00	180	Salle des fêtes - Lyon	Une soirée de musique jazz avec buffet.	soly
5	Atelier cuisine végétarienne	2025-06-25 14:30:00	120	Maison des associations - Grenoble	Préparation de recettes végétariennes simples et savoureuses.	soly
6	Randonnée en Chartreuse	2025-07-02 08:00:00	300	Parking du Col de Porte	Une belle randonnée avec pique-nique en haut.	soly
7	Soirée jeux de société	2025-06-30 19:00:00	240	Café l Echapee - Chambery	Venez jouer à vos jeux préférés autour d’un verre !	soly
\.


--
-- Data for Name: fanfarons; Type: TABLE DATA; Schema: public; Owner: groupe7
--
--compte admin mdpasse : admin
--compte claraB mdp : musique44
COPY public.fanfarons (nomfanfaron, email, motdepasse, nom, prenom, genre, contraintealimentaire, createdat, lastconnection, isadmin) FROM stdin;
mbernard	m.bernard@example.com	fc9f365ef8cfe72b9962548faec1f1ac12a4242b1f538c403f64735a2de58ebe	Bernard	Marie	F	végétarienne	2025-06-01 14:19:31.850069	\N	f
claraB	clara.b@example.com	461ad2aa1840b32a101f2536b43bcb5569b1d0f51a66cde26bc4b30a21efe5c1	Besson	Clara	F	sans gluten	2025-06-01 14:38:46.40809	\N	f
frankL	frank.l@example.com	551f2b93f7aca15200a540372c61d82e15f16776f5f21d016d6797f48078907e	Lemoine	Frank	H	aucune	2025-06-01 14:19:31.850069	\N	t
admin	admin@example.com	8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918	Admin	User	H	aucune	2025-06-01 14:19:31.850069	2025-06-01 14:44:18.268049	t
soly	salim.chab@gmail.fr	6693efcd0503059fc0561450033ac9fe712aff09e31e460f4f321f4324585188	chab	chab	H	Viande++	2025-06-01 00:00:00	2025-06-01 14:45:32.474902	f
\.


--
-- Data for Name: groupecommission; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.groupecommission (commission) FROM stdin;
Logistique
Communication
Animation
Prestation
\.


--
-- Data for Name: impliquer; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.impliquer (nomfanfaron, commission) FROM stdin;
soly	Logistique
soly	Communication
soly	Prestation
\.


--
-- Data for Name: pupitre; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.pupitre (nom) FROM stdin;
Trompette
Tuba
Clarinette
Saxophone
\.


--
-- Data for Name: sinscrire; Type: TABLE DATA; Schema: public; Owner: groupe7
--

COPY public.sinscrire (nomfanfaron, id, nompupitre, statut) FROM stdin;
\.


--
-- Name: evenement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: groupe7
--

SELECT pg_catalog.setval('public.evenement_id_seq', 7, true);


--
-- Name: appartenir appartenir_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.appartenir
    ADD CONSTRAINT appartenir_pkey PRIMARY KEY (nomfanfaron, nom);


--
-- Name: evenement evenement_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.evenement
    ADD CONSTRAINT evenement_pkey PRIMARY KEY (id);


--
-- Name: fanfarons fanfarons_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.fanfarons
    ADD CONSTRAINT fanfarons_pkey PRIMARY KEY (nomfanfaron);


--
-- Name: groupecommission groupecommission_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.groupecommission
    ADD CONSTRAINT groupecommission_pkey PRIMARY KEY (commission);


--
-- Name: impliquer impliquer_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.impliquer
    ADD CONSTRAINT impliquer_pkey PRIMARY KEY (nomfanfaron, commission);


--
-- Name: pupitre pupitre_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.pupitre
    ADD CONSTRAINT pupitre_pkey PRIMARY KEY (nom);


--
-- Name: sinscrire sinscrire_pkey; Type: CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.sinscrire
    ADD CONSTRAINT sinscrire_pkey PRIMARY KEY (nomfanfaron, id);


--
-- Name: appartenir appartenir_nom_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.appartenir
    ADD CONSTRAINT appartenir_nom_fkey FOREIGN KEY (nom) REFERENCES public.pupitre(nom);


--
-- Name: appartenir appartenir_nomfanaron_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.appartenir
    ADD CONSTRAINT appartenir_nomfanaron_fkey FOREIGN KEY (nomfanfaron) REFERENCES public.fanfarons(nomfanfaron) ON DELETE CASCADE;


--
-- Name: evenement evenement_nomfanaron_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.evenement
    ADD CONSTRAINT evenement_nomfanaron_fkey FOREIGN KEY (nomfanfaron) REFERENCES public.fanfarons(nomfanfaron) ON DELETE CASCADE;


--
-- Name: impliquer impliquer_commission_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.impliquer
    ADD CONSTRAINT impliquer_commission_fkey FOREIGN KEY (commission) REFERENCES public.groupecommission(commission);


--
-- Name: impliquer impliquer_nomfanaron_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.impliquer
    ADD CONSTRAINT impliquer_nomfanaron_fkey FOREIGN KEY (nomfanfaron) REFERENCES public.fanfarons(nomfanfaron);


--
-- Name: sinscrire sinscrire_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.sinscrire
    ADD CONSTRAINT sinscrire_id_fkey FOREIGN KEY (id) REFERENCES public.evenement(id) ON DELETE CASCADE;


--
-- Name: sinscrire sinscrire_nomfanaron_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.sinscrire
    ADD CONSTRAINT sinscrire_nomfanaron_fkey FOREIGN KEY (nomfanfaron) REFERENCES public.fanfarons(nomfanfaron) ON DELETE CASCADE;


--
-- Name: sinscrire sinscrire_nompupitre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: groupe7
--

ALTER TABLE ONLY public.sinscrire
    ADD CONSTRAINT sinscrire_nompupitre_fkey FOREIGN KEY (nompupitre) REFERENCES public.pupitre(nom);


--
-- PostgreSQL database dump complete
--


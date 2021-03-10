--Create Owner realted entities
CREATE SEQUENCE owners_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE owners
(
    id integer NOT NULL DEFAULT nextval('owners_id_seq'),
    created_at character varying(255) ,
    email_id character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number character varying(255),
    address character varying(255),
    CONSTRAINT owners_pkey PRIMARY KEY (id)
);

--Create pet related entities
CREATE SEQUENCE pet_types_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE SEQUENCE pets_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE pet_types
(
    id integer PRIMARY KEY NOT NULL DEFAULT nextval('pet_types_id_seq'),
    name character varying(80),
    CONSTRAINT pk_types PRIMARY KEY (id)
)

CREATE TABLE public.pets
(
    id integer NOT NULL DEFAULT nextval('pets_id_seq'),
    created_at character varying(255),
    name character varying(255),
    birth_date date,
    owner_id integer,
    type integer,
    CONSTRAINT pets_pkey PRIMARY KEY (id),
);

ALTER TABLE pets
    ADD CONSTRAINT fk6teg4kcjcnjhduguft56wcfoa FOREIGN KEY (owner_id)
        REFERENCES owners (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE pets
    ADD CONSTRAINT fkhl3k99gclesr07pgel8l1luyt FOREIGN KEY (id)
        REFERENCES pets (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

--Create Specialities entities
CREATE SEQUENCE specialties_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE specialties
(
    id integer NOT NULL DEFAULT nextval('specialties_id_seq'),
    created_at character varying(255) ,
    name character varying(255) ,
    CONSTRAINT specialties_pkey PRIMARY KEY (id)
);


--create vet entities
CREATE SEQUENCE vets_id_seq
    INCREMENT 1
    START 100
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE vets
(
    id integer NOT NULL DEFAULT nextval('vets_id_seq'),
    created_at character varying(255),
    email_id character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number character varying(255),
    CONSTRAINT vets_pkey PRIMARY KEY (id)
);

CREATE TABLE vet_specialties
(
    vet_id integer NOT NULL PRIMARY KEY,
    specialty_id integer NOT NULL PRIMARY KEY,
    created_at character varying(255),
    name character varying(255),
);

ALTER TABLE vet_specialties
    ADD CONSTRAINT fk35uiboyrpfn1bndrr5jorcj0m FOREIGN KEY (specialty_id)
        REFERENCES specialties
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE vet_specialties
    ADD CONSTRAINT fkby1c0fbaa0byaifi63vt18sx9 FOREIGN KEY (vet_id)
        REFERENCES vets (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


--create appointment entities
CREATE SEQUENCE appointments_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE appointments
(
    id integer NOT NULL DEFAULT nextval('appointments_id_seq') PRIMARY KEY,
    created_at character varying(255),
    canceled boolean DEFAULT false,
    description character varying(255),
    owner_id integer,
    pet_id integer,
    timeslot character varying(255),
    vet_id integer,
);

ALTER TABLE appointments
    ADD CONSTRAINT fk62dl3dvwsbveq3vv067becwmj FOREIGN KEY (pet_id)
        REFERENCES pets
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE appointments
    ADD CONSTRAINT fk99ftkqigrro0k1ccm06bb0emd FOREIGN KEY (id)
        REFERENCES appointments (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE appointments
    ADD CONSTRAINT fkij3s37eghwey25hfo6d7xwky6 FOREIGN KEY (vet_id)
        REFERENCES vets (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

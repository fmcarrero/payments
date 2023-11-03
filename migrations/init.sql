-- public.recipients definition

-- Drop table

-- DROP TABLE public.recipients;

CREATE TABLE public.recipients (
                                   id numeric NOT NULL,
                                   first_name varchar(255) NOT NULL,
                                   last_name varchar(255) NOT NULL,
                                   routing_number varchar(255) NOT NULL,
                                   national_identification_number varchar(255) NOT NULL,
                                   account_number varchar(255) NOT NULL,
                                   bank_name varchar(255) NOT NULL,
                                   user_id numeric NOT NULL,
                                   created_at timestamp NOT NULL,
                                   currency varchar(10) NOT NULL DEFAULT 'USD'::character varying,
                                   CONSTRAINT recipients_pk PRIMARY KEY (id)
);
-- // CLOUD-84181 Event log tables
-- Migration SQL that makes the change goes here.

CREATE SEQUENCE eventlog_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE eventlog
(
   id                     bigint NOT NULL DEFAULT nextval('eventlog_id_seq'),
   eventLogjson           TEXT
);

ALTER TABLE ONLY eventlog
ADD CONSTRAINT eventlog_pkey PRIMARY KEY (id);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE IF EXISTS eventlog;
DROP SEQUENCE IF EXISTS eventlog_id_seq;

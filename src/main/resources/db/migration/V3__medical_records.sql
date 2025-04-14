create table encounter
(
	id varchar(36) not null
		constraint pk_encounter
			primary key,

	organization_id        varchar(36),

	patient_id        varchar(36),
	practitioner_id   varchar(36),
    encounter_name    varchar(255),
    encounter_date    date,

    version bigint default 0
);


create table medical_record
(
	id varchar(36) not null
		constraint pk_medical_record
			primary key,

    encounter_id varchar(36),

    type varchar(255) not null,
	display varchar(255),
	code varchar(255),
	specialization varchar(36),

    version bigint default 0
);

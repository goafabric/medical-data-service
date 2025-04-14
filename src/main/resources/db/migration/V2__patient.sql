create table patient
(
	id varchar(36) not null
		constraint pk_patient
			primary key,
    organization_id varchar(36),

	given_name varchar(255),
	family_name varchar(255),

	gender varchar(255),
	birth_date date,

    version bigint default 0
);

create index idx_patient_organization_id on patient(organization_id);

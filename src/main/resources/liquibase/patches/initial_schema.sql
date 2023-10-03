create table patient(
    id bigserial not null primary key,
    date_of_birth date  not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    middle_name   varchar(255),
    uuid  uuid  not null constraint uk_patient_uuid unique
);
create index idx_patient_uuid on patient (uuid);

create table physician(
    id bigserial not null primary key,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    middle_name varchar(255),
    uuid uuid not null constraint uk_physician_uuid unique
);
create index idx_physician_uuid on physician (uuid);

create table appointment_slot(
    id bigserial not null primary key,
    end_date     timestamp(6),
    start_date   timestamp(6),
    uuid uuid not null constraint uk_appointment_uuid unique,
    patient_id bigint constraint fk_appointment_patient references patient(id),
    physician_id bigint constraint fk_appointment_physician references physician(id)
);
create index idx_appointment_uuid on appointment_slot (uuid);

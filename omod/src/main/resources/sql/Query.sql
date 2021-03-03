UPDATE openmrs.concept SET datatype_id = 2 WHERE concept_id IN (138398,159364,1633,161628,161649);

DELETE FROM openmrs.liquibasechangelog where id like '%nutrition%';
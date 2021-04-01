/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.reporting.library.nutritionNCDsCacx;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyaemr.calculation.library.ovc.OnOVCProgramCalculation;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KPTypeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.DurationToNextAppointmentDataDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dev on 1/10/18.
 */

/**
 * Library of cohort definitions used specifically in Datim Reports
 */
@Component
public class NCDsCohortLibrary {
    /**
     * Patients screened for NCDs
     * @return
     */
    public CohortDefinition ncdTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph;";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with alzheimers
     * @return
     */
    public CohortDefinition alzheimersTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Alzheimers Disease and other Dementias';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Arthritis
     * @return
     */
    public CohortDefinition arthritisTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Arthritis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Asthma
     * @return
     */
    public CohortDefinition asthmaTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Asthma';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Cancer
     * @return
     */
    public CohortDefinition cancerTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Cancer';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Cardiovascular diseases
     * @return
     */
    public CohortDefinition cardiovascularTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Cardiovascular diseases';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Chronic Hepatitis
     * @return
     */
    public CohortDefinition hepatitisTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Chronic Hepatitis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Chronic Kidney Disease
     * @return
     */
    public CohortDefinition kidneyTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Chronic Kidney Disease';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Chronic Obstructive Pulmonary Disease(COPD)
     * @return
     */
    public CohortDefinition copdTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Chronic Obstructive Pulmonary Disease(COPD)';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Chronic Renal Failure
     * @return
     */
    public CohortDefinition crfTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Chronic Renal Failure';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Cystic Fibrosis
     * @return
     */
    public CohortDefinition cfTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Cystic Fibrosis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Deafness and Hearing impairment
     * @return
     */
    public CohortDefinition deafTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Deafness and Hearing impairment';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Diabetes
     * @return
     */
    public CohortDefinition diabetesTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Diabetes';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Endometriosis
     * @return
     */
    public CohortDefinition endometriosisTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Endometriosis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Epilepsy
     * @return
     */
    public CohortDefinition epilepsyTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Epilepsy';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Glaucoma
     * @return
     */
    public CohortDefinition glaucomaTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Glaucoma';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Heart Disease
     * @return
     */
    public CohortDefinition heartTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Heart Disease';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Hyperlipidaemia
     * @return
     */
    public CohortDefinition hyperlipidaemiaTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Hyperlipidaemia';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Hypertension
     * @return
     */
    public CohortDefinition hypertensionTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Hypertension';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Hypothyroidism
     * @return
     */
    public CohortDefinition hypothyroidismTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Hypothyroidism';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Mental illness
     * @return
     */
    public CohortDefinition mentalIllnessTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Mental illness';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Multiple Sclerosis
     * @return
     */
    public CohortDefinition multipleSclerosisTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Multiple Sclerosis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Obesity
     * @return
     */
    public CohortDefinition obesityTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Obesity';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Osteoporosis
     * @return
     */
    public CohortDefinition osteoporosisTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Osteoporosis';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Sickle Cell Anaemia
     * @return
     */
    public CohortDefinition sickleCellAnaemiaTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Sickle Cell Anaemia';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
    
    /**
     * Patients with Thyroid disease
     * @return
     */
    public CohortDefinition thyroiddiseaseTotal() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT ph.patient_id FROM (SELECT o.person_id patient_id, v.visit_id, CAST(v.`date_started` AS DATE) visit_date, IF(p.`gender`='F', 'Female', IF(p.`gender`='M', 'Male', NULL)) gender, TIMESTAMPDIFF(YEAR,p.`birthdate`,NOW()) age,\n" +
                "MAX(IF(o.concept_id=162747,IF(o.value_coded=1065, 'Yes', IF(o.value_coded=1066, 'No', NULL)),NULL)) AS has_chronic_illness ,\n" +
                "MAX(IF(o.concept_id=1284,IF(o.value_coded=117399, 'Hypertension', IF(o.value_coded=149019, 'Alzheimers Disease and other Dementias', IF(o.value_coded=148432, 'Arthritis', IF(o.value_coded=153754, 'Asthma', IF(o.value_coded=159351, 'Cancer', IF(o.value_coded=119270, 'Cardiovascular diseases', IF(o.value_coded=120637, 'Chronic Hepatitis', IF(o.value_coded=145438, 'Chronic Kidney Disease', IF(o.value_coded=1295, 'Chronic Obstructive Pulmonary Disease(COPD)', IF(o.value_coded=120576, 'Chronic Renal Failure', IF(o.value_coded=119692, 'Cystic Fibrosis', IF(o.value_coded=120291, 'Deafness and Hearing impairment', IF(o.value_coded=119481, 'Diabetes', IF(o.value_coded=118631, 'Endometriosis', IF(o.value_coded=117855, 'Epilepsy', IF(o.value_coded=117789, 'Glaucoma', IF(o.value_coded=139071, 'Heart Disease', IF(o.value_coded=115728, 'Hyperlipidaemia', IF(o.value_coded=117321, 'Hypothyroidism', IF(o.value_coded=151342, 'Mental illness', IF(o.value_coded=133687, 'Multiple Sclerosis', IF(o.value_coded=115115, 'Obesity', IF(o.value_coded=114662, 'Osteoporosis', IF(o.value_coded=117703, 'Sickle Cell Anaemia', IF(o.value_coded=118976, 'Thyroid disease', NULL))))))))))))))))))))))))),NULL)) AS ncds\n" +
                "FROM openmrs.obs o\n" +
                "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n" +
                "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n" +
                "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n" +
                "WHERE o.concept_id IN (1284,162747)\n" +
                "AND v.date_started BETWEEN date(:startDate) and date(:endDate)\n" +
                "GROUP BY e.`encounter_id`\n" +
                "ORDER BY o.`person_id`) ph WHERE ph.ncds='Thyroid disease';";

        cd.setName("NCD");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("NCDs Screening");
        return cd;
    }
}



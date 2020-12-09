/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.keaddonexample.reporting.library.patientsVisits;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.springframework.stereotype.Component;

/**
 * Library of cohort definitions for differentiated care
 */
@Component
public class PatientVisitsCohortLibrary {
    public CohortDefinition patientsEnrolledInHiv(){
        SqlCohortDefinition cd = new SqlCohortDefinition();
        // etl_patient_hiv_followup
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_patient_hiv_followup;";
        cd.setName("patientsEnrolledInHiv");
        cd.setQuery(sqlQuery);
        cd.setDescription("Returns a list of patients who had clinical visits");

        return cd;
    }

    public CohortDefinition patientsNotEnrolledInHiv(){
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id from(\n" +
                "                      select c.patient_id,f.clinicalVisits clinicalVisits,f.person_present patient_present,c.latest_vis_date latest_visit_date,f.visit_date fup_visit_date,c.latest_tca ltca\n" +
                "                                    from kenyaemr_etl.etl_current_in_care c\n" +
                "                             inner join kenyaemr_etl.etl_patient_hiv_followup f on f.patient_id = c.patient_id and c.latest_vis_date =f.visit_date\n" +
                "                      where c.started_on_drugs is not null  and f.voided = 0 group by c.patient_id) cic where cic.clinicalVisits=1\n" +
                "                                                                                                          and cic.patient_present = 978\n" +
                "                                                                                                          and timestampdiff(month,cic.latest_visit_date,cic.ltca) <4;";
        cd.setName("patientsNotEnrolledInHIV");
        cd.setQuery(sqlQuery);
        cd.setDescription("Returns a list of patients not enrolled in HIV");

        return cd;
    }
}

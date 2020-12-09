/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.reporting.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.nutrition.reporting.cohort.definition.PatientPhdpCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for ART Patients
 */
@Handler(supports = {PatientPhdpCohortDefinition.class})
public class PatientPHDPCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    EvaluationService evaluationService;

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

        PatientPhdpCohortDefinition definition = (PatientPhdpCohortDefinition) cohortDefinition;

        if (definition == null) {
            return null;
        }

        Cohort newCohort = new Cohort();

        context = ObjectUtil.nvl(context, new EvaluationContext());

        String qry = "SELECT e.`encounter_id` \n"
                + "FROM openmrs.obs o\n"
                + "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n"
                + "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n"
                + "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n"
                + "WHERE o.concept_id IN (159777,112603,159423,161557,164934,161558,163300)\n"
                + "AND value_coded NOT IN (1090, 1091, 978)\n"
                + "AND date_started BETWEEN date(:startDate) AND date(:endDate)\n"
                + "GROUP BY e.`encounter_id`\n"
                + "ORDER BY e.`encounter_id`;";

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.append(qry);
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        builder.addParameter("endDate", endDate);
        builder.addParameter("startDate", startDate);

        List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
        newCohort.setMemberIds(new HashSet<Integer>(ptIds));

        return new EvaluatedCohort(newCohort, definition, context);
    }

}

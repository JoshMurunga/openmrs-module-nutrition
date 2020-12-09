/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.keaddonexample.reporting.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.keaddonexample.reporting.cohort.definition.HivPatientsVisitsCohortDefinition;
import org.openmrs.module.keaddonexample.reporting.cohort.definition.PatientsOnArtRegisterCohortDefinition;
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
 * Evaluator for Hiv Patients Visits
 */
@Handler(supports = {HivPatientsVisitsCohortDefinition.class})
public class HivPatientsVisistsCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EvaluationService evaluationService;

	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		HivPatientsVisitsCohortDefinition definition = (HivPatientsVisitsCohortDefinition) cohortDefinition;

		if (definition == null)
			return null;

		Cohort newCohort = new Cohort();

		context = ObjectUtil.nvl(context, new EvaluationContext());
		//EncounterQueryResult queryResult = new EncounterQueryResult(definition, context);

		String qry = "select fup.patient_id\n" +
				"from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"  join kenyaemr_etl.etl_patient_demographics dm on dm.patient_id=fup.patient_id\n" +
				"  join kenyaemr_etl.etl_hiv_enrollment en on en.patient_id=fup.patient_id\n" +
				"where fup.visit_date  between date(:startDate) and date(:endDate);\n;";


		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date)context.getParameterValue("startDate");
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("endDate", endDate);
		builder.addParameter("startDate", startDate);

		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));

		return new EvaluatedCohort(newCohort, definition, context);
	}

}

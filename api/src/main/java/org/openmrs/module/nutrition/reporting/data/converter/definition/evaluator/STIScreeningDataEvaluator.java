/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.reporting.data.converter.definition.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.nutrition.reporting.data.converter.definition.STIScreeningDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Evaluates a STIScreeningDataDefinition
 */
@Handler(supports = STIScreeningDataDefinition.class, order = 50)
public class STIScreeningDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT e.`encounter_id`, \n"
                + "MAX(IF(o.concept_id=161558,IF(o.value_coded=703, 'Positive', IF(o.value_coded=664, 'Negative', IF(o.value_coded=1118, 'Not Done', IF(o.value_coded=1175, 'N/A', NULL)))),NULL)) AS sti_screening \n"
                + "FROM openmrs.obs o\n"
                + "INNER JOIN openmrs.`encounter` e ON o.`encounter_id`=e.`encounter_id`\n"
                + "INNER JOIN openmrs.`visit` v ON e.`visit_id`=v.`visit_id`\n"
                + "INNER JOIN openmrs.`person` p ON p.`person_id`=o.`person_id`\n"
                + "WHERE o.concept_id IN (159777,112603,159423,161557,164934,161558,163300)\n"
                + "AND value_coded NOT IN (1090, 1091, 978)\n"
                + "GROUP BY e.`encounter_id`\n"
                + "ORDER BY e.`encounter_id`;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}

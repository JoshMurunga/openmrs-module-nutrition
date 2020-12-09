/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.keaddonexample.calculation;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.BaseCalculation;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Adult social work form eligibility calculation
 */

/**
 * Eligibility criteria include:
 * age >= 18 years old
 *
 */
public class EligibleForAdultSocialWorkScreeningCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {

	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		CalculationResultMap ret = new CalculationResultMap();

		// Everybody eligible!
		for (int ptId : cohort) {

			boolean ageQualifier = false;
			// get patient's age
			Integer age = Context.getPatientService().getPatient(ptId).getAge(new Date());
			if ( age >= 18) {
				ageQualifier = true;
			    }

			ret.put(ptId, new BooleanResult(ageQualifier, this));
		}

		return ret;
	}
	@Override
	public String getFlagMessage() {
		return "Eligible for social work";
	}
}
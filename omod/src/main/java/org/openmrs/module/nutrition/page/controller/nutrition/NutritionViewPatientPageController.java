/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.page.controller.nutrition;

import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nutrition.NutritionConstants;
import org.openmrs.module.nutrition.EmrWebConstants;
import org.openmrs.module.nutrition.metadata.NutritionMetadata;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.page.PageModel;

import java.util.Arrays;
import java.util.List;

/**
 * View patient page for clinician app
 */
@AppPage(NutritionConstants.APP_NUTRITION)
public class NutritionViewPatientPageController {

	public void controller(PageModel model) {
		Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);
		Form nutritionForm = MetadataUtils.existing(Form.class, NutritionMetadata._Form.NUTRITION);

		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Arrays.asList(nutritionForm), null, null, null, null, false);

		model.put("hasEncounters", encounters.size() > 0 ? true : false);
	}
}
/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.fragment.controller.nutrition;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nutrition.metadata.NutritionMetadata;
import org.openmrs.module.nutrition.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * serves counseling fragment
 */
public class NutritionHistoryFragmentController {

    ConceptService conceptService = Context.getConceptService();
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    public void controller(FragmentModel model, @FragmentParam("patient") Patient patient) {

        PatientWrapper patientWrapper = new PatientWrapper(patient);
        Form nutritionForm = MetadataUtils.existing(Form.class, NutritionMetadata._Form.NUTRITION);
        List<Encounter> nutririonEncounters = patientWrapper.allEncounters(nutritionForm);
        TreeMap<String, List<SimpleObject>> orderedEpisodes = getSessionDetails(nutririonEncounters);
        model.put("episodes", orderedEpisodes);
    }

    /**
     * Extract session information from encounters and order them based on
     * counseling episodes
     *
     * @param encounters
     * @return
     */
    TreeMap<String, List<SimpleObject>> getSessionDetails(List<Encounter> encounters) {

        TreeMap<String, List<SimpleObject>> orderedEpisodes = new TreeMap<String, List<SimpleObject>>();

        for (Encounter e : encounters) {
            String episodeStartDateString = null;

            episodeStartDateString = DATE_FORMAT.format(e.getEncounterDatetime());

            SimpleObject encData = SimpleObject.create(
                    "episodeStartDate", episodeStartDateString,
                    "encDate", e.getEncounterDatetime(),
                    "encounter", Arrays.asList(e),
                    "form", e.getForm()
            );

            if (orderedEpisodes.containsKey(episodeStartDateString)) {
                orderedEpisodes.get(episodeStartDateString).add(encData);
            } else {
                List<SimpleObject> sessionList = new ArrayList<SimpleObject>();
                sessionList.add(encData);
                orderedEpisodes.put(episodeStartDateString, sessionList);
            }
        }

        return orderedEpisodes;
    }

}

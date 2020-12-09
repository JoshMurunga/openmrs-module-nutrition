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
package org.openmrs.module.nutrition.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * Nutrition metadata bundle
 */
@Component
public class NutritionMetadata extends AbstractMetadataBundle {

    public static class _EncounterType {
		public static final String NUTRITION = "d69dedbd-3933-4e44-8292-bea939ce980a";
    }

    public static class _Form {
		public static final String NUTRITION = "b694b1bc-2086-47dd-a4ad-ba48f9471e4b";
    }

    /**
     * @see
     * org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
     */
    @Override
    public void install() {
		install(encounterType("Ntutrition", "Nutrition Encounter", _EncounterType.NUTRITION));

		install(form("Nutrition form", null, _EncounterType.NUTRITION, "1", _Form.NUTRITION));
    }
}

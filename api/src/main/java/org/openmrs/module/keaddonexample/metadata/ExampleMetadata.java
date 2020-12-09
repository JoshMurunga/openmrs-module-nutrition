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

package org.openmrs.module.keaddonexample.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * Example metadata bundle
 */
@Component
public class ExampleMetadata extends AbstractMetadataBundle {

	public static class _EncounterType {
		public static final String EXAMPLE = "d69dedbd-3933-4e44-8292-bea939ce980a";
		public static final String SOCIAL_WORK = "4fc436f4-9f3c-4f69-b60f-ad79dd114c47";
		public static final String VMMC_INITIAL = "23028d1b-4cbf-4b73-b2d5-903e9d97a470";
		public static final String VMMC_FOLLOWUP = "f357a69a-400b-4e7e-b1cd-81112159f2a8";
	}

	public static class _Form {
		public static final String EXAMPLE = "b694b1bc-2086-47dd-a4ad-ba48f9471e4b";
		public static final String ADULT_SCREENING = "0640058c-aa3f-47d0-867b-3025f2e5f80d";
		public static final String VMMC_INITIAL_FORM = "5f91bcbf-edd2-4580-9b9a-85fbfa37ac4e";
		public static final String VMMC_FOLLOWUP_FORM = "434d97d1-13c7-4e2f-bae8-ca57eb42f6a0";
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("Example encounter", "Just an example", _EncounterType.EXAMPLE));
		install(encounterType("Social Work", "Social Work Encounter", _EncounterType.SOCIAL_WORK));
		install(encounterType("VMMC Initial ", "VMMC Initial Encounter", _EncounterType.VMMC_INITIAL));
		install(encounterType("VMMC Followup  ", "VMMC Followup Encounter", _EncounterType.VMMC_FOLLOWUP));

		install(form("Example form", null, _EncounterType.EXAMPLE, "1", _Form.EXAMPLE));
		install(form("Social Work Adult Screening form", null, _EncounterType.SOCIAL_WORK, "1", _Form.ADULT_SCREENING));
		install(form("VMMC Initial Screening form", null, _EncounterType.VMMC_INITIAL, "1", _Form.VMMC_INITIAL_FORM));
		install(form("VMMC Followup Screening form", null, _EncounterType.VMMC_FOLLOWUP, "1", _Form.VMMC_FOLLOWUP_FORM));
	}
}
/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.keaddonexample.reporting.builder;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.keaddonexample.reporting.cohort.definition.HivPatientsVisitsCohortDefinition;
import org.openmrs.module.keaddonexample.reporting.cohort.definition.PatientsOnArtRegisterCohortDefinition;
import org.openmrs.module.keaddonexample.reporting.data.converter.definition.ARTRegimenDataDefinition;
import org.openmrs.module.keaddonexample.reporting.data.converter.definition.ARTStartDateDataDefinition;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.etl.common.report.hivPatientsVisits"})
public class HivPatientsVisitsReportBuilder extends AbstractHybridReportBuilder {

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return allPatientsCohort();
	}

    protected Mapped<CohortDefinition> allPatientsCohort() {
        CohortDefinition cd = new HivPatientsVisitsCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setName("HIV Patients Visits");
        return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {

        PatientDataSetDefinition hivPatientsVisits = hivPatientsVisitsDataSetDefinition();
		hivPatientsVisits.addRowFilter(allPatientsCohort());

        DataSetDefinition allPatientsDSD = hivPatientsVisits;


        return Arrays.asList(
                ReportUtils.map(allPatientsDSD, "startDate=${startDate},endDate=${endDate}")
        );
    }

	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(
				new Parameter("startDate", "Start Date", Date.class),
				new Parameter("endDate", "End Date", Date.class),
				new Parameter("dateBasedReporting", "", String.class)
		);
	}

	protected PatientDataSetDefinition hivPatientsVisitsDataSetDefinition() {
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("HivPatientsVisits");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);

		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");

		dsd.addColumn("Unique Patient No", identifierDef, "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
		dsd.addColumn("Sex", new GenderDataDefinition(), "");

		// new columns
		//dsd.addColumn("ART start date", new ARTStartDateDataDefinition(), "");
		//dsd.addColumn("ART Regimen", new ARTRegimenDataDefinition(), "");
		return dsd;
	}
}

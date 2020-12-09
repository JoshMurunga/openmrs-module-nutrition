/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.reporting.builder;

import org.openmrs.module.nutrition.reporting.data.converter.definition.CacxScreeningDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.CondomsProvidedDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.HIVPartnerDisclosureDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.NutritionalStatusDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PartnerTestingDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PatientAgeDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PatientGenderDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PatientNameDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PtnIdDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.PatientUPNDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.STIScreeningDataDefinition;
import org.openmrs.module.nutrition.reporting.data.converter.definition.SubstanceAbuseScreeningDataDefinition;
import org.openmrs.module.nutrition.reporting.cohort.definition.PatientPhdpCohortDefinition;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
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
@Builds({"kenyaemr.etl.common.report.patientPHDP"})
public class PatientPhdpReportBuilder extends AbstractHybridReportBuilder {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
        return allPatientsCohort();
    }

    protected Mapped<CohortDefinition> allPatientsCohort() {
        CohortDefinition cd = new PatientPhdpCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setName("Patients' PHDP");
        return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {

        PatientDataSetDefinition allPatients = patientPHDPDataSetDefinition();
        allPatients.addRowFilter(allPatientsCohort());
        DataSetDefinition allPatientsDSD = allPatients;

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

    protected PatientDataSetDefinition patientPHDPDataSetDefinition() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition("PatientPHDP");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("id", new PtnIdDataDefinition(), "");
        dsd.addColumn("Name", new PatientNameDataDefinition(), "");
        dsd.addColumn("Unique Patient No", new PatientUPNDataDefinition(), "");
        dsd.addColumn("Sex", new PatientGenderDataDefinition(), "");
        dsd.addColumn("Age", new PatientAgeDataDefinition(), "");
        dsd.addColumn("Nutritional Status", new NutritionalStatusDataDefinition(), "");
        dsd.addColumn("Condoms Provided", new CondomsProvidedDataDefinition(), "");
        dsd.addColumn("Substance Abuse Screening", new SubstanceAbuseScreeningDataDefinition(), "");
        dsd.addColumn("HIV Partner Disclosure", new HIVPartnerDisclosureDataDefinition(), "");
        dsd.addColumn("Partner Testing", new PartnerTestingDataDefinition(), "");
        dsd.addColumn("Cacx Screening", new CacxScreeningDataDefinition(), "");
        dsd.addColumn("STI Screening", new STIScreeningDataDefinition(), "");

        return dsd;
    }
}

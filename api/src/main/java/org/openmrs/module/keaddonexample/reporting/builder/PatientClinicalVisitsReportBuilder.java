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

import org.openmrs.module.keaddonexample.reporting.library.patientsVisits.PatientVisitsIndicatorLibrary;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Report builder for patients' clinical visits
 */
@Component
@Builds({"kenyaemr.etl.common.report.patientClinicalVisits"})
public class PatientClinicalVisitsReportBuilder extends AbstractReportBuilder {

    @Autowired
    private PatientVisitsIndicatorLibrary patientVisitsIndicatorLibrary;

    @Autowired
    private CommonDimensionLibrary commonDimensions;


    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return new ArrayList<Parameter>();
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(patientsEnrolledInHiv(), "")

        );
    }

    protected DataSetDefinition patientsEnrolledInHiv() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.diffCareAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        ColumnParameters all0_to14 = new ColumnParameters(null, "0-14", "age=<15");
        ColumnParameters f15Plus = new ColumnParameters(null, "15+, Female", "gender=F|age=15+");
        ColumnParameters m15Plus = new ColumnParameters(null, "15+, Male", "gender=M|age=15+");
        ColumnParameters colTot = new ColumnParameters(null, "Total", "");

        List<ColumnParameters> diffCareDisaggregations =
                Arrays.asList(all0_to14, f15Plus, m15Plus, colTot);

        cohortDsd.setName("1");
        cohortDsd.setDescription("Dataset for patients who are enrolled in HIV program");

        EmrReportingUtils.addRow(cohortDsd, "Patient visits", "", ReportUtils.map(patientVisitsIndicatorLibrary.patientsEnrolledInHiv()), diffCareDisaggregations, Arrays.asList("01", "02", "03", "04"));
        return cohortDsd;

    }
}

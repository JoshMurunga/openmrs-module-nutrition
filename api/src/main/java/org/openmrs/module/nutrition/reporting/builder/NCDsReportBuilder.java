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

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.DurationToNextAppointmentDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KPTypeDataDefinition;
import org.openmrs.module.nutrition.reporting.library.nutritionNCDsCacx.NCDsIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for Datim report
 */
@Component
@Builds({"kenyaemr.etl.common.report.ncds"})
public class NCDsReportBuilder extends AbstractReportBuilder {

    static final int FSW_CONCEPT = 160579;
    static final int MSM_CONCEPT = 160578;
    static final int PWID_CONCEPT = 105;

    @Autowired
    private CommonDimensionLibrary commonDimensions;

    @Autowired
    private NCDsIndicatorLibrary ncdsIndicators;


    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(careAndTreatmentDataSet(), "startDate=${startDate},endDate=${endDate}")
        );
    }


    /**
     * Creates the dataset for section #3: Care and Treatment
     *
     * @return the dataset
     */
    protected DataSetDefinition careAndTreatmentDataSet() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.setName("3");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.datimFineAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        ColumnParameters colInfants = new ColumnParameters(null, "<1", "age=<1");

        ColumnParameters children_1_to_9 = new ColumnParameters(null, "1-9", "age=1-9");

        ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

        /*DatimQ4 Column parameters*/

        ColumnParameters colInfant = new ColumnParameters(null, "<1", "age=<1");
        ColumnParameters all0_to_2m = new ColumnParameters(null, "0-2", "age=0-2");
        ColumnParameters all2_to_12m = new ColumnParameters(null, "2-12", "age=2-12");
        ColumnParameters f1_to_9 = new ColumnParameters(null, "1-9, Female", "gender=F|age=1-9");
        ColumnParameters f10_14 = new ColumnParameters(null, "10-14, Female", "gender=F|age=10-14");
        ColumnParameters m10_14 = new ColumnParameters(null, "10-14, Male", "gender=M|age=10-14");
        ColumnParameters f15_19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
        ColumnParameters m15_19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
        ColumnParameters f20_24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
        ColumnParameters m20_24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
        ColumnParameters f25_49 = new ColumnParameters(null, "25-49, Female", "gender=F|age=20-49");
        ColumnParameters m25_49 = new ColumnParameters(null, "25-49, Male", "gender=M|age=20-49");
        ColumnParameters f_Over_50 = new ColumnParameters(null, "50+, Female", "gender=F|age=50+");
        ColumnParameters m_Over_50 = new ColumnParameters(null, "50+, Male", "gender=M|age=50+");
        ColumnParameters colTot = new ColumnParameters(null, "Total", "");

        /*New age disaggregations*/
        ColumnParameters fInfant = new ColumnParameters(null, "<1, Female", "gender=F|age=<1");
        ColumnParameters mInfant = new ColumnParameters(null, "<1, Male", "gender=M|age=<1");
        ColumnParameters f1_to4 = new ColumnParameters(null, "1-4, Female", "gender=F|age=1-4");
        ColumnParameters m1_to4 = new ColumnParameters(null, "1-4, Male", "gender=M|age=1-4");
        ColumnParameters f5_to9 = new ColumnParameters(null, "5-9, Female", "gender=F|age=5-9");
        ColumnParameters m5_to9 = new ColumnParameters(null, "5-9, Male", "gender=M|age=5-9");
        ColumnParameters funder10 = new ColumnParameters(null, "<10, Female", "gender=F|age=<10");
        ColumnParameters f10_to14 = new ColumnParameters(null, "10-14, Female", "gender=F|age=10-14");
        ColumnParameters m10_to14 = new ColumnParameters(null, "10-14, Male", "gender=M|age=10-14");
        ColumnParameters f15_to19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
        ColumnParameters m15_to19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
        ColumnParameters f20_to24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
        ColumnParameters m20_to24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
        ColumnParameters f25_to29 = new ColumnParameters(null, "25-29, Female", "gender=F|age=25-29");
        ColumnParameters m25_to29 = new ColumnParameters(null, "25-29, Male", "gender=M|age=25-29");
        ColumnParameters f30_to34 = new ColumnParameters(null, "30-34, Female", "gender=F|age=30-34");
        ColumnParameters m30_to34 = new ColumnParameters(null, "30-34, Male", "gender=M|age=30-34");
        ColumnParameters f35_to39 = new ColumnParameters(null, "35-39, Female", "gender=F|age=35-39");
        ColumnParameters m35_to39 = new ColumnParameters(null, "35-39, Male", "gender=M|age=35-39");
        ColumnParameters f40_to44 = new ColumnParameters(null, "40-44, Female", "gender=F|age=40-44");
        ColumnParameters m40_to44 = new ColumnParameters(null, "40-44, Male", "gender=M|age=40-44");
        ColumnParameters f45_to49 = new ColumnParameters(null, "45-49, Female", "gender=F|age=45-49");
        ColumnParameters m45_to49 = new ColumnParameters(null, "45-49, Male", "gender=M|age=45-49");
        ColumnParameters fAbove50 = new ColumnParameters(null, "50+, Female", "gender=F|age=50+");
        ColumnParameters mAbove50 = new ColumnParameters(null, "50+, Male", "gender=M|age=50+");
        ColumnParameters all0_to4 = new ColumnParameters(null, "0-4", "age=0-4");
        ColumnParameters all5_to9 = new ColumnParameters(null, "5-9", "age=5-9");
        ColumnParameters all1_to9 = new ColumnParameters(null, "1-9", "age=1-9");
        ColumnParameters all0_to14 = new ColumnParameters(null, "0-14", "age=0-14");
        ColumnParameters all10_to14 = new ColumnParameters(null, "10-14", "age=10-14");
        ColumnParameters all15_to19 = new ColumnParameters(null, "15-19", "age=15-19");
        ColumnParameters all20_to24 = new ColumnParameters(null, "20-24", "age=20-24");
        ColumnParameters allOver25 = new ColumnParameters(null, "50+", "age=50+");
        ColumnParameters all25_to29 = new ColumnParameters(null, "25-29", "age=25-29");
        ColumnParameters all30_to34 = new ColumnParameters(null, "30-34", "age=30-34");
        ColumnParameters all35_to39 = new ColumnParameters(null, "35-39", "age=35-39");
        ColumnParameters all40_to44 = new ColumnParameters(null, "40-44", "age=40-44");
        ColumnParameters all45_to49 = new ColumnParameters(null, "45-49", "age=45-49");
        ColumnParameters allAbove50 = new ColumnParameters(null, "50+", "age=50+");

        ColumnParameters funder15 = new ColumnParameters(null, "<15, Female", "gender=F|age=<15");
        ColumnParameters munder15 = new ColumnParameters(null, "<15, Male", "gender=M|age=<15");
        ColumnParameters fabove15 = new ColumnParameters(null, "15+, Female", "gender=F|age=15+");
        ColumnParameters mabove15 = new ColumnParameters(null, "15+, Male", "gender=M|age=15+");

        List<ColumnParameters> datimNewAgeDisaggregation =
                Arrays.asList(fInfant, mInfant, f1_to4, m1_to4, f5_to9, m5_to9, f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24,
                        f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50, colTotal);


        String indParams = "startDate=${startDate},endDate=${endDate}";

        //NCDs Total
        //Number of Adults and Children screened for ncds
        EmrReportingUtils.addRow(cohortDsd, "NCD_Total", "NCDs Screening", ReportUtils.map(ncdsIndicators.ncdTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));
        
        //alzheimers Total
        //Number of Adults and Children with alzheimers
        EmrReportingUtils.addRow(cohortDsd, "ALZ", "Alzheimers cases", ReportUtils.map(ncdsIndicators.alzheimersTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //arthritis Total
        //Number of Adults and Children with arthritis
        EmrReportingUtils.addRow(cohortDsd, "ARTH", "Arthritis cases", ReportUtils.map(ncdsIndicators.arthritisTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //asthma Total
        //Number of Adults and Children with asthma
        EmrReportingUtils.addRow(cohortDsd, "ASTH", "Asthma cases", ReportUtils.map(ncdsIndicators.asthmaTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //cancer Total
        //Number of Adults and Children with cancer
        EmrReportingUtils.addRow(cohortDsd, "CAN", "Cancer cases", ReportUtils.map(ncdsIndicators.cancerTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //cardiovascular Total
        //Number of Adults and Children with cardiovascular
        EmrReportingUtils.addRow(cohortDsd, "CAD", "Cardiovascular disease cases", ReportUtils.map(ncdsIndicators.cardiovascularTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //hepatitis Total
        //Number of Adults and Children with hepatitis
        EmrReportingUtils.addRow(cohortDsd, "HEP", "Chronic hepatitis cases", ReportUtils.map(ncdsIndicators.hepatitisTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //kidney Total
        //Number of Adults and Children with Chronic Kidney Disease
        EmrReportingUtils.addRow(cohortDsd, "KID", "Chronic Kidney Disease cases", ReportUtils.map(ncdsIndicators.kidneyTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //copd Total
        //Number of Adults and Children with Chronic Obstructive Pulmonary Disease(COPD)
        EmrReportingUtils.addRow(cohortDsd, "COPD", "Chronic Obstructive Pulmonary Disease cases", ReportUtils.map(ncdsIndicators.copdTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //crf Total
        //Number of Adults and Children with Chronic Renal Failure
        EmrReportingUtils.addRow(cohortDsd, "CRF", "Chronic Renal Failure cases", ReportUtils.map(ncdsIndicators.crfTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //cf Total
        //Number of Adults and Children with Cystic Fibrosis
        EmrReportingUtils.addRow(cohortDsd, "CYS", "Cystic Fibrosis cases", ReportUtils.map(ncdsIndicators.cfTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //deaf Total
        //Number of Adults and Children with Deafness and Hearing impairment
        EmrReportingUtils.addRow(cohortDsd, "DEAF", "Deafness and Hearing impairment cases", ReportUtils.map(ncdsIndicators.deafTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //diabetes Total
        //Number of Adults and Children with diabetes
        EmrReportingUtils.addRow(cohortDsd, "DIAB", "Diabetes cases", ReportUtils.map(ncdsIndicators.diabetesTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //endometriosis Total
        //Number of Adults and Children with endometriosis
        EmrReportingUtils.addRow(cohortDsd, "END", "Endometriosis cases", ReportUtils.map(ncdsIndicators.endometriosisTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //epilepsy Total
        //Number of Adults and Children with epilepsy
        EmrReportingUtils.addRow(cohortDsd, "EPL", "Epilepsy cases", ReportUtils.map(ncdsIndicators.epilepsyTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //glaucoma Total
        //Number of Adults and Children with glaucoma
        EmrReportingUtils.addRow(cohortDsd, "GLA", "Glaucoma cases", ReportUtils.map(ncdsIndicators.glaucomaTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //heart Total
        //Number of Adults and Children with Heart Disease
        EmrReportingUtils.addRow(cohortDsd, "HRT", "Heart Disease cases", ReportUtils.map(ncdsIndicators.heartTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //hyperlipidaemia Total
        //Number of Adults and Children with hyperlipidaemia
        EmrReportingUtils.addRow(cohortDsd, "HLDA", "Hyperlipidaemia cases", ReportUtils.map(ncdsIndicators.hyperlipidaemiaTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //hypertension Total
        //Number of Adults and Children with hypertension
        EmrReportingUtils.addRow(cohortDsd, "HYTN", "Hypertension cases", ReportUtils.map(ncdsIndicators.hypertensionTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //hypothyroidism Total
        //Number of Adults and Children with hypothyroidism
        EmrReportingUtils.addRow(cohortDsd, "HTDM", "Hypothyroidism cases", ReportUtils.map(ncdsIndicators.hypothyroidismTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //mental Illness Total
        //Number of Adults and Children with hypothyroidism
        EmrReportingUtils.addRow(cohortDsd, "MENT", "Mental Illness cases", ReportUtils.map(ncdsIndicators.mentalIllnessTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //multiple Sclerosis Total
        //Number of Adults and Children with multiple Sclerosis
        EmrReportingUtils.addRow(cohortDsd, "MS", "Multiple Sclerosis cases", ReportUtils.map(ncdsIndicators.multipleSclerosisTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //obesity Total
        //Number of Adults and Children with obesity
        EmrReportingUtils.addRow(cohortDsd, "OBES", "Obesity cases", ReportUtils.map(ncdsIndicators.obesityTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //osteoporosis Total
        //Number of Adults and Children with osteoporosis
        EmrReportingUtils.addRow(cohortDsd, "OST", "Osteoporosis cases", ReportUtils.map(ncdsIndicators.osteoporosisTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //sickle Cell Anaemia Total
        //Number of Adults and Children with sickle Cell Anaemia
        EmrReportingUtils.addRow(cohortDsd, "SCA", "Sickle Cell Anaemia cases", ReportUtils.map(ncdsIndicators.sickleCellAnaemiaTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        //thyroid disease Total
        //Number of Adults and Children with thyroid disease
        EmrReportingUtils.addRow(cohortDsd, "THY", "Thyroid Disease cases", ReportUtils.map(ncdsIndicators.thyroiddiseaseTotal(), indParams), datimNewAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));

        
        return cohortDsd;
    }
}



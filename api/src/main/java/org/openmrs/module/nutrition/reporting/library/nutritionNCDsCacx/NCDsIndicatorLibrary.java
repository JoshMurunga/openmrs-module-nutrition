/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition.reporting.library.nutritionNCDsCacx;

import org.openmrs.module.kenyacore.report.ReportUtils;

import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KPTypeDataDefinition;

import org.openmrs.module.kenyaemr.reporting.data.converter.definition.DurationToNextAppointmentDataDefinition;
import org.openmrs.module.nutrition.reporting.library.nutritionNCDsCacx.NCDsCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;


/**
 * Library of DATIM related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class NCDsIndicatorLibrary {
    @Autowired
    private NCDsCohortLibrary ncdsCohorts;


    /**
     * Number of patients screened for ncds
     * @return the indicator
     */
    public CohortIndicator ncdTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.ncdTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Alzheimer's
     * @return the indicator
     */
    public CohortIndicator alzheimersTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.alzheimersTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with arthritis
     * @return the indicator
     */
    public CohortIndicator arthritisTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.arthritisTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with asthma
     * @return the indicator
     */
    public CohortIndicator asthmaTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.asthmaTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with cancer
     * @return the indicator
     */
    public CohortIndicator cancerTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.cancerTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with cardiovascular
     * @return the indicator
     */
    public CohortIndicator cardiovascularTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.cardiovascularTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with hepatitis
     * @return the indicator
     */
    public CohortIndicator hepatitisTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.hepatitisTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Chronic Kidney Disease
     * @return the indicator
     */
    public CohortIndicator kidneyTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.kidneyTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Chronic Obstructive Pulmonary Disease(COPD)
     * @return the indicator
     */
    public CohortIndicator copdTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.copdTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Chronic Renal Failure
     * @return the indicator
     */
    public CohortIndicator crfTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.crfTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Cystic Fibrosis
     * @return the indicator
     */
    public CohortIndicator cfTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.cfTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Deafness and Hearing impairment
     * @return the indicator
     */
    public CohortIndicator deafTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.deafTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with diabetes
     * @return the indicator
     */
    public CohortIndicator diabetesTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.diabetesTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with endometriosis
     * @return the indicator
     */
    public CohortIndicator endometriosisTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.endometriosisTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with epilepsy
     * @return the indicator
     */
    public CohortIndicator epilepsyTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.epilepsyTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with glaucoma
     * @return the indicator
     */
    public CohortIndicator glaucomaTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.glaucomaTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with Heart Disease
     * @return the indicator
     */
    public CohortIndicator heartTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.heartTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with hyperlipidaemia
     * @return the indicator
     */
    public CohortIndicator hyperlipidaemiaTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.hyperlipidaemiaTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with hypertension
     * @return the indicator
     */
    public CohortIndicator hypertensionTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.hypertensionTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with hypothyroidism
     * @return the indicator
     */
    public CohortIndicator hypothyroidismTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.hypothyroidismTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with mental Illness
     * @return the indicator
     */
    public CohortIndicator mentalIllnessTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.mentalIllnessTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with multiple Sclerosis
     * @return the indicator
     */
    public CohortIndicator multipleSclerosisTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.multipleSclerosisTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with obesity
     * @return the indicator
     */
    public CohortIndicator obesityTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.obesityTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with osteoporosis
     * @return the indicator
     */
    public CohortIndicator osteoporosisTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.osteoporosisTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with sickle Cell Anaemia
     * @return the indicator
     */
    public CohortIndicator sickleCellAnaemiaTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.sickleCellAnaemiaTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
    /**
     * Number of patients with thyroid disease
     * @return the indicator
     */
    public CohortIndicator thyroiddiseaseTotal() {
        return cohortIndicator("NCDs Screening", ReportUtils.map(ncdsCohorts.thyroiddiseaseTotal(), "startDate=${startDate},endDate=${endDate}"));
    }
    
}

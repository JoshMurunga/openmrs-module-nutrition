/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nutrition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;

/**
 * This class contains the logic that is run every time this module is either
 * started or stopped.
 */
public class NutritionActivator implements ModuleActivator {

    protected static final Log log = LogFactory.getLog(NutritionActivator.class);

    /**
     * @see ModuleActivator#willRefreshContext()
     */
    public void willRefreshContext() {
        log.info("Refreshing Nutrition Module");
    }

    /**
     * @see ModuleActivator#contextRefreshed()
     */
    public void contextRefreshed() {
        log.info("Nutrition Module refreshed");
    }

    /**
     * @see ModuleActivator#willStart()
     */
    public void willStart() {
        log.info("Starting Nutrition Module");
    }

    /**
     * @see ModuleActivator#started()
     */
    public void started() {
        log.info("Nutrition Module started");
    }

    /**
     * @see ModuleActivator#willStop()
     */
    public void willStop() {
        log.info("Stopping Nutrition Module");
    }

    /**
     * @see ModuleActivator#stopped()
     */
    public void stopped() {
        log.info("Nutrition Module stopped");
    }
}
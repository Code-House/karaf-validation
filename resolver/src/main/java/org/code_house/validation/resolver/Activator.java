/*
 * (C) Copyright 2016-2017 Code-House and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.code_house.validation.resolver;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.ServiceTracker;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;
import java.util.Hashtable;

/**
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class Activator implements BundleActivator {

    private BundleTracker bundleTracker;
    private ServiceTracker serviceTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        bundleTracker = new BundleTracker(context, Bundle.ACTIVE, new ValidationResolverBundleTrackerCustomizer());
        bundleTracker.open();

        ValidationProviderHolder holder = new ValidationProviderHolder(context);

        ValidationProviderServiceTrackerCustomizer serviceTrackerCustomizer = new ValidationProviderServiceTrackerCustomizer(context, holder);
        serviceTracker = new ServiceTracker(context, ValidationProvider.class, serviceTrackerCustomizer);
        serviceTracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceTracker != null) {
            serviceTracker.close();
        }

        if (bundleTracker != null) {
            bundleTracker.close();
        }
    }
}

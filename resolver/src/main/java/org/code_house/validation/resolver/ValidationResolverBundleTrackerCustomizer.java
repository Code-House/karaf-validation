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
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.spi.ValidationProvider;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Hashtable;

/**
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class ValidationResolverBundleTrackerCustomizer implements BundleTrackerCustomizer<List<ServiceRegistration>> {

    private final Logger logger = LoggerFactory.getLogger(ValidationResolverBundleTrackerCustomizer.class);

    public List<ServiceRegistration> addingBundle(Bundle bundle, BundleEvent event) {
        Enumeration<URL> entries = bundle.findEntries("META-INF/services", ValidationProvider.class.getName(), false);

        if (entries == null) {
            logger.trace("No matching entries found in bundle {}", bundle.getSymbolicName());
            return null;
        }

        List<ServiceRegistration> registrations = new ArrayList<ServiceRegistration>();
        if (entries.hasMoreElements()) {
            logger.trace("Found validation provider bundle {}", bundle.getSymbolicName());

            ClassLoader classLoader = bundle.adapt(BundleWiring.class).getClassLoader();
            ServiceLoader<ValidationProvider> providers = ServiceLoader.load(ValidationProvider.class, classLoader);

            for (ValidationProvider provider : providers) {
                registrations.add(bundle.getBundleContext().registerService(ValidationProvider.class, provider, new Hashtable<String, Object>()));
            }
        }

        return registrations;
    }

    public void modifiedBundle(Bundle bundle, BundleEvent event, List<ServiceRegistration> object) {
        removedBundle(bundle, event, object);
        addingBundle(bundle, event);
    }

    public void removedBundle(Bundle bundle, BundleEvent event, List<ServiceRegistration> object) {
        if (object != null) {
            for (ServiceRegistration registration : object) {
                registration.unregister();
            }
        }

    }
}

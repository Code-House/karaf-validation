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
package org.code_house.validation.example.core.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import javax.validation.ValidationProviderResolver;

/**
 * Bundle activator which launch tracking of {@link ValidationProviderResolver} instances and registers {@link OsgiMessageValidator}
 * in return.
 *
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class Activator implements BundleActivator {
    private ServiceTracker serviceTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        serviceTracker = new ServiceTracker(context, ValidationProviderResolver.class, new OsgiMessageValidatorRegistrar(context));
        serviceTracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceTracker != null) {
            serviceTracker.close();
        }
    }

}

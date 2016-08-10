/*
 * (C) Copyright 2016 Code-House and others.
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

import org.osgi.framework.*;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.spi.ValidationProvider;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Łukasz Dywicki <luke@code-house.org>
 */
public class ValidationProviderServiceTrackerCustomizer implements ServiceTrackerCustomizer<ValidationProvider, ValidationProvider> {

    private final BundleContext bundleContext;
    private final ValidationProviderHolder holder;

    public ValidationProviderServiceTrackerCustomizer(BundleContext bundleContext, ValidationProviderHolder holder) {
        this.bundleContext = bundleContext;
        this.holder = holder;
    }

    @Override
    public ValidationProvider addingService(ServiceReference<ValidationProvider> reference) {
        ValidationProvider service = bundleContext.getService(reference);
        holder.add(service);
        return service;
    }

    @Override
    public void modifiedService(ServiceReference<ValidationProvider> reference, ValidationProvider service) {

    }

    @Override
    public void removedService(ServiceReference<ValidationProvider> reference, ValidationProvider service) {
        holder.remove(service);
        bundleContext.ungetService(reference);
    }

}

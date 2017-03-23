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

import org.code_house.validation.example.api.MessageValidator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import java.util.Hashtable;

/**
 * Service tracker customizer to transform {@link ValidationProviderResolver} into {@link OsgiMessageValidator}.
 *
 * This class contains bootstrap logic for validation.
 *
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class OsgiMessageValidatorRegistrar implements ServiceTrackerCustomizer<ValidationProviderResolver, ServiceRegistration> {

    private final BundleContext bundleContext;

    public OsgiMessageValidatorRegistrar(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public ServiceRegistration addingService(ServiceReference<ValidationProviderResolver> reference) {
        ValidationProviderResolver service = bundleContext.getService(reference);

        Configuration<?> configure = Validation.byDefaultProvider()
            .providerResolver(service)
            .configure();

        OsgiMessageValidator validator = new OsgiMessageValidator(configure);
        return bundleContext.registerService(MessageValidator.class, validator, new Hashtable<String, Object>());
    }

    @Override
    public void modifiedService(ServiceReference<ValidationProviderResolver> reference, ServiceRegistration service) {

    }

    @Override
    public void removedService(ServiceReference<ValidationProviderResolver> reference, ServiceRegistration service) {
        service.unregister();
    }

}

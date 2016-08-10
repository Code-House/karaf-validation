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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Łukasz Dywicki <luke@code-house.org>
 */
public class ValidationProviderHolder {

    private final BundleContext context;
    private final List<ValidationProvider<?>> providers = new CopyOnWriteArrayList<>();

    private ServiceRegistration registration;

    public ValidationProviderHolder(BundleContext context) {
        this.context = context;
    }

    public void add(ValidationProvider provider) {
        providers.add(provider);
        register();
    }

    private void register() {
        if (registration == null && !providers.isEmpty()) {
            OsgiValidationProviderResolver validationResolver = new OsgiValidationProviderResolver(providers);
            registration = context.registerService(ValidationProviderResolver.class, validationResolver, new Hashtable<String, Object>());
        }

        if (registration != null && providers.isEmpty()) {
            registration.unregister();
        }
    }

    public void remove(ValidationProvider provider) {
        providers.remove(provider);
        register();
    }

}

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
package org.code_house.validation.example.consumer.internal;

import org.code_house.validation.example.api.MessageValidator;
import org.code_house.validation.example.model.Message;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        ServiceReference<MessageValidator> reference = context.getServiceReference(MessageValidator.class);

        if (reference == null) {
            System.out.println("No message validator found");
            return;
        }

        try {
            MessageValidator service = context.getService(reference);
            System.out.println("Validation result: " + service.validate(new Message(0, "", null)));
        } finally {
            context.ungetService(reference);
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }

}

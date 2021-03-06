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

/**
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class Caller {

    public Caller(MessageValidator validator) {
        System.out.println("Validation result: " + validator.validate(new Message(0, "", null)));
    }

}

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
package org.code_house.validation.example.model;

import javax.validation.constraints.*;

/**
 * Simple data structure which represents message with receiver(int), code (string) and message (string).
 *
 * @author Łukasz Dywicki &lt;luke@code-house.org&gt;
 */
public class Message {

    private final Integer receiver;
    private final String code;
    private final String message;

    public Message(Integer receiver, String code, String message) {
        this.receiver = receiver;
        this.code = code;
        this.message = message;
    }

    @Min(111)
    @Max(999999)
    public Integer getReceiver() {
        return receiver;
    }

    @Size(min = 3, max = 6)
    public String getCode() {
        return code;
    }

    @NotNull
    public String getMessage() {
        return message;
    }
}

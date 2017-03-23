/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bval.jsr;

import org.apache.bval.constraints.*;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: Provides access to the default constraints/validator implementation classes built into the framework.
 * These are configured in DefaultConstraints.properties.
 */
public class ConstraintDefaults {

    /**
     * The default constraint data stored herein.
     */
    private Map<String, Class<? extends ConstraintValidator<?, ?>>[]> defaultConstraints;

    /**
     * Create a new ConstraintDefaults instance.
     */
    public ConstraintDefaults() {
        defaultConstraints = loadDefaultConstraints();
    }

    /**
     * Get the default constraint data.
     * @return String-keyed map
     */
    public Map<String, Class<? extends ConstraintValidator<?, ?>>[]> getDefaultConstraints() {
        return defaultConstraints;
    }

    /**
     * Get the default validator implementation types for the specified constraint annotation type. 
     * @param annotationType the annotation type
     * @return array of {@link ConstraintValidator} implementation classes
     */
    @SuppressWarnings("unchecked")
    public <A extends Annotation> Class<? extends ConstraintValidator<A, ?>>[] getValidatorClasses(
        Class<A> annotationType) {
        return (Class<? extends ConstraintValidator<A, ?>>[]) getDefaultConstraints().get(annotationType.getName());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Class<? extends ConstraintValidator<?, ?>>[]> loadDefaultConstraints() {
        Map<String, Class<? extends ConstraintValidator<?, ?>>[]> defaults = new LinkedHashMap<>();
        put(defaults, AssertFalse.class, AssertFalseValidator.class);
        put(defaults, AssertTrue.class, AssertTrueValidator.class);

        put(defaults, DecimalMax.class, DecimalMaxValidatorForNumber.class, DecimalMaxValidatorForString.class);
        put(defaults, DecimalMin.class, DecimalMaxValidatorForNumber.class, DecimalMaxValidatorForString.class);
        put(defaults, Digits.class, DigitsValidatorForNumber.class, DigitsValidatorForString.class);

        put(defaults, Future.class, FutureValidatorForDate.class, FutureValidatorForCalendar.class);
        put(defaults, Max.class, MaxValidatorForNumber.class, MaxValidatorForString.class);
        put(defaults, Min.class, MinValidatorForNumber.class, MinValidatorForString.class);
        put(defaults, NotNull.class, NotNullValidator.class);
        put(defaults, Null.class, NullValidator.class);
        put(defaults, Past.class, PastValidatorForDate.class, PastValidatorForCalendar.class);

        put(defaults, Size.class, SizeValidatorForCharSequence.class,
            SizeValidatorForMap.class,
            SizeValidatorForCollection.class,
            SizeValidatorForArrayOfBoolean.class,
            SizeValidatorForArrayOfByte.class,
            SizeValidatorForArrayOfChar.class,
            SizeValidatorForArrayOfDouble.class,
            SizeValidatorForArrayOfFloat.class,
            SizeValidatorForArrayOfInt.class,
            SizeValidatorForArrayOfLong.class,
            SizeValidatorForArrayOfObject.class,
            SizeValidatorForArrayOfShort.class);
        put(defaults, Pattern.class, PatternValidator.class);

        return defaults;
    }

    private void put(Map<String, Class<? extends ConstraintValidator<?, ?>>[]> constraintMap, Class annotation, Class<? extends ConstraintValidator<?, ?>>... validators) {
        constraintMap.put(annotation.getName(), validators);
    }

}

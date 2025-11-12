/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.restassured.path.json.mapper.factory;


import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.MapperBuilder;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

/**
 * Simply creates a new Jackson 3.0 ObjectMapper
 */
public class DefaultJackson3ObjectMapperFactory implements Jackson3ObjectMapperFactory {

    private static volatile Optional<List<JacksonModule>> modules = empty();

    protected final JsonMapper defaultMapper;

    public DefaultJackson3ObjectMapperFactory() {
        this.defaultMapper = JsonMapper.builder()
                .addModules(initModules()).build();
    }

    public ObjectMapper create(Type cls, String charset) {
        return this.defaultMapper;
    }

    private List<JacksonModule> initModules() {
        if (!modules.isPresent()) {
            modules = Optional.of(MapperBuilder.findModules(getClass().getClassLoader()));
        }

        return modules.orElseThrow(IllegalAccessError::new);
    }
}

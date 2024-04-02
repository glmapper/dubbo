/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.metrics.otlp;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.ErrorTypeAwareLogger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.metrics.report.AbstractMetricsReporter;
import org.apache.dubbo.rpc.model.ApplicationModel;

import java.util.ArrayList;

import io.micrometer.registry.otlp.OtlpMeterRegistry;

/**
 * Metrics reporter for prometheus.
 */
public class OtlpMetricsReporter extends AbstractMetricsReporter {

    private final ErrorTypeAwareLogger logger = LoggerFactory.getErrorTypeAwareLogger(OtlpMetricsReporter.class);

    private final OtlpMeterRegistry otlpMeterRegistry = new OtlpMeterRegistry();

    public OtlpMetricsReporter(URL url, ApplicationModel applicationModel) {
        super(url, applicationModel);
    }

    @Override
    public void doInit() {
        addMeterRegistry(this.otlpMeterRegistry);
    }

    public String getResponse() {
        return new ArrayList<>(otlpMeterRegistry.getMeters()).toString();
    }

    @Override
    public void doDestroy() {

    }
}

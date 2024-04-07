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

import io.micrometer.core.instrument.Clock;
import io.micrometer.registry.otlp.OtlpConfig;
import io.micrometer.registry.otlp.OtlpMeterRegistry;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.ErrorTypeAwareLogger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.metrics.report.AbstractMetricsReporter;
import org.apache.dubbo.rpc.model.ApplicationModel;

/**
 * Metrics reporter for Otlp.
 */
public class OtlpMetricsReporter extends AbstractMetricsReporter {

    private final ErrorTypeAwareLogger logger = LoggerFactory.getErrorTypeAwareLogger(OtlpMetricsReporter.class);

    private final OtlpMeterRegistry otlpMeterRegistry;

    public OtlpMetricsReporter(URL url, ApplicationModel applicationModel) {
        super(url, applicationModel);
        OtlpConfig config = new OtlpConfig() {
            @Override
            public String get(String key) {
                return OtlpConfig.DEFAULT.get(key);
            }

            @Override
            public String url() {
                return url.getParameter("exporter.otlpConfig.endpoint", "http://localhost:4318/v1/metrics");
            }
        };
        this.otlpMeterRegistry = new OtlpMeterRegistry(config, Clock.SYSTEM);
    }

    @Override
    public void doInit() {
        addMeterRegistry(this.otlpMeterRegistry);
    }

    public String getResponse() {
        return null;
    }

    @Override
    public void doDestroy() {
        if (this.otlpMeterRegistry != null) {
            this.otlpMeterRegistry.close();
            logger.warn("Destroying OtlpMetricsReporter");
        }
    }
}

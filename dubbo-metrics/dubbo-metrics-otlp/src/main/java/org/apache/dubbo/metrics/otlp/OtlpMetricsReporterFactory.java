package org.apache.dubbo.metrics.otlp;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.metrics.report.AbstractMetricsReporterFactory;
import org.apache.dubbo.metrics.report.MetricsReporter;
import org.apache.dubbo.rpc.model.ApplicationModel;

public class OtlpMetricsReporterFactory extends AbstractMetricsReporterFactory {

    public OtlpMetricsReporterFactory(ApplicationModel applicationModel) {
        super(applicationModel);
    }

    @Override
    public MetricsReporter createMetricsReporter(URL url) {
        return new OtlpMetricsReporter(url, getApplicationModel());
    }
}

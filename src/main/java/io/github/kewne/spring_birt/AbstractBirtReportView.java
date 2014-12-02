package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class AbstractBirtReportView extends AbstractUrlBasedView {
    private IReportEngine reportEngine;

    public void setReportEngine(IReportEngine reportEngine) {
        this.reportEngine = reportEngine;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (reportEngine == null) {
            reportEngine = getApplicationContext()
                    .getBean(IReportEngine.class);
        }
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream reportPath = null;
        try {
            reportPath = getServletContext()
                    .getResourceAsStream(getUrl());
            if (reportPath == null) {
                reportPath = new ClassPathResource(getUrl()).getInputStream();
            }
            IReportRunnable report = reportEngine.openReportDesign(getUrl(), reportPath);
            IRunAndRenderTask task = reportEngine.createRunAndRenderTask(report);

            task.setLocale(request.getLocale());
            for (Map.Entry<String, Object> e : model.entrySet()) {
                task.setParameterValue(e.getKey(), e.getValue());
            }
            RenderOption opts = buildRenderOptions(request, response);
            response.setContentType(getContentType());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            task.setRenderOption(opts);

            task.run();
            task.close();
        } finally {
            if (reportPath != null) {
                reportPath.close();
            }
        }

    }

    protected abstract RenderOption buildRenderOptions(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Override
    public abstract String getContentType();
}

package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class BirtReportPdfView extends AbstractUrlBasedView {

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
        String reportPath = getServletContext()
                .getRealPath(getUrl());
        IReportRunnable report = reportEngine.openReportDesign(reportPath);
        IRunAndRenderTask task = reportEngine.createRunAndRenderTask(report);

        task.setLocale(request.getLocale());
        for (Map.Entry<String, Object> e : model.entrySet()) {
            task.setParameterValue(e.getKey(), e.getValue());
        }
        RenderOption opts = buildRenderOptions(response);
        task.setRenderOption(opts);

        task.run();
        task.close();
    }

    private RenderOption buildRenderOptions(HttpServletResponse response) throws IOException {
        RenderOption opts = new PDFRenderOption();
        opts.setOutputFormat("PDF");
        opts.setOutputStream(response.getOutputStream());
        response.setContentType("application/pdf");
        return opts;
    }

}

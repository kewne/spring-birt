package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
        if (!new File(reportPath).exists()) {
            reportPath = new ClassPathResource(getUrl()).getFile().getAbsolutePath();
        }
        IReportRunnable report = reportEngine.openReportDesign(reportPath);
        IRunAndRenderTask task = reportEngine.createRunAndRenderTask(report);

        task.setLocale(request.getLocale());
        for (Map.Entry<String, Object> e : model.entrySet()) {
            task.setParameterValue(e.getKey(), e.getValue());
        }
        RenderOption opts = buildRenderOptions(request, response);
        task.setRenderOption(opts);

        task.run();
        task.close();
    }

    private RenderOption buildRenderOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletServerHttpRequest goodRequest = new ServletServerHttpRequest(request);
        RenderOption opts = new PDFRenderOption();
        opts.setOutputFormat("PDF");
        opts.setOutputStream(response.getOutputStream());
        response.setContentType("application/pdf");
        return opts;
    }

}

package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PdfBirtReportView extends AbstractBirtReportView {

    @Override
    protected RenderOption buildRenderOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RenderOption opts = new PDFRenderOption();
        opts.setOutputFormat("PDF");
        opts.setOutputStream(response.getOutputStream());
        return opts;
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }
}

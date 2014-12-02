package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XlsBirtReportView extends AbstractBirtReportView {
    @Override
    protected RenderOption buildRenderOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EXCELRenderOption opt = new EXCELRenderOption();
        opt.setOutputFormat("xlsx");
        opt.setOutputStream(response.getOutputStream());
        response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
        return opt;
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}

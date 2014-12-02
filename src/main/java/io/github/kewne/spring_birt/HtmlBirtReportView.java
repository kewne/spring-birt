package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlBirtReportView extends AbstractBirtReportView {
    @Override
    protected RenderOption buildRenderOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTMLRenderOption opt = new HTMLRenderOption();
        opt.setEmitterID("io.github.kewne.spring_birt.htmlPlusEmbeddedSvg");
        opt.setOutputFormat(HTMLRenderOption.HTML);
        opt.setOutputStream(response.getOutputStream());
        opt.setBaseImageURL(null);
        opt.setImageDirectory(null);
        opt.setEnableAgentStyleEngine(true);
        return opt;
    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }
}

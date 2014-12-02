package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.emitter.html.HTMLReportEmitter;

import java.nio.charset.StandardCharsets;

public class HtmlWithEmbeddedSvgReportEmitter extends HTMLReportEmitter {

    @Override
    protected void outputSVGImage(IImageContent image, StringBuffer styleBuffer, int display) {
        writer.cdata(new String(image.getData(), StandardCharsets.UTF_8));
    }
}

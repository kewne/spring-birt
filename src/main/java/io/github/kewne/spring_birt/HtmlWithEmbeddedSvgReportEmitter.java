package io.github.kewne.spring_birt;

import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.emitter.html.HTMLReportEmitter;

import java.io.IOException;

public class HtmlWithEmbeddedSvgReportEmitter extends HTMLReportEmitter {

    @Override
    protected void outputSVGImage(IImageContent image, StringBuffer styleBuffer, int display) {
        try {
            out.write(image.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

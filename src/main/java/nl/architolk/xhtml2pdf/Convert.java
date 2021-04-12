package nl.architolk.xhtml2pdf;

import java.io.File;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;

public class Convert {

  private static final Logger LOG = LoggerFactory.getLogger(Convert.class);

  public static void main(String[] args) {

    if (args.length == 2) {

      LOG.info("Starting conversion");
      LOG.info("Input file: {}",args[0]);
      LOG.info("Ouput file: {}",args[1]);

      try {

        FileOutputStream os = new FileOutputStream(args[1]);
        ITextRenderer renderer = new ITextRenderer();

        ChainingReplacedElementFactory chainingReplacedElementFactory = new ChainingReplacedElementFactory();
        chainingReplacedElementFactory.addReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory());
        chainingReplacedElementFactory.addReplacedElementFactory(new SVGReplacedElementFactory());
        renderer.getSharedContext().setReplacedElementFactory(chainingReplacedElementFactory);

        renderer.setDocument(new File(args[0]));
        renderer.layout();
        renderer.createPDF(os);
        LOG.info("Done!");
      }
      catch (Exception e) {
        LOG.error(e.getMessage(),e);
      }
    } else {
      LOG.info("Usage: xhtml2pdf <input.xhtml> <output.pdf>");
    }
  }
}

package org.cipres.treebase.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Properties;
import java.net.URI;

import org.cipres.treebase.ContextManager;
import org.cipres.treebase.util.AbstractStandalone;
import org.cipres.treebase.domain.study.Study;
import org.nexml.model.Document;
import org.nexml.model.DocumentFactory;
import org.cipres.treebase.domain.nexus.nexml.NexmlDocumentWriter;


public class ExportNexml extends AbstractStandalone{

    public static void main(String[] args) {
        setupContext();
        for (String arg : args) {
            Long sid = Long.parseLong(arg);
            Study study = null;
            try {
                study = ContextManager.getStudyService().findByID(sid);
            } catch(NumberFormatException nfe){
                System.err.println("Bad argument format: " + arg);
                return ;
            } catch (NullPointerException e){
                System.err.println("Study " + arg + " does not exist");
            }
            NexmlDocumentWriter ndc = getNexmlDocumentConverter(study, null);
            String x = ndc.fromTreeBaseToXml(study).getXmlString();
            System.out.println(x);
            System.out.flush();
        }
    }

    protected static NexmlDocumentWriter getNexmlDocumentConverter(Study study, Properties properties) {
        String baseURI = null;
        if ( null != properties ) {
            baseURI = properties.getProperty("nexml.uri.base");
        }
        Document document = null;
        try {
            document = DocumentFactory.createDocument();
            document.setBaseURI(new URI(baseURI));//NPE
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        NexmlDocumentWriter ndc = new NexmlDocumentWriter(study,
                                                          ContextManager.getTaxonLabelHome(),
                                                          document,
                                                          baseURI);
        return ndc;
    }
}
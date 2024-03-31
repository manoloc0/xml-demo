import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {
    public static void main(String[] args) {
        try {
            Document doc = XMLUtility.loadXMLDocumentFromFile("/Users/tylercadenas/sde/xml-demo/src/dsm.xml");
            Element newDiagnosis = XMLUtility.createDiagnosisElement(doc, "Cool Guy Syndrome", "CGS", "666", "420.69");
            XMLUtility.addElement(doc, "Diagnoses", newDiagnosis);
            XMLUtility.updateElementValue(doc, "Obsessive Compulsive Disorder", "Page", "420");
            XMLUtility.saveXMLDocumentToFile(doc, "/Users/tylercadenas/sde/xml-demo/src/dsm.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


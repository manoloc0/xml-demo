import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLUtility {

    public static Document loadXMLDocumentFromFile(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filePath));
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static void saveXMLDocumentToFile(Document doc, String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    public static NodeList getElementsByTag(Document doc, String tagName) {
        return doc.getElementsByTagName(tagName);
    }

    public static void updateElementValue(Document doc, String diagnosisName, String elementTag, String newValue) {
        NodeList diagnoses = getElementsByTag(doc, "Diagnosis");
        for (int i = 0; i < diagnoses.getLength(); i++) {
            Node diagnosis = diagnoses.item(i);
            if (diagnosis.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) diagnosis;
                if (element.getElementsByTagName("Name").item(0).getTextContent().equals(diagnosisName)) {
                    element.getElementsByTagName(elementTag).item(0).setTextContent(newValue);
                    break;
                }
            }
        }
    }

    public static void addElement(Document doc, String rootElementTag, Element newElement) {
        Node rootElement = doc.getElementsByTagName(rootElementTag).item(0);
        rootElement.appendChild(newElement);
    }

    public static Element createDiagnosisElement(Document doc, String name, String abbreviation, String page, String code) {
        Element diagnosis = doc.createElement("Diagnosis");

        Element nameElement = doc.createElement("Name");
        nameElement.appendChild(doc.createTextNode(name));
        diagnosis.appendChild(nameElement);

        Element abbreviationElement = doc.createElement("Abbreviation");
        abbreviationElement.appendChild(doc.createTextNode(abbreviation));
        diagnosis.appendChild(abbreviationElement);

        Element pageElement = doc.createElement("Page");
        pageElement.appendChild(doc.createTextNode(page));
        diagnosis.appendChild(pageElement);

        Element codeElement = doc.createElement("Code");
        codeElement.appendChild(doc.createTextNode(code));
        diagnosis.appendChild(codeElement);

        return diagnosis;
    }

    // Example of usage in a main method or other method
    public static void main(String[] args) {
        try {
            Document doc = loadXMLDocumentFromFile("path/to/your/xmlfile.xml");
            Element newDiagnosis = createDiagnosisElement(doc, "Example Disorder", "ED", "101", "999.9");
            addElement(doc, "Diagnoses", newDiagnosis);
            updateElementValue(doc, "Example Disorder", "Page", "102");
            saveXMLDocumentToFile(doc, "path/to/your/xmlfile.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
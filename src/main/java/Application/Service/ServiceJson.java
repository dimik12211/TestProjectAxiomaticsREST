package Application.Service;

import Application.DAO.DAOJson;
import Application.Model.Person;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class ServiceJson {

    @Autowired
    private DAOJson daoJson;

    public void save(Person person) {
        try {
            daoJson.save(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String JsonToXML(Person person) {
        JSONObject jsonObject = new JSONObject(person);
        jsonObject.remove("id");

        String xml = XML.toString(jsonObject, "person");
        xml = "<![CDATA[" + xml + "]]>";
        /*DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
        Document doc = builder.newDocument();
        Element rootElem = doc.createElement("root");
        CDATASection cdata = doc.createCDATASection(xml);
        rootElem.appendChild(cdata);*/
        return xml;
    }
}

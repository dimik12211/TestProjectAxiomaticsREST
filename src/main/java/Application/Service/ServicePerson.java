package Application.Service;

import Application.DAO.DAO_Person;
import Application.Model.Person;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePerson {

    @Autowired
    private DAO_Person daoPerson;

    private final String soapRequestStart = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
            "xmlns:prod=\"http://www.example.org/Application/producingwebservice\">\n<soapenv:Header/>\n<soapenv:Body>\n<prod:getCountryRequest>\n<prod:xml>\n";

    private final String soapRequestEnd = "\n</prod:xml>\n</prod:getCountryRequest>\n</soapenv:Body>\n</soapenv:Envelope>\n";

    private final String soapResponseStart = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><ns2:getCountryResponse xmlns:ns2=\"http://www.example.org/Application/producingwebservice\"><ns2:xml>";

    private final String soapResponseEnd = "</ns2:xml></ns2:getCountryResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";

    public void save(Person person) {
        try {
            daoPerson.save(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Person person){
        try {
            daoPerson.update(person);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String jsonToXML(String personJson) {
        JSONObject jsonObject = new JSONObject(personJson);
        String xml = XML.toString(jsonObject, "person");
        xml = "<![CDATA[" + xml + "]]>";
        return xml;
    }

    public String xmlResponse(String xml){
        try {
            xml = xml.replace("&lt;", "<");
            xml = xml.replace("&gt;", ">");
            xml = xml.substring(soapResponseStart.length());
            xml = xml.substring(0, xml.length() - soapResponseEnd.length());
            xml = xml.trim();
            if (xml.startsWith("<![CDATA[")) {
                xml = xml.substring(9);
                int i = xml.indexOf("]]>");
                if (i != -1) {
                    xml = xml.substring(0, i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return xml;
    }

    public StringBuffer stringBufferCreate(String xml){
        StringBuffer buffer = new StringBuffer();
        buffer.append(soapRequestStart + xml + soapRequestEnd);
        return buffer;
    }
}

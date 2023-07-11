package Application.Controller;

import Application.Model.Person;
import Application.Service.ServicePerson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class RESTController {

    @Autowired
    private ServicePerson servicePerson;

    @PostMapping("/REST")
    public String restPostMapping(@RequestBody String personJson) {
        String response_xml = "Ошибка: повторите запрос позже";
        try {
            Person person = new Person(personJson, "");
            servicePerson.save(person);
            String xml = servicePerson.jsonToXML(personJson);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8083/ws");
            StringEntity entity = new StringEntity(servicePerson.stringBufferCreate(xml).toString(), "utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "text/xml; charset=utf-8");
            httpPost.setHeader("SOAPAction", "GetData");
            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            response_xml = br.readLine();
            response_xml = servicePerson.xmlResponse(response_xml);
            person.setXml(response_xml);
            servicePerson.update(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response_xml;
    }
}

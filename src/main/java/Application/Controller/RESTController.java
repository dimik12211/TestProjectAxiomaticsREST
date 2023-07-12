package Application.Controller;

import Application.Model.Person;
import Application.Service.ServicePerson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class RESTController {

    Logger logger = LoggerFactory.getLogger(RESTController.class);

    @Autowired
    private ServicePerson servicePerson;

    @PostMapping("/REST")
    public String restPostMapping(@RequestBody String personJson) {
        String responseXml = "Ошибка: повторите запрос позже";
        try {
            Person person = new Person(personJson, "");
            servicePerson.save(person);
            String xml = servicePerson.jsonToXML(personJson);
            logger.info("XML успешно создан");
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8083/ws");
            StringEntity entity = new StringEntity(servicePerson.stringBufferCreate(xml).toString(), "utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "text/xml; charset=utf-8");
            CloseableHttpResponse response = client.execute(httpPost);
            logger.info("SOAP запрос успешно создан и отправлен");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            responseXml = bufferedReader.readLine();
            responseXml = servicePerson.xmlResponse(responseXml);
            person.setXml(responseXml);
            servicePerson.update(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseXml;
    }
}

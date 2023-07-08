package Application.Controller;

import Application.Model.Person;
import Application.Service.ServiceJson;
import org.apache.http.HttpVersion;
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
    private ServiceJson serviceJson;

    @PostMapping("/REST")
    public String restPostMapping(@RequestBody Person person) {
        serviceJson.save(person);
        String xml = serviceJson.JsonToXML(person);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8083/SOAP");
        String response_xml = "Ошибка: повторите запрос позже";
        try {
            StringEntity entity = new StringEntity(xml,"utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "text/html; charset=utf-8");

            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()));
            response_xml = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response_xml;
    }
}

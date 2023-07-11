package Application.Model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "t_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Type(type = "text")
    @Column (columnDefinition = "text")
    private String json;

    @Type(type = "text")
    @Column (columnDefinition = "text")
    private String xml;

    public Person() {
    }

    public Person(String personJson, String personXml) {
        this.json = personJson;
        this.xml = personXml;
    }

    public int getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}

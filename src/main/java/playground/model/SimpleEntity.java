package playground.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="simpleEntity")
public class SimpleEntity {
    @Id
    private String id;

    private Integer version;

    private String title;

    private double[] location;

    @Email
    private String email;

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double[] getLocation() {
        return location;
    }
    public void setLocation(double[] location) {
        this.location = location;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

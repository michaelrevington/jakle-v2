package dev.revington.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.revington.util.DefaultValueFilter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Document("users")
@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = DefaultValueFilter.class)
public class User {

    private String firstname; 
    private String lastname;
    
    @Id
    private String id;

    private String username;
    private String name; 
    private String email;
    private String password;
    private String profilePic;
    private String socketId;
    private String active;
    private String type;
    private String identity;
    private String bio;

    private Date dateOfBirth;

    private int attempts;
    private int status;
    private int validity;
    private int activity;
    private int count; 
 
    private long time;
    private long created;

    private HashMap<String, String> links;

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public String getUsername() {
        return username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public HashMap<String, String> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, String> links) {
        this.links = links;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getActivity() {
        return activity;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    } 

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    } 

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

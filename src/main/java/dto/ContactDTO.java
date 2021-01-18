/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Contact;

/**
 *
 * @author jacobsimonsen
 */
public class ContactDTO {

    String name;
    String email;
    String company;
    String jobtitle;
    String phoneNumber;
    String password;

    public ContactDTO(String name, String password, String email, String company, String jobtitle, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.company = company;
        this.jobtitle = jobtitle;
        this.phoneNumber = phoneNumber;
    }

    public ContactDTO(Contact contact) {
        this.name = contact.getUserName();
        this.email = contact.getEmail();
        this.company = contact.getCompany();
        this.jobtitle = contact.getJobtitle();
        this.phoneNumber = contact.getPhoneNumber();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

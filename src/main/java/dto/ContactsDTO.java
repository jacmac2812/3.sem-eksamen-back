/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Contact;
import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobsimonsen
 */
public class ContactsDTO {

    List<ContactDTO> all = new ArrayList();

    public ContactsDTO(List<Contact> userEntities) {
        userEntities.forEach((c) -> {
            all.add(new ContactDTO(c));
        });
    }

    public List<ContactDTO> getAll() {
        return all;
    }

}

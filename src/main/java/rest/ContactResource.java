/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ContactDTO;
import errorhandling.MissingInputException;
import facades.ContactFacade;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author jacobsimonsen
 */
@Path("contacts")
public class ContactResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ContactFacade FACADE = ContactFacade.getContactFacade(EMF);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createContact(String contact) throws MissingInputException {
        ContactDTO cDTO = GSON.fromJson(contact, ContactDTO.class);
        ContactDTO cAdded = FACADE.createContact(cDTO.getName(), cDTO.getPassword(), cDTO.getEmail(), cDTO.getCompany(), cDTO.getJobtitle(), cDTO.getPhoneNumber());
        return GSON.toJson(cAdded);
    }

//    @Path("/{name}")
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deleteUser(@PathParam("name") String name) {
//        UserDTO uDeleted = FACADE.deleteUser(name);
//        return GSON.toJson(uDeleted);
//    }
//
//    @Path("/{name}")
//    @PUT
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public String editUser(@PathParam("name") String name, String user) {
//        UserDTO uDTO = GSON.fromJson(user, UserDTO.class);
//        UserDTO uEdited = FACADE.editUser(uDTO, name);
//        return GSON.toJson(uEdited);
//    }
////
//    @Path("/all")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
//    public String getAllUsers() {
//        UsersDTO usDTO = FACADE.getAllUsers();
//        return GSON.toJson(usDTO);
//    }
}

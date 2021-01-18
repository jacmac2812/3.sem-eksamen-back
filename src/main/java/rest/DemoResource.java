package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ChuckDTO;
import dto.CombinedDTO;
import dto.ContactDTO;
import dto.ContactsDTO;
import dto.DadDTO;
import dto.SwabiDTO;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import facades.ContactFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import jokefetcher.JokeFetcher;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    private static EntityManagerFactory emf;
    private static final ContactFacade FACADE = ContactFacade.getContactFacade(EMF);

    @Context
    SecurityContext securityContext;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("contact")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createContact(String contact) throws MissingInputException {
        ContactDTO cDTO = GSON.fromJson(contact, ContactDTO.class);
        ContactDTO cAdded = FACADE.createContact(cDTO.getName(), cDTO.getPassword(), cDTO.getEmail(), cDTO.getCompany(), cDTO.getJobtitle(), cDTO.getPhoneNumber());
        return GSON.toJson(cAdded);
    }

    @Path("/{name}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteContact(@PathParam("name") String name) {
        ContactDTO cDeleted = FACADE.deleteContact(name);
        return GSON.toJson(cDeleted);
    }
 @Path("alls")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllContacts() {
        ContactsDTO csDTO = FACADE.getAllContacts();
        return GSON.toJson(csDTO);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("populate")
    public String populate() throws IOException {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "hello");
        User admin = new User("admin", "with");
        User both = new User("user_admin", "you");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();

        return GSON.toJson("hej");
    }

}

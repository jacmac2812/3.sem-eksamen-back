package facades;

import dto.ContactDTO;
import dto.ContactsDTO;
import entities.Contact;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;
import security.errorhandling.AuthenticationException;

/**
 * @author jacobsimonsen
 */
public class ContactFacade {

    private static EntityManagerFactory emf;

    private static ContactFacade instance;

    private ContactFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static ContactFacade getContactFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ContactFacade();
        }
        return instance;
    }

    public Contact getVeryfiedContact(String contactname, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Contact contact;
        try {
            contact = em.find(Contact.class, contactname);
            if (contact == null || !contact.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return contact;
    }

    public ContactDTO createContact(String name, String password, String email, String company, String jobtitle, String phoneNumber) throws MissingInputException {
        
        if (name.length() == 0 || password.length() == 0) {
            throw new MissingInputException("Name and/or password is missing");
        }
        if (email.length() == 0 || email.contains("@") == false) {
            throw new MissingInputException("Email missing and/or does not contain @");
        }
        if (company.length() == 0 || jobtitle.length() == 0) {
            throw new MissingInputException("Company and/or jobtile is missing");
        }
        if (phoneNumber.length() != 8) {
            throw new MissingInputException("Phonenumber is the wrong length");
        }
        
        EntityManager em = emf.createEntityManager();

        try {

            Contact c = new Contact(name, password, email, company, jobtitle, phoneNumber);
            Role userRole = new Role("user");

            c.addRole(userRole);
            em.getTransaction().begin();

            em.persist(c);

            em.getTransaction().commit();

            return new ContactDTO(c);
        } finally {
            em.close();
        }
    }

    public ContactDTO deleteContact(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            Contact contact = em.find(Contact.class, name);

            em.getTransaction().begin();

            em.remove(contact);

            em.getTransaction().commit();

            ContactDTO cDTO = new ContactDTO(contact);

            return cDTO;

        } finally {
            em.close();
        }
    }

//    public UserDTO editUser(UserDTO u, String name) {
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            User user = em.find(User.class, name);
//            
//            if (u.getPassword().length() != 0) {
//                user.setUserPass(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(5)));
//            }
//            
//            if (u.getEmail().length() != 0 || u.getEmail().contains("@") == true) {
//                user.setEmail(u.getEmail());
//            }
//            
//            if (u.getPhoneNumber().length() != 0) {
//                user.setPhoneNumber(u.getPhoneNumber());
//            }            
//
//            em.getTransaction().begin();
//
//            em.persist(user);
//
//            em.getTransaction().commit();
//
//            UserDTO uDTO = new UserDTO(user);
//            return uDTO;
//        } finally {
//            em.close();
//        }
//    }
//
    public ContactsDTO getAllContacts() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Contact> query = em.createQuery("SELECT u FROM User u", entities.Contact.class);
            List<Contact> contacts = query.getResultList();
            ContactsDTO all = new ContactsDTO(contacts);
            return all;
        } finally {
            em.close();
        }
    }
}

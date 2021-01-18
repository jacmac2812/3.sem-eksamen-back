/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.ContactDTO;
import entities.Contact;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Acer
 */
public class ContactFacadeTest {

    private static EntityManagerFactory emf;
    private static ContactFacade facade;
    Contact contact1;
    Contact contact2;
    Contact contact3;
        
    public ContactFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ContactFacade.getCrmFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            contact1 = new Contact("bent", "bent@mail.dk", "apple", "boss",  "12345678");
            contact2= new Contact("hans", "hans@mail.dk", "hyuf", "slave",  "87654321");
            contact3 = new Contact("grete", "grete@mail.dk", "cola", "undersaat",  "11223344");


            em.getTransaction().begin();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

//    @Test
//    public void testGetAllContacts() {
//        assertEquals(3, facade.getAllContacts().getAll().size(), "Expect three users");
//    }

//    @Test
//    public void testAddPerson() throws MissingInputException {
//        ContactDTO cDTO = facade.addContact("test", "test@mail.dk", "hej", "fff", "11223344");
//        assertEquals("test", cDTO.getName(), "Expect the same name");
//        assertEquals(4, facade.getAllContacts().getAll().size(), "Excepts four persons");
//    }
//
//    @Test
//    public void testAddPersonException() {
//        try {
//            ContactDTO cDTO = facade.addContact("test", "", "test@mail.dk", "11223344");
//        } catch (MissingInputException ex) {
//            assertEquals("Name and/or password is missing", ex.getMessage(), "Except the same error message");
//        }
//    }
//
//    @Test
//    public void testEditPerson() {
//        ContactDTO cDTO = new ContactDTO("test", "test@mail.dk", "hej", "fff", "11223344");
//        cDTO.setEmail("newmail@mail.dk");
//        ContactDTO cDTOedited = facade.editUser(cDTO, contact.getUserName());
//        assertEquals(cDTOedited.getEmail(), cDTO.getEmail(), "Except the same email");
//    }
//
//    @Test
//    public void testDeletePerson() {
//        ContactDTO cDTO = facade.deleteUser(both.getContactName());
//        assertEquals(2, facade.getAllContacts().getAll().size(), "Excepts two persons");
//    }

}

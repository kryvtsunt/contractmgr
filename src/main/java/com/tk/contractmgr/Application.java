package com.tk.contractmgr;

import com.tk.contractmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class Application {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Tim", "Kryvtsun")
                .withEmail("@husky")
                .withPhone(101L)
                .build();
        save(contact);
        for(Contact c: fetchAllContacts()) {
            System.out.println(c);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Contact.class);
        List<Contact> contacts = criteria.list();
        session.close();
        return contacts;
    }

    private static void save(Contact contact){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(contact);
        session.getTransaction().commit();
        session.close();
    }
}

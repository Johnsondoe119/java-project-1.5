package com.bofa.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    /*
    This class purpose is to handle the database startup.
    */
    private Session ses;
    private SessionFactory sf;

    public HibernateUtil() {
        sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    /*
    Need a way to obtain a session object since that is used to execute statements.
    */
    public Session getSession() {
        // Always create a new session.
        ses = sf.openSession();
        return ses;
    }

    public void closeSession() {
        if (ses != null && ses.isOpen()) {
            ses.close();
        }
    }

    public void closeSessionFactory() {
        if (sf != null) {
            sf.close();
        }
    }
}

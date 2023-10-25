package com.bofa.daos;

import com.bofa.entities.User;
import com.bofa.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO extends GeneralDAO<User> {
    @Override
    public User getById(int id){
        //3 step proccess
        //start by get/create a session
        Session ses = HibernateUtil.getSession();

        //next execute the query
        User u = (User) ses.get(User.class, id);
        //Select * From clothes Where clothes_id

        //return desired amount
        return u;
    }

    @Override
    public List<User> getAll(){
        //follow 3-step process

        //Get Session
        Session ses = HibernateUtil.getSession();

        //Execute statement
        List<User> user = ses.createQuery("from User").list();//.uniqueResult will be for result 1

        //Return result
        return user;
    }

    @Override
    public User save(User objectToBeSaved){
        //Create/Get the session
        Session ses = HibernateUtil.getSession();
        //To save we have to call the .svae() method
        //the save method would make the call that looks like
        //INSERT INTO clothes (clothesname, password, role_id) VALUES (?, ?, ?)

        int pk = (int) ses.save(objectToBeSaved); //.save returns the primary key

        //return desired object
        return getById(pk);
    }

    //Steps for update and delete are a little different, we will need to start a transaction
    //transactions are a setries of dml statements that operate like a git push, either they all execute
    //correctly and get saved, or if one falls it willl rollback the database to before any of them got executed

    @Override
    public void update(User objectToBeUpdated){
        //still follow our general steps but we need a transaction for this one and delete
        Session ses = HibernateUtil.getSession();

        //Begin transaction
        Transaction tx = ses.beginTransaction();

        //Execute the query
        ses.update(objectToBeUpdated); //update based off the objects and returns void

        //commit(save) the transaction
        tx.commit();
    }

    @Override
    public void delete(User objectToBeDeleted){
        //Get session
        Session ses = HibernateUtil.getSession();
        //start transaction
        Transaction tx = ses.beginTransaction();
        // Execute statement
        //For deleting we call ses.delete
        ses.delete(objectToBeDeleted);
        //commit the transaction
        tx.commit();
    }

    public User getUserByUsername(String username){
        Session ses = HibernateUtil.getSession();

        User u = (User) ses.createQuery("from User where username = '" + username + " '").uniqueResult();

        return u;
    }
}

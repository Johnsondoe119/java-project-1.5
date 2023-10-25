package com.bofa.repos;

import com.bofa.entities.User;
import com.bofa.exceptions.EntityNotFoundException;
import com.bofa.utils.MockDB;

import java.util.List;
import java.util.Optional;

public class UserRepos extends GenericRepo<User> {

    @Override
    public List<User> getAll() {
        return MockDB.registeredUser;
    }

    @Override
    public User getById(int userId) throws EntityNotFoundException {
      /*  for (User user : MockDB.registeredUser) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        throw new EntityNotFoundException("User Not Found");

       */

        Optional<User> resultUser = MockDB.registeredUser.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst();
        if (resultUser.isPresent()){
            return resultUser.get();
        }else{
            throw new EntityNotFoundException("User was not found!");
        }
    }

    @Override
    public User save(User userToSave) {
        if (MockDB.registeredUser.add(userToSave)) {
            return userToSave;
        }
        System.out.println("Error while saving. User not stored in the database");
        return null;
    }

    public void update(User userToUpdate) {
        int index = -1;
        for (User user : MockDB.registeredUser) {
            if (user.getUserId() == userToUpdate.getUserId()) {
                index = MockDB.registeredUser.indexOf(user);
                break;
            }
        }
        if (index != -1) {
            MockDB.registeredUser.set(index, userToUpdate);
        }
    }

    public void delete(int userId) {
        MockDB.registeredUser.removeIf(user -> user.getUserId() == userId);
    }

    @Override
    public Optional<List<String>> searchByTitle(String title) {
        return Optional.empty();
    }

    public static User getUserByUsername(String username) throws EntityNotFoundException {
        for (User user : MockDB.registeredUser) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new EntityNotFoundException("No user with username: " + username + " found.");
    }
}

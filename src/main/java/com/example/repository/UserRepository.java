package com.example.repository;

import com.example.model.Order;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User>{
    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public UserRepository() {
    }
    public ArrayList<User> getUsers(){
        return findAll();
    }
    public User getUserById(UUID userId){
        ArrayList<User> users = getUsers();
        for(User user : users){
            if(user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    public User addUser(User user){
        save(user);
        return user;
    }
    public List<Order> getOrdersByUserId(UUID userId){
        User user = getUserById(userId);

        return user.getOrders();
    }
    public void addOrderToUser(UUID userId, Order order){
        ArrayList<User> users = getUsers();
        for(User user : users){
            if(user.getId().equals(userId)) {
                user.getOrders().add(order);
                overrideData(users);
                System.out.println("Order added");
                return;
            }
        }
        System.out.println("Order not added: User not found");
    }
    public void removeOrderFromUser(UUID userId, UUID orderId){
        ArrayList<User> users = getUsers();
        for(User user : users){
            if(user.getId().equals(userId)) {
                List<Order> orders = user.getOrders();
                for(Order order : orders){
                    if (order.)
                }
                overrideData(users);
                System.out.println("Order added");
                return;
            }
        }
        System.out.println("Order not added: User not found");
    }
    public void deleteUserById(UUID userId){
        ArrayList<User> users = getUsers();
        for(User user : users){
            if(user.getId().equals(userId)) {
                users.remove(user);
                overrideData(users);
                System.out.println("User with id " + userId + " was deleted.");
            }
        }
        System.out.println("User with id " + userId + " was not found");
    }

}

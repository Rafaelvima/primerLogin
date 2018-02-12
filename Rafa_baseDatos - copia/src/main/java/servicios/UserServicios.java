/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.UsersDAO;
import java.util.List;
import model.User;

/**
 *
 * @author Rafa
 */
public class UserServicios {

    public List<User> getAllUsers() {
        UsersDAO dao = new UsersDAO();

        return dao.getAllUsersJDBCTemplate();
    }

    public User getUserById(int id) {
        UsersDAO dao = new UsersDAO();

        return dao.getUserById(id);

    }

    public int addUser(User userNuevo) {

        UsersDAO dao = new UsersDAO();

        return dao.addUserJDBCTemplate(userNuevo);
    }

    public int delUser(User userNuevo) {
        UsersDAO dao = new UsersDAO();

        return dao.addUserJDBCTemplate(userNuevo);
    }

    public User ActivarUser(String cod) {
        UsersDAO dao = new UsersDAO();

        return dao.ActivarUser(cod);
    }

    public int ActivacionUser(String cod) {
        UsersDAO dao = new UsersDAO();

        return dao.ActivacionUser(cod);
    }

    public String BuscarNombre(String nom) {
        UsersDAO dao = new UsersDAO();

        return dao.BuscarNombre(nom);
    }

    public User LoginUser(String Nombre) {
        UsersDAO dao = new UsersDAO();

        return dao.LoginUser(Nombre);
    }
}

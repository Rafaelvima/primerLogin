/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;

public class UsersDAO {
//Select JDBC spring

    public List<User> getAllUsersJDBCTemplate() {
        List<User> users;
        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    DBConnection.getInstance().getDataSource());
            users = jtm.query("Select * from USERS",
                    new BeanPropertyRowMapper(User.class));
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            users = null;
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<User> h
                    = new BeanHandler<>(User.class);
            user = qr.query(con, "select * FROM USERS where ID = ?",
                    h, id);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return user;
    }

    public User ActivarUser(String cod) {
        User user;
        try {
            String sql = "SELECT * FROM USERS WHERE CODIGO_ACTIVACION=?";
            JdbcTemplate jtm = new JdbcTemplate(
                    DBConnection.getInstance().getDataSource());
            user = (User) jtm.queryForObject(
                    sql, new Object[]{cod},
                    new BeanPropertyRowMapper(User.class));
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            user = null;
        }

        return user;
    }

    public int ActivacionUser(String cod) {
        int filas;
        try {
            String sql = "update USERS set ACTIVO=1 WHERE CODIGO_ACTIVACION=?";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update(sql, cod);
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = 0;
        }

        return filas;
    }

    public String BuscarNombre(String nom) {
       String filas;
        try {
            String sql = "select NOMBRE FROM USERS WHERE NOMBRE=?";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.queryForObject(sql, String.class, nom);
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = null;
        }

        return filas;
    }

    public int addUserJDBCTemplate(User a) {
        int filas;
        try {
            String sql = "insert into USERS (NOMBRE,PASSWORD,CODIGO_ACTIVACION,ACTIVO,FECHA_ACTIVACION,EMAIL) VALUES(?,?,?,0,?,?)";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update(sql, a.getNombre(), a.getPassword(), a.getCodigo_activacion(), a.getFecha_activacion(), a.getEmail());
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = 0;
        }
        return filas;
    }

    public int delUser(User a) {
        int filas;
        try {
            String sql = "insert into USERS (NOMBRE,PASSWORD,CODIGO_ACTIVACION,ACTIVO,FECHA_ACTIVACION,EMAIL) VALUES(?,?,?,0,?,?)";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update(sql, a.getNombre(), a.getPassword(), a.getCodigo_activacion(), a.getFecha_activacion(), a.getEmail());
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = 0;
        }
        return filas;
    }

    public User LoginUser(String Nombre) {
        User filas;
        try {
            String sql = "select * FROM USERS WHERE NOMBRE=?";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = (User) jtm.queryForObject(
                    sql, new Object[]{
                        Nombre
                    },
                    new BeanPropertyRowMapper(User.class));
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = null;
        }

        return filas;
    }

    public int updateUser(User a) throws Exception {
        int filas;
        try {
            String sql = "update USERS set NOMBRE=?,PASSWORD=?,CODIGO_ACTIVACION=?,ACTIVO=?,FECHA_ACTIVACION=?,EMAIL=? WHERE ID=?";
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            filas = jtm.update(sql, a.getNombre(), a.getPassword(), a.getCodigo_activacion(), a.getFecha_activacion(), a.getEmail(),a.getId());
        } catch (DataAccessException e) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, e);
            filas = 0;
        }

        return filas;
    
    }

}

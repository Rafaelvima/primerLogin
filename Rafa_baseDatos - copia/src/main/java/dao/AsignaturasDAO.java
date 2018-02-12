/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Asignatura;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Rafa
 */
public class AsignaturasDAO {
//Select DBUtils

    public List<Asignatura> getAllAsignaturas() {
        JdbcTemplate jtm = new JdbcTemplate(
                DBConnection.getInstance().getDataSource());
        List<Asignatura> alumnos = jtm.query("Select * from ASIGNATURAS",
                new BeanPropertyRowMapper(Asignatura.class));
        return alumnos;
    }
    //DEL SI

    public void delAsig(Asignatura u) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();

            int filas = qr.update(con,
                    "DELETE FROM ASIGNATURAS WHERE ID=?",
                    u.getId());

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(AsignaturasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
    }

    //UPDATE SI
    public void updateAsig(Asignatura u) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();

            int filas = qr.update(con,
                    "UPDATE ASIGNATURAS SET NOMBRE=?,CURSO=?"
                    + ", CICLO=? WHERE ID=?",
                    u.getNombre(), u.getCurso(), u.getCiclo(), u.getId());

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(AsignaturasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
    }

    // insert DBUTILS SI
    public Asignatura addAsig(Asignatura u) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            con.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<Asignatura> h = new BeanHandler<>(Asignatura.class);
            Asignatura id = qr.insert(con,
                    "INSERT INTO ASIGNATURAS(ID,NOMBRE,CURSO,CICLO) VALUES(?,?,?,?)", h,
                    u.getId(), u.getNombre(), u.getCurso(), u.getCiclo());
            u.setId(id.getId());
            con.commit();

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(AsignaturasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return u;

    }

}

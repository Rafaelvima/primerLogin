/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import model.Nota;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author Rafa
 */
public class NotasDAO {

    //Select DBUtils
    public List<Nota> getAllNotas() {
        List<Nota> lista = null;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Nota>> h = new BeanListHandler<>(Nota.class);
            lista = qr.query(con, "select * FROM NOTAS", h);

        } catch (SQLException | NamingException ex) {
            Logger.getLogger(NotasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return lista;
    }

    //UPDATE SI
    public void updateNota(Nota u) {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();

            int filas = qr.update(con,
                    "UPDATE NOTAS SET NOTA=?"
                    + " WHERE ID_ALUMNO=? AND ID_ASIGNATURA=?",
                    u.getNota(), u.getId_alumno(), u.getId_asignatura());
            if (filas == 0) {
                addNota(u);
            }

        } catch (Exception ex) {
            Logger.getLogger(NotasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
    }

    // insert DBUTILS SI
    public Nota addNota(Nota u) throws SQLException {

        Connection con = null;

        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            con.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<Nota> h = new BeanHandler<>(Nota.class);
            qr.insert(con, "INSERT INTO NOTAS(ID_ALUMNO,ID_ASIGNATURA,NOTA) VALUES(?,?,?)", h,
                    u.getId_alumno(), u.getId_asignatura(), u.getNota());
            con.commit();

        } catch (SQLException | NamingException ex) {

            Logger.getLogger(NotasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return u;

    }
    //DEL SI

    public void delNota(Nota u) {

        Connection con = null;
        int filas;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();

            filas = qr.update(con,
                    "DELETE FROM NOTAS WHERE ID_ALUMNO=? AND ID_ASIGNATURA=?",
                    u.getId_alumno(), u.getId_asignatura());

        } catch (SQLIntegrityConstraintViolationException ex) {
            Logger.getLogger(NotasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(NotasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
    }

}

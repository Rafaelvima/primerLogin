/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Alumno;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class AlumnosDAO
{
//Select JDBC spring
   public List<Alumno> getAllAlumnosJDBCTemplate() {
     
        JdbcTemplate jtm = new JdbcTemplate(
          DBConnection.getInstance().getDataSource());
        List<Alumno> alumnos = jtm.query("Select * from ALUMNOS",
          new BeanPropertyRowMapper(Alumno.class));
        return alumnos;
    }
   public Alumno getUserById(int id) {
        Alumno alumno = null;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getDataSourceFromServer().getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<Alumno> h
              = new BeanHandler<>(Alumno.class);
            alumno = qr.query(con, "select * FROM ALUMNOS where ID = ?",
              h, id);
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return alumno;
    }

     public Alumno addUserJDBCTemplate(Alumno a) {
 
       
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
          DBConnection.getInstance().getDataSource()).withTableName("ALUMNOS").usingGeneratedKeyColumns("ID");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("NOMBRE", a.getNombre());
        parameters.put("FECHA_NACIMIENTO", a.getFecha_nacimiento());
        parameters.put("MAYOR_EDAD", a.getMayor_edad());
        a.setId(jdbcInsert.executeAndReturnKey(parameters).longValue());
        return a;
    }
     
    public int delUser(Alumno u) throws Exception
    {
        Connection con = DBConnection.getInstance().getConnection();
        int filas;
        try
        {
            con = DBConnection.getInstance().getConnection();
            QueryRunner qr = new QueryRunner();

            filas = qr.update(con,
                    "DELETE FROM ALUMNOS WHERE ID=?",
                    u.getId());

        } catch (Exception ex)
        {filas=0;
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally
        {
            
            DBConnection.getInstance().cerrarConexion(con);
        }
        return filas;
    }

    public int updateUser(Alumno u) throws Exception
    {
        Connection con = DBConnection.getInstance().getConnection();
        int filas;
        QueryRunner qr = new QueryRunner();
        try
        {
            con = DBConnection.getInstance().getConnection();
            filas = qr.update(con,
                    "UPDATE ALUMNOS SET NOMBRE=?,FECHA_NACIMIENTO=?,MAYOR_EDAD=? WHERE ID=?",
                    u.getNombre(),u.getFecha_nacimiento(),u.getMayor_edad(),u.getId());

        } catch (Exception ex)
        {filas=0;
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally
        {
            
            DBConnection.getInstance().cerrarConexion(con);
        }
        return filas;
    }

}

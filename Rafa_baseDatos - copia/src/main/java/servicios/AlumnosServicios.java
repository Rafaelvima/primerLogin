/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.AlumnosDAO;
import java.util.List;
import model.Alumno;

/**
 *
 * @author rafa
 */
public class AlumnosServicios {

    public List<Alumno> getAllAlumnos() {
        AlumnosDAO dao = new AlumnosDAO();

        return dao.getAllAlumnosJDBCTemplate();
    }

    public Alumno getAlumnoById(int id) {
        AlumnosDAO dao = new AlumnosDAO();

        return dao.getUserById(id);

    }

    public Alumno addAlumno(Alumno alumnoNuevo) {
        AlumnosDAO dao = new AlumnosDAO();

        return dao.addUserJDBCTemplate(alumnoNuevo);
    }

    public int delAlumno(Alumno alumnoNuevo) throws Exception {
        AlumnosDAO dao = new AlumnosDAO();

        return dao.delUser(alumnoNuevo);
    }

    public int updateUser(Alumno alumnoNuevo) throws Exception {
        AlumnosDAO dao = new AlumnosDAO();

        return dao.updateUser(alumnoNuevo);
    }

}

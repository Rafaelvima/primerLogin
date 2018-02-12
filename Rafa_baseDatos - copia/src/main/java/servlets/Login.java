/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.UsersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.Configuration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import servicios.MandarMail;
import servicios.UserServicios;
import utils.*;

/**
 *
 * @author user
 */
@WebServlet(name = "Login", urlPatterns
        = {
            "/login"
        })
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //registrar, activar, logear.
        User u = new User();
        LocalDateTime ahora;
        LocalDateTime despues;
        UserServicios us = new UserServicios();
        String codigoRandom;
        String op = request.getParameter("op");
        String password;
        String nombre;
        if (op == null) {
            op = "";
        }
        switch (op) {
            case "registrar":
                nombre = request.getParameter("nombre");
                password = request.getParameter("pass");
                String email = request.getParameter("correo");
                //comprobar si existe ese usuario
                String existe = us.BuscarNombre(nombre);
                if (existe == null) {

                    String hash = PasswordHash.getInstance().createHash(password);
                    codigoRandom = utils.Utils.randomAlphaNumeric(10);
                    ahora = LocalDateTime.now();
                    u.setNombre(nombre);
                    u.setPassword(hash);
                    u.setActivo(0);
                    u.setEmail(email);
                    u.setCodigo_activacion(codigoRandom);
                    u.setFecha_activacion(ahora);
                    us.addUser(u);
                    MandarMail mail = new MandarMail();
                    mail.mandarMail("rafaelvima@gmail.com", "el codigo de activacion es " + codigoRandom, "importante");
                    request.setAttribute("prueba", "usuario creado pero tienes que activarlo");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);

                } else {
                    request.setAttribute("prueba", "no puedes crear, ya existe ");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);

                }
                break;

            case "activar":
                String cod_activo = request.getParameter("cod_activo");
                User activando = us.ActivarUser(cod_activo);
                if (activando != null) {
                    if (activando.getActivo() == 0) {
                        despues = LocalDateTime.now().minusMinutes(config.Configuration.getInstance().getMaxTiempo());
                        if (activando.getFecha_activacion().isAfter(despues)) {
                            us.ActivacionUser(cod_activo);
                            request.setAttribute("activar", "activo bien ya puedes iniciar sesion");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);

                        } else {
                            //tarde para activar
                            request.setAttribute("activar", "tarde para activar");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);

                        }
                    } else {
                        //no existe el usuario
                        request.setAttribute("activar", "ya estaba ativado");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);

                    }
                } else {
                    //usuario ya esta activado
                    request.setAttribute("activar", "no existe usuario para ese codigo");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);

                }
                break;
            case "entrar":
                //validate
                nombre = request.getParameter("nombre");
                password = request.getParameter("pass");
                if (!"".equals(nombre) && !"".equals(password)) {
                    User entrando = us.LoginUser(nombre);

                    try {
                        boolean pasas = PasswordHash.getInstance().validatePassword(password, entrando.getPassword());
                        if (pasas) {
                            //login ok {
                            if (entrando.getActivo() == 1) {
                                request.setAttribute("logeo", "login ok");
                                request.getSession().setAttribute("LOGIN", "OK");
                                request.getRequestDispatcher("/OK.jsp").forward(request, response);
                            } else {
                                //pero no esta activo
                                request.setAttribute("logeo", "login ok pero no esta activo");
                                request.getRequestDispatcher("/login.jsp").forward(request, response);
                            }

                        } else {
                            request.setAttribute("logeo", "error al hacer login");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);
                            //no puedes hacer login
                        }
                    } catch (IOException | ServletException e) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    request.setAttribute("logeo", "falta algun campo del logeo");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
                break;
            case "cierra":
                request.setAttribute("logeo", "cerrada la sesion");
                request.getSession().setAttribute("LOGIN", "no");
            default:
                request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

package com.revature.cityRecords;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

class Address {
    private int houseNumber;
    private String streetName;
    public Address(int houseNumber, String streetName) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }
    public Address() {
    }
    public int getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    @Override
    public String toString() {
        return "Address [houseNumber=" + houseNumber + ", streetName=" + streetName + "]";
    }
    
}

public class PotHolesApp {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;INIT=runscript from 'classpath:schema.sql'";
        Connection connection = DriverManager.getConnection(url, "sa", "");
        

        HttpServlet potHolesServlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                List<Address> potHolesList = new ArrayList<>();
                try {
                    ResultSet rs = connection.prepareStatement("select * from Address").executeQuery();
                    while (rs.next()){
                potHolesList.add(new Address(rs.getInt("HouseNumber"), rs.getString("StreetName")));//potHolesList.add(rs.getString("StreetName"));//inside the getString it is the column name of the table in schema.sql
      }
            } catch (SQLException e) {
                System.err.println("Failed to retreive from DB: " + e.getSQLState());;
            }
                ObjectMapper mapper = new ObjectMapper();
                String results = mapper.writeValueAsString(potHolesList);
                resp.setContentType("application/json");
                resp.getWriter().println(results);
            }
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                ObjectMapper mapper = new ObjectMapper();
                Address newAddress = mapper.readValue(req.getInputStream(), Address.class);
                try {
                    PreparedStatement stmt = connection.prepareStatement("insert into 'address' values (?,?)");
                    stmt.setInt(1, newAddress.getHouseNumber());
                    stmt.setString(2, newAddress.getStreetName());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Failed to insert the values: " + e.getMessage());
                }

            }
        };

        

        Tomcat server = new Tomcat();
        server.getConnector();
        server.addContext("",null);
        server.addServlet("", "defaultServlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String filename = req.getPathInfo();
                String resourceDir = "static";
                InputStream file = getClass().getClassLoader().getResourceAsStream(resourceDir + filename);
                String mimeType = getServletContext().getMimeType(filename);
                resp.setContentType(mimeType);
                IOUtils.copy(file,resp.getOutputStream());
            }
        }).addMapping("/*");
        server.addServlet("","potHolesServlet", potHolesServlet).addMapping("/potHolesList");
        try {
            server.start();
        } catch (LifecycleException e) {
            System.err.println("failed to start server"+ e.getMessage());
        }

    }
}

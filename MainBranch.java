/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mr. Mittal
 */
public class MainBranch {

    public void startProject() {
        Scanner sc = new Scanner(System.in);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
        boolean done=true;
        AccountCreate ac = new AccountCreate();
        while(done){
        System.out.println("Select an oprtion\n1.Create an account\n2.Login Account");
        int in = sc.nextInt();
        switch(in) {
            case 1 -> ac.main();
            case 2 -> {
                System.out.println("enter your email: ");
                String email = sc.next();
                System.out.println("enter your password: ");
                String pass = sc.next();

                String find = "SELECT * from accountdetails where email=? and password=?";
                PreparedStatement pst = con.prepareStatement(find);
                pst.setString(1, email);
                pst.setString(2, pass);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                      new Login(rs.getInt("id"));
                } else {
                    System.out.println("Id & Password Invalid");
                }
            }
            case 3->
            done=false;
        
            }
        }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("DATABASE ERROR = "+e.getMessage());
        }
    }
}

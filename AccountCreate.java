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
class AccountCreate {

    void main() throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");

        System.out.println("Enter your name :");
        String name = sc.nextLine();

        System.out.println("Enter your Email :");
        String email = sc.next();

        String find = "SELECT * from accountdetails where email=?";
        PreparedStatement pst2 = con.prepareStatement(find);
        pst2.setString(1, email);
        ResultSet rs = pst2.executeQuery();
        if (rs.next()) {
            System.out.println("Email already exits :");
        } else {

            System.out.println("Enter your ContactNumber :");
            String cont = sc.next();
            System.out.println("Choose your Account Type :\n1.Saving\n2.Current");
            boolean done = true;
            String acc = "";
            while (done) {
                int type = sc.nextInt();
                switch (type) {
                    case 1:
                        acc = "Saving";
                        done = false;
                        break;
                    case 2:
                        acc = "Current";
                        done = false;
                        break;
                    default:
                        System.out.println("enter again");
                        break;
                }
            }
            System.out.println("Enter your Password :");
            String pass = sc.next();
            String qry = "INSERT into accountdetails(name,contact,email,password,accountType) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, name);
            pst.setString(2, cont);
            pst.setString(3, email);
            pst.setString(4, pass);
            pst.setString(5, acc);
            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Details Updated");
            } else {
                System.out.println("Error Occur");
            }
        }
    }
}

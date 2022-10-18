/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;

import com.mysql.cj.result.LocalDateTimeValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Mr. Mittal
 */
class Login {

    LocalDateTime myobj = LocalDateTime.now();
    DateTimeFormatter mydate = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
    String date = myobj.format(mydate);
    int user_id;
    Scanner scan = new Scanner(System.in);

    Login(int b) {
        user_id = b;
        System.out.println("Select an option\n1.deposite\n2.widthdraw\n3.Tranfer\n4.passbook\n5.change password\n6.Logout");
        int a = scan.nextInt();
        switch (a) {
            case 1:
                diposit();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                trans();
                break;
            case 4:
                passbook();
                break;
            case 5:
                change();
                break;
            case 6:
                log();
                break;
        }

    }

    void diposit() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            String getBalance = "SELECT balance FROM accountdetails WHERE id='" + user_id + "'";
            PreparedStatement pst = con.prepareStatement(getBalance);
            ResultSet rs = pst.executeQuery();
            this.date = date;
            int b = this.user_id;
            if (rs.next()) {
                float accountBal = rs.getFloat("balance");
                System.out.print("Enter Amount to be Deposite = ");
                float new_bal = scan.nextFloat();

                accountBal = new_bal + accountBal;

                PreparedStatement pst_newbal = con.prepareStatement("UPDATE accountdetails SET balance='" + accountBal + "' WHERE id='" + user_id + "'");
                int nb = pst_newbal.executeUpdate();
                if (nb == 1) {
                    String qry = "INSERT into passbook(dateTime,transactionType,deposite,withdraw,balance,UserId) values(?,?,?,?,?,?)";
                    PreparedStatement pst2 = con.prepareStatement(qry);
                    pst2.setString(1, date);
                    pst2.setString(2, "deposite");
                    pst2.setFloat(3, new_bal);
                    pst2.setFloat(4, (float) 00.0);
                    pst2.setFloat(5, accountBal);
                    pst2.setInt(6, b);
                    int i = pst2.executeUpdate();
                    if (i == 1) {
                        System.out.println("Money deposite");
                    } else {
                        System.err.println("transaction error");
                    }

                } else {
                    System.err.println("BALANCE CANNOT BE DEIPOS");
                }
            } else {
                System.out.println("Invalid user");
            }
        } catch (Exception e) {
            System.err.println("DATABSE ON DIPOSTE ERROR => " + e.getMessage());
        }
    }

    void withdraw() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            String getBalance = "SELECT balance FROM accountdetails WHERE id='" + user_id + "'";
            PreparedStatement pst = con.prepareStatement(getBalance);
            ResultSet rs = pst.executeQuery();
            this.date = date;
            int b = this.user_id;
            if (rs.next()) {
                float accbal = rs.getFloat("balance");
                System.out.println("enter amount to withdraw: ");
                float wa = scan.nextFloat();
                if (accbal > wa) {
                    accbal = accbal - wa;
                } else {
                    System.err.println("enough amount");
                }
                PreparedStatement pst_new = con.prepareStatement("UPDATE accountdetails SET balance='" + accbal + "' WHERE id='" + user_id + "'");
                int nb = pst_new.executeUpdate();
                if (nb == 1) {
                    String qry = "INSERT into passbook(dateTime,transactionType,deposite,withdraw,balance,UserId) values(?,?,?,?,?,?)";
                    PreparedStatement pst2 = con.prepareStatement(qry);
                    pst2.setString(1, date);
                    pst2.setString(2, "withdraw");
                    pst2.setFloat(3, (float) 0.0);
                    pst2.setFloat(4, wa);
                    pst2.setFloat(5, accbal);
                    pst2.setInt(6, b);
                    int i = pst2.executeUpdate();
                    if (i == 1) {
                        System.out.println("Money Withraw");
                    } else {
                        System.err.println("transaction error");
                    }

                } else {
                    System.err.println("BALANCE CANNOT BE withdraw");
                }
            } else {
                System.out.println("Invalid user");

            }

        } catch (Exception e) {
            System.err.println("Database on withdraw error => " + e.getMessage());
        }
    }

    void trans() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            System.out.println("enter email-Id: ");
            String email = scan.next();
            String mail = "SELECT * FROM accountdetails WHERE email=?";
            PreparedStatement pst1 = con.prepareStatement(mail);
            pst1.setString(1, email);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                int id = rs1.getInt("id");
                float bal = rs1.getFloat("balance");
                String getBalance = "SELECT balance FROM accountdetails WHERE id='" + user_id + "'";
                PreparedStatement pst = con.prepareStatement(getBalance);
                ResultSet rs = pst.executeQuery();
//                System.out.println("enter money: ");
                this.date = date;
                int b = this.user_id;
                if (rs.next()) {
                    float accbal = rs.getFloat("balance");
                    System.out.println("enter amount to transfer: ");
                    float wa = scan.nextFloat();
                    if (accbal > wa) {
                        accbal = accbal - wa;
                        bal = bal + wa;
                    } else {
                        System.err.println("enough amount");
                    }
                    PreparedStatement pst_new = con.prepareStatement("UPDATE accountdetails SET balance='" + accbal + "' WHERE id='" + user_id + "'");
                    PreparedStatement pst_new2 = con.prepareStatement("UPDATE accountdetails SET balance='" + bal + "' WHERE id='" + id + "'");
                    int nb2 = pst_new2.executeUpdate();
                    int nb = pst_new.executeUpdate();
                    if (nb == 1) {
                        String qry = "INSERT into passbook(dateTime,transactionType,deposite,withdraw,balance,UserId) values(?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(qry);
                        pst2.setString(1, date);
                        pst2.setString(2, "withdraw");
                        pst2.setFloat(3, (float) 0.0);
                        pst2.setFloat(4, wa);
                        pst2.setFloat(5, accbal);
                        pst2.setInt(6, b);
                        int i = pst2.executeUpdate();
                    }
                    if (nb2 == 1) {
                        String qry = "INSERT into passbook(dateTime,transactionType,deposite,withdraw,balance,UserId) values(?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(qry);
                        pst2.setString(1, date);
                        pst2.setString(2, "diposite");
                        pst2.setFloat(3, wa);
                        pst2.setFloat(4, (float) 0.0);
                        pst2.setFloat(5, bal);
                        pst2.setInt(6, id);
                        int i = pst2.executeUpdate();
                    }
                    System.out.println("Transaction successful");
                }
            } else {
                System.out.println("email invalid");
            }

        } catch (Exception e) {
            System.out.println("DataBase Error:" + e.getMessage());
        }
    }

    void passbook() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            String getBalance = "SELECT * FROM passbook WHERE UserId='" + user_id + "'";
            PreparedStatement pst = con.prepareStatement(getBalance);
            ResultSet rs = pst.executeQuery();
            System.out.println("Date-Time\t\tTransactonType\tdeposite\twithdraw\tbalance\tuserid");
            while (rs.next()) {
                System.out.print(rs.getString("dateTime"));
                System.out.print("\t" + rs.getString("transactionType"));
                System.out.print("\t" + rs.getFloat("deposite"));
                System.out.print("\t\t" + rs.getFloat("withdraw"));
                System.out.print("\t\t" + rs.getFloat("balance"));
                System.out.print("\t" + rs.getInt("UserId"));
                System.out.println();

            }

        } catch (Exception e) {
            System.err.println("DataBase on withdraw error => " + e.getMessage());
        }

    }

    void change() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            String chng = "SELECT password FROM accountdetails WHERE id='" + user_id + "'";
            System.out.println("Enter New Password");
            String pass = scan.next();
            PreparedStatement pst = con.prepareStatement("UPDATE accountdetails SET password='" + pass + "' WHERE id='" + user_id + "'");
            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Password Update");
            } else {
                System.out.println("Something error");
            }
        } catch (Exception e) {
            System.out.println("DataBase Error: " + e.getMessage());
        }
    }

    void log() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "");
            String chng = "SELECT password FROM accountdetails WHERE id='" + user_id + "'";

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

}

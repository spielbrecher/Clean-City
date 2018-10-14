/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleancity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author andyjagello
 */


public class ParticipateFrame extends JDialog{
    JList whatToDo;
    JScrollPane scrollPane;
    JButton btnAdd;
    JPanel panel;
    JTextField tfSum;
    
    int ID;

    private static final String url = "jdbc:mysql://localhost:3306/mydb2?verifyServerCertificate=FALSE&useSSL=TRUE&requireSSL=TRUE";
    private static final String user = "root";
    private static final String passDB = "root1234567890";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    
    public ParticipateFrame(int ID){
        this.ID=ID;
        setTitle("Решить проблему");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String query = "select shortdescription, maxprice, idproblem from problem where done=0";
        javax.swing.ListModel model = new DefaultListModel();
        java.util.List<String> rows = new java.util.ArrayList<>();
            
        System.out.println(query);
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, passDB);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            rs = stmt.executeQuery(query);
            
            rs.first();            
            if(!rs.isBeforeFirst())
                while(!rs.isAfterLast()){
                    String descr = rs.getString("shortdescription");
                    int maxPrice = rs.getInt("maxPrice");
                    int idproblem = rs.getInt("idproblem");
                    String rtl = descr+" "+maxPrice+" "+idproblem;
                    rows.add(rtl);
                    rs.next();
                } 
            
            }catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            finally {

            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        whatToDo = new JList(rows.toArray());
        scrollPane = new JScrollPane(whatToDo);

        tfSum = new JTextField(10);

        btnAdd = new JButton("Ok");


        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel pControls = new JPanel();
        pControls.add(new JLabel("Сумма"));
        pControls.add(tfSum);
        pControls.add(btnAdd);
        panel.add(pControls, BorderLayout.SOUTH);
        add(panel);
        setSize(640,480);
        //pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);

        btnAdd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int n = whatToDo.getSelectedIndex();
                if(n!=-1){
                    try {
                        // opening database connection to MySQL server
                        con = DriverManager.getConnection(url, user, passDB);
                        // getting Statement object to execute query
                        stmt = con.createStatement();
                        // executing SELECT query
                        rs = stmt.executeQuery(query);

                        rs.first();            
                        if(!rs.isBeforeFirst()){
                            for(int i=1; i<n+1; i++){
                                rs.next();
                            } 
                            int id = rs.getInt("idproblem");
                            String query = "insert into worker (person_idperson, problem_idproblem, sum) values ("+ID+", "+id+" ,"+tfSum.getText()+")";
                            stmt.execute(query);
                            setVisible(false);
                        }
                        

                        }catch (SQLException sqlEx) {
                            sqlEx.printStackTrace();
                        }
                        finally {

                        //close connection ,stmt and resultset here
                        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                    }

                }
            }
        });
            
    }
}

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
import javax.swing.table.*;
/**
 *
 * @author andyjagello
 */
public class MainFrame extends javax.swing.JFrame {
    
    DefaultTableModel model1;
    DefaultTableModel model2;
    
    JTable table1;
    JScrollPane scrollPane1;
    JTable table2;
    JScrollPane scrollPane2;
    
    JButton btnAddOrder;
    JButton btnParticipate;
    
    int ID;

    private static final String url = "jdbc:mysql://localhost:3306/mydb2?verifyServerCertificate=FALSE&useSSL=TRUE&requireSSL=TRUE";
    private static final String user = "root";
    private static final String passDB = "root1234567890";
    
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame(int ID) {
        initComponents();
        this.ID = ID;
        model1 = new DefaultTableModel();
        model2 = new DefaultTableModel();
        
        model1.addColumn("Проблема");
        model1.addColumn("Сумма");
        model2.addColumn("Проблема");
        model2.addColumn("Сумма");
        
        System.out.println("Заказать");
        String query = "select shortdescription, maxprice from problem where person_idperson ="+ID+" and done=0";

        System.out.println(query);
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, passDB);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            rs = stmt.executeQuery(query);
            try{
                rs.first();
                if(!rs.isBeforeFirst())
                    while(!rs.isAfterLast()){
                        String descr = rs.getString("shortdescription");
                        int maxPrice = rs.getInt("maxPrice");
                        Object[] row = new Object[]{descr, maxPrice};
                        model1.addRow(row);
                        rs.next();
                    } 
            }catch(Exception e){}
        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        System.out.println("Исполнить");
        query = "select problem.shortdescription, worker.sum from problem inner join worker on worker.problem_idproblem = idproblem where worker.person_idperson ="+ID+" and done=0";
            
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
                    int maxPrice = rs.getInt("sum");
                    Object[] row = new Object[]{descr, maxPrice};
                    model2.addRow(row);
    
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

        table1 = new JTable(model1);
        table2 = new JTable(model2);
        scrollPane1 = new JScrollPane(table1);
        scrollPane2 = new JScrollPane(table2);
        JPanel panel = new JPanel();
        
        btnAddOrder = new JButton("Добавить проблему");
        btnParticipate = new JButton("Решить проблему");
        
        btnAddOrder.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent ae){
                AddProblemFrame pf = new AddProblemFrame(ID);
                pf.setModal(true);
            }
        });
        
        btnParticipate.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent ae){
                ParticipateFrame pf = new ParticipateFrame(ID);
                pf.setModal(true);
                String query = "select problem.shortdescription, worker.sum from problem inner join worker on worker.problem_idproblem = idproblem where worker.person_idperson ="+ID+" and done=0";

                DefaultTableModel model3 = new DefaultTableModel();
                model3.addColumn("Проблема");
                model3.addColumn("Сумма");
                
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
                            int maxPrice = rs.getInt("sum");
                            Object[] row = new Object[]{descr, maxPrice};
                            model3.addRow(row);

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


                table2.setModel(model3);

            }
        });
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddOrder);
        btnPanel.add(btnParticipate);
        
        panel.setLayout(new java.awt.GridLayout(5,1));
        panel.add(new JLabel("Проблемы"));
        panel.add(scrollPane1);
        panel.add(new JLabel("Проблемы, которые я готов решить"));
        panel.add(scrollPane2);
        panel.add(btnPanel);
        setLayout(new java.awt.BorderLayout());
        add(panel, java.awt.BorderLayout.CENTER);
        
        setTitle("Чистый город");
        setDefaultCloseOperation(javax.swing.JFrame.HIDE_ON_CLOSE);
        setSize(640,480);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

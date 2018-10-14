package cleancity;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class CleanCity extends JFrame{   
    
    JLabel lbName;
    JTextField tfName;
    JLabel lbPassword;
    JPasswordField tfPassword;
    
    JCheckBox cbNewUser;
    
    JButton btnLogin;
    
    String userName;
    String password;
    
    int ID;
    
    private static final String url = "jdbc:mysql://localhost:3306/mydb2?verifyServerCertificate=FALSE&useSSL=TRUE&requireSSL=TRUE";
    private static final String user = "root";
    private static final String passDB = "root1234567890";
    
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    
    public CleanCity(){
        lbName = new JLabel("Имя:");
        tfName = new JTextField(25);
        lbPassword = new JLabel("Пароль");
        tfPassword = new javax.swing.JPasswordField(25);
        tfPassword.setEchoChar('*');
        
        cbNewUser = new JCheckBox("Я новый пользователь");
        
        btnLogin = new JButton("Ok");
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(6,1));
        
        panel.add(lbName);
        panel.add(tfName);
        panel.add(lbPassword);
        panel.add(tfPassword);
        panel.add(cbNewUser);
        panel.add(btnLogin);
        
        add(panel);
        this.setTitle("Чистый город");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        btnLogin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(cbNewUser.isSelected()){
                    // create new user
                    userName = tfName.getText();
                    password = String.valueOf(tfPassword.getPassword());
                    createUser(userName, password);
                    loginUser(userName, password);

                }else{
                    userName = tfName.getText();
                    password = String.valueOf(tfPassword.getPassword());
                    loginUser(userName, password);
                    
                }
            }
        
        });
    }
    
    public void createUser(String userName, String password){
        String query = "insert into person (name, password) values (\""
                +userName+" \",\""+password+"\")";
        //System.out.println(query);
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, passDB);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            boolean ok = stmt.execute(query);
            System.out.println(ok);

        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {

            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }        
    
    
    public void loginUser(String userName, String password){
        String query = "select idperson from person where name =\""+userName+"\" and password= \""+password+"\"";
        System.out.println(query);
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, passDB);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            rs = stmt.executeQuery(query);
            rs.first();
            if(rs.isBeforeFirst() || rs.getInt("idperson")==0){
                JOptionPane.showMessageDialog(this, "Пароль неверное");
            } else{
                ID = rs.getInt("idperson");
                System.out.println(ID);
            }
            MainFrame mf = new MainFrame(ID);

        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {

            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new CleanCity();
            }
        });
    }
    
}

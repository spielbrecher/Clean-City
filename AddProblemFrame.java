/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleancity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author andyjagello
 */
public class AddProblemFrame extends JDialog{
    int ID;
    JTextField tfProblemDescr;
    JTextField tfSum;
    JButton btnOk;
    
    public AddProblemFrame(int ID){
        this.ID=ID;
        tfProblemDescr = new JTextField(45);
        tfSum = new JTextField(10);
        btnOk = new JButton("Ok");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        
        panel.add(new JLabel("Проблема"));
        panel.add(tfProblemDescr);
        panel.add(new JLabel("Сумма"));
        panel.add(tfSum);
        panel.add(btnOk);
        setLayout(new BorderLayout());
        
        add(panel, BorderLayout.CENTER);       
        setSize(320,240);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }
    
}

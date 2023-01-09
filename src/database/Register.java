package database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Reader;
import java.sql.*;

public class Register {
    public JPanel Panel;
    private JLabel label1;
    private JTextField txtusername;
    private JPasswordField txtpass;
    private JButton REGISTRIRAJSEButton;
    private JButton nazajNaLOGINButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("REGISTRACIJA");
        frame.setContentPane(new Register().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://mouse.db.elephantsql.com:5432/vyrnewjo", "vyrnewjo", "VwnnjMoNmq70-Jem728CuQ7fz8ux5rTI");
            System.out.println("Povezava na bazo uspešna!");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Register() {
        connect();
        nazajNaLOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().Panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                Panel.setVisible(false);
                try {
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        REGISTRIRAJSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        String username_u, pass_u;
                        username_u = txtusername.getText();
                        pass_u = txtpass.getText();

                        try {
                            pst = con.prepareStatement("SELECT insert_uporabniki(?, ?);");
                            pst.setString(1, username_u);
                            pst.setString(2, pass_u);

                            pst.executeQuery();
                            JOptionPane.showMessageDialog(null, "USPEŠNO STE SE REGISTRIRALI!");
                            txtusername.setText("");
                            txtpass.setText("");
                            JFrame frame = new JFrame("LOGIN");
                            frame.setContentPane(new Login().Panel);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.pack();
                            frame.setVisible(true);

                        } catch (SQLException e1) {

                            JOptionPane.showMessageDialog(null, "REGISTRACIJA NEUSPEŠNA!");

                        }
                    }
        });
    }
}

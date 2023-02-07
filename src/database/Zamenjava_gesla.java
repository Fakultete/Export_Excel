package database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zamenjava_gesla {
    private JPasswordField txtstaro;
    private JPasswordField txtnovo;
    private JButton OKButton;
    public JPanel panel;
    private JTextField txtusername;
    private JButton NAZAJButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ZAMENJAVA GESLA");
        frame.setContentPane(new Zamenjava_gesla().panel);
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
public Zamenjava_gesla() {
        connect();
    OKButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String uporabnik, staro_u, novo_u;
            uporabnik = txtusername.getText();
            staro_u = txtstaro.getText();
            novo_u = txtnovo.getText();

            if(uporabnik.isEmpty() || staro_u.isEmpty() || novo_u.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Prosim vnesite podatke v vsa polja!!");
                return;
            }
            try {
                pst = con.prepareStatement("UPDATE uporabniki SET geslo=? WHERE username=? AND geslo=?;");
                pst.setString(1, novo_u);
                pst.setString(2, uporabnik);
                pst.setString(3, staro_u);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "USPEŠNO POSODOBLJENO GESLO!");
                txtusername.setText("");
                txtstaro.setText("");
                txtnovo.setText("");

                JFrame frame = new JFrame("PRIJAVA");
                frame.setContentPane(new Login().Panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                panel.setVisible(false);
                try {
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "POSKUSITE ZNOVA!");
            }
        }
    });
    NAZAJButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*JFrame frame = new JFrame("Login");
            frame.setContentPane(new Login().Panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            panel.setVisible(false);
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }*/
            

        }
    });
}
}

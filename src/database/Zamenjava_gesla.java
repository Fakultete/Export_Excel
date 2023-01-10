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
            String staro_u, novo_u;
            staro_u = txtstaro.getText();
            novo_u = txtnovo.getText();

            try {
                pst = con.prepareStatement("SELECT update_geslo(?, ?);");
                pst.setString(1, staro_u);
                pst.setString(2, novo_u);

                pst.executeQuery();
                JOptionPane.showMessageDialog(null, "USPEŠNO POSODOBLJENO GESLO!");
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
            }
        }
    });
}
}

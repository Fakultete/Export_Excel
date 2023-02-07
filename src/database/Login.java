package database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login {
    private JTextField txtusername;
    private JButton PRIJAVAButton;
    private JButton REGISTRIRAJSEButton;
    private JLabel label1;
    private JPasswordField txtpass;
    public JPanel Panel;
    private JButton ZAMENJNJAJGESLOButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("PRIJAVA");
        frame.setContentPane(new Login().Panel);
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
    public Login() {
        connect();
        REGISTRIRAJSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("REGISTRACIJA");
                frame.setContentPane(new Register().Panel);
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
        PRIJAVAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username_u, pass_u;
                username_u = txtusername.getText();
                pass_u = txtpass.getText();

                if(username_u.isEmpty() || pass_u.isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Prosim vnesite podatke v vsa polja!!");
                    return;
                }

                try {
                    pst = con.prepareStatement("SELECT select_user(?, ?);");
                    pst.setString(1, username_u);
                    pst.setString(2, pass_u);

                    pst.executeQuery();

                    JOptionPane.showMessageDialog(null, "USPEŠNO STE SE PRIJAVILI!");
                    txtusername.setText("");
                    txtpass.setText("");

                    if(username_u.equals("admin") && pass_u.equals("pass"))
                    {
                            JFrame frame = new JFrame("FAKULTETE");
                            frame.setContentPane(new Fakultete().Form);
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
                    else {
                        JFrame frame = new JFrame("Fakultete v Sloveniji");
                        frame.setContentPane(new Uporabnik().Form);
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

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "POSKUSITE ZNOVA!");
                }
            }
        });
        ZAMENJNJAJGESLOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("ZAMENJAVA GESLA");
                frame.setContentPane(new Zamenjava_gesla().panel);
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
    }
}

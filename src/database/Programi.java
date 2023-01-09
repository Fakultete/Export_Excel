package database;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Programi {
    public JPanel Form;
    private JLabel label;
    private JTable programi;
    private JButton ISCIButton;
    private JTextField txtime;
    private JTextField txtopis;
    private JComboBox combostopnja;
    private JComboBox combofakulteta;
    private JButton OSVEZIButton;
    private JButton VNESIButton;
    private JButton POSODOBIButton;
    private JButton IZBRISIButton;
    private JButton NAZAJNAFAKULTETEButton;
    private JComboBox iscifakulteta;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fakultete");
        frame.setContentPane(new Programi().Form);
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
    void table_load() {
        try {
            pst = con.prepareStatement("SELECT p.id, p.ime, s.naziv, f.ime FROM programi p INNER JOIN stopnje s ON s.id=p.stopnja_id INNER JOIN fakultete f ON f.id=p.fakulteta_id ORDER BY p.id;");
            ResultSet rs = pst.executeQuery();
            programi.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Programi() {
        connect();
        table_load();
        /**
         * INSERT
         */
        VNESIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ime_p, opis_p, stopnja_p, fakulteta_p;
                ime_p = txtime.getText();
                opis_p = txtopis.getText();
                stopnja_p = combostopnja.getSelectedItem().toString();
                fakulteta_p = combofakulteta.getSelectedItem().toString();

                try {
                    pst = con.prepareStatement("SELECT insert_programi(?, ?, ?, ?);");
                    pst.setString(1, ime_p);
                    pst.setString(2, opis_p);
                    pst.setString(3, stopnja_p);
                    pst.setString(4, fakulteta_p);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "PROGRAM VNEŠEN!");
                    table_load();
                    txtime.setText("");
                    txtopis.setText("");
                    combostopnja.setSelectedItem("");
                    combofakulteta.setSelectedItem("");

                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        NAZAJNAFAKULTETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Fakultete");
                frame.setContentPane(new Fakultete().Form);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                Form.setVisible(false);
                try {
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}


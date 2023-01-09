package database;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JButton ODJAVAButton;

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
            pst = con.prepareStatement("SELECT p.id, p.ime, p.opis, s.naziv, f.ime FROM programi p INNER JOIN stopnje s ON s.id=p.stopnja_id INNER JOIN fakultete f ON f.id=p.fakulteta_id ORDER BY p.id;");
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

                    pst.executeQuery();
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
        //OB KLIKU V TABELI NA VRSTICO
        programi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel RecordTable = (DefaultTableModel)programi.getModel();
                int SelectedRows = programi.getSelectedRow();

                txtime.setText(RecordTable.getValueAt(SelectedRows,1).toString());
                txtopis.setText(RecordTable.getValueAt(SelectedRows,2).toString());
                combostopnja.setSelectedItem(RecordTable.getValueAt(SelectedRows,3).toString());
                combofakulteta.setSelectedItem(RecordTable.getValueAt(SelectedRows,4).toString());
            }
        });
        /**
         * UPDATE
         */
        POSODOBIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel RecordTable = (DefaultTableModel)programi.getModel();
                int SelectedRows = programi.getSelectedRow();
                int auto_id=Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());

                String ime_p, opis_p, stopnja_p, fakulteta_p;
                ime_p = txtime.getText();
                opis_p = txtopis.getText();
                stopnja_p = combostopnja.getSelectedItem().toString();
                fakulteta_p = combofakulteta.getSelectedItem().toString();

                try {
                    pst = con.prepareStatement("SELECT update_programi(?, ?, ?, ?, ?);");
                    pst.setInt(1, auto_id);
                    pst.setString(2, ime_p);
                    pst.setString(3, opis_p);
                    pst.setString(4, stopnja_p);
                    pst.setString(5, fakulteta_p);

                    pst.executeQuery();
                    JOptionPane.showMessageDialog(null, "USPEŠNO POSODOBLJENO!");
                    table_load();
                    txtime.setText("");
                    txtopis.setText("");
                    combostopnja.setSelectedItem("");
                    combofakulteta.setSelectedItem("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        /**
         * DELETE
         */
        IZBRISIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel RecordTable = (DefaultTableModel)programi.getModel();
                int SelectedRows = programi.getSelectedRow();
                int auto_id=Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());

                try {
                    pst = con.prepareStatement("SELECT delete_programi(?)");
                    pst.setInt(1, auto_id);

                    JFrame frame = new JFrame("PRIJAVA");
                    frame.setContentPane(new Login().Panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    Form.setVisible(false);
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }                    JOptionPane.showMessageDialog(null, "USPEŠNO STE IZBRISALI PROGRAM!");
                    table_load();
                    txtime.setText("");
                    txtopis.setText("");
                    combostopnja.setSelectedItem("");
                    combofakulteta.setSelectedItem("");
                }
                catch (SQLException e1) {
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
        combostopnja.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    pst = con.prepareStatement("SELECT DISTINCT s.naziv FROM stopnje s LEFT OUTER JOIN programi p ON s.id=p.stopnja_id;");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try{
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) {
                        String abc = rs.getString(1);
                        combostopnja.addItem(abc);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        combofakulteta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    pst = con.prepareStatement("SELECT DISTINCT ime FROM fakultete;");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try{
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) {
                        String abc = rs.getString(1);
                        combofakulteta.addItem(abc);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        OSVEZIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load();
            }
        });
        iscifakulteta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    pst = con.prepareStatement("SELECT DISTINCT ime FROM fakultete;");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try{
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) {
                        String abc = rs.getString(1);
                        iscifakulteta.addItem(abc);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ISCIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String r = iscifakulteta.getSelectedItem().toString();
                    pst = con.prepareStatement("SELECT p.id, p.ime, p.opis, s.naziv, f.ime FROM programi p INNER JOIN stopnje s ON s.id=p.stopnja_id INNER JOIN fakultete f ON f.id=p.fakulteta_id WHERE f.ime=? ORDER BY p.id;");
                    pst.setString(1, r);
                    ResultSet rs = pst.executeQuery();
                    programi.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException r) {
                    r.printStackTrace();
                }
            }
        });
        ODJAVAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}


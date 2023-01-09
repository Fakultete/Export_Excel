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

public class Fakultete{
    private JButton VNESIButton;
    private JButton POSODOBIButton;
    private JButton IZBRISIButton;
    private JTextField txtime;
    private JTextField txtkljucna_beseda;
    private JTextField txtopis;
    private JTextField txtkraj;
    private JPanel Form;
    private JTable fakultete;
    private JButton poglejPorgrameIzbraneFakulteteButton;
    private JComboBox kraji;
    private JButton OSVEZIButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Fakultete");
        frame.setContentPane(new Fakultete().Form);
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
            pst = con.prepareStatement("SELECT f.id, f.ime, f.kljucna_beseda, f.opis, k.ime FROM fakultete f INNER JOIN kraji k ON k.id=f.kraj_id ORDER BY f.id;");
            ResultSet rs = pst.executeQuery();
            fakultete.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Fakultete() {
        connect();
        table_load();
        /**
         * INSERT
         */
        VNESIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ime_f, kljucna_beseda_f, opis_f, kraj_f;
                ime_f = txtime.getText();
                kljucna_beseda_f = txtkljucna_beseda.getText();
                opis_f = txtopis.getText();
                kraj_f=kraji.getSelectedItem().toString();

                try {
                    pst = con.prepareStatement("SELECT insert_fakultete(?, ?, ?, ?);");
                    pst.setString(1, ime_f);
                    pst.setString(2, kljucna_beseda_f);
                    pst.setString(3, opis_f);
                    pst.setString(4, kraj_f);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "FAKULTETA VNEŠENA!");
                    table_load();
                    txtime.setText("");
                    txtkljucna_beseda.setText("");
                    txtopis.setText("");
                    kraji.setSelectedItem("");

                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        //OB KLIKU V TABELI NA VRSTICO
        fakultete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel RecordTable = (DefaultTableModel)fakultete.getModel();
                int SelectedRows = fakultete.getSelectedRow();

                txtime.setText(RecordTable.getValueAt(SelectedRows,1).toString());
                txtkljucna_beseda.setText(RecordTable.getValueAt(SelectedRows,2).toString());
                txtopis.setText(RecordTable.getValueAt(SelectedRows,3).toString());
                kraji.setSelectedItem(RecordTable.getValueAt(SelectedRows,4).toString());
            }
        });
        /**
         * UPDATE
         */
        POSODOBIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel RecordTable = (DefaultTableModel)fakultete.getModel();
                int SelectedRows = fakultete.getSelectedRow();
                int auto_id=Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());


                String ime_f, kljucna_beseda_f, opis_f, kraj_f;
                ime_f = txtime.getText();
                kljucna_beseda_f = txtkljucna_beseda.getText();
                kraj_f = kraji.getSelectedItem().toString();
                opis_f = txtopis.getText();

                try {
                    pst = con.prepareStatement("SELECT update_fakultete(?, ?, ?, ?, ?);");
                    pst.setInt(1, auto_id);
                    pst.setString(2, ime_f);
                    pst.setString(3, kljucna_beseda_f);
                    pst.setString(4, opis_f);
                    pst.setString(5, kraj_f);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "USPEŠNO POSODOBLJENO!");
                    table_load();
                    txtime.setText("");
                    txtkljucna_beseda.setText("");
                    txtopis.setText("");
                    kraji.setSelectedItem("");
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
                DefaultTableModel RecordTable = (DefaultTableModel)fakultete.getModel();
                int SelectedRows = fakultete.getSelectedRow();
                int auto_id=Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());

                try {
                    pst = con.prepareStatement("SELECT delete_fakultete(?)");
                    pst.setInt(1, auto_id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "USPEŠNO STE IZBRISALI FAKULTETO!");
                    table_load();
                    txtime.setText("");
                    txtkljucna_beseda.setText("");
                    txtopis.setText("");
                    kraji.setSelectedItem("");
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        kraji.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    pst = con.prepareStatement("SELECT k.ime FROM kraji k LEFT OUTER JOIN fakultete f on k.id = f.kraj_id ORDER BY k.ime ASC;");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try{
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) {
                        String abc = rs.getString(1);
                        kraji.addItem(abc);
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
    }
}
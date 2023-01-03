package database;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JTable extends JFrame {

    private JPanel contentPane;
    private javax.swing.JTable jt1;

    /**
     * Zagon aplikacije
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JTable frame = new JTable();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Okvir
     */

    public JTable() {

        setTitle("Fakultete v Slovenji");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 535, 305);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 64, 500, 107);
        contentPane.add(scrollPane);
        //My_test obj=new My_test();// with test data
        my_mysql obj=new my_mysql();


        String[] column= {"Ime fakultete","Ključna beseda", "Kraj"};

//jt1=new javax.swing.JTable(obj.my_test_select(),column);//test data
        jt1 = new javax.swing.JTable(obj.my_db_select(),column);
        scrollPane.setViewportView(jt1);
    }
}

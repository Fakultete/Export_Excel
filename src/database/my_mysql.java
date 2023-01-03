package database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class my_mysql {
    public  String[][] my_db_select() {

        String[][] data = new String[11][3]; // [rows][columns]

        try{
            Class.forName("org.postgresql.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:postgresql://mouse.db.elephantsql.com:5432/vyrnewjo","vyrnewjo","VwnnjMoNmq70-Jem728CuQ7fz8ux5rTI");
            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery("SELECT f.ime, f.kljucna_beseda, split_part(k.ime, ' ', 1) FROM fakultete f INNER JOIN kraji k ON k.id=f.kraj_id;");

            // Looping to store result in returning array data //
            int i=0;
            while(rs.next())  {
                for(int j=0;j<3;j++) {
                    //System.out.print(rs.getString(j+1));
                    data[i][j]=rs.getString(j+1);
                }
                //System.out.println();
                i=i+1;
            }
// Below lines to check data before returning. //
/*
 for (int x=0;x<data.length;x++) {
	 for (int y=0;(data[x]!= null && y <data[x].length);y++) {
		 System.out.print(data[x][y] + " ");
	 }
	 System.out.println();
 }
*/
            con.close();
        }catch(Exception e){ System.out.println(e);}

        return data;
    }
}

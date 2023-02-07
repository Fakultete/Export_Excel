package database;
import it.firegloves.mempoi.builder.MempoiBuilder;
import it.firegloves.mempoi.domain.MempoiReport;
import it.firegloves.mempoi.domain.MempoiSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
public class export_excel {

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://mouse.db.elephantsql.com:5432/vyrnewjo", "vyrnewjo", "VwnnjMoNmq70-Jem728CuQ7fz8ux5rTI");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * FROM select_fak();");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("lawix10");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("id");
            rowhead.createCell((short) 1).setCellValue("IME");
            rowhead.createCell((short) 2).setCellValue("KLJUČNA BESEDA");
            rowhead.createCell((short) 3).setCellValue("OPIS");
            rowhead.createCell((short) 4).setCellValue("KRAJ");
            int i = 1;
            while (rs.next()){
                HSSFRow row = sheet.createRow((short) i);
                row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("id")));
                row.createCell((short) 1).setCellValue(rs.getString("ime"));
                row.createCell((short) 2).setCellValue(rs.getString("kljucno"));
                row.createCell((short) 3).setCellValue(rs.getString("opis"));
                row.createCell((short) 4).setCellValue(rs.getString("kraj"));
                i++;
            }
            String yemi = "C:\\Users\\Zoja7\\Desktop\\Fakultete_v_Sloveniji.xls";
            FileOutputStream fileOut = new FileOutputStream(yemi);
            workbook.write(fileOut);
            fileOut.close();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}



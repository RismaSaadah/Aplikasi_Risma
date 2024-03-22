/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi_risma_syahnaz;

//import com.mysql.jdbc.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.Connection;
/**
 *
 * @author user
 */
public class Koneksi {

 
    Connection Koneksi =null;
    
    public static Connection KoneksiDB(){
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection Koneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir_syahnaz_risma","root","");
        
        return Koneksi;
    } catch (Exception e) {
       
        JOptionPane.showMessageDialog(null,e);
        return null;
    }
        
}
    
}

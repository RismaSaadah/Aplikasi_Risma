/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasi_risma_syahnaz;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import java.util.Date;
/**
 *
 * @author user
 */
public class formpenjualan extends javax.swing.JFrame {
Connection konek;
PreparedStatement pst, pst2;
ResultSet rst;
int inputstok, inputstok2, inputharga, inputjumlah, kurangistok, tambahstok;
String harga, idproduk, idprodukpenjualan, iddetail, jam, tanggal, sub_total;

    /**
     * Creates new form 
     */
    public formpenjualan() {
        initComponents();
        konek = Koneksi.KoneksiDB();
this.setLocationRelativeTo(null);
tampilJam();
autonumber();
penjumlahan();
    }

    private void simpan(){
        String tgl=txttanggal1.getText();
        String jam=txtjam.getText();
      try {
            String sql="insert into penjualan (PenjualanID,DetailID,TanggalPenjualan,JamPenjualan,TotalHarga) value (?,?,?,?,?)";
            pst=konek.prepareStatement(sql);
            pst.setString(1, txtnamabarang.getText());
            pst.setString(2, iddetail);
            pst.setString(3, tgl);
            pst.setString(4, jam);
            pst.setString(5, txttotal.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
    }
    
    private void total(){
    int total, bayar, kembali;
        total= Integer.parseInt(txtbayar.getText());
        bayar= Integer.parseInt(txttotal.getText());
        kembali=total-bayar;
        String ssub=String.valueOf(kembali);
        txtkembalian.setText(ssub);
    }
    
    public void clsr(){
    txtjumlah.setText("");
    }
    
    public void cari(){
    try {
        String sql="select * from produk where ProdukID LIKE '%"+txtnamabarang.getText()+"%'";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        tblbarang.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ JOptionPane.showMessageDialog(null, e);} 
    }
    
    public void kurangi_stok(){
    int qty;
    qty=Integer.parseInt(txtjumlah.getText());
    kurangistok=inputstok-qty;
    }
    
    private void subtotal(){
    int jumlah, sub;
         jumlah= Integer.parseInt(txtjumlah.getText());
         sub=(jumlah*inputharga);
         sub_total=String.valueOf(sub);     
    }
    
    public void tambah_stok(){
    tambahstok=inputjumlah+inputstok2;
        try {
        String update="update produk set Stok='"+tambahstok+"' where ProdukID='"+idproduk+"'";
        pst2=konek.prepareStatement(update);
        pst2.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
    }
    
    public void ambil_stock(){
    try {
    String sql="select * from produk where ProdukID='"+idproduk+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {    
    String stok=rst.getString(("Stok"));
    inputstok2= Integer.parseInt(stok);
    }
    }catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);}
    }
    
    public void penjumlahan(){
        int totalBiaya = 0;
        int subtotal;
        DefaultTableModel dataModel = (DefaultTableModel) tbltransaksi.getModel();
        int jumlah = tbltransaksi.getRowCount();
        for (int i=0; i<jumlah; i++){
        subtotal = Integer.parseInt(dataModel.getValueAt(i, 4).toString());
        totalBiaya += subtotal;
        }
        txttotal.setText(String.valueOf(totalBiaya));
    }
    
    public void autonumber(){
    try{
        String sql = "SELECT MAX(RIGHT(PenjualanID,3)) AS NO FROM penjualan";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        while (rst.next()) {
                if (rst.first() == false) {
                    txtnamabarang.setText("IDP001");
                } else {
                    rst.last();
                    int auto_id = rst.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    for (int j = 0; j < 3 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    txtnamabarang.setText("IDP" + no);
                }
            }
        rst.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
    }
    
    public void detail(){
    try {
        String Kode_detail=txtkodetransaksi.getText();
        String KD="D"+Kode_detail;
        String sql="select * from detailpenjualan where DetailID='"+KD+"'";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        tbltransaksi.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ 
           JOptionPane.showMessageDialog(null, e);} 
    }
    
    public void tampilJam(){
    Thread clock=new Thread(){
        public void run(){
            for(;;){
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
                txtjam.setText(format.format(cal.getTime()));
                txttanggal1.setText(format2.format(cal.getTime()));
                
            try { sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(formpenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      };
    clock.start();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtnamabarang = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtdiskon = new javax.swing.JTextField();
        txtbayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtkembalian = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtkodetransaksi = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbarang = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnkeluar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbltransaksi = new javax.swing.JTable();
        btntambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnamabarang1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttotal1 = new javax.swing.JTextField();
        txtjumlah1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtdiskon1 = new javax.swing.JTextField();
        txtbayar1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtkembalian1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtkodetransaksi1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbarang1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        btnkeluar1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbltransaksi1 = new javax.swing.JTable();
        btntambah1 = new javax.swing.JButton();
        txtjam = new javax.swing.JTextField();
        txttanggal1 = new javax.swing.JTextField();

        txtnamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamabarangActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\icons8-search-30.png")); // NOI18N
        jButton1.setText("cari");

        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setText("hapus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("jumlah");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("total ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("diskon");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Bayar");

        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Kembalian");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Kode Transaksi");

        txtkembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkembalianActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Masukan Nama Barang");

        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setText("Bayar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tblbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbarang);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("data transaksi");

        btnkeluar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnkeluar.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\icons8-new-30.png")); // NOI18N
        btnkeluar.setText("keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        tbltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbltransaksi);

        btntambah.setText("TAMBAH");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setFont(new java.awt.Font("Book Antiqua", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TRANSAKSI PENJUALAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel1)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 841, 94);

        txtnamabarang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamabarang1ActionPerformed(evt);
            }
        });
        getContentPane().add(txtnamabarang1);
        txtnamabarang1.setBounds(30, 129, 123, 20);

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-search-20.png"))); // NOI18N
        jButton4.setText("cari");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(225, 106, 80, 27);

        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-20 (1).png"))); // NOI18N
        jButton5.setText("hapus");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(462, 352, 97, 27);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("jumlah");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(514, 167, 64, 17);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("total ");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(30, 474, 35, 17);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("diskon");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(601, 253, 64, 17);
        getContentPane().add(txttotal1);
        txttotal1.setBounds(91, 468, 138, 28);
        getContentPane().add(txtjumlah1);
        txtjumlah1.setBounds(475, 202, 128, 22);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Bayar");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(30, 525, 39, 17);
        getContentPane().add(txtdiskon1);
        txtdiskon1.setBounds(564, 296, 127, 22);

        txtbayar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayar1ActionPerformed(evt);
            }
        });
        getContentPane().add(txtbayar1);
        txtbayar1.setBounds(87, 522, 138, 22);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Kembalian");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(289, 452, 73, 17);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("Kode Transaksi");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(30, 278, 108, 17);

        txtkembalian1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkembalian1ActionPerformed(evt);
            }
        });
        getContentPane().add(txtkembalian1);
        txtkembalian1.setBounds(368, 449, 108, 22);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Masukan Nama Barang");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(30, 106, 159, 17);
        getContentPane().add(txtkodetransaksi1);
        txtkodetransaksi1.setBounds(156, 275, 121, 22);

        jButton6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton6.setText("Bayar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(496, 418, 72, 24);

        tblbarang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblbarang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbarang1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblbarang1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(30, 167, 414, 90);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("data transaksi");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(30, 323, 98, 17);

        btnkeluar1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnkeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-exit-20.png"))); // NOI18N
        btnkeluar1.setText("keluar");
        btnkeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnkeluar1);
        btnkeluar1.setBounds(553, 459, 234, 27);

        tbltransaksi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbltransaksi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltransaksi1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbltransaksi1);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(30, 352, 414, 90);

        btntambah1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btntambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-plus-20 (1).png"))); // NOI18N
        btntambah1.setText("TAMBAH");
        btntambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambah1ActionPerformed(evt);
            }
        });
        getContentPane().add(btntambah1);
        btntambah1.setBounds(684, 180, 103, 27);
        getContentPane().add(txtjam);
        txtjam.setBounds(657, 111, 130, 22);
        getContentPane().add(txttanggal1);
        txttanggal1.setBounds(448, 111, 130, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamabarangActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
                // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayarActionPerformed

    private void txtkembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkembalianActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbarangMouseClicked
               // TODO add your handling code here:
    }//GEN-LAST:event_tblbarangMouseClicked

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        new menuutama().setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void tbltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltransaksiMouseClicked
               // TODO add your handling code here:
    }//GEN-LAST:event_tbltransaksiMouseClicked

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btntambahActionPerformed

    private void txtnamabarang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamabarang1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamabarang1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
try {
            String sql="delete from tbl_detailpenjualan where ProdukID=?";
            pst=konek.prepareStatement(sql);
            pst.setString(1, idprodukpenjualan);
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        detail();
        penjumlahan();
        tambah_stok();
        cari();              // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtbayar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayar1ActionPerformed

    private void txtkembalian1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkembalian1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkembalian1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
 total();
     simpan();
     autonumber();
     detail();
     txttotal.setText("");
     txtbayar.setText("");
     txtkembalian.setText("");
     txtnamabarang.setText("");
     cari();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tblbarang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbarang1MouseClicked
try {
    int row=tblbarang.getSelectedRow();
    String tabel_klik=(tblbarang.getModel().getValueAt(row, 0).toString());
    String sql="select * from tbl_produk where ProdukID='"+tabel_klik+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {
    idproduk=rst.getString(("ProdukID"));    
    String stok=rst.getString(("Stok"));
    inputstok= Integer.parseInt(stok);
    harga=rst.getString(("Harga"));
    inputharga= Integer.parseInt(harga);
    }
}catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}                // TODO add your handling code here:
    }//GEN-LAST:event_tblbarang1MouseClicked

    private void btnkeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluar1ActionPerformed
this.dispose();        new menuutama().setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluar1ActionPerformed

    private void tbltransaksi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltransaksi1MouseClicked
try {
    int row=tbltransaksi.getSelectedRow();
    idprodukpenjualan=(tbltransaksi.getModel().getValueAt(row, 1).toString());
    String sql="select * from tbl_detailpenjualan where ProdukID='"+idprodukpenjualan+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {   
    String jumlah=rst.getString(("JumlahProduk"));
    inputjumlah= Integer.parseInt(jumlah);
    }
}catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}
ambil_stock();              // TODO add your handling code here:
    }//GEN-LAST:event_tbltransaksi1MouseClicked

    private void btntambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambah1ActionPerformed
subtotal();
        kurangi_stok();
        try {
            String Kode_detail=txtnamabarang.getText();
            iddetail="D"+Kode_detail;
            String sql="insert into tbl_detailpenjualan (DetailID,ProdukID,Harga,JumlahProduk,Subtotal) value (?,?,?,?,?)";
            String update="update tbl_produk set Stok='"+kurangistok+"' where ProdukID='"+idproduk+"'";
            pst=konek.prepareStatement(sql);
            pst2=konek.prepareStatement(update);
            pst.setString(1, iddetail);
            pst.setString(2, idproduk);
            pst.setString(3, harga);
            pst.setString(4, txtjumlah.getText());
            pst.setString(5, sub_total);
            pst.execute();
            pst2.execute();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        detail();
        penjumlahan();
        cari();
        clsr();

        // TODO add your handling code here:
    }//GEN-LAST:event_btntambah1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
cari();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formpenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnkeluar1;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btntambah1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblbarang;
    private javax.swing.JTable tblbarang1;
    private javax.swing.JTable tbltransaksi;
    private javax.swing.JTable tbltransaksi1;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtbayar1;
    private javax.swing.JTextField txtdiskon;
    private javax.swing.JTextField txtdiskon1;
    private javax.swing.JTextField txtjam;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtjumlah1;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtkembalian1;
    private javax.swing.JTextField txtkodetransaksi;
    private javax.swing.JTextField txtkodetransaksi1;
    private javax.swing.JTextField txtnamabarang;
    private javax.swing.JTextField txtnamabarang1;
    private javax.swing.JTextField txttanggal1;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txttotal1;
    // End of variables declaration//GEN-END:variables
}

package simrskhanza;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class DlgCariTagihanOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection  koneksi=koneksiDB.condb();
    private PreparedStatement psrekening;
    private ResultSet rsrekening;
    private double ttljmdokter=0,ttljmpetugas=0,ttlpendapatan=0,ttlbhp=0;
    private String Suspen_Piutang_Operasi_Ranap="",Operasi_Ranap="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",
            Utang_Jasa_Medik_Dokter_Operasi_Ranap="",Beban_Jasa_Medik_Paramedis_Operasi_Ranap="",
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap="",HPP_Obat_Operasi_Ranap="",Persediaan_Obat_Kamar_Operasi_Ranap="",status="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariTagihanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Operasi",
                      "No.Rawat",
                      "Pasien",
                      "Jns.Ans","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 34; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(130);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        kdmem.setDocument(new batasInput((byte)10).getKata(kdmem));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgCariTagihanOperasi")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),2).toString());
                    }   
                    kdmem.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgCariTagihanOperasi")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
           
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Operasi_Ranap=rsrekening.getString("Suspen_Piutang_Operasi_Ranap");
                    Operasi_Ranap=rsrekening.getString("Operasi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                    HPP_Obat_Operasi_Ranap=rsrekening.getString("HPP_Obat_Operasi_Ranap");
                    Persediaan_Obat_Kamar_Operasi_Ranap=rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    private DlgPasien member=new DlgPasien(null,false);
    private double total=0;
    private int no=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        nmmem = new widget.TextBox();
        BtnCari5 = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(java.awt.Color.darkGray);
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusObatOperasi);

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(java.awt.Color.darkGray);
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihanOperasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Tagihan Operasi/VK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(null);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 70, 23);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 10, 100, 23);

        label13.setText("Pasien :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 10, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        BtnCari5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari5.setMnemonic('2');
        BtnCari5.setToolTipText("Alt+2");
        BtnCari5.setName("BtnCari5"); // NOI18N
        BtnCari5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari5ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari5);
        BtnCari5.setBounds(774, 10, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(173, 10, 30, 23);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(200, 10, 100, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari);

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
        panelisi1.add(LTotal);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnCari5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari5ActionPerformed
        var.setform("DlgCariTagihanOperasi");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_BtnCari5ActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            TCari.requestFocus();   
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdmem);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdmem.setText("");
        nmmem.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,33).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi operasi"); 
            }
            Sequel.AutoComitTrue();
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptOperasi.jrxml","report","::[ Transaksi Operasi ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
    if(var.getkode().equals("Admin Utama")){
        ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;status="";
        status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        ttljmdokter=Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+"+
                "biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        ttljmpetugas=Sequel.cariIsiAngka("select sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+"+
                "biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+"+
                "biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) "+
                "from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        ttlpendapatan=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1+operasi.biayaoperator2+"+
                "operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+"+
                "operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+"+
                "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+"+
                "operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+"+
                "operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+"+
                "operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+"+
                "operasi.biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");

        Sequel.AutoComitFalse();
        if(Sequel.queryutf("delete from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1)+"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +"'")==true){
            if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==false){
               ttlbhp=0;
            }
            if(status.equals("Ranap")){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlpendapatan+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlpendapatan+"','0'","Rekening");                              
                }
                if(ttljmdokter>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban Jasa Medik Dokter Operasi Ranap','0','"+ttljmdokter+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','"+ttljmdokter+"','0'","Rekening");                              
                }
                if(ttljmpetugas>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban Jasa Medik Petugas Operasi Ranap','0','"+ttljmpetugas+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang Jasa Medik Petugas Operasi Ranap','"+ttljmpetugas+"','0'","Rekening");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                }
                jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OPERASI RAWAT INAP PASIEN OLEH "+var.getkode());                                              
            }       
        }
        Sequel.AutoComitTrue();
        tampil();
    }else{
        if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;status="";
            status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            ttljmdokter=Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+"+
                    "biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            ttljmpetugas=Sequel.cariIsiAngka("select sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+"+
                    "biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+"+
                    "biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) "+
                    "from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            ttlpendapatan=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1+operasi.biayaoperator2+"+
                    "operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+"+
                    "operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+"+
                    "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+"+
                    "operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                    "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+"+
                    "operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+"+
                    "operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+"+
                    "operasi.biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");

            Sequel.AutoComitFalse();
            if(Sequel.queryutf("delete from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1)+"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +"'")==true){
                if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==false){
                   ttlbhp=0;
                }
                if(status.equals("Ranap")){
                    Sequel.queryu("delete from tampjurnal");    
                    if(ttlpendapatan>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlpendapatan+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlpendapatan+"','0'","Rekening");                              
                    }
                    if(ttljmdokter>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban Jasa Medik Dokter Operasi Ranap','0','"+ttljmdokter+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','"+ttljmdokter+"','0'","Rekening");                              
                    }
                    if(ttljmpetugas>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban Jasa Medik Petugas Operasi Ranap','0','"+ttljmpetugas+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang Jasa Medik Petugas Operasi Ranap','"+ttljmpetugas+"','0'","Rekening");                              
                    }
                    if(ttlbhp>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                    }
                    jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OPERASI RAWAT INAP PASIEN OLEH "+var.getkode());                                              
                }       
            }
            Sequel.AutoComitTrue();
            tampil();
        }
    }
}//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
    if(var.getkode().equals("Admin Utama")){
        ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;
        status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
        Sequel.AutoComitFalse();
        if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==true){
            if(status.equals("Ranap")){
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlbhp+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                             
                    jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OBAT OPERASI RAWAT INAP OLEH "+var.getkode());                                                  
                }
            }
        }
        Sequel.AutoComitTrue();
        tampil();
    }else{
        if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;
            status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
            Sequel.AutoComitFalse();
            if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==true){
                if(status.equals("Ranap")){
                    if(ttlbhp>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlbhp+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                             
                        jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OBAT OPERASI RAWAT INAP OLEH "+var.getkode());                                                  
                    }
                }
            }
            Sequel.AutoComitTrue();
            tampil();
        }
    }
        
}//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariTagihanOperasi dialog = new DlgCariTagihanOperasi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari5;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdmem;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        String tanggal="  operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' ";
        String mem="";      
        if(!kdmem.getText().equals("")){
            mem=" and pasien.no_rkm_medis='"+kdmem.getText()+"' ";
        }
        String sql="select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,operasi.jenis_anasthesi,"+
                   "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien "+
                    " on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    " where "+tanggal+mem+" and operasi.no_rawat like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+" and reg_periksa.no_rkm_medis like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+" and pasien.nm_pasien like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+" and operasi.tgl_operasi like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+" and operasi.jenis_anasthesi like '%"+TCari.getText()+"%'  "+                   
                    " group by operasi.no_rawat,operasi.tgl_operasi order by operasi.tgl_operasi,operasi.no_rawat ";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
       Valid.tabelKosong(tabMode);
        try{
            ResultSet rs=koneksi.prepareStatement(sql).executeQuery();
            while(rs.next()){
                total=0;
                tabMode.addRow(new Object[]{rs.getString("tgl_operasi"),
                               rs.getString("no_rawat"),
                               rs.getString("no_rkm_medis")+", "+rs.getString("nm_pasien"),
                               rs.getString("jenis_anasthesi"),"Perawatan",
                               "Operator 1",
                               "Operator 2",
                               "Operator 3",
                               "Asisten Operator 1",
                               "Asisten Operator 2",
                               "Asisten Operator 3",
                               "Instrumen",
                               "Dokter Anak",
                               "Perawat Resusitas",
                               "Dokter Anestesi",
                               "Asisten Anestesi 1",
                               "Asisten Anestesi 2",
                               "Bidan 1",
                               "Bidan 2",
                               "Bidan 3",
                               "Perawat Luar",
                               "Onloop 1",
                               "Onloop 2",
                               "Onloop 3",
                               "Onloop 4",
                               "Onloop 5",
                               "Sewa OK/VK",
                               "Alat",
                               "Akomodasi",
                               "N.M.S.",
                               "Sarpras",
                               "Dokter PJ Anak",
                               "Dokter Umum",
                               "Biaya Perawatan"});     
                ResultSet rs2=koneksi.prepareStatement(
                        "select operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                        "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                        "operasi.dokter_anestesi, operasi.asisten_anestesi,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, "+
                        "operasi.omloop,operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum,"+
                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2,operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,"+
                        "operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                        "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+"+
                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras) as total from operasi inner join paket_operasi "+
                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs.getString("no_rawat")+"' and operasi.tgl_operasi='"+rs.getString("tgl_operasi")+"'").executeQuery();
                no=1;
                while(rs2.next()){
                    Object[] data2={"","","","",no+". "+rs2.getString("nm_perawatan"),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator1")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator2")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator1")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("instrumen")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_anak")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("perawaat_resusitas")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_anestesi")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_anestesi")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_anestesi2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("perawat_luar")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop4")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop5")),
                               "",
                               "",
                               "",
                               "",
                               "",
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_pjanak")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_umum")),
                               ""};
                    tabMode.addRow(data2);  
                    Object[] data3={"","","","","",Valid.SetAngka(rs2.getDouble("biayaoperator1")),
                               Valid.SetAngka(rs2.getDouble("biayaoperator2")),
                               Valid.SetAngka(rs2.getDouble("biayaoperator3")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator1")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator2")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator3")),
                               Valid.SetAngka(rs2.getDouble("biayainstrumen")),
                               Valid.SetAngka(rs2.getDouble("biayadokter_anak")),
                               Valid.SetAngka(rs2.getDouble("biayaperawaat_resusitas")),
                               Valid.SetAngka(rs2.getDouble("biayadokter_anestesi")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_anestesi")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_anestesi2")),
                               Valid.SetAngka(rs2.getDouble("biayabidan")),
                               Valid.SetAngka(rs2.getDouble("biayabidan2")),
                               Valid.SetAngka(rs2.getDouble("biayabidan3")),
                               Valid.SetAngka(rs2.getDouble("biayaperawat_luar")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop2")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop3")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop4")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop5")),
                               Valid.SetAngka(rs2.getDouble("biayasewaok")),
                               Valid.SetAngka(rs2.getDouble("biayaalat")),
                               Valid.SetAngka(rs2.getDouble("akomodasi")),
                               Valid.SetAngka(rs2.getDouble("bagian_rs")),
                               Valid.SetAngka(rs2.getDouble("biayasarpras")),
                               Valid.SetAngka(rs2.getDouble("biaya_dokter_pjanak")),
                               Valid.SetAngka(rs2.getDouble("biaya_dokter_umum")),
                               Valid.SetAngka(rs2.getDouble("total"))};
                    tabMode.addRow(data3); 
                    total=total+rs2.getDouble("total");
                    no++;
                }
                
                Object[] data3={"","","","","Obat & BHP", "Satuan", "Harga","Jml","","","","","","","","","","","","","","","","","","","","","","","","","","Biaya Obat"};
                tabMode.addRow(data3); 
                ResultSet rs3=koneksi.createStatement().executeQuery(
                        "select beri_obat_operasi.kd_obat,obatbhp_ok.nm_obat,kodesatuan.satuan, beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah "+
                        "from beri_obat_operasi inner join obatbhp_ok inner join  kodesatuan "+
                        "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat and obatbhp_ok.kode_sat=kodesatuan.kode_sat "+
                        "where beri_obat_operasi.no_rawat='"+rs.getString("no_rawat")+"' and beri_obat_operasi.tanggal='"+rs.getString("tgl_operasi")+"'");
                no=1;
                while(rs3.next()){
                    Object[] data2={"","","","",no+". "+rs3.getString("nm_obat"),rs3.getString("satuan"), rs3.getString("hargasatuan"), 
                                   rs3.getString("jumlah"),"","","","","","","","","","","","","","","","","","","","","","","","","",Valid.SetAngka(rs3.getDouble("jumlah")*rs3.getDouble("hargasatuan"))};
                    tabMode.addRow(data2);  
                    total=total+(rs3.getDouble("jumlah")*rs3.getDouble("hargasatuan"));
                    no++;
                }
                
                Object[] data4={"","","","","Total Biaya :", "", "","","","","","","","","","","","","","","","","","","","",""," ","","","","","",Valid.SetAngka(total)};
                tabMode.addRow(data4); 
            }      
            rs.last();  
            LTotal.setText(""+rs.getRow());
           
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void isCek(){
        MnHapusObatOperasi.setEnabled(var.getoperasi());
        MnHapusTagihanOperasi.setEnabled(var.getoperasi());
        BtnPrint.setEnabled(var.getoperasi());
    }
     
    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(row!= -1){
            Kd2.setText(tabMode.getValueAt(row,0).toString());
        }
    }
     
    public JTextField getTextField(){
        return Kd2;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
 
}

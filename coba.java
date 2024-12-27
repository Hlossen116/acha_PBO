    // Bagaian UI delete databasenya
    public void deleteData() {
        titleLabel.setText("Menu Delete Data");
        pages = 2;
        content.setVisible(false);

        mhsHapusPanel = new JPanel();
        mhsHapusPanel.setLayout(new GridBagLayout());
        mhsHapusPanel.setBackground(Color.WHITE);
        mhsHapusPanel.setVisible(true);

        GridBagConstraints displayDeleteDataGrid = new GridBagConstraints();
        displayDeleteDataGrid.insets = new Insets(10, 10, 10, 10); // Padding
        displayDeleteDataGrid.fill = GridBagConstraints.HORIZONTAL;

        // Tambahkan label ID
        displayDeleteDataGrid.gridx = 0; // Posisi kiri
        displayDeleteDataGrid.gridy = 0;
        mhsHapusPanel.add(createLabel("Masukkan NIM : ", 16), displayDeleteDataGrid);

        // Tambahkan field input ID
        displayDeleteDataGrid.gridx = 1; // Di sebelah label
        displayDeleteDataGrid.gridy = 0;
        nimDeleteField = new JTextField(10);
        mhsHapusPanel.add(nimDeleteField, displayDeleteDataGrid);

        // Tombol Hapus Data
        displayDeleteDataGrid.gridx = 2;
        displayDeleteDataGrid.gridy = 0;
        prosesHapus.setPreferredSize(new Dimension(100, 30)); // Ukuran tombol
        mhsHapusPanel.add(prosesHapus, displayDeleteDataGrid);

        // Tombol Show Data
        displayDeleteDataGrid.gridx = 3;
        displayDeleteDataGrid.gridy = 0;
        reloadButton.setText("Show Data");
        reloadButton.setPreferredSize(new Dimension(100, 30)); // Ukuran tombol
        mhsHapusPanel.add(reloadButton, displayDeleteDataGrid);

        // Tabel Data
        displayDeleteDataGrid.gridx = 0;
        displayDeleteDataGrid.gridy = 1;
        displayDeleteDataGrid.gridwidth = 4; // Tabel menempati 4 kolom
        displayDeleteDataGrid.fill = GridBagConstraints.BOTH; // Mengisi ruang horizontal dan vertikal
        displayDeleteDataGrid.weightx = 1.0; // Membuat tabel fleksibel
        displayDeleteDataGrid.weighty = 1.0;

        tableModel = new DefaultTableModel(new String[]{"NIM", "Nama", "Email", "Jenis Kelamin", "Alamat", "Fakultas", "Prodi", "Semester"}, 0);
        table = new JTable(tableModel);

        // Menonaktifkan auto-resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Mengatur lebar kolom secara manual
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(200);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(150);
        columnModel.getColumn(7).setPreferredWidth(100);

        // Membungkus tabel dalam JScrollPane tanpa border
        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBorder(null); // Hapus border scrollpane
        mhsHapusPanel.add(scrollPane, displayDeleteDataGrid);

        // Tombol kembali
        displayDeleteDataGrid.gridx = 0;
        displayDeleteDataGrid.gridy = 2; // Berada di bawah tabel
        displayDeleteDataGrid.gridwidth = 1; // Tombol hanya 1 kolom
        displayDeleteDataGrid.weightx = 0; // Tidak fleksibel
        displayDeleteDataGrid.weighty = 0;
        displayDeleteDataGrid.anchor = GridBagConstraints.WEST; // Align ke kiri
        back.setPreferredSize(new Dimension(100, 30)); // Ukuran tombol kembali
        mhsHapusPanel.add(back, displayDeleteDataGrid);

        this.add(mhsHapusPanel, BorderLayout.CENTER);
    } 






// ini proses delete databasenya
    public void procesDeleteDb() {
        String nim = nimDeleteField.getText();
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan NIM yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/perkuliahan", "root", "")) {
            String sql = "DELETE FROM mahasiswa WHERE nim = ?";
            PreparedStatement cobaDele = con.prepareStatement(sql);
                cobaDele.setString(1, nim);
                int rowsAffected = cobaDele.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    nimDeleteField.setText(""); // Reset input
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } // proses delete datanya













// ini untuk pebacaaan data dari database ke IU nya
public void reloadDatabase(){
        String dbUrl = "jdbc:mysql://localhost:3306/perkuliahan"; // Sesuaikan dengan info DB Anda
        String usernameDb = "root"; // Sesuaikan dengan username
        String passwordDb = "";

        String sql = "SELECT * FROM mahasiswa"; // Query SQL untuk mengambil semua data

        try (Connection con = DriverManager.getConnection(dbUrl, usernameDb, passwordDb)) {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                // Clear existing data
                tableModel.setRowCount(0); // Hapus semua baris lama

                // Tambahkan baris baru dari ResultSet
                while (rs.next()) {
                    String nim = rs.getString("nim");
                    String nama = rs.getString("nama");
                    String email = rs.getString("email");
                    String jenisKelamin = rs.getString("jenis_kelamin");
                    String alamat = rs.getString("alamat");
                    String fakultas = rs.getString("fakultas");
                    String prodi = rs.getString("prodi");
                    int semester = rs.getInt("semester");

                    // Menambahkan data ke JTable
                    tableModel.addRow(new Object[]{nim, nama, email,jenisKelamin, alamat, fakultas, prodi, semester});
                }

                // Perbarui UI setelah data ditambahkan
                tableModel.fireTableDataChanged();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal koneksi ke database: " + ex.getMessage());
        }
}
    
    
    


// bagian kembali kemenu admin
    public void backToAdmin(){
        titleLabel.setText("Menu Admin");
        if(pages == 1){
            pages = 0;
            mhsInsertPanel.setVisible(false);
            content.setVisible(true);
        }
        else if(pages == 2){
            // hapus
            pages = 0;
            mhsHapusPanel.setVisible(false);
            content.setVisible(true);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertMhs){
            insertData();
        }else if(e.getSource() == delMhs){
            deleteData();
        }else if(e.getSource() == back){
            backToAdmin();
        }else if(e.getSource() == reloadButton){  // tombol show datanya
            if(pages == 2){
                reloadDatabase(); // proses baca datanya
            }
        }else if(e.getSource() == prosesHapus){
            procesDeleteDb();
        }
    }

package com.controlacademico.Vista;

import com.controlacademico.dao.*;
import com.controlacademico.modelo.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 12);
    
    private static final Color COLOR_PRIMARY = new Color(22, 115, 177); // Azul institucional
    private static final Color COLOR_ACCENT = new Color(52, 152, 219); // Azul claro para hover/selección
    private static final Color COLOR_BACKGROUND = new Color(248, 250, 252); // Fondo muy claro
    private static final Color COLOR_CARD_BG = Color.WHITE; // Fondo para tarjetas/bloques
    private static final Color COLOR_BORDER = new Color(230, 230, 230); // Borde suave
    private static final Color COLOR_GRAY_TEXT = new Color(150, 150, 150); // Color para Placeholders

    public VentanaPrincipal() {
        // 1. Aplicar Look and Feel Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("No se pudo aplicar Nimbus LAF.");
        }

        setTitle("Control Académico | Dashboard de Administración");
        setSize(1350, 800); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_HEADER.deriveFont(Font.PLAIN, 14f)); 
        tabs.setBackground(COLOR_CARD_BG);
        tabs.setForeground(COLOR_PRIMARY);
        
        // Asignación de Paneles (Todos los métodos están ahora definidos)
        tabs.addTab("Estudiantes ", crearPanelEstudiantes());
        tabs.addTab("Docentes ", crearPanelDocentes());
        tabs.addTab("Periodos ", crearPanelPeriodos());
        tabs.addTab("Cursos ", crearPanelCursos());
        
        tabs.addTab("Clases ", crearPanelClases());
        tabs.addTab("Cortes ", crearPanelCortes());
        tabs.addTab("Componentes ", crearPanelComponentes());
        tabs.addTab("Calificaciones ", crearPanelCalificaciones());
        tabs.addTab("Asistencias ", crearPanelAsistencias());
        tabs.addTab("Herramientas ", crearPanelHerramientas());

        add(tabs);
    }
    
    // -------------------------------------------------------------------------
    // --- MÉTODOS DE ESTILO ---
    // -------------------------------------------------------------------------

    private JButton createStyledButton(String text, String iconName) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_LABEL);
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_ACCENT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_PRIMARY);
            }
        });
        
        try {
            if (iconName != null) {
                
                // btn.setIcon(new ImageIcon(getClass().getResource("/icons/" + iconName + ".png")));
            }
        } catch (Exception e) { /* Ignorar si el icono no carga */ }
        
        return btn;
    }
    
    private void styleTable(JTable table, JScrollPane scroll) {
        table.setFont(FONT_BODY);
        table.getTableHeader().setFont(FONT_LABEL.deriveFont(13f));
        table.getTableHeader().setBackground(COLOR_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);
        table.setGridColor(COLOR_BORDER);
        table.setSelectionBackground(COLOR_ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false); 
        table.setIntercellSpacing(new Dimension(0, 1));

        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(COLOR_CARD_BG);
        
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
    }

    private JPanel createFormCard(JPanel formPanel, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 0, 5, 0),
            BorderFactory.createLineBorder(COLOR_BORDER, 1, true)
        ));
        wrapper.setBackground(COLOR_CARD_BG);
        
        JLabel titleLabel = new JLabel(" " + title);
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(COLOR_PRIMARY.darker());
        titleLabel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBorder(new EmptyBorder(10, 15, 10, 15));
        contentWrapper.add(formPanel, BorderLayout.CENTER);
        
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(contentWrapper, BorderLayout.CENTER);
        return wrapper;
    }
    
    private void addPlaceholder(JTextField field, String text) {
        field.setText(text);
        field.setForeground(COLOR_GRAY_TEXT);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                    field.setForeground(COLOR_GRAY_TEXT);
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- PANEL ESTUDIANTES  ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtIdent = new JTextField(15);
        JTextField txtNombre = new JTextField(25);
        JTextField txtCorreoInst = new JTextField(20);
        JTextField txtCorreoPer = new JTextField(20);
        JTextField txtTelefono = new JTextField(15);
        JCheckBox chkVocero = new JCheckBox("Es vocero"); chkVocero.setOpaque(false);
        JTextArea txtComentarios = new JTextArea(3, 20); 
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        scrollComentarios.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        JComboBox<String> comboTipoDoc = new JComboBox<>(new String[]{"CC", "TI", "CE", "PA"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        
        addPlaceholder(txtIdent, "Ej: 1020304050");
        addPlaceholder(txtNombre, "Nombre Completo del Estudiante");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtIdent, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Tipo Doc.:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboTipoDoc, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboGenero, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Correo Institucional:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtCorreoInst, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Correo Personal:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtCorreoPer, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Es Vocero:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(chkVocero, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollComentarios, gbc);

        controlPanel.add(createFormCard(formContent, "Datos del Estudiante"));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", "add_white");
        JButton btnUpdate = createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete = createStyledButton("Eliminar", "delete_white"); 
        JButton btnList = createStyledButton("Recargar Ventana", "refresh_white");
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);
        
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Identificación", "Nombre", "Correo Inst", "Teléfono"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        EstudianteDAO dao = new EstudianteDAO();

        btnInsert.addActionListener(e -> {
            try {
                Estudiante est = new Estudiante();
                est.setIdentificacion(txtIdent.getText().equals("Ej: 1020304050") ? "" : txtIdent.getText());
                est.setNombre(txtNombre.getText().equals("Nombre Completo del Estudiante") ? "" : txtNombre.getText());
                est.setCorreoInstitucional(txtCorreoInst.getText());
                est.setCorreoPersonal(txtCorreoPer.getText());
                est.setTelefono(txtTelefono.getText());
                est.setEsVocero(chkVocero.isSelected());
                est.setComentarios(txtComentarios.getText());
                est.setTipoDocumento(comboTipoDoc.getSelectedItem().toString());
                est.setGenero(comboGenero.getSelectedItem().toString());
                int id = dao.insertarEstudiante(est);
                if (id != -1) { JOptionPane.showMessageDialog(this, "Estudiante insertado con ID: " + id); btnList.doClick(); }
                else JOptionPane.showMessageDialog(this, "Error al insertar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnList.addActionListener(e -> {
            model.setRowCount(0);
            List<Estudiante> lista = dao.listarEstudiantes();
            for (Estudiante est : lista) {
                model.addRow(new Object[]{est.getEstudianteId(), est.getIdentificacion(), est.getNombre(), est.getCorreoInstitucional(), est.getTelefono()});
            }
        });

        btnDelete.addActionListener(e -> {
            int rowIdx = table.getSelectedRow();
            if (rowIdx == -1) { JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar"); return; }
            int id = (int) model.getValueAt(rowIdx, 0);
            boolean ok = dao.eliminarEstudiante(id);
            JOptionPane.showMessageDialog(this, ok ? "Estudiante eliminado" : "Error al eliminar");
            btnList.doClick();
        });

        btnUpdate.addActionListener(e -> {
            int rowIdx = table.getSelectedRow();
            if (rowIdx == -1) { JOptionPane.showMessageDialog(this, "Seleccione una fila para actualizar"); return; }
            try {
                int id = (int) model.getValueAt(rowIdx, 0);
                Estudiante est = new Estudiante();
                est.setEstudianteId(id);
                est.setIdentificacion(txtIdent.getText().equals("Ej: 1020304050") ? "" : txtIdent.getText());
                est.setNombre(txtNombre.getText().equals("Nombre Completo del Estudiante") ? "" : txtNombre.getText());
                est.setCorreoInstitucional(txtCorreoInst.getText());
                est.setCorreoPersonal(txtCorreoPer.getText());
                est.setTelefono(txtTelefono.getText());
                est.setEsVocero(chkVocero.isSelected());
                est.setComentarios(txtComentarios.getText());
                est.setTipoDocumento(comboTipoDoc.getSelectedItem().toString());
                est.setGenero(comboGenero.getSelectedItem().toString());
                boolean ok = dao.actualizarEstudiante(est);
                JOptionPane.showMessageDialog(this, ok ? "Estudiante actualizado" : "Error al actualizar");
                btnList.doClick();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        table.getSelectionModel().addListSelectionListener(ev -> {
            int r = table.getSelectedRow();
            if (r == -1) return;
            txtId.setText(String.valueOf(model.getValueAt(r, 0)));
            txtIdent.setText(String.valueOf(model.getValueAt(r, 1)));
            txtNombre.setText(String.valueOf(model.getValueAt(r, 2)));
            txtCorreoInst.setText(String.valueOf(model.getValueAt(r, 3)));
            txtTelefono.setText(String.valueOf(model.getValueAt(r, 4)));
            
            txtIdent.setForeground(Color.BLACK);
            txtNombre.setForeground(Color.BLACK);
            
            try {
                int targetId = Integer.parseInt(txtId.getText());
                Estudiante full = dao.listarEstudiantes().stream()
                        .filter(x -> x.getEstudianteId() == targetId)
                        .findFirst().orElse(null);

                if (full != null) {
                    txtCorreoPer.setText(full.getCorreoPersonal());
                    chkVocero.setSelected(full.isEsVocero());
                    txtComentarios.setText(full.getComentarios());
                    comboTipoDoc.setSelectedItem(full.getTipoDocumento());
                    comboGenero.setSelectedItem(full.getGenero());
                }
            } catch (Exception ignore) {}
        }); 

        return panel;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    // --- PANEL DOCENTES ---
    // -------------------------------------------------------------------------------------------------------------------------

    private JPanel crearPanelDocentes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextField txtIdent = new JTextField(15);
        JTextField txtCorreo = new JTextField(20);
        JTextField txtTitulo = new JTextField(15);
        JTextField txtIdiomas = new JTextField(20);
        JTextArea txtCerts = new JTextArea(3, 20); 
        JScrollPane scrollCerts = new JScrollPane(txtCerts);
        scrollCerts.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"CC", "TI", "CE"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        
        addPlaceholder(txtNombre, "Nombre Completo del Docente");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtIdent, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Tipo Identificación:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboTipo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboGenero, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Idiomas:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtIdiomas, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Certificaciones:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollCerts, gbc);

        controlPanel.add(createFormCard(formContent, "Datos del Docente")); 
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate = createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete = createStyledButton("Eliminar", "delete_white"); 
        JButton btnList = createStyledButton("Recargar Ventana", "refresh_white");
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Identificación", "Correo"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        DocenteDAO dao = new DocenteDAO();

        btnInsert.addActionListener(e -> {
            try {
                Docente d = new Docente();
                d.setNombreDocente(txtNombre.getText().equals("Nombre Completo del Docente") ? "" : txtNombre.getText());
                d.setIdentificacion(txtIdent.getText());
                d.setCorreo(txtCorreo.getText());
                d.setTipoIdentificacion(comboTipo.getSelectedItem().toString());
                d.setGenero(comboGenero.getSelectedItem().toString());
                d.setTituloEstudios(txtTitulo.getText());
                d.setIdiomas(txtIdiomas.getText());
                d.setCertificaciones(txtCerts.getText());
                int id = dao.insertarDocente(d);
                JOptionPane.showMessageDialog(this, id != -1 ? "Docente insertado ID: " + id : "Error al insertar docente");
                btnList.doClick();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnList.addActionListener(e -> {
            model.setRowCount(0);
            for (Docente d : dao.listarDocentes())
                model.addRow(new Object[]{d.getDocenteId(), d.getNombreDocente(), d.getIdentificacion(), d.getCorreo()});
        });

        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            int id = (int) model.getValueAt(r, 0);
            boolean ok = dao.eliminarDocente(id);
            JOptionPane.showMessageDialog(this, ok ? "Docente eliminado" : "Error al eliminar");
            btnList.doClick();
        });

        btnUpdate.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            try {
                int id = (int) model.getValueAt(r, 0);
                Docente d = new Docente();
                d.setDocenteId(id);
                d.setNombreDocente(txtNombre.getText().equals("Nombre Completo del Docente") ? "" : txtNombre.getText());
                d.setIdentificacion(txtIdent.getText());
                d.setCorreo(txtCorreo.getText());
                d.setTipoIdentificacion(comboTipo.getSelectedItem().toString());
                d.setGenero(comboGenero.getSelectedItem().toString());
                d.setTituloEstudios(txtTitulo.getText());
                d.setIdiomas(txtIdiomas.getText());
                d.setCertificaciones(txtCerts.getText());
                boolean ok = dao.actualizarDocente(d);
                JOptionPane.showMessageDialog(this, ok ? "Docente actualizado" : "Error al actualizar");
                btnList.doClick();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        table.getSelectionModel().addListSelectionListener(ev -> {
            int r = table.getSelectedRow();
            if (r == -1) return;
            txtId.setText(String.valueOf(model.getValueAt(r, 0)));
            txtNombre.setText(String.valueOf(model.getValueAt(r, 1)));
            txtIdent.setText(String.valueOf(model.getValueAt(r, 2)));
            txtCorreo.setText(String.valueOf(model.getValueAt(r, 3)));
            
            txtNombre.setForeground(Color.BLACK);
            
            try {
                int targetId = Integer.parseInt(txtId.getText());
                Docente full = dao.listarDocentes().stream()
                        .filter(x -> x.getDocenteId() == targetId)
                        .findFirst().orElse(null);
                
                if (full != null) {
                    comboTipo.setSelectedItem(full.getTipoIdentificacion());
                    comboGenero.setSelectedItem(full.getGenero());
                    txtTitulo.setText(full.getTituloEstudios());
                    txtIdiomas.setText(full.getIdiomas());
                    txtCerts.setText(full.getCertificaciones());
                }
            } catch (Exception ignore) {}
        });

        return panel;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    // --- PANEL CURSOS (Copia del código ya proporcionado) ---
    // -------------------------------------------------------------------------------------------------------------------------

    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextArea txtDesc = new JTextArea(4, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JComboBox<Integer> comboDocente = new JComboBox<>();
        
        addPlaceholder(txtNombre, "Ej: Programación Avanzada");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre del Curso:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Periodo ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboPeriodo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Docente ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboDocente, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollDesc, gbc);

        controlPanel.add(createFormCard(formContent, "Datos del Curso"));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate = createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete = createStyledButton("Eliminar", "delete_white"); 
        JButton btnList = createStyledButton("Recargar Ventana", "refresh_white");
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Periodo ID", "Docente ID"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        CursoDAO dao = new CursoDAO(); 
        PeriodoAcademicoDAO pdao = new PeriodoAcademicoDAO(); 
        DocenteDAO ddao = new DocenteDAO();

        btnList.addActionListener(e -> {
            model.setRowCount(0);
            comboPeriodo.removeAllItems();
            comboDocente.removeAllItems();
            pdao.listarPeriodos().forEach(p -> comboPeriodo.addItem(p.getPeriodoAcademicoId()));
            ddao.listarDocentes().forEach(d -> comboDocente.addItem(d.getDocenteId()));
            dao.listarCursos().forEach(c -> model.addRow(new Object[]{
                c.getCursoId(), c.getNombreCurso(), c.getPeriodoAcademicoId(), c.getDocenteId()
            }));
        });

        btnInsert.addActionListener(e -> {
            try {
                Curso c = new Curso();
                c.setNombreCurso(txtNombre.getText().equals("Ej: Programación Avanzada") ? "" : txtNombre.getText());
                c.setDescripcionCurso(txtDesc.getText());
                c.setPeriodoAcademicoId((Integer) comboPeriodo.getSelectedItem());
                c.setDocenteId((Integer) comboDocente.getSelectedItem());
                int id = dao.insertarCursoYObtenerId(c);
                JOptionPane.showMessageDialog(this, id != -1 ? "Curso insertado ID: " + id : "Error al insertar curso");
                btnList.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            int id = (int) model.getValueAt(r, 0);
            boolean ok = dao.eliminarCurso(id);
            JOptionPane.showMessageDialog(this, ok ? "Curso eliminado" : "Error al eliminar");
            btnList.doClick();
        });

        btnUpdate.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            try {
                int id = (int) model.getValueAt(r, 0);
                Curso c = new Curso();
                c.setCursoId(id);
                c.setNombreCurso(txtNombre.getText().equals("Ej: Programación Avanzada") ? "" : txtNombre.getText());
                c.setDescripcionCurso(txtDesc.getText());
                c.setPeriodoAcademicoId((Integer) comboPeriodo.getSelectedItem());
                c.setDocenteId((Integer) comboDocente.getSelectedItem());
                boolean ok = dao.actualizarCurso(c);
                JOptionPane.showMessageDialog(this, ok ? "Curso actualizado" : "Error al actualizar");
                btnList.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        table.getSelectionModel().addListSelectionListener(ev -> {
            int r = table.getSelectedRow();
            if (r == -1) return;
            txtId.setText(String.valueOf(model.getValueAt(r, 0)));
            txtNombre.setText(String.valueOf(model.getValueAt(r, 1)));
            comboPeriodo.setSelectedItem(model.getValueAt(r, 2)); 
            comboDocente.setSelectedItem(model.getValueAt(r, 3)); 
            
            txtNombre.setForeground(Color.BLACK);
            
            try{
                int cursoId = (int) model.getValueAt(r, 0);
                Curso c = dao.listarCursos().stream()
                        .filter(x -> x.getCursoId() == cursoId)
                        .findFirst().orElse(null);
                if(c != null) {
                   txtDesc.setText(c.getDescripcionCurso());
                }
            } catch(Exception ignored){}
        });

        return panel;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    // --- PANEL PERIODOS (Copia del código ya proporcionado) ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelPeriodos(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextField txtFechaInicio = new JTextField(15);
        JTextField txtFechaFin = new JTextField(15);
        
        addPlaceholder(txtFechaInicio, "YYYY-MM-DD");
        addPlaceholder(txtFechaFin, "YYYY-MM-DD");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre periodo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha inicio:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtFechaInicio, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha fin:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtFechaFin, gbc);

        controlPanel.add(createFormCard(formContent, "Datos del Periodo Académico"));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nombre","Inicio","Fin"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        PeriodoAcademicoDAO dao = new PeriodoAcademicoDAO();

        btnList.addActionListener(e->{
            model.setRowCount(0);
            for(PeriodoAcademico p: dao.listarPeriodos()){
                model.addRow(new Object[]{p.getPeriodoAcademicoId(), p.getNombrePeriodo(), p.getFechaInicio(), p.getFechaFin()});
            }
        });

        btnInsert.addActionListener(e->{
            try{
                PeriodoAcademico p = new PeriodoAcademico();
                p.setNombrePeriodo(txtNombre.getText());
                
                String inicio = txtFechaInicio.getText().equals("YYYY-MM-DD") ? "" : txtFechaInicio.getText();
                String fin = txtFechaFin.getText().equals("YYYY-MM-DD") ? "" : txtFechaFin.getText();

                p.setFechaInicio(inicio.isEmpty() ? null : Date.valueOf(inicio));
                p.setFechaFin(fin.isEmpty() ? null : Date.valueOf(fin));
                
                int id = dao.insertarPeriodoAcademico(p);
                JOptionPane.showMessageDialog(this, id!=-1?"Periodo insertado ID: "+id:"Error al insertar periodo");
                btnList.doClick();
            }catch(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnDelete.addActionListener(e->{
            int r = table.getSelectedRow(); if(r==-1){ JOptionPane.showMessageDialog(this,"Seleccione fila"); return; }
            int id = (int) model.getValueAt(r,0);
            boolean ok = dao.eliminarPeriodoAcademico(id);
            JOptionPane.showMessageDialog(this, ok?"Periodo eliminado":"Error al eliminar");
            btnList.doClick();
        });

        btnUpdate.addActionListener(e->{
            int r = table.getSelectedRow(); if(r==-1){ JOptionPane.showMessageDialog(this,"Seleccione fila"); return; }
            try{
                int id = (int) model.getValueAt(r,0);
                PeriodoAcademico p = new PeriodoAcademico();
                
                String inicio = txtFechaInicio.getText().equals("YYYY-MM-DD") ? "" : txtFechaInicio.getText();
                String fin = txtFechaFin.getText().equals("YYYY-MM-DD") ? "" : txtFechaFin.getText();
                
                p.setPeriodoAcademicoId(id);
                p.setNombrePeriodo(txtNombre.getText());
                p.setFechaInicio(inicio.isEmpty() ? null : Date.valueOf(inicio));
                p.setFechaFin(fin.isEmpty() ? null : Date.valueOf(fin));
                
                boolean ok = dao.actualizarPeriodoAcademico(p);
                JOptionPane.showMessageDialog(this, ok?"Periodo actualizado":"Error al actualizar");
                btnList.doClick();
            }catch(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        table.getSelectionModel().addListSelectionListener(ev->{
            int r = table.getSelectedRow(); if(r==-1) return;
            txtId.setText(String.valueOf(model.getValueAt(r,0)));
            txtNombre.setText(String.valueOf(model.getValueAt(r,1)));
            txtFechaInicio.setText(String.valueOf(model.getValueAt(r,2)));
            txtFechaFin.setText(String.valueOf(model.getValueAt(r,3)));
            
            txtFechaInicio.setForeground(Color.BLACK);
            txtFechaFin.setForeground(Color.BLACK);
        });

        return panel;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL CLASES ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelClases(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JTextField txtNumero = new JTextField(10);
        JTextField txtFecha = new JTextField(15); 
        JTextField txtTema = new JTextField(25);
        JTextArea txtDesc = new JTextArea(3, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        JTextArea txtComentarios = new JTextArea(3, 20);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        scrollComentarios.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        addPlaceholder(txtFecha, "YYYY-MM-DD");
        addPlaceholder(txtTema, "Ej: Introducción a Swing");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboCurso, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Número Clase:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNumero, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtTema, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollDesc, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollComentarios, gbc);

        controlPanel.add(createFormCard(formContent, "Gestión de Clases")); 
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso ID","Número","Fecha","Tema"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        ClaseDAO dao = new ClaseDAO(); CursoDAO cdao = new CursoDAO();

        btnList.addActionListener(e->{ 
            model.setRowCount(0); comboCurso.removeAllItems(); 
            for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); 
            for(Clases cl: dao.listarClases()) model.addRow(new Object[]{cl.getClaseId(), cl.getCursoId(), cl.getNumeroClase(), cl.getFechaClase(), cl.getTemaClase()}); 
        });

        btnInsert.addActionListener(e->{ 
            try{ 
                Clases cl=new Clases(); 
                String fecha = txtFecha.getText().equals("YYYY-MM-DD") ? "" : txtFecha.getText();
                String tema = txtTema.getText().equals("Ej: Introducción a Swing") ? "" : txtTema.getText();
                
                cl.setCursoId((Integer)comboCurso.getSelectedItem()); 
                cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); 
                cl.setFechaClase(fecha.isEmpty() ? null : Date.valueOf(fecha)); 
                cl.setTemaClase(tema); 
                cl.setDescripcionClase(txtDesc.getText()); 
                cl.setComentariosClase(txtComentarios.getText()); 
                boolean ok=dao.insertarClase(cl); 
                JOptionPane.showMessageDialog(this, ok?"Clase insertada":"Error al insertar clase"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        btnDelete.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            int id=(int)model.getValueAt(r,0); 
            boolean ok=dao.eliminarClase(id); 
            JOptionPane.showMessageDialog(this, ok?"Clase eliminada":"Error al eliminar"); 
            btnList.doClick(); 
        });

        btnUpdate.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            try{ 
                int id=(int)model.getValueAt(r,0); 
                Clases cl=new Clases(); 
                String fecha = txtFecha.getText().equals("YYYY-MM-DD") ? "" : txtFecha.getText();
                String tema = txtTema.getText().equals("Ej: Introducción a Swing") ? "" : txtTema.getText();
                
                cl.setClaseId(id); 
                cl.setCursoId((Integer)comboCurso.getSelectedItem()); 
                cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); 
                cl.setFechaClase(fecha.isEmpty() ? null : Date.valueOf(fecha));
                cl.setTemaClase(tema); 
                cl.setDescripcionClase(txtDesc.getText()); 
                cl.setComentariosClase(txtComentarios.getText()); 
                boolean ok=dao.actualizarClase(cl); 
                JOptionPane.showMessageDialog(this, ok?"Clase actualizada":"Error al actualizar"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        table.getSelectionModel().addListSelectionListener(ev->{ 
            int r=table.getSelectedRow(); if(r==-1) return; 
            txtId.setText(String.valueOf(model.getValueAt(r,0))); 
            comboCurso.setSelectedItem(model.getValueAt(r,1)); 
            txtNumero.setText(String.valueOf(model.getValueAt(r,2))); 
            txtFecha.setText(String.valueOf(model.getValueAt(r,3))); 
            txtTema.setText(String.valueOf(model.getValueAt(r,4)));
            
            txtFecha.setForeground(Color.BLACK);
            txtTema.setForeground(Color.BLACK);
            
        });

        return panel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL CORTES ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelCortes(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JTextField txtNombre = new JTextField(25);
        JTextField txtPorc = new JTextField(10);
        JTextArea txtComentarios = new JTextArea(3, 20);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        scrollComentarios.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboCurso, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Periodo ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboPeriodo, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre Corte:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Porcentaje:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtPorc, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollComentarios, gbc);
        
        controlPanel.add(createFormCard(formContent, "Gestión de Cortes de Evaluación")); 
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso","Periodo","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        CorteEvaluacionDAO dao = new CorteEvaluacionDAO(); CursoDAO cdao=new CursoDAO(); PeriodoAcademicoDAO pdao=new PeriodoAcademicoDAO();

        btnList.addActionListener(e->{ 
            model.setRowCount(0); comboCurso.removeAllItems(); comboPeriodo.removeAllItems(); 
            for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); 
            for(PeriodoAcademico p: pdao.listarPeriodos()) comboPeriodo.addItem(p.getPeriodoAcademicoId()); 
            for(CorteEvaluacion ce: dao.listarCortesEvaluacion()) model.addRow(new Object[]{ce.getCorteEvaluacionId(), ce.getCursoId(), ce.getPeriodoAcademicoId(), ce.getNombreCorte(), ce.getPorcentaje()}); 
        });

        btnInsert.addActionListener(e->{ 
            try{ 
                CorteEvaluacion ce=new CorteEvaluacion(); 
                ce.setCursoId((Integer)comboCurso.getSelectedItem()); 
                ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); 
                ce.setNombreCorte(txtNombre.getText()); 
                ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); 
                ce.setComentariosCorte(txtComentarios.getText()); 
                int id=dao.insertarCorteEvaluacionYObtenerId(ce); 
                JOptionPane.showMessageDialog(this,id!=-1?"Corte insertado":"Error"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        btnDelete.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            int id=(int)model.getValueAt(r,0); 
            boolean ok=dao.eliminarCorteEvaluacion(id); 
            JOptionPane.showMessageDialog(this, ok?"Corte eliminado":"Error al eliminar"); 
            btnList.doClick(); 
        });

        btnUpdate.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            try{ 
                int id=(int)model.getValueAt(r,0); 
                CorteEvaluacion ce=new CorteEvaluacion(); 
                ce.setCorteEvaluacionId(id); 
                ce.setCursoId((Integer)comboCurso.getSelectedItem()); 
                ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); 
                ce.setNombreCorte(txtNombre.getText()); 
                ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); 
                ce.setComentariosCorte(txtComentarios.getText()); 
                boolean ok=dao.actualizarCorteEvaluacion(ce); 
                JOptionPane.showMessageDialog(this, ok?"Corte actualizado":"Error al actualizar"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        table.getSelectionModel().addListSelectionListener(ev->{ 
            int r=table.getSelectedRow(); if(r==-1) return; 
            txtId.setText(String.valueOf(model.getValueAt(r,0))); 
            comboCurso.setSelectedItem(model.getValueAt(r,1)); 
            comboPeriodo.setSelectedItem(model.getValueAt(r,2)); 
            txtNombre.setText(String.valueOf(model.getValueAt(r,3))); 
            txtPorc.setText(String.valueOf(model.getValueAt(r,4)));
            // Comentarios requieren buscar el objeto completo.
        });

        return panel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL COMPONENTES ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelComponentes(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCorte=new JComboBox<>();
        JTextField txtNombre=new JTextField(25);
        JTextField txtPorc=new JTextField(10);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Corte ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboCorte, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Porcentaje:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtPorc, gbc);

        controlPanel.add(createFormCard(formContent, "Gestión de Componentes de Evaluación")); 
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Corte","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        ComponenteEvaluacionDAO dao=new ComponenteEvaluacionDAO(); CorteEvaluacionDAO cdao=new CorteEvaluacionDAO();

        btnList.addActionListener(e->{ 
            model.setRowCount(0); comboCorte.removeAllItems(); 
            for(CorteEvaluacion ce: cdao.listarCortesEvaluacion()) comboCorte.addItem(ce.getCorteEvaluacionId()); 
            for(ComponenteEvaluacion comp: dao.listarComponentesEvaluacion()) model.addRow(new Object[]{comp.getComponenteEvaluacionId(), comp.getCorteEvaluacionId(), comp.getNombreComponente(), comp.getPorcentaje()}); 
        });

        btnInsert.addActionListener(e->{ 
            try{ 
                ComponenteEvaluacion comp=new ComponenteEvaluacion(); 
                comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); 
                comp.setNombreComponente(txtNombre.getText()); 
                comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); 
                int id=dao.insertarComponenteEvaluacionYObtenerid(comp); 
                JOptionPane.showMessageDialog(this, id!=-1?"Componente insertado":"Error"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        btnDelete.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            int id=(int)model.getValueAt(r,0); 
            boolean ok=dao.eliminarComponenteEvaluacion(id); 
            JOptionPane.showMessageDialog(this, ok?"Componente eliminado":"Error al eliminar"); 
            btnList.doClick(); 
        });

        btnUpdate.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            try{ 
                int id=(int)model.getValueAt(r,0); 
                ComponenteEvaluacion comp=new ComponenteEvaluacion(); 
                comp.setComponenteEvaluacionId(id); 
                comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); 
                comp.setNombreComponente(txtNombre.getText()); 
                comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); 
                boolean ok=dao.actualizarComponenteEvaluacion(comp); 
                JOptionPane.showMessageDialog(this, ok?"Componente actualizado":"Error al actualizar"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        return panel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL CALIFICACIONES ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelCalificaciones(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboComp=new JComboBox<>();
        JTextField txtNota=new JTextField(10);
        JTextArea txtComent=new JTextArea(3, 20);
        JScrollPane scrollComent = new JScrollPane(txtComent);
        scrollComent.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Estudiante ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboEst, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Componente ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboComp, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtNota, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollComent, gbc);

        controlPanel.add(createFormCard(formContent, "Registro de Calificación")); 
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Componente","Nota"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);

        CalificacionDAO dao=new CalificacionDAO(); EstudianteDAO edao=new EstudianteDAO(); ComponenteEvaluacionDAO cdao=new ComponenteEvaluacionDAO();

        btnList.addActionListener(e->{ 
            model.setRowCount(0); comboEst.removeAllItems(); comboComp.removeAllItems(); 
            for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); 
            for(ComponenteEvaluacion comp: cdao.listarComponentesEvaluacion()) comboComp.addItem(comp.getComponenteEvaluacionId()); 
            for(Calificacion cal: dao.listarCalificaciones()) model.addRow(new Object[]{cal.getCalificacionId(), cal.getEstudianteId(), cal.getComponenteEvaluacionId(), cal.getNota()}); 
        });

        btnInsert.addActionListener(e->{ 
            try{ 
                Calificacion cal=new Calificacion(); 
                cal.setEstudianteId((Integer)comboEst.getSelectedItem()); 
                cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); 
                cal.setNota(Double.parseDouble(txtNota.getText())); 
                cal.setComentariosCalificacion(txtComent.getText()); 
                int id=dao.insertarCalificacionYObtenerId(cal); 
                JOptionPane.showMessageDialog(this, id!=-1?"Calificación insertada":"Error"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        btnDelete.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            int id=(int)model.getValueAt(r,0); 
            boolean ok=dao.eliminarCalificacion(id); 
            JOptionPane.showMessageDialog(this, ok?"Calificación eliminada":"Error al eliminar"); 
            btnList.doClick(); 
        });

        btnUpdate.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            try{ 
                int id=(int)model.getValueAt(r,0); 
                Calificacion cal=new Calificacion(); 
                cal.setCalificacionId(id); 
                cal.setEstudianteId((Integer)comboEst.getSelectedItem()); 
                cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); 
                cal.setNota(Double.parseDouble(txtNota.getText())); 
                cal.setComentariosCalificacion(txtComent.getText()); 
                boolean ok=dao.actualizarCalificacion(cal); 
                JOptionPane.showMessageDialog(this, ok?"Calificación actualizada":"Error al actualizar"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        return panel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL ASISTENCIAS ---
    // -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelAsistencias(){
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboCurso=new JComboBox<>();
        JTextField txtFecha=new JTextField(15);
        JComboBox<String> comboEstado=new JComboBox<>(new String[]{"Presente","Ausente","Tardanza"});
        JTextArea txtNovedades=new JTextArea(3, 20);
        JScrollPane scrollNovedades = new JScrollPane(txtNovedades);
        scrollNovedades.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        addPlaceholder(txtFecha, "YYYY-MM-DD");

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Estudiante ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboEst, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboCurso, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(comboEstado, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Novedades:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(scrollNovedades, gbc);

        controlPanel.add(createFormCard(formContent, "Registro de Asistencia")); 
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", "add_white"); 
        JButton btnUpdate=createStyledButton("Actualizar", "edit_white"); 
        JButton btnDelete=createStyledButton("Eliminar", "delete_white"); 
        JButton btnList=createStyledButton("Recargar Ventana", "refresh_white"); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);
        controlPanel.add(botones);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Curso","Fecha","Estado"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        JScrollPane scrollTable = new JScrollPane(table);
        styleTable(table, scrollTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, scrollTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(splitPane, BorderLayout.CENTER);
        
        AsistenciaDAO dao=new AsistenciaDAO(); EstudianteDAO edao=new EstudianteDAO(); CursoDAO cdao=new CursoDAO();

        btnList.addActionListener(e->{ 
            model.setRowCount(0); comboEst.removeAllItems(); comboCurso.removeAllItems(); 
            for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); 
            for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); 
            for(Asistencia a: dao.listarAsistencias()) model.addRow(new Object[]{a.getAsistenciaId(), a.getEstudianteId(), a.getCursoId(), a.getFechaClase(), a.getEstadoAsistencia()}); 
        });

        btnInsert.addActionListener(e->{ 
            try{ 
                Asistencia a=new Asistencia(); 
                String fecha = txtFecha.getText().equals("YYYY-MM-DD") ? "" : txtFecha.getText();
                
                a.setEstudianteId((Integer)comboEst.getSelectedItem()); 
                a.setCursoId((Integer)comboCurso.getSelectedItem()); 
                a.setFechaClase(fecha.isEmpty() ? null : Date.valueOf(fecha)); 
                a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); 
                a.setNovedades(txtNovedades.getText()); 
                int id=dao.insertarAsistenciaYObtenerId(a); 
                JOptionPane.showMessageDialog(this, id!=-1?"Asistencia insertada":"Error"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        btnDelete.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            int id=(int)model.getValueAt(r,0); 
            boolean ok=dao.eliminarAsistencia(id); 
            JOptionPane.showMessageDialog(this, ok?"Asistencia eliminada":"Error al eliminar"); 
            btnList.doClick(); 
        });

        btnUpdate.addActionListener(e->{ 
            int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} 
            try{ 
                int id=(int)model.getValueAt(r,0); 
                Asistencia a=new Asistencia(); 
                String fecha = txtFecha.getText().equals("YYYY-MM-DD") ? "" : txtFecha.getText();
                
                a.setAsistenciaId(id); 
                a.setEstudianteId((Integer)comboEst.getSelectedItem()); 
                a.setCursoId((Integer)comboCurso.getSelectedItem()); 
                a.setFechaClase(fecha.isEmpty() ? null : Date.valueOf(fecha)); 
                a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); 
                a.setNovedades(txtNovedades.getText()); 
                boolean ok=dao.actualizarAsistencia(a); 
                JOptionPane.showMessageDialog(this, ok?"Asistencia actualizada":"Error al actualizar"); 
                btnList.doClick(); 
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} 
        });

        return panel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // --- IMPLEMENTACIÓN: PANEL HERRAMIENTAS ---
    // -------------------------------------------------------------------------------------------------------------------------
    

        private JPanel crearPanelHerramientas() {
            JPanel panel = new JPanel(new BorderLayout(15, 15));
            panel.setBorder(new EmptyBorder(15, 15, 15, 15));
            panel.setBackground(COLOR_BACKGROUND);

            JTextArea txt = new JTextArea();
            txt.setEditable(false);
            txt.setFont(FONT_BODY.deriveFont(14f));
            JScrollPane scroll = new JScrollPane(txt);
            scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
            controlPanel.setOpaque(false);
            
            // --- Nuevo Panel de Consulta Detallada ---
            JPanel consultaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JComboBox<String> comboTablas = new JComboBox<>(new String[]{
                "Estudiantes", "Docentes", "Cursos", "Periodos", 
                "Clases", "Cortes", "Componentes", "Calificaciones", "Asistencias"
            });
            comboTablas.setFont(FONT_BODY.deriveFont(13f));
            comboTablas.setPreferredSize(new Dimension(150, 35));
            
            JButton btnConsultarDetallado = createStyledButton("Consultar Tabla", "refresh_white");
            
            consultaPanel.add(new JLabel("Seleccionar Tabla:"));
            consultaPanel.add(comboTablas);
            consultaPanel.add(btnConsultarDetallado);
            
            // --- Panel de Mantenimiento---
            JPanel mantenimientoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            JButton btnLimpiar = createStyledButton(" Limpiar Todos los Registros", "delete_white");
            
            mantenimientoPanel.add(btnLimpiar);

            // paneles de botones al controlPanel principal
            controlPanel.add(createFormCard(consultaPanel, "Consultar Datos por Tabla"));
            controlPanel.add(createFormCard(mantenimientoPanel, "Acciones de Mantenimiento"));

            panel.add(controlPanel, BorderLayout.NORTH);
            panel.add(scroll, BorderLayout.CENTER);

            // ------------------------------------
            // Lógica de Eventos de Herramientas
            // ------------------------------------

            // 1. Botón Consultar Detallado
            btnConsultarDetallado.addActionListener(e -> {
                String tablaSeleccionada = (String) comboTablas.getSelectedItem();
                String datos = consultarYFormatearDatos(tablaSeleccionada);
                txt.setText(datos);
                scroll.getVerticalScrollBar().setValue(0); // Volver al inicio
            });

            // 2. Botón Limpiar
            btnLimpiar.addActionListener(e -> {
                txt.setText("");
                txt.append("Limpiando base de datos...\n");
                
                txt.append("Limpieza completa (Llamada al DAO comentada para prevenir errores si la clase no existe)\n");
            });
            
            return panel;
        }



        // CONSULTAR DATOS

        private String consultarYFormatearDatos(String tabla) {
            StringBuilder sb = new StringBuilder();
            sb.append("==================================================\n");
            sb.append("CONSULTA DETALLADA: ").append(tabla.toUpperCase()).append("\n");
            sb.append("==================================================\n");

        try {
            switch (tabla) {
                case "Estudiantes":
                    EstudianteDAO edao = new EstudianteDAO();
                    List<Estudiante> estudiantes = edao.listarEstudiantes();
                    sb.append("Total de Registros: ").append(estudiantes.size()).append("\n\n");
                    for (Estudiante e : estudiantes) {
                        sb.append(String.format("ID: %d | Nombre: %s | Ident: %s\n", e.getEstudianteId(), e.getNombre(), e.getIdentificacion()));
                        sb.append(String.format(" Correo Inst: %s | Teléfono: %s | Género: %s\n", e.getCorreoInstitucional(), e.getTelefono(), e.getGenero()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Docentes":
                    DocenteDAO ddao = new DocenteDAO();
                    List<Docente> docentes = ddao.listarDocentes();
                    sb.append("Total de Registros: ").append(docentes.size()).append("\n\n");
                    for (Docente d : docentes) {
                        sb.append(String.format("ID: %d | Nombre: %s | Ident: %s\n", d.getDocenteId(), d.getNombreDocente(), d.getIdentificacion()));
                        sb.append(String.format(" Correo: %s | Título: %s | Idiomas: %s\n", d.getCorreo(), d.getTituloEstudios(), d.getIdiomas()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Cursos":
                    CursoDAO cdao = new CursoDAO();
                    List<Curso> cursos = cdao.listarCursos();
                    sb.append("Total de Registros: ").append(cursos.size()).append("\n\n");
                    for (Curso c : cursos) {
                        sb.append(String.format("ID: %d | Nombre: %s \n", c.getCursoId(), c.getNombreCurso()));
                        sb.append(String.format(" Periodo ID: %d | Docente ID: %d | Descripción: %s\n", c.getPeriodoAcademicoId(), c.getDocenteId(), c.getDescripcionCurso()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Periodos":
                    PeriodoAcademicoDAO pdao = new PeriodoAcademicoDAO();
                    List<PeriodoAcademico> periodos = pdao.listarPeriodos();
                    sb.append("Total de Registros: ").append(periodos.size()).append("\n\n");
                    for (PeriodoAcademico p : periodos) {
                        sb.append(String.format("ID: %d | Nombre: %s\n", p.getPeriodoAcademicoId(), p.getNombrePeriodo()));
                        sb.append(String.format(" Fechas: %s hasta %s\n", p.getFechaInicio(), p.getFechaFin()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;

                case "Clases":
                    ClaseDAO cladao = new ClaseDAO();
                    List<Clases> clases = cladao.listarClases();
                    sb.append("Total de Registros: ").append(clases.size()).append("\n\n");
                    for (Clases cl : clases) {
                        sb.append(String.format("ID: %d | Curso ID: %d | No. Clase: %d\n", cl.getClaseId(), cl.getCursoId(), cl.getNumeroClase()));
                        sb.append(String.format(" Fecha: %s | Tema: %s\n", cl.getFechaClase(), cl.getTemaClase().substring(0, Math.min(cl.getTemaClase().length(), 50)) + "..."));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Cortes":
                    CorteEvaluacionDAO cedao = new CorteEvaluacionDAO();
                    List<CorteEvaluacion> cortes = cedao.listarCortesEvaluacion();
                    sb.append("Total de Registros: ").append(cortes.size()).append("\n\n");
                    for (CorteEvaluacion ce : cortes) {
                        sb.append(String.format("ID: %d | Curso ID: %d | Periodo ID: %d\n", ce.getCorteEvaluacionId(), ce.getCursoId(), ce.getPeriodoAcademicoId()));
                        sb.append(String.format(" Nombre: %s | Porcentaje: %.2f%%\n", ce.getNombreCorte(), ce.getPorcentaje()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Componentes":
                    ComponenteEvaluacionDAO compdao = new ComponenteEvaluacionDAO();
                    List<ComponenteEvaluacion> componentes = compdao.listarComponentesEvaluacion();
                    sb.append("Total de Registros: ").append(componentes.size()).append("\n\n");
                    for (ComponenteEvaluacion comp : componentes) {
                        sb.append(String.format("ID: %d | Corte ID: %d\n", comp.getComponenteEvaluacionId(), comp.getCorteEvaluacionId()));
                        sb.append(String.format(" Nombre: %s | Porcentaje: %.2f%%\n", comp.getNombreComponente(), comp.getPorcentaje()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Calificaciones":
                    CalificacionDAO caldao = new CalificacionDAO();
                    List<Calificacion> calificaciones = caldao.listarCalificaciones();
                    sb.append("Total de Registros: ").append(calificaciones.size()).append("\n\n");
                    for (Calificacion cal : calificaciones) {
                        sb.append(String.format("ID: %d | Estudiante ID: %d | Comp. ID: %d\n", cal.getCalificacionId(), cal.getEstudianteId(), cal.getComponenteEvaluacionId()));
                        sb.append(String.format(" Nota: %.2f\n", cal.getNota()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                case "Asistencias":
                    AsistenciaDAO asidao = new AsistenciaDAO();
                    List<Asistencia> asistencias = asidao.listarAsistencias();
                    sb.append("Total de Registros: ").append(asistencias.size()).append("\n\n");
                    for (Asistencia a : asistencias) {
                        sb.append(String.format("ID: %d | Estudiante ID: %d | Curso ID: %d\n", a.getAsistenciaId(), a.getEstudianteId(), a.getCursoId()));
                        sb.append(String.format(" Fecha: %s | Estado: %s\n", a.getFechaClase(), a.getEstadoAsistencia()));
                        sb.append("--------------------------------------------------\n");
                    }
                    break;
                    default:
                        sb.append("Consulta no implementada para la tabla: ").append(tabla);
                }
            } catch (Exception ex) {
                sb.append("ERROR al acceder a la base de datos para ").append(tabla).append(". Asegúrese de que la conexión sea válida.\nError: ").append(ex.getMessage()).append("\n");
                
            }

            return sb.toString();
        }



           


    // ---------------------- MAIN ----------------------
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
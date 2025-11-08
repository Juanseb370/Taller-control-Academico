package com.controlacademico.Vista;

import com.controlacademico.dao.*;
import com.controlacademico.modelo.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {

    // Constantes de Estilo
    private static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 18);
    private static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 12);
    private static final Color COLOR_PRIMARY = new Color(0, 123, 255); // Azul moderno
    private static final Color COLOR_SECONDARY = new Color(220, 220, 220); // Gris claro para botones
    private static final Color COLOR_BACKGROUND = new Color(248, 249, 250); // Blanco roto
    private static final Color COLOR_HEADER_BG = new Color(52, 58, 64); // Gris oscuro para encabezados

    public VentanaPrincipal() {
        // 1. Aplicar Nimbus Look and Feel (Modernidad)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("No se pudo aplicar Nimbus LAF.");
        }

        setTitle("Control Académico | Dashboard de Administración");
        setSize(1300, 750); // Tamaño mayor para mejor visualización del dashboard
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND); // Fondo de la ventana principal

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_HEADER.deriveFont(Font.PLAIN, 14f)); // Pestañas más estilizadas
        
        // Asignación de Paneles
        tabs.addTab("Estudiantes 🎓", crearPanelEstudiantes());
        tabs.addTab("Docentes 🧑‍🏫", crearPanelDocentes());
        tabs.addTab("Cursos 📚", crearPanelCursos());
        tabs.addTab("Periodos 🗓️", crearPanelPeriodos());
        tabs.addTab("Clases 📅", crearPanelClases());
        tabs.addTab("Cortes ✂️", crearPanelCortes());
        tabs.addTab("Componentes ⚙️", crearPanelComponentes());
        tabs.addTab("Calificaciones 💯", crearPanelCalificaciones());
        tabs.addTab("Asistencias ✅", crearPanelAsistencias());
        tabs.addTab("Herramientas 🛠️", crearPanelHerramientas());

        add(tabs);
    }

    // Método para crear botones estilizados
    private JButton createStyledButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY.deriveFont(Font.BOLD, 13f));
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        // Intentar agregar iconos (asumiendo que existen en el classpath)
        try {
            if (iconPath != null) {
                btn.setIcon(new ImageIcon(getClass().getResource(iconPath)));
            }
        } catch (Exception e) {
            // Icono no encontrado, usar solo texto
        }
        return btn;
    }
    
    // Método para aplicar estilo moderno a JTables
    private void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.getTableHeader().setFont(FONT_BODY.deriveFont(Font.BOLD, 13f));
        table.getTableHeader().setBackground(COLOR_HEADER_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setSelectionBackground(COLOR_PRIMARY.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false); // Ocultar líneas de cuadrícula para un look más limpio
    }

    // Método de utilidad para envolver el formulario con estilo
    private JPanel createFormWrapper(JPanel formPanel, String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY.darker(), 1, true),
                title,
                0, 2, FONT_HEADER.deriveFont(14f), COLOR_PRIMARY.darker()
            ),
            new EmptyBorder(15, 15, 15, 15) // Espaciado interno
        ));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(formPanel, BorderLayout.CENTER);
        return wrapper;
    }
    
// -------------------------------------------------------------------------------------------------------------------------
// --- REDISEÑO DEL PANEL ESTUDIANTES ---
// -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelEstudiantes() {
        // Utilizamos un JPanel principal con un BoxLayout vertical para apilar el formulario y la tabla
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        // --- Formulario (Rediseñado con GridBagLayout para mejor control) ---
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtIdent = new JTextField(15);
        JTextField txtNombre = new JTextField(25);
        JTextField txtCorreoInst = new JTextField(20);
        JTextField txtCorreoPer = new JTextField(20);
        JTextField txtTelefono = new JTextField(15);
        JCheckBox chkVocero = new JCheckBox("Es vocero"); chkVocero.setOpaque(false);
        JTextField txtComentarios = new JTextField(30);
        JComboBox<String> comboTipoDoc = new JComboBox<>(new String[]{"CC", "TI", "CE", "PA"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});

        // Helper para añadir componentes al GBL
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; formContent.add(txtNombre, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtIdent, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Correo Institucional:"), gbc);
        gbc.gridx = 1; formContent.add(txtCorreoInst, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Correo Personal:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtCorreoPer, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; formContent.add(txtTelefono, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Tipo Documento:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboTipoDoc, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; formContent.add(comboGenero, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtComentarios, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(chkVocero, gbc);
        
        // Envolver el formulario
        JPanel formWrapper = createFormWrapper(formContent, "Datos del Estudiante");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT); // Asegura que no se estire horizontalmente

        // --- Botones (Estilizados) ---
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", "/icons/add.png");
        JButton btnUpdate = createStyledButton("Actualizar", "/icons/edit.png");
        JButton btnDelete = createStyledButton("Eliminar", "/icons/delete.png");
        JButton btnList = createStyledButton("Listar / Cargar IDs", "/icons/refresh.png");
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);

        // --- Tabla (Estilizada) ---
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Identificación", "Nombre", "Correo Inst", "Teléfono"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        // Agregar componentes al panel principal
        panel.add(formWrapper);
        panel.add(Box.createVerticalStrut(15)); // Espacio vertical
        panel.add(botones);
        panel.add(Box.createVerticalStrut(15)); // Espacio vertical
        panel.add(scroll);

        // Lógica de Eventos (Sin cambios lógicos, solo se reemplaza el contenido del panel)
        EstudianteDAO dao = new EstudianteDAO();

        btnInsert.addActionListener(e -> {
            try {
                Estudiante est = new Estudiante();
                est.setIdentificacion(txtIdent.getText());
                est.setNombre(txtNombre.getText());
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
                est.setIdentificacion(txtIdent.getText());
                est.setNombre(txtNombre.getText());
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
            
            // Recargar detalles completos (optimizado a un solo recorrido de la lista)
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
// --- REDISEÑO DEL PANEL DOCENTES ---
// -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelDocentes() {
        // Estructura similar a Estudiantes para consistencia visual (Dashboard Look)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        // Formulario
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextField txtIdent = new JTextField(15);
        JTextField txtCorreo = new JTextField(20);
        JTextField txtTitulo = new JTextField(15);
        JTextField txtIdiomas = new JTextField(20);
        JTextField txtCerts = new JTextField(20);
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"CC", "TI", "CE"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});

        // Helper para añadir componentes al GBL
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; formContent.add(txtNombre, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtIdent, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; formContent.add(txtCorreo, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Tipo Identificación:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboTipo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; formContent.add(comboGenero, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Idiomas:"), gbc);
        gbc.gridx = 1; formContent.add(txtIdiomas, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Certificaciones:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtCerts, gbc);

        JPanel formWrapper = createFormWrapper(formContent, "Datos del Docente");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", null); 
        JButton btnUpdate = createStyledButton("Actualizar", null); 
        JButton btnDelete = createStyledButton("Eliminar", null); 
        JButton btnList = createStyledButton("Listar / Cargar IDs", null);
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);

        // Tabla
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Identificación", "Correo"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15)); 
        panel.add(botones);
        panel.add(Box.createVerticalStrut(15)); 
        panel.add(scroll);

        // Lógica de Eventos (Se mantiene la lógica original)
        DocenteDAO dao = new DocenteDAO();

        btnInsert.addActionListener(e -> {
            try {
                Docente d = new Docente();
                d.setNombreDocente(txtNombre.getText());
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
                d.setNombreDocente(txtNombre.getText());
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
// --- REDISEÑO DEL PANEL CURSOS ---
// -------------------------------------------------------------------------------------------------------------------------
    private JPanel crearPanelCursos() {
        // Estructura vertical consistente
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        // Formulario con GridBagLayout
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextField txtDesc = new JTextField(30);
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JComboBox<Integer> comboDocente = new JComboBox<>();

        // Helper para añadir componentes al GBL
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre del Curso:"), gbc);
        gbc.gridx = 1; formContent.add(txtNombre, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtDesc, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Periodo ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboPeriodo, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Docente ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboDocente, gbc);

        JPanel formWrapper = createFormWrapper(formContent, "Datos del Curso");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert = createStyledButton("Insertar", null); 
        JButton btnUpdate = createStyledButton("Actualizar", null); 
        JButton btnDelete = createStyledButton("Eliminar", null); 
        JButton btnList = createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); 
        botones.add(btnDelete); botones.add(btnList);

        // Tabla
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Periodo ID", "Docente ID"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model); 
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);

        // Lógica de Eventos
        CursoDAO dao = new CursoDAO(); 
        PeriodoAcademicoDAO pdao = new PeriodoAcademicoDAO(); 
        DocenteDAO ddao = new DocenteDAO();

        btnList.addActionListener(e -> {
            model.setRowCount(0);
            comboPeriodo.removeAllItems();
            comboDocente.removeAllItems();
            // Cargar Combos
            pdao.listarPeriodos().forEach(p -> comboPeriodo.addItem(p.getPeriodoAcademicoId()));
            ddao.listarDocentes().forEach(d -> comboDocente.addItem(d.getDocenteId()));
            // Cargar Tabla
            dao.listarCursos().forEach(c -> model.addRow(new Object[]{
                c.getCursoId(), c.getNombreCurso(), c.getPeriodoAcademicoId(), c.getDocenteId()
            }));
        });

        btnInsert.addActionListener(e -> {
            try {
                Curso c = new Curso();
                c.setNombreCurso(txtNombre.getText());
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
                c.setNombreCurso(txtNombre.getText());
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
            // Se asume que el tercer campo de la tabla es la descripción en el código original,
            // pero el modelo de tabla solo tiene 4 columnas, por lo que adaptamos.
            // Para la descripción, necesitaríamos consultar el objeto Curso completo o ajustar el model de la tabla
            
            // Asumiendo que las columnas 2 y 3 contienen los IDs del Periodo y Docente:
            comboPeriodo.setSelectedItem(model.getValueAt(r, 2)); 
            comboDocente.setSelectedItem(model.getValueAt(r, 3)); 
            
            // Para la descripción, si no está en el modelo de la tabla, se necesita una consulta:
            try{
                int cursoId = (int) model.getValueAt(r, 0);
                Curso c = dao.listarCursos().stream()
                        .filter(x -> x.getCursoId() == cursoId) // <-- Usamos '==' para evitar dereferenciar si getCursoId() es 'int'
                        .findFirst().orElse(null);
                if(c != null) {
                   txtDesc.setText(c.getDescripcionCurso());
                }
            } catch(Exception ignored){}
        });

        return panel;
    }
    
// -------------------------------------------------------------------------------------------------------------------------
// --- REDISEÑO DEL PANEL PERIODOS (Y resto de paneles) ---
// -------------------------------------------------------------------------------------------------------------------------
    
    // NOTA: Se aplica el mismo patrón de diseño (BoxLayout + GridBagLayout estilizado) 
    // a los métodos restantes para mantener la consistencia visual, conservando la lógica original.

    private JPanel crearPanelPeriodos(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JTextField txtNombre = new JTextField(25);
        JTextField txtFechaInicio = new JTextField("YYYY-MM-DD", 15); // YYYY-MM-DD
        JTextField txtFechaFin = new JTextField("YYYY-MM-DD", 15); // YYYY-MM-DD

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre periodo:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; formContent.add(txtNombre, gbc);
        gbc.gridwidth = 1; row++;

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha inicio (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; formContent.add(txtFechaInicio, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Fecha fin (YYYY-MM-DD):"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtFechaFin, gbc);


        JPanel formWrapper = createFormWrapper(formContent, "Datos del Periodo Académico");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nombre","Inicio","Fin"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

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
                p.setFechaInicio(Date.valueOf(txtFechaInicio.getText()));
                p.setFechaFin(Date.valueOf(txtFechaFin.getText()));
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
                p.setPeriodoAcademicoId(id);
                p.setNombrePeriodo(txtNombre.getText());
                p.setFechaInicio(Date.valueOf(txtFechaInicio.getText()));
                p.setFechaFin(Date.valueOf(txtFechaFin.getText()));
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
        });
        
        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);
        return panel;
    }

    private JPanel crearPanelClases() {
        // Estructura vertical consistente
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JTextField txtNumero = new JTextField(10);
        JTextField txtFecha = new JTextField("YYYY-MM-DD", 15); // format yyyy-MM-dd
        JTextField txtTema = new JTextField(25);
        JTextField txtDesc = new JTextField(25);
        JTextField txtComentarios = new JTextField(25);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboCurso, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Número Clase:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtNumero, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; formContent.add(txtFecha, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtTema, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; formContent.add(txtDesc, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtComentarios, gbc);

        JPanel formWrapper = createFormWrapper(formContent, "Datos de la Clase");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso ID","Número","Fecha","Tema"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);

        ClaseDAO dao = new ClaseDAO(); CursoDAO cdao = new CursoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCurso.removeAllItems(); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(Clases cl: dao.listarClases()) model.addRow(new Object[]{cl.getClaseId(), cl.getCursoId(), cl.getNumeroClase(), cl.getFechaClase(), cl.getTemaClase()}); });

        btnInsert.addActionListener(e->{ try{ Clases cl=new Clases(); cl.setCursoId((Integer)comboCurso.getSelectedItem()); cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); cl.setFechaClase(Date.valueOf(txtFecha.getText())); cl.setTemaClase(txtTema.getText()); cl.setDescripcionClase(txtDesc.getText()); cl.setComentariosClase(txtComentarios.getText()); boolean ok=dao.insertarClase(cl); JOptionPane.showMessageDialog(this, ok?"Clase insertada":"Error al insertar clase"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarClase(id); JOptionPane.showMessageDialog(this, ok?"Clase eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Clases cl=new Clases(); cl.setClaseId(id); cl.setCursoId((Integer)comboCurso.getSelectedItem()); cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); cl.setFechaClase(Date.valueOf(txtFecha.getText())); cl.setTemaClase(txtTema.getText()); cl.setDescripcionClase(txtDesc.getText()); cl.setComentariosClase(txtComentarios.getText()); boolean ok=dao.actualizarClase(cl); JOptionPane.showMessageDialog(this, ok?"Clase actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        table.getSelectionModel().addListSelectionListener(ev->{ int r=table.getSelectedRow(); if(r==-1) return; txtId.setText(String.valueOf(model.getValueAt(r,0))); comboCurso.setSelectedItem(model.getValueAt(r,1)); txtNumero.setText(String.valueOf(model.getValueAt(r,2))); txtFecha.setText(String.valueOf(model.getValueAt(r,3))); txtTema.setText(String.valueOf(model.getValueAt(r,4))); 
            // Para la descripción y comentarios, se requeriría una consulta o cargar la lista completa.
        });

        return panel;
    }

    private JPanel crearPanelCortes(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);
        
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JTextField txtId = new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JTextField txtNombre = new JTextField(25);
        JTextField txtPorc = new JTextField(10);
        JTextField txtComentarios = new JTextField(25);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboCurso, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Periodo ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboPeriodo, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nombre Corte:"), gbc);
        gbc.gridx = 1; formContent.add(txtNombre, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Porcentaje:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtPorc, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.gridy = row++; formContent.add(txtComentarios, gbc);
        gbc.gridwidth = 1;

        JPanel formWrapper = createFormWrapper(formContent, "Datos del Corte de Evaluación");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso","Periodo","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); 
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);

        CorteEvaluacionDAO dao = new CorteEvaluacionDAO(); CursoDAO cdao=new CursoDAO(); PeriodoAcademicoDAO pdao=new PeriodoAcademicoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCurso.removeAllItems(); comboPeriodo.removeAllItems(); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(PeriodoAcademico p: pdao.listarPeriodos()) comboPeriodo.addItem(p.getPeriodoAcademicoId()); for(CorteEvaluacion ce: dao.listarCortesEvaluacion()) model.addRow(new Object[]{ce.getCorteEvaluacionId(), ce.getCursoId(), ce.getPeriodoAcademicoId(), ce.getNombreCorte(), ce.getPorcentaje()}); });

        btnInsert.addActionListener(e->{ try{ CorteEvaluacion ce=new CorteEvaluacion(); ce.setCursoId((Integer)comboCurso.getSelectedItem()); ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); ce.setNombreCorte(txtNombre.getText()); ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); ce.setComentariosCorte(txtComentarios.getText()); int id=dao.insertarCorteEvaluacionYObtenerId(ce); JOptionPane.showMessageDialog(this,id!=-1?"Corte insertado":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarCorteEvaluacion(id); JOptionPane.showMessageDialog(this, ok?"Corte eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); CorteEvaluacion ce=new CorteEvaluacion(); ce.setCorteEvaluacionId(id); ce.setCursoId((Integer)comboCurso.getSelectedItem()); ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); ce.setNombreCorte(txtNombre.getText()); ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); ce.setComentariosCorte(txtComentarios.getText()); boolean ok=dao.actualizarCorteEvaluacion(ce); JOptionPane.showMessageDialog(this, ok?"Corte actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });
        
        table.getSelectionModel().addListSelectionListener(ev->{ int r=table.getSelectedRow(); if(r==-1) return; txtId.setText(String.valueOf(model.getValueAt(r,0))); comboCurso.setSelectedItem(model.getValueAt(r,1)); comboPeriodo.setSelectedItem(model.getValueAt(r,2)); txtNombre.setText(String.valueOf(model.getValueAt(r,3))); txtPorc.setText(String.valueOf(model.getValueAt(r,4))); 
            // Para comentarios, se requeriría una consulta.
        });

        return panel;
    }

    private JPanel crearPanelComponentes(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboCorte=new JComboBox<>();
        JTextField txtNombre=new JTextField(25);
        JTextField txtPorc=new JTextField(10);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Corte ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboCorte, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Porcentaje:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtPorc, gbc);

        JPanel formWrapper = createFormWrapper(formContent, "Datos del Componente de Evaluación");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Corte","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        styleTable(table);
        JScrollPane scroll=new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);

        ComponenteEvaluacionDAO dao=new ComponenteEvaluacionDAO(); CorteEvaluacionDAO cdao=new CorteEvaluacionDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCorte.removeAllItems(); for(CorteEvaluacion ce: cdao.listarCortesEvaluacion()) comboCorte.addItem(ce.getCorteEvaluacionId()); for(ComponenteEvaluacion comp: dao.listarComponentesEvaluacion()) model.addRow(new Object[]{comp.getComponenteEvaluacionId(), comp.getCorteEvaluacionId(), comp.getNombreComponente(), comp.getPorcentaje()}); });

        btnInsert.addActionListener(e->{ try{ ComponenteEvaluacion comp=new ComponenteEvaluacion(); comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); comp.setNombreComponente(txtNombre.getText()); comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); int id=dao.insertarComponenteEvaluacionYObtenerid(comp); JOptionPane.showMessageDialog(this, id!=-1?"Componente insertado":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarComponenteEvaluacion(id); JOptionPane.showMessageDialog(this, ok?"Componente eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); ComponenteEvaluacion comp=new ComponenteEvaluacion(); comp.setComponenteEvaluacionId(id); comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); comp.setNombreComponente(txtNombre.getText()); comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); boolean ok=dao.actualizarComponenteEvaluacion(comp); JOptionPane.showMessageDialog(this, ok?"Componente actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        return panel;
    }

    private JPanel crearPanelCalificaciones(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboComp=new JComboBox<>();
        JTextField txtNota=new JTextField(10);
        JTextField txtComent=new JTextField(25);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Estudiante ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboEst, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Componente ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboComp, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1; formContent.add(txtNota, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(txtComent, gbc);

        JPanel formWrapper = createFormWrapper(formContent, "Registro de Calificación");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Componente","Nota"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        styleTable(table);
        JScrollPane scroll=new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);

        CalificacionDAO dao=new CalificacionDAO(); EstudianteDAO edao=new EstudianteDAO(); ComponenteEvaluacionDAO cdao=new ComponenteEvaluacionDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboEst.removeAllItems(); comboComp.removeAllItems(); for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); for(ComponenteEvaluacion comp: cdao.listarComponentesEvaluacion()) comboComp.addItem(comp.getComponenteEvaluacionId()); for(Calificacion cal: dao.listarCalificaciones()) model.addRow(new Object[]{cal.getCalificacionId(), cal.getEstudianteId(), cal.getComponenteEvaluacionId(), cal.getNota()}); });

        btnInsert.addActionListener(e->{ try{ Calificacion cal=new Calificacion(); cal.setEstudianteId((Integer)comboEst.getSelectedItem()); cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); cal.setNota(Double.parseDouble(txtNota.getText())); cal.setComentariosCalificacion(txtComent.getText()); int id=dao.insertarCalificacionYObtenerId(cal); JOptionPane.showMessageDialog(this, id!=-1?"Calificación insertada":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarCalificacion(id); JOptionPane.showMessageDialog(this, ok?"Calificación eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Calificacion cal=new Calificacion(); cal.setCalificacionId(id); cal.setEstudianteId((Integer)comboEst.getSelectedItem()); cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); cal.setNota(Double.parseDouble(txtNota.getText())); cal.setComentariosCalificacion(txtComent.getText()); boolean ok=dao.actualizarCalificacion(cal); JOptionPane.showMessageDialog(this, ok?"Calificación actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        return panel;
    }

    private JPanel crearPanelAsistencias(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JTextField txtId=new JTextField(10); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboCurso=new JComboBox<>();
        JTextField txtFecha=new JTextField("YYYY-MM-DD", 15);
        JComboBox<String> comboEstado=new JComboBox<>(new String[]{"Presente","Ausente","Tardanza"});
        JTextField txtNovedades=new JTextField(25);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formContent.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Estudiante ID:"), gbc);
        gbc.gridx = 1; formContent.add(comboEst, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Curso ID:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboCurso, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; formContent.add(txtFecha, gbc);
        gbc.gridx = 2; formContent.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3; gbc.gridy = row++; formContent.add(comboEstado, gbc);
        
        gbc.gridx = 0; gbc.gridy = row; formContent.add(new JLabel("Novedades:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.gridy = row++; formContent.add(txtNovedades, gbc);
        gbc.gridwidth = 1;


        JPanel formWrapper = createFormWrapper(formContent, "Registro de Asistencia");
        formWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Curso","Fecha","Estado"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); 
        styleTable(table);
        JScrollPane scroll=new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));
        
        JPanel botones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        botones.setOpaque(false);
        JButton btnInsert=createStyledButton("Insertar", null); 
        JButton btnUpdate=createStyledButton("Actualizar", null); 
        JButton btnDelete=createStyledButton("Eliminar", null); 
        JButton btnList=createStyledButton("Listar / Cargar IDs", null); 
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(formWrapper); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(botones); 
        panel.add(Box.createVerticalStrut(15));
        panel.add(scroll);
        
        AsistenciaDAO dao=new AsistenciaDAO(); EstudianteDAO edao=new EstudianteDAO(); CursoDAO cdao=new CursoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboEst.removeAllItems(); comboCurso.removeAllItems(); for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(Asistencia a: dao.listarAsistencias()) model.addRow(new Object[]{a.getAsistenciaId(), a.getEstudianteId(), a.getCursoId(), a.getFechaClase(), a.getEstadoAsistencia()}); });

        btnInsert.addActionListener(e->{ try{ Asistencia a=new Asistencia(); a.setEstudianteId((Integer)comboEst.getSelectedItem()); a.setCursoId((Integer)comboCurso.getSelectedItem()); a.setFechaClase(Date.valueOf(txtFecha.getText())); a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); a.setNovedades(txtNovedades.getText()); int id=dao.insertarAsistenciaYObtenerId(a); JOptionPane.showMessageDialog(this, id!=-1?"Asistencia insertada":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarAsistencia(id); JOptionPane.showMessageDialog(this, ok?"Asistencia eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Asistencia a=new Asistencia(); a.setAsistenciaId(id); a.setEstudianteId((Integer)comboEst.getSelectedItem()); a.setCursoId((Integer)comboCurso.getSelectedItem()); a.setFechaClase(Date.valueOf(txtFecha.getText())); a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); a.setNovedades(txtNovedades.getText()); boolean ok=dao.actualizarAsistencia(a); JOptionPane.showMessageDialog(this, ok?"Asistencia actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        return panel;
    }

    private JPanel crearPanelHerramientas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COLOR_BACKGROUND);

        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setFont(FONT_BODY.deriveFont(14f));
        JScrollPane scroll = new JScrollPane(txt);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY.brighter()));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setOpaque(false);
        JButton btnLimpiar = createStyledButton(" Limpiar Todos los registros de la Base de Datos", null);
        JButton btnListarTotales = createStyledButton(" Mostrar Toda la Información", null);

        botones.add(btnLimpiar);
        botones.add(btnListarTotales);
        panel.add(botones, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        btnLimpiar.addActionListener(e -> {
            txt.append("Limpiando base de datos...\n");
            LimpiarBDDAO.eliminarTodosLosRegistros();
            txt.append("Limpieza completa\n");
        });

        btnListarTotales.addActionListener(e -> {
            txt.setText("Totales:\n");
            txt.append("Estudiantes: " + new EstudianteDAO().listarEstudiantes().size() + "\n");
            txt.append("Docentes: " + new DocenteDAO().listarDocentes().size() + "\n");
            txt.append("Cursos: " + new CursoDAO().listarCursos().size() + "\n");
            txt.append("Clases: " + new ClaseDAO().listarClases().size() + "\n");
            txt.append("Calificaciones: " + new CalificacionDAO().listarCalificaciones().size() + "\n");
            txt.append("Asistencias: " + new AsistenciaDAO().listarAsistencias().size() + "\n");
            txt.append("Periodos Académicos: " + new PeriodoAcademicoDAO().listarPeriodos().size() + "\n");
        });

        return panel;
    }

    // ---------------------- MAIN ----------------------
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
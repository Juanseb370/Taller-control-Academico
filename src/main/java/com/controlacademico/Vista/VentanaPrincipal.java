
package com.controlacademico.Vista;

import com.controlacademico.dao.*;
import com.controlacademico.modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

/**
 * VentanaPrincipal - Interfaz Swing completa con pestañas para CRUD de todas las entidades.
 * Guardar en: src/com/controlacademico/vista/VentanaPrincipal.java
 * Requiere que tus DAOs (EstudianteDAO, DocenteDAO, CursoDAO, ClaseDAO, CorteEvaluacionDAO,
 * ComponenteEvaluacionDAO, CalificacionDAO, AsistenciaDAO, DocenteCursoDAO, PeriodoAcademicoDAO)
 * tengan los métodos insertar..., listar..., actualizar..., eliminar... como en la conversación.
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Control Académico - Administrador");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Estudiantes", crearPanelEstudiantes());
        tabs.addTab("Docentes", crearPanelDocentes());
        tabs.addTab("Cursos", crearPanelCursos());
        tabs.addTab("Periodos", crearPanelPeriodos());
        tabs.addTab("Clases", crearPanelClases());
        tabs.addTab("Cortes", crearPanelCortes());
        tabs.addTab("Componentes", crearPanelComponentes());
        tabs.addTab("Calificaciones", crearPanelCalificaciones());
        tabs.addTab("Asistencias", crearPanelAsistencias());
        tabs.addTab("Herramientas", crearPanelHerramientas());

        add(tabs);
    }

    // ---------------------- PANEL ESTUDIANTES ----------------------
    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Formulario
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtIdent = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreoInst = new JTextField();
        JTextField txtCorreoPer = new JTextField();
        JTextField txtTelefono = new JTextField();
        JCheckBox chkVocero = new JCheckBox("Es vocero");
        JTextField txtComentarios = new JTextField();
        JComboBox<String> comboTipoDoc = new JComboBox<>(new String[]{"CC","TI","CE"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino","Femenino","Otro"});

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Identificación:")); form.add(txtIdent);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Correo Institucional:")); form.add(txtCorreoInst);
        form.add(new JLabel("Correo Personal:")); form.add(txtCorreoPer);
        form.add(new JLabel("Teléfono:")); form.add(txtTelefono);
        form.add(new JLabel("Tipo Documento:")); form.add(comboTipoDoc);
        form.add(new JLabel("Género:")); form.add(comboGenero);
        form.add(new JLabel("Comentarios:")); form.add(txtComentarios);
        form.add(new JLabel("")); form.add(chkVocero);

        // Tabla
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Identificación","Nombre","Correo Inst","Teléfono"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // Botones
        JPanel botones = new JPanel();
        JButton btnInsert = new JButton("Insertar");
        JButton btnUpdate = new JButton("Actualizar");
        JButton btnDelete = new JButton("Eliminar");
        JButton btnList = new JButton("Listar");
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

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
                if(id!=-1) { JOptionPane.showMessageDialog(this,"Estudiante insertado con ID: "+id); btnList.doClick(); }
                else JOptionPane.showMessageDialog(this,"Error al insertar estudiante","Error",JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}        
        });

        btnList.addActionListener(e -> {
            model.setRowCount(0);
            List<Estudiante> lista = dao.listarEstudiantes();
            for(Estudiante est: lista){
                model.addRow(new Object[]{est.getEstudianteId(), est.getIdentificacion(), est.getNombre(), est.getCorreoInstitucional(), est.getTelefono()});
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila para eliminar"); return; }
            int id = (int)model.getValueAt(row,0);
            boolean ok = dao.eliminarEstudiante(id);
            JOptionPane.showMessageDialog(this, ok?"Estudiante eliminado":"Error al eliminar");
            btnList.doClick();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila para actualizar"); return; }
            try{
                int id = (int)model.getValueAt(row,0);
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
                JOptionPane.showMessageDialog(this, ok?"Estudiante actualizado":"Error al actualizar");
                btnList.doClick();
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}        
        });

        // Cuando seleccionen fila, llenar formulario
        table.getSelectionModel().addListSelectionListener(ev -> {
            int r = table.getSelectedRow();
            if(r==-1) return;
            txtId.setText(String.valueOf(model.getValueAt(r,0)));
            txtIdent.setText(String.valueOf(model.getValueAt(r,1)));
            txtNombre.setText(String.valueOf(model.getValueAt(r,2)));
            txtCorreoInst.setText(String.valueOf(model.getValueAt(r,3)));
            txtTelefono.setText(String.valueOf(model.getValueAt(r,4)));
            // The rest require fetching by id or from list - simplest: fetch full record
            try{
                Estudiante full = dao.listarEstudiantes().stream().filter(x->x.getEstudianteId()==Integer.parseInt(txtId.getText())).findFirst().orElse(null);
                if(full!=null){ txtCorreoPer.setText(full.getCorreoPersonal()); chkVocero.setSelected(full.isEsVocero()); txtComentarios.setText(full.getComentarios()); comboTipoDoc.setSelectedItem(full.getTipoDocumento()); comboGenero.setSelectedItem(full.getGenero()); }
            }catch(Exception ignore){}
        });

        return panel;
    }

    // ---------------------- PANEL DOCENTES ----------------------
    private JPanel crearPanelDocentes(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtNombre = new JTextField();
        JTextField txtIdent = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtIdiomas = new JTextField();
        JTextField txtCerts = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"CC","TI","CE"});
        JComboBox<String> comboGenero = new JComboBox<>(new String[]{"Masculino","Femenino","Otro"});

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Identificación:")); form.add(txtIdent);
        form.add(new JLabel("Correo:")); form.add(txtCorreo);
        form.add(new JLabel("Tipo Identificación:")); form.add(comboTipo);
        form.add(new JLabel("Género:")); form.add(comboGenero);
        form.add(new JLabel("Título:")); form.add(txtTitulo);
        form.add(new JLabel("Idiomas:")); form.add(txtIdiomas);
        form.add(new JLabel("Certificaciones:")); form.add(txtCerts);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nombre","Identificación","Correo"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); JScrollPane scroll = new JScrollPane(table);

        JPanel botones = new JPanel();
        JButton btnInsert = new JButton("Insertar"); JButton btnUpdate = new JButton("Actualizar"); JButton btnDelete = new JButton("Eliminar"); JButton btnList = new JButton("Listar");
        botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);

        DocenteDAO dao = new DocenteDAO();

        btnInsert.addActionListener(e->{
            try{
                Docente d = new Docente();
                d.setNombreDocente(txtNombre.getText()); d.setIdentificacion(txtIdent.getText()); d.setCorreo(txtCorreo.getText()); d.setTipoIdentificacion(comboTipo.getSelectedItem().toString()); d.setGenero(comboGenero.getSelectedItem().toString()); d.setTituloEstudios(txtTitulo.getText()); d.setIdiomas(txtIdiomas.getText()); d.setCertificaciones(txtCerts.getText());
                int id = dao.insertarDocente(d);
                JOptionPane.showMessageDialog(this, id!=-1?"Docente insertado ID: "+id:"Error al insertar docente"); btnList.doClick();
            }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}        
        });

        btnList.addActionListener(e->{ model.setRowCount(0); for(Docente d: dao.listarDocentes()) model.addRow(new Object[]{d.getDocenteId(), d.getNombreDocente(), d.getIdentificacion(), d.getCorreo()}); });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarDocente(id); JOptionPane.showMessageDialog(this, ok?"Docente eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Docente d=new Docente(); d.setDocenteId(id); d.setNombreDocente(txtNombre.getText()); d.setIdentificacion(txtIdent.getText()); d.setCorreo(txtCorreo.getText()); d.setTipoIdentificacion(comboTipo.getSelectedItem().toString()); d.setGenero(comboGenero.getSelectedItem().toString()); d.setTituloEstudios(txtTitulo.getText()); d.setIdiomas(txtIdiomas.getText()); d.setCertificaciones(txtCerts.getText()); boolean ok=dao.actualizarDocente(d); JOptionPane.showMessageDialog(this, ok?"Docente actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        table.getSelectionModel().addListSelectionListener(ev->{ int r=table.getSelectedRow(); if(r==-1) return; txtId.setText(String.valueOf(model.getValueAt(r,0))); txtNombre.setText(String.valueOf(model.getValueAt(r,1))); txtIdent.setText(String.valueOf(model.getValueAt(r,2))); txtCorreo.setText(String.valueOf(model.getValueAt(r,3))); try{ Docente full=dao.listarDocentes().stream().filter(x->x.getDocenteId()==Integer.parseInt(txtId.getText())).findFirst().orElse(null); if(full!=null){ comboTipo.setSelectedItem(full.getTipoIdentificacion()); comboGenero.setSelectedItem(full.getGenero()); txtTitulo.setText(full.getTituloEstudios()); txtIdiomas.setText(full.getIdiomas()); txtCerts.setText(full.getCertificaciones()); } }catch(Exception ignore){} });

        return panel;
    }

    // ---------------------- PANEL CURSOS ----------------------
    private JPanel crearPanelCursos(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtNombre = new JTextField();
        JTextField txtDesc = new JTextField();
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JComboBox<Integer> comboDocente = new JComboBox<>();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Descripción:")); form.add(txtDesc);
        form.add(new JLabel("Periodo ID:")); form.add(comboPeriodo);
        form.add(new JLabel("Docente ID:")); form.add(comboDocente);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nombre","Periodo","Docente"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); JScrollPane scroll = new JScrollPane(table);

        JPanel botones = new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);

        CursoDAO dao = new CursoDAO(); PeriodoAcademicoDAO pdao = new PeriodoAcademicoDAO(); DocenteDAO ddao = new DocenteDAO();

        // cargar combos con IDs existentes
        btnList.addActionListener(e->{ model.setRowCount(0); comboPeriodo.removeAllItems(); comboDocente.removeAllItems(); for(PeriodoAcademico p: pdao.listarPeriodos()) comboPeriodo.addItem(p.getPeriodoAcademicoId()); for(Docente d: ddao.listarDocentes()) comboDocente.addItem(d.getDocenteId()); for(Curso c: dao.listarCursos()) model.addRow(new Object[]{c.getCursoId(), c.getNombreCurso(), c.getPeriodoAcademicoId(), c.getDocenteId()}); });

        btnInsert.addActionListener(e->{ try{ Curso c=new Curso(); c.setNombreCurso(txtNombre.getText()); c.setDescripcionCurso(txtDesc.getText()); c.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); c.setDocenteId((Integer)comboDocente.getSelectedItem()); int id=dao.insertarCursoYObtenerId(c); JOptionPane.showMessageDialog(this, id!=-1?"Curso insertado ID: "+id:"Error al insertar curso"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarCurso(id); JOptionPane.showMessageDialog(this, ok?"Curso eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Curso c=new Curso(); c.setCursoId(id); c.setNombreCurso(txtNombre.getText()); c.setDescripcionCurso(txtDesc.getText()); c.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); c.setDocenteId((Integer)comboDocente.getSelectedItem()); boolean ok=dao.actualizarCurso(c); JOptionPane.showMessageDialog(this, ok?"Curso actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        table.getSelectionModel().addListSelectionListener(ev->{ int r=table.getSelectedRow(); if(r==-1) return; txtId.setText(String.valueOf(model.getValueAt(r,0))); txtNombre.setText(String.valueOf(model.getValueAt(r,1))); txtDesc.setText(String.valueOf(model.getValueAt(r,2))); comboPeriodo.setSelectedItem(model.getValueAt(r,2)); comboDocente.setSelectedItem(model.getValueAt(r,3)); });

        return panel;
    }
//-------------------------------------------------------------------------------------------------------------------------




//------------------------------------------------------------------------------------------------ FIN
    // ---------------------- PANEL PERIODOS ACADÉMICOS ----------------------
    private JPanel crearPanelPeriodos(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JTextField txtNombre = new JTextField();
        JTextField txtFechaInicio = new JTextField(); // YYYY-MM-DD
        JTextField txtFechaFin = new JTextField(); // YYYY-MM-DD

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre periodo:")); form.add(txtNombre);
        form.add(new JLabel("Fecha inicio (YYYY-MM-DD):")); form.add(txtFechaInicio);
        form.add(new JLabel("Fecha fin (YYYY-MM-DD):")); form.add(txtFechaFin);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nombre","Inicio","Fin"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); JScrollPane scroll = new JScrollPane(table);
        JPanel botones = new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

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

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------- PANEL CLASES ----------------------
    private JPanel crearPanelClases(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JTextField txtNumero = new JTextField();
        JTextField txtFecha = new JTextField(); // format yyyy-MM-dd
        JTextField txtTema = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtComentarios = new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Curso ID:")); form.add(comboCurso);
        form.add(new JLabel("Número Clase:")); form.add(txtNumero);
        form.add(new JLabel("Fecha (YYYY-MM-DD):")); form.add(txtFecha);
        form.add(new JLabel("Tema:")); form.add(txtTema);
        form.add(new JLabel("Descripción:")); form.add(txtDesc);
        form.add(new JLabel("Comentarios:")); form.add(txtComentarios);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso ID","Número","Fecha","Tema"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); JScrollPane scroll = new JScrollPane(table);
        JPanel botones = new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);

        ClaseDAO dao = new ClaseDAO(); CursoDAO cdao = new CursoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCurso.removeAllItems(); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(Clases cl: dao.listarClases()) model.addRow(new Object[]{cl.getClaseId(), cl.getCursoId(), cl.getNumeroClase(), cl.getFechaClase(), cl.getTemaClase()}); });

        btnInsert.addActionListener(e->{ try{ Clases cl=new Clases(); cl.setCursoId((Integer)comboCurso.getSelectedItem()); cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); cl.setFechaClase(Date.valueOf(txtFecha.getText())); cl.setTemaClase(txtTema.getText()); cl.setDescripcionClase(txtDesc.getText()); cl.setComentariosClase(txtComentarios.getText()); boolean ok=dao.insertarClase(cl); JOptionPane.showMessageDialog(this, ok?"Clase insertada":"Error al insertar clase"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarClase(id); JOptionPane.showMessageDialog(this, ok?"Clase eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Clases cl=new Clases(); cl.setClaseId(id); cl.setCursoId((Integer)comboCurso.getSelectedItem()); cl.setNumeroClase(Integer.parseInt(txtNumero.getText())); cl.setFechaClase(Date.valueOf(txtFecha.getText())); cl.setTemaClase(txtTema.getText()); cl.setDescripcionClase(txtDesc.getText()); cl.setComentariosClase(txtComentarios.getText()); boolean ok=dao.actualizarClase(cl); JOptionPane.showMessageDialog(this, ok?"Clase actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        table.getSelectionModel().addListSelectionListener(ev->{ int r=table.getSelectedRow(); if(r==-1) return; txtId.setText(String.valueOf(model.getValueAt(r,0))); comboCurso.setSelectedItem(model.getValueAt(r,1)); txtNumero.setText(String.valueOf(model.getValueAt(r,2))); txtFecha.setText(String.valueOf(model.getValueAt(r,3))); txtTema.setText(String.valueOf(model.getValueAt(r,4))); });

        return panel;
    }

    // ---------------------- PANEL CORTES ----------------------
    private JPanel crearPanelCortes(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId = new JTextField(); txtId.setEditable(false);
        JComboBox<Integer> comboCurso = new JComboBox<>();
        JComboBox<Integer> comboPeriodo = new JComboBox<>();
        JTextField txtNombre = new JTextField();
        JTextField txtPorc = new JTextField();
        JTextField txtComentarios = new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Curso ID:")); form.add(comboCurso);
        form.add(new JLabel("Periodo ID:")); form.add(comboPeriodo);
        form.add(new JLabel("Nombre Corte:")); form.add(txtNombre);
        form.add(new JLabel("Porcentaje:")); form.add(txtPorc);
        form.add(new JLabel("Comentarios:")); form.add(txtComentarios);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Curso","Periodo","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model); JScrollPane scroll = new JScrollPane(table);
        JPanel botones = new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        CorteEvaluacionDAO dao = new CorteEvaluacionDAO(); CursoDAO cdao=new CursoDAO(); PeriodoAcademicoDAO pdao=new PeriodoAcademicoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCurso.removeAllItems(); comboPeriodo.removeAllItems(); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(PeriodoAcademico p: pdao.listarPeriodos()) comboPeriodo.addItem(p.getPeriodoAcademicoId()); for(CorteEvaluacion ce: dao.listarCortesEvaluacion()) model.addRow(new Object[]{ce.getCorteEvaluacionId(), ce.getCursoId(), ce.getPeriodoAcademicoId(), ce.getNombreCorte(), ce.getPorcentaje()}); });

        btnInsert.addActionListener(e->{ try{ CorteEvaluacion ce=new CorteEvaluacion(); ce.setCursoId((Integer)comboCurso.getSelectedItem()); ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); ce.setNombreCorte(txtNombre.getText()); ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); ce.setComentariosCorte(txtComentarios.getText()); int id=dao.insertarCorteEvaluacionYObtenerId(ce); JOptionPane.showMessageDialog(this,id!=-1?"Corte insertado":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarCorteEvaluacion(id); JOptionPane.showMessageDialog(this, ok?"Corte eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); CorteEvaluacion ce=new CorteEvaluacion(); ce.setCorteEvaluacionId(id); ce.setCursoId((Integer)comboCurso.getSelectedItem()); ce.setPeriodoAcademicoId((Integer)comboPeriodo.getSelectedItem()); ce.setNombreCorte(txtNombre.getText()); ce.setPorcentaje(Double.parseDouble(txtPorc.getText())); ce.setComentariosCorte(txtComentarios.getText()); boolean ok=dao.actualizarCorteEvaluacion(ce); JOptionPane.showMessageDialog(this, ok?"Corte actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------- PANEL COMPONENTES ----------------------
    private JPanel crearPanelComponentes(){
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId=new JTextField(); txtId.setEditable(false);
        JComboBox<Integer> comboCorte=new JComboBox<>();
        JTextField txtNombre=new JTextField();
        JTextField txtPorc=new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Corte ID:")); form.add(comboCorte);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Porcentaje:")); form.add(txtPorc);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Corte","Nombre","%"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); JScrollPane scroll=new JScrollPane(table);
        JPanel botones=new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        ComponenteEvaluacionDAO dao=new ComponenteEvaluacionDAO(); CorteEvaluacionDAO cdao=new CorteEvaluacionDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboCorte.removeAllItems(); for(CorteEvaluacion ce: cdao.listarCortesEvaluacion()) comboCorte.addItem(ce.getCorteEvaluacionId()); for(ComponenteEvaluacion comp: dao.listarComponentesEvaluacion()) model.addRow(new Object[]{comp.getComponenteEvaluacionId(), comp.getCorteEvaluacionId(), comp.getNombreComponente(), comp.getPorcentaje()}); });

        btnInsert.addActionListener(e->{ try{ ComponenteEvaluacion comp=new ComponenteEvaluacion(); comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); comp.setNombreComponente(txtNombre.getText()); comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); int id=dao.insertarComponenteEvaluacionYObtenerid(comp); JOptionPane.showMessageDialog(this, id!=-1?"Componente insertado":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarComponenteEvaluacion(id); JOptionPane.showMessageDialog(this, ok?"Componente eliminado":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); ComponenteEvaluacion comp=new ComponenteEvaluacion(); comp.setComponenteEvaluacionId(id); comp.setCorteEvaluacionId((Integer)comboCorte.getSelectedItem()); comp.setNombreComponente(txtNombre.getText()); comp.setPorcentaje(Double.parseDouble(txtPorc.getText())); boolean ok=dao.actualizarComponenteEvaluacion(comp); JOptionPane.showMessageDialog(this, ok?"Componente actualizado":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------- PANEL CALIFICACIONES ----------------------
    private JPanel crearPanelCalificaciones(){
        JPanel panel=new JPanel(new BorderLayout(10,10));
        JPanel form=new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId=new JTextField(); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboComp=new JComboBox<>();
        JTextField txtNota=new JTextField();
        JTextField txtComent=new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Estudiante ID:")); form.add(comboEst);
        form.add(new JLabel("Componente ID:")); form.add(comboComp);
        form.add(new JLabel("Nota:")); form.add(txtNota);
        form.add(new JLabel("Comentarios:")); form.add(txtComent);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Componente","Nota"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); JScrollPane scroll=new JScrollPane(table);
        JPanel botones=new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        CalificacionDAO dao=new CalificacionDAO(); EstudianteDAO edao=new EstudianteDAO(); ComponenteEvaluacionDAO cdao=new ComponenteEvaluacionDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboEst.removeAllItems(); comboComp.removeAllItems(); for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); for(ComponenteEvaluacion comp: cdao.listarComponentesEvaluacion()) comboComp.addItem(comp.getComponenteEvaluacionId()); for(Calificacion cal: dao.listarCalificaciones()) model.addRow(new Object[]{cal.getCalificacionId(), cal.getEstudianteId(), cal.getComponenteEvaluacionId(), cal.getNota()}); });

        btnInsert.addActionListener(e->{ try{ Calificacion cal=new Calificacion(); cal.setEstudianteId((Integer)comboEst.getSelectedItem()); cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); cal.setNota(Double.parseDouble(txtNota.getText())); cal.setComentariosCalificacion(txtComent.getText()); int id=dao.insertarCalificacionYObtenerId(cal); JOptionPane.showMessageDialog(this, id!=-1?"Calificación insertada":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarCalificacion(id); JOptionPane.showMessageDialog(this, ok?"Calificación eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Calificacion cal=new Calificacion(); cal.setCalificacionId(id); cal.setEstudianteId((Integer)comboEst.getSelectedItem()); cal.setComponenteEvaluacionId((Integer)comboComp.getSelectedItem()); cal.setNota(Double.parseDouble(txtNota.getText())); cal.setComentariosCalificacion(txtComent.getText()); boolean ok=dao.actualizarCalificacion(cal); JOptionPane.showMessageDialog(this, ok?"Calificación actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------- PANEL ASISTENCIAS ----------------------
    private JPanel crearPanelAsistencias(){
        JPanel panel=new JPanel(new BorderLayout(10,10));
        JPanel form=new JPanel(new GridLayout(0,2,8,8));
        JTextField txtId=new JTextField(); txtId.setEditable(false);
        JComboBox<Integer> comboEst=new JComboBox<>();
        JComboBox<Integer> comboCurso=new JComboBox<>();
        JTextField txtFecha=new JTextField();
        JComboBox<String> comboEstado=new JComboBox<>(new String[]{"Presente","Ausente","Tardanza"});
        JTextField txtNovedades=new JTextField();

        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Estudiante ID:")); form.add(comboEst);
        form.add(new JLabel("Curso ID:")); form.add(comboCurso);
        form.add(new JLabel("Fecha (YYYY-MM-DD):")); form.add(txtFecha);
        form.add(new JLabel("Estado:")); form.add(comboEstado);
        form.add(new JLabel("Novedades:")); form.add(txtNovedades);

        DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Estudiante","Curso","Fecha","Estado"},0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model); JScrollPane scroll=new JScrollPane(table);
        JPanel botones=new JPanel(); JButton btnInsert=new JButton("Insertar"); JButton btnUpdate=new JButton("Actualizar"); JButton btnDelete=new JButton("Eliminar"); JButton btnList=new JButton("Listar"); botones.add(btnInsert); botones.add(btnUpdate); botones.add(btnDelete); botones.add(btnList);

        AsistenciaDAO dao=new AsistenciaDAO(); EstudianteDAO edao=new EstudianteDAO(); CursoDAO cdao=new CursoDAO();

        btnList.addActionListener(e->{ model.setRowCount(0); comboEst.removeAllItems(); comboCurso.removeAllItems(); for(Estudiante est: edao.listarEstudiantes()) comboEst.addItem(est.getEstudianteId()); for(Curso c: cdao.listarCursos()) comboCurso.addItem(c.getCursoId()); for(Asistencia a: dao.listarAsistencias()) model.addRow(new Object[]{a.getAsistenciaId(), a.getEstudianteId(), a.getCursoId(), a.getFechaClase(), a.getEstadoAsistencia()}); });

        btnInsert.addActionListener(e->{ try{ Asistencia a=new Asistencia(); a.setEstudianteId((Integer)comboEst.getSelectedItem()); a.setCursoId((Integer)comboCurso.getSelectedItem()); a.setFechaClase(Date.valueOf(txtFecha.getText())); a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); a.setNovedades(txtNovedades.getText()); int id=dao.insertarAsistenciaYObtenerId(a); JOptionPane.showMessageDialog(this, id!=-1?"Asistencia insertada":"Error"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        btnDelete.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} int id=(int)model.getValueAt(r,0); boolean ok=dao.eliminarAsistencia(id); JOptionPane.showMessageDialog(this, ok?"Asistencia eliminada":"Error al eliminar"); btnList.doClick(); });

        btnUpdate.addActionListener(e->{ int r=table.getSelectedRow(); if(r==-1){JOptionPane.showMessageDialog(this,"Seleccione fila");return;} try{ int id=(int)model.getValueAt(r,0); Asistencia a=new Asistencia(); a.setAsistenciaId(id); a.setEstudianteId((Integer)comboEst.getSelectedItem()); a.setCursoId((Integer)comboCurso.getSelectedItem()); a.setFechaClase(Date.valueOf(txtFecha.getText())); a.setEstadoAsistencia(comboEstado.getSelectedItem().toString()); a.setNovedades(txtNovedades.getText()); boolean ok=dao.actualizarAsistencia(a); JOptionPane.showMessageDialog(this, ok?"Asistencia actualizada":"Error al actualizar"); btnList.doClick(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);} });

        panel.add(form,BorderLayout.NORTH); panel.add(scroll,BorderLayout.CENTER); panel.add(botones,BorderLayout.SOUTH);
        return panel;
    }

    // ---------------------- PANEL HERRAMIENTAS ----------------------
    private JPanel crearPanelHerramientas() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    JTextArea txt = new JTextArea();
    txt.setEditable(false);
    JScrollPane scroll = new JScrollPane(txt);

    JPanel botones = new JPanel();
    JButton btnLimpiar = new JButton(" Limpiar Todos los registros de la Base de Datos");
    JButton btnListarTotales = new JButton(" Mostrar Toda la Información");

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
        txt.append("Totales:\n");
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

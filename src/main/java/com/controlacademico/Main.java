// package com.controlacademico;

// import com.controlacademico.dao.*;
// import com.controlacademico.modelo.*;
// import java.sql.Date;


// public class Main {
//     public static void main(String[] args) {


// ///--------------------------------------ESTE ES EL METODOPARA ELIMINAR TODOS LOS REGISTROS DE LA BASE DE DATOS

//         //Esto eliminará TODOS los datos de la base de datos
//         System.out.println(" INICIANDO LIMPIEZA DE LA BASE DE DATOS...");
//         LimpiarBDDAO.eliminarTodosLosRegistros();
//         System.out.println(" LIMPIEZA FINALIZADA.");
    

// //--------------------------------------------------------------------------------------------------------------------------------------------------------------


//         //--------------------------------------------------------------------------------------------------------------------------------------------

//         //-------------------------------------- INSERTAR INFORMACIÓN EN LA BASE DE DATOS --------------------------------------

//         //---------------------------  INSERTAR ESTUDIANTE ---------------------------------
//         Estudiante nuevoEstudiante = new Estudiante();
//         nuevoEstudiante.setIdentificacion("1005872947");
//         nuevoEstudiante.setNombre("Juan Almendra");
//         nuevoEstudiante.setCorreoInstitucional("ALMENDRA@estudiante.uniajc.edu.co");
//         nuevoEstudiante.setCorreoPersonal("almendra@gmail.com");
//         nuevoEstudiante.setTelefono("3225973565");
//         nuevoEstudiante.setEsVocero(false);
//         nuevoEstudiante.setComentarios("estudiante de cuarto semestre.");
//         nuevoEstudiante.setTipoDocumento("CC");
//         nuevoEstudiante.setGenero("Masculino");
//         EstudianteDAO daoEstudiante = new EstudianteDAO();
//         int insertadoEstudiante = daoEstudiante.insertarEstudiante(nuevoEstudiante);

//         if (insertadoEstudiante == -1) {
//             System.out.println(" Error al insertar el estudiante.");
//             return;
//         }
       
        


//         //---------------------------  INSERTAR DOCENTE ---------------------------------
//         Docente nuevoDocente = new Docente();
//         nuevoDocente.setNombreDocente("JUAN PEREZ GOMEZ");
//         nuevoDocente.setIdentificacion("98132654");
//         nuevoDocente.setTipoIdentificacion("CC");
//         nuevoDocente.setGenero("masculino");
//         nuevoDocente.setCorreo("JPG.gomez@uniajc.edu.co");
//         nuevoDocente.setTituloEstudios("Ingeniería de Sistemas");
//         nuevoDocente.setIdiomas("Inglés");
//         nuevoDocente.setCertificaciones("Oracle Java SE 11");

//         DocenteDAO daoDocente = new DocenteDAO();
//         int idDocenteGenerado = daoDocente.insertarDocente(nuevoDocente);



//         //---------------------------  INSERTAR PERIODO ACADÉMICO ---------------------------------
//         PeriodoAcademico nuevoPeriodo = new PeriodoAcademico();
//         nuevoPeriodo.setNombrePeriodo("CUARTO SEMESTRE 2025");
//         nuevoPeriodo.setFechaInicio(Date.valueOf("2025-01-15"));
//         nuevoPeriodo.setFechaFin(Date.valueOf("2025-06-30"));

//         PeriodoAcademicoDAO daoPeriodo = new PeriodoAcademicoDAO();
//         int idPeriodoGenerado = daoPeriodo.insertarPeriodoAcademico(nuevoPeriodo);




//         //---------------------------  INSERTAR CURSO ---------------------------------
//         Curso nuevoCurso = new Curso();
//         nuevoCurso.setNombreCurso("Programación Orientada a Objetos");
//         nuevoCurso.setDescripcionCurso("Curso enfocado en los principios de POO usando Java.");
//         nuevoCurso.setPeriodoAcademicoId(idPeriodoGenerado);
//         nuevoCurso.setDocenteId(idDocenteGenerado); // relación directa en tabla cursos

//         CursoDAO daoCurso = new CursoDAO();
//         int idCursoGenerado = daoCurso.insertarCursoYObtenerId(nuevoCurso); //  ahora este método devuelve el ID del curso





//         //---------------------------  METODO PARA QUE SE INSERTEN AUTOMATICAMENTE LOS IDS DE DOCENTE Y CURSO EN LA TABLA DOCENTE CURSO ---------------------------------
//         DocenteCurso relacionDocentesCursos = new DocenteCurso();
//         relacionDocentesCursos.setDocenteId(idDocenteGenerado); // Usar el ID del docente recién insertado
//         relacionDocentesCursos.setCursoId(idCursoGenerado);
//         DocenteCursoDAO daoDocenteCurso = new DocenteCursoDAO();
//         boolean insertadoDocenteCurso = daoDocenteCurso.insertarDocenteCurso(relacionDocentesCursos);
//         if (insertadoDocenteCurso) {
//             System.out.println(" Relación Docente-Curso insertada correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar la relación Docente-Curso.");


//     }



        


//             //---------------------------- METODO PARA INSERTAR CLASE EN LA BASE DE DATOS ---------------------------------

//             Clases nuevaClases = new Clases();
//             nuevaClases.setCursoId(idCursoGenerado); // Usar el ID del curso recién insertado
//             nuevaClases.setNumeroClase(1);
//             nuevaClases.setFechaClase(Date.valueOf("2025-03-01"));
//             nuevaClases.setTemaClase("Introducción a la Programación Orientada a Objetos");
//             nuevaClases.setDescripcionClase("Explicación de los principios básicos de la POO: clases, objetos, herencia y polimorfismo.");
//             nuevaClases.setComentariosClase("Los estudiantes mostraron buen interés."); 
//             ClaseDAO daoClase = new ClaseDAO();
//             boolean insertadoClase = daoClase.insertarClase(nuevaClases);

         

//             if (insertadoClase) {
//                 System.out.println(" Clase Insertada Correctamente.");
//             } else {
//                 System.out.println(" No se pudo insertar la Clase.");
//             }
            


//             //------------------------------METODO PARA INSERTAR CORTE DE EVALUACIÓN EN LA BASE DE DATOS ---------------------------------
//             CorteEvaluacion nuevoCorte = new CorteEvaluacion();
//             nuevoCorte.setCursoId(idCursoGenerado);             // Usar el ID del curso recién insertado
//             nuevoCorte.setPeriodoAcademicoId(idPeriodoGenerado);   // Usar el ID del periodo recién insertado
//             nuevoCorte.setNombreCorte("Primer Corte");
//             nuevoCorte.setPorcentaje(30.0);
//             nuevoCorte.setComentariosCorte("Evaluación de la primera mitad del curso.");

//             CorteEvaluacionDAO daoCorte = new CorteEvaluacionDAO();
            
//             int idCortegenerado = daoCorte.insertarCorteEvaluacionYObtenerId(nuevoCorte);
           
//             if (idCortegenerado != -1) {
//                 System.out.println(" Corte de evaluación insertado correctamente.");
//             } else {
//                 System.out.println(" No se pudo insertar el corte de evaluación.");
//             }



//             //---------------------------METODO PARA INSERTAR COMPONENTE DE EVALUACIÓN EN LA BASE DE DATOS con EL ID DEL CORTE RECIÉN INSERTADO---------------------------------
//             ComponenteEvaluacion nuevoComponente = new ComponenteEvaluacion();
//             nuevoComponente.setCorteEvaluacionId(idCortegenerado); // Usar el ID del corte recién insertado
//             nuevoComponente.setNombreComponente("Examen Parcial");
//             nuevoComponente.setPorcentaje(50.0);    
//             ComponenteEvaluacionDAO daoComponente = new ComponenteEvaluacionDAO();
            
//              int idComponenteGenerado = daoComponente.insertarComponenteEvaluacionYObtenerid(nuevoComponente);

//                 if (idComponenteGenerado != -1) {
//                     System.out.println(" Componente de evaluación insertado correctamente. ID generado: " + idComponenteGenerado);
//                 } else {
//                     System.out.println(" No se pudo insertar el componente de evaluación.");
// }


//             //--------------------------------------------------------------------------------------------

  
//             //--------------------------- INSERTAR CALIFICACIÓN ---------------------------------
//             Calificacion nuevaCalificacion = new Calificacion();
//             nuevaCalificacion.setEstudianteId(insertadoEstudiante); // ID del estudiante recién insertado
//             nuevaCalificacion.setComponenteEvaluacionId(idComponenteGenerado); // ID del componente recién insertado
//             nuevaCalificacion.setNota(4.5);
//             nuevaCalificacion.setComentariosCalificacion("Excelente desempeño en el examen parcial.");

//             CalificacionDAO daoCalificacion = new CalificacionDAO();
//             int idCalificacionGenerada = daoCalificacion.insertarCalificacionYObtenerId(nuevaCalificacion);

//             if (idCalificacionGenerada != -1) {
//                 System.out.println(" Calificación insertada correctamente con ID: " + idCalificacionGenerada);
//             } else {
//                 System.out.println(" No se pudo insertar la calificación.");
//             }

// //-------------------------------------------------------------------

//             //--------------------------- INSERTAR ASISTENCIA ---------------------------------


//                 Asistencia nuevaAsistencia = new Asistencia();
//                 nuevaAsistencia.setEstudianteId(insertadoEstudiante); // ID del estudiante insertado
//                 nuevaAsistencia.setCursoId(idCursoGenerado);          // ID del curso insertado
//                 nuevaAsistencia.setFechaClase(Date.valueOf("2025-10-28"));
//                 nuevaAsistencia.setEstadoAsistencia("Presente");        // VALORES SON:: PRESENTE, AUSENTE, TARDANZA
//                 nuevaAsistencia.setNovedades("Llegó 10 minutos tarde, pero participó activamente.");

//                 AsistenciaDAO daoAsistencia = new AsistenciaDAO();
//                 int idAsistenciaGenerada = daoAsistencia.insertarAsistenciaYObtenerId(nuevaAsistencia);

//                 if (idAsistenciaGenerada != -1) {
//                     System.out.println(" Asistencia registrada correctamente con ID: " + idAsistenciaGenerada);
//                 } else {
//                     System.out.println(" No se pudo registrar la asistencia.");
//                 }

// //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// //------------------------------------------------LISTAR INFORMACION



//                 //------------------------------------ LISTAR ESTUDIANTES ------------------------------------
//             System.out.println("\n LISTADO DE ESTUDIANTES:");
//             for (Estudiante e : daoEstudiante.listarEstudiantes()) {
//                 System.out.println("ID: " + e.getEstudianteId() + " | Nombre: " + e.getNombre() + " | Correo: " + e.getCorreoInstitucional());
//             }

//             //------------------------------------ LISTAR DOCENTES ------------------------------------
//             System.out.println("\n LISTADO DE DOCENTES:");
//             for (Docente d : daoDocente.listarDocentes()) {
//                 System.out.println("ID: " + d.getDocenteId() + " | Nombre: " + d.getNombreDocente() + " | Correo: " + d.getCorreo());
//             }

//             //------------------------------------ LISTAR CURSOS ------------------------------------
//             System.out.println("\n LISTADO DE CURSOS:");
//             for (Curso c : daoCurso.listarCursos()) {
//                 System.out.println("ID: " + c.getCursoId() + " | Nombre: " + c.getNombreCurso() + " | Descripción: " + c.getDescripcionCurso());
//             }

//             //------------------------------------ LISTAR CLASES ------------------------------------
//             System.out.println("\n LISTADO DE CLASES:");
//             for (Clases clase : daoClase.listarClases()) {
//                 System.out.println("ID: " + clase.getClaseId() + " | Tema: " + clase.getTemaClase() + " | Fecha: " + clase.getFechaClase());
//             }

//             //------------------------------------ LISTAR CORTES DE EVALUACIÓN ------------------------------------
//             System.out.println("\n LISTADO DE CORTES DE EVALUACIÓN:");
//             for (CorteEvaluacion corte : daoCorte.listarCortesEvaluacion()) {
//                 System.out.println("ID: " + corte.getCorteEvaluacionId() + " | Nombre: " + corte.getNombreCorte() + " | Porcentaje: " + corte.getPorcentaje());
//             }

//             //------------------------------------ LISTAR COMPONENTES DE EVALUACIÓN ------------------------------------
//             System.out.println("\n LISTADO DE COMPONENTES DE EVALUACIÓN:");
//             for (ComponenteEvaluacion comp : daoComponente.listarComponentesEvaluacion()) {
//                 System.out.println("ID: " + comp.getComponenteEvaluacionId() + " | Nombre: " + comp.getNombreComponente() + " | Porcentaje: " + comp.getPorcentaje());
//             }

//             //------------------------------------ LISTAR CALIFICACIONES ------------------------------------
//             CalificacionDAO daocalificacion = new CalificacionDAO();
//             System.out.println("\n LISTADO DE CALIFICACIONES:");
//             for (Calificacion cal : daocalificacion.listarCalificaciones()) {
//                 System.out.println("ID: " + cal.getCalificacionId() + " | Estudiante ID: " + cal.getEstudianteId() +
//                         " | Nota: " + cal.getNota() + " | Comentarios: " + cal.getComentariosCalificacion());
//             }

//             //------------------------------------ LISTAR ASISTENCIAS ------------------------------------
//             AsistenciaDAO daoasistencia = new AsistenciaDAO();
//             System.out.println("\n LISTADO DE ASISTENCIAS:");
//             for (Asistencia asis : daoasistencia.listarAsistencias()) {
//                 System.out.println("ID: " + asis.getAsistenciaId() + " | Estudiante ID: " + asis.getEstudianteId() +
//                         " | Curso ID: " + asis.getCursoId() + " | Estado: " + asis.getEstadoAsistencia() +
//                         " | Fecha: " + asis.getFechaClase());
//             }




// //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// //----------------------- actualizar general




//         System.out.println("\n=====  ACTUALIZACIÓN DE REGISTROS =====");

//         // ================================
//         // DAO INSTANCIAS
//         // ================================
//         EstudianteDAO estudianteDAO = new EstudianteDAO();
//         DocenteDAO docenteDAO = new DocenteDAO();
//         CursoDAO cursoDAO = new CursoDAO();
//         CorteEvaluacionDAO corteDAO = new CorteEvaluacionDAO();
//         ComponenteEvaluacionDAO componenteDAO = new ComponenteEvaluacionDAO();
//         CalificacionDAO calificacionDAO = new CalificacionDAO();
//         AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

//         // ================================
//         // ESTUDIANTE
//         // ================================
//         Estudiante estudiante = new Estudiante();
//         estudiante.setEstudianteId(1); // ID existente
//         estudiante.setIdentificacion("100200300");
//         estudiante.setNombre("Carlos Actualizado");
//         estudiante.setCorreoInstitucional("carlos.actualizado@uniajc.edu.co");
//         estudiante.setCorreoPersonal("carlosactualizado@gmail.com");
//         estudiante.setTelefono("3105559999");
//         estudiante.setEsVocero(false);
//         estudiante.setComentarios("Actualización de datos del estudiante");
//         estudiante.setTipoDocumento("CC");
//         estudiante.setGenero("M");

//         boolean actualizadoEst = estudianteDAO.actualizarEstudiante(estudiante);
//         System.out.println(actualizadoEst ? " Estudiante actualizado correctamente" : " Error al actualizar estudiante");


//         // ================================
//         // DOCENTE
//         // ================================
//         Docente docente = new Docente();
//         docente.setDocenteId(1);
//         docente.setNombreDocente("María López Actualizada");
//         docente.setIdentificacion("987654321");
//         docente.setTipoIdentificacion("CC");
//         docente.setGenero("F");
//         docente.setCorreo("maria.lopez@uniajc.edu.co");
//         docente.setTituloEstudios("Magíster en Educación");
//         docente.setIdiomas("Inglés B2");
//         docente.setCertificaciones("Docencia universitaria");

//         boolean actualizadoDoc = docenteDAO.actualizarDocente(docente);
//         System.out.println(actualizadoDoc ? " Docente actualizado correctamente" : " Error al actualizar docente");


//         // ================================
//         // CURSO
//         // ================================
//         Curso curso = new Curso();
//         curso.setCursoId(1);
//         curso.setNombreCurso("Programación Avanzada");
//         curso.setDescripcionCurso("Curso actualizado con nuevas prácticas de POO");
//         curso.setPeriodoAcademicoId(1);
//         curso.setDocenteId(1);

//         boolean actualizadoCurso = cursoDAO.actualizarCurso(curso);
//         System.out.println(actualizadoCurso ? "Curso actualizado correctamente" : " Error al actualizar curso");


//         // ================================
//         // CORTE DE EVALUACIÓN
//         // ================================
//         CorteEvaluacion corte = new CorteEvaluacion();
//         corte.setCorteEvaluacionId(1);
//         corte.setCursoId(1);
//         corte.setPeriodoAcademicoId(1);
//         corte.setNombreCorte("Primer Corte - Actualizado");
//         corte.setPorcentaje(35.0);
//         corte.setComentariosCorte("Se actualizó el porcentaje y nombre del corte");

//         boolean actualizadoCorte = corteDAO.actualizarCorteEvaluacion(corte);
//         System.out.println(actualizadoCorte ? " Corte de evaluación actualizado correctamente" : " Error al actualizar corte");


//         // ================================
//         // COMPONENTE DE EVALUACIÓN
//         // ================================
//         ComponenteEvaluacion componente = new ComponenteEvaluacion();
//         componente.setComponenteEvaluacionId(1);
//         componente.setCorteEvaluacionId(1);
//         componente.setNombreComponente("Examen Final - Actualizado");
//         componente.setPorcentaje(50.0);

//         boolean actualizadoComponente = componenteDAO.actualizarComponenteEvaluacion(componente);
//         System.out.println(actualizadoComponente ? " Componente de evaluación actualizado correctamente" : " Error al actualizar componente");


//         // ================================
//         // CALIFICACIÓN
//         // ================================
//         Calificacion calificacion = new Calificacion();
//         calificacion.setCalificacionId(1);
//         calificacion.setEstudianteId(1);
//         calificacion.setComponenteEvaluacionId(1);
//         calificacion.setNota(4.8);
//         calificacion.setComentariosCalificacion("Actualización: excelente desempeño");

//         boolean actualizadoCalif = calificacionDAO.actualizarCalificacion(calificacion);
//         System.out.println(actualizadoCalif ? " Calificación actualizada correctamente" : " Error al actualizar calificación");


//         // ================================
//         // ASISTENCIA
//         // ================================
//         Asistencia asistencia = new Asistencia();
//         asistencia.setAsistenciaId(1);
//         asistencia.setEstudianteId(1);
//         asistencia.setCursoId(1);
//         asistencia.setFechaClase(Date.valueOf("2025-10-28"));
//         asistencia.setEstadoAsistencia("Presente");
//         asistencia.setNovedades("Actualizado: llegó puntual");

//         boolean actualizadoAsistencia = asistenciaDAO.actualizarAsistencia(asistencia);
//         System.out.println(actualizadoAsistencia ? " Asistencia actualizada correctamente" : " Error al actualizar asistencia");


//         System.out.println("\n=====  ACTUALIZACIÓN COMPLETA =====");
//      }
//  }




    


    

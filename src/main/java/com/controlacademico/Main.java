package com.controlacademico;

import com.controlacademico.dao.*;
import com.controlacademico.modelo.*;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        //---------------------------  INSERTAR ESTUDIANTE ---------------------------------
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setIdentificacion("82957412");
        nuevoEstudiante.setNombre("Juan Sebastian Almendra Pechene");
        nuevoEstudiante.setCorreoInstitucional("dasda20015@estudiante.uniajc.edu.co");
        nuevoEstudiante.setCorreoPersonal("dasdas021415@gmail.com");
        nuevoEstudiante.setTelefono("3225973565");
        nuevoEstudiante.setEsVocero(false);
        nuevoEstudiante.setComentarios("estudiante de cuarto semestre.");
        nuevoEstudiante.setTipoDocumento("CC");
        nuevoEstudiante.setGenero("Masculino");
        EstudianteDAO daoEstudiante = new EstudianteDAO();
        int insertadoEstudiante = daoEstudiante.insertarEstudiante(nuevoEstudiante);

        if (insertadoEstudiante == -1) {
            System.out.println(" Error al insertar el estudiante.");
            return;
        }
       
        


        //---------------------------  INSERTAR DOCENTE ---------------------------------
        Docente nuevoDocente = new Docente();
        nuevoDocente.setNombreDocente("feli gomez");
        nuevoDocente.setIdentificacion("952013516");
        nuevoDocente.setTipoIdentificacion("CC");
        nuevoDocente.setGenero("Femenino");
        nuevoDocente.setCorreo("dasdasd.gomez@uniajc.edu.co");
        nuevoDocente.setTituloEstudios("Ingeniería de Sistemas");
        nuevoDocente.setIdiomas("Inglés");
        nuevoDocente.setCertificaciones("Oracle Java SE 11");

        DocenteDAO daoDocente = new DocenteDAO();
        int idDocenteGenerado = daoDocente.insertarDocente(nuevoDocente);



        //---------------------------  INSERTAR PERIODO ACADÉMICO ---------------------------------
        PeriodoAcademico nuevoPeriodo = new PeriodoAcademico();
        nuevoPeriodo.setNombrePeriodo("CUARTO SEMESTRE 2025");
        nuevoPeriodo.setFechaInicio(Date.valueOf("2025-01-15"));
        nuevoPeriodo.setFechaFin(Date.valueOf("2025-06-30"));

        PeriodoAcademicoDAO daoPeriodo = new PeriodoAcademicoDAO();
        int idPeriodoGenerado = daoPeriodo.insertarPeriodoAcademico(nuevoPeriodo);




        //---------------------------  INSERTAR CURSO ---------------------------------
        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombreCurso("Programación Orientada a Objetos");
        nuevoCurso.setDescripcionCurso("Curso enfocado en los principios de POO usando Java.");
        nuevoCurso.setPeriodoAcademicoId(idPeriodoGenerado);
        nuevoCurso.setDocenteId(idDocenteGenerado); // relación directa en tabla cursos

        CursoDAO daoCurso = new CursoDAO();
        int idCursoGenerado = daoCurso.insertarCursoYObtenerId(nuevoCurso); //  ahora este método devuelve el ID del curso





        //---------------------------  METODO PARA QUE SE INSERTEN AUTOMATICAMENTE LOS IDS DE DOCENTE Y CURSO EN LA TABLA DOCENTE CURSO ---------------------------------
        DocenteCurso relacionDocentesCursos = new DocenteCurso();
        relacionDocentesCursos.setDocenteId(idDocenteGenerado); // Usar el ID del docente recién insertado
        relacionDocentesCursos.setCursoId(idCursoGenerado);
        DocenteCursoDAO daoDocenteCurso = new DocenteCursoDAO();
        boolean insertadoDocenteCurso = daoDocenteCurso.insertarDocenteCurso(relacionDocentesCursos);
        if (insertadoDocenteCurso) {
            System.out.println(" Relación Docente-Curso insertada correctamente.");
        } else {
            System.out.println(" No se pudo insertar la relación Docente-Curso.");


    }



        


            //---------------------------- METODO PARA INSERTAR CLASE EN LA BASE DE DATOS ---------------------------------

            Clases nuevaClases = new Clases();
            nuevaClases.setCursoId(idCursoGenerado); // Usar el ID del curso recién insertado
            nuevaClases.setNumeroClase(1);
            nuevaClases.setFechaClase(Date.valueOf("2025-03-01"));
            nuevaClases.setTemaClase("Introducción a la Programación Orientada a Objetos");
            nuevaClases.setDescripcionClase("Explicación de los principios básicos de la POO: clases, objetos, herencia y polimorfismo.");
            nuevaClases.setComentariosClase("Los estudiantes mostraron buen interés."); 
            ClaseDAO daoClase = new ClaseDAO();
            boolean insertadoClase = daoClase.insertarClase(nuevaClases);

         

            if (insertadoClase) {
                System.out.println(" Clase Insertada Correctamente.");
            } else {
                System.out.println(" No se pudo insertar la Clase.");
            }
            


            //------------------------------METODO PARA INSERTAR CORTE DE EVALUACIÓN EN LA BASE DE DATOS ---------------------------------
            CorteEvaluacion nuevoCorte = new CorteEvaluacion();
            nuevoCorte.setCursoId(idCursoGenerado);             // Usar el ID del curso recién insertado
            nuevoCorte.setPeriodoAcademicoId(idPeriodoGenerado);   // Usar el ID del periodo recién insertado
            nuevoCorte.setNombreCorte("Primer Corte");
            nuevoCorte.setPorcentaje(30.0);
            nuevoCorte.setComentariosCorte("Evaluación de la primera mitad del curso.");

            CorteEvaluacionDAO daoCorte = new CorteEvaluacionDAO();
            
            int idCortegenerado = daoCorte.insertarCorteEvaluacionYObtenerId(nuevoCorte);
           
            if (idCortegenerado != -1) {
                System.out.println(" Corte de evaluación insertado correctamente.");
            } else {
                System.out.println(" No se pudo insertar el corte de evaluación.");
            }



            //---------------------------METODO PARA INSERTAR COMPONENTE DE EVALUACIÓN EN LA BASE DE DATOS con EL ID DEL CORTE RECIÉN INSERTADO---------------------------------
            ComponenteEvaluacion nuevoComponente = new ComponenteEvaluacion();
            // Usar el ID del corte de evaluación recién insertado
            nuevoComponente.setCorteEvaluacionId(idCortegenerado); // ID del corte existente
            nuevoComponente.setNombreComponente("Examen Parcial");
            nuevoComponente.setPorcentaje(40.0);   
            ComponenteEvaluacionDAO daoComponente = new ComponenteEvaluacionDAO();
            boolean insertadoComponente = daoComponente.insertarComponenteEvaluacion(nuevoComponente);

            
            if (insertadoComponente) {
                System.out.println(" Componente de evaluación insertado correctamente.");
            } else {
                System.out.println(" No se pudo insertar el componente de evaluación.");
            }



   }
}
























//        

//         //---------------------------  INSERTAR CALIFICACIÓN ---------------------------------
//         Calificacion nuevaCalificacion = new Calificacion();

//         // Usar IDs existentes en la base de datos:
        
//          nuevaCalificacion.setEstudianteId(76);   // ID del estudiante existente
//          nuevaCalificacion.setComponenteId(23);   // ID del componente existente
//         // nuevaCalificacion.setEstudianteId(76);   // ID del estudiante existente
//         // nuevaCalificacion.setComponenteId(23);   // ID del componente existente
//         nuevaCalificacion.setNota(5.0);
//         nuevaCalificacion.setComentariosCalificacion("Excelente desempeño en el examen Julian.");

//         CalificacionDAO daoCalificacion = new CalificacionDAO();
//         boolean insertadoCalificacion = daoCalificacion.insertarCalificacion(nuevaCalificacion);

//         if (insertadoCalificacion) {
//             System.out.println(" Calificación insertada correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar la calificación.");
//         }







//         //---------------------------  INSERTAR ASISTENCIA ---------------------------------
//         Asistencia nuevaAsistencia = new Asistencia();
//             // Usar IDs existentes en la base de datos:
//             nuevaAsistencia.setEstudianteId(76); // ID del estudiante existente
//             nuevaAsistencia.setCursoId(28);     // ID del curso existente
//             nuevaAsistencia.setFechaClase(Date.valueOf("2025-03-01"));
//             nuevaAsistencia.setEstadoAsistencia("presente"); // ENUM válido 
//             nuevaAsistencia.setNovedades("Participó activamente en la clase.");

//             AsistenciaDAO daoAsistencia = new AsistenciaDAO();
//                 boolean insertadoAsistencia = daoAsistencia.insertarAsistencia(nuevaAsistencia);

//                 if (insertadoAsistencia) {
//                     System.out.println(" Asistencia insertada correctamente.");
//                 } else {
//                      System.out.println(" No se pudo insertar la asistencia.");
//                 }





//-------------------------------------------------------------------------------------FIN COMENTAR-----------------------------------------------------


 










                             





























































































//----------------------------------------------------------------------------

//codigo alterno---------------

// package com.controlacademico;


// import com.controlacademico.dao.EstudianteDAO;
// import com.controlacademico.dao.PeriodoAcademicoDAO;
// import com.controlacademico.modelo.Curso;
// import com.controlacademico.modelo.Docente;
// import com.controlacademico.modelo.DocenteCurso;
// import com.controlacademico.modelo.Estudiante;
// import com.controlacademico.modelo.PeriodoAcademico;

// import java.sql.Date;

// import com.controlacademico.dao.CursoDAO;
// import com.controlacademico.dao.DocenteCursoDAO;
// import com.controlacademico.dao.DocenteDAO;


// public class Main {
//     public static void main(String[] args) {




//         //---------------------------METODO PARA INSERTAR DATOS ESTUDIANTE---------------------------------

//         Estudiante nuevo = new Estudiante();
//         nuevo.setIdentificacion("123456789");
//         nuevo.setNombre("Carlos Mendoza");
//         nuevo.setCorreoInstitucional("carlos.mendoza@uniajc.edu.co");
//         nuevo.setCorreoPersonal("carlos.mendoza@gmail.com");
//         nuevo.setTelefono("3124567890");
//         nuevo.setEsVocero(true);
//         nuevo.setComentarios("Estudiante destacado del grupo");
//         nuevo.setTipoDocumento("CC");
//         nuevo.setGenero("Masculino");

//         EstudianteDAO dao = new EstudianteDAO();
//         boolean insertado = dao.insertarEstudiante(nuevo);

//         if (insertado) {
//             System.out.println(" Estudiante insertado correctamente.");
//         } else {
//             System.out.println("No se pudo insertar el estudiante.");
//         }



// //---------------------------METODO PARA INSERTAR DATOS DOCENTE---------------------------------
    

//         Docente nuevo2Docente = new Docente();
//         nuevo2Docente.setNombreDocente("María Fernanda Ríos");
//         nuevo2Docente.setIdentificacion("987654321");
//         nuevo2Docente.setTipoIdentificacion("CC");
//         nuevo2Docente.setGenero("Femenino");
//         nuevo2Docente.setCorreo("maria.rios@uniajc.edu.co");
//         nuevo2Docente.setTituloEstudios("Magíster en Educación");
//         nuevo2Docente.setIdiomas("Español, Inglés");
//         nuevo2Docente.setCertificaciones("Certificada en docencia universitaria");

//         DocenteDAO dao2 = new DocenteDAO();
//         boolean insertado2 = dao2.insertarDocente(nuevo2Docente);

//         if (insertado2) {
//             System.out.println(" Docente insertado correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar el docente.");
//         }



// //---------------------------METODO PARA INSERTAR DATOS PERIODO ACADEMICO---------------------------------



//         // Crear un periodo académico de ejemplo
//         PeriodoAcademico nuevo4PeriodoAcademico = new PeriodoAcademico();
        
//         nuevo4PeriodoAcademico.setNombrePeriodo("2025-1");
//         nuevo4PeriodoAcademico.setFechaInicio(Date.valueOf("2025-02-01"));
//         nuevo4PeriodoAcademico.setFechaFin(Date.valueOf("2025-06-30"));

//         PeriodoAcademicoDAO dao4 = new PeriodoAcademicoDAO();
//         boolean insertado4 = dao4.insertarPeriodoAcademico(nuevo4PeriodoAcademico);

//         if (insertado4) {
//             System.out.println(" Periodo académico insertado correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar el periodo académico.");
//         }






//         //---------------------------METODO PARA INSERTAR DATOS CURSOS---------------------------------

// //PENDIENTE A INSERTAR


//         // Crear un curso de ejemplo
//         Curso nuevo3Curso = new Curso();
        
//         nuevo3Curso.setNombreCurso("Programación Orientada a Objetos");
//         nuevo3Curso.setPeriodoAcademicoId(nuevo4PeriodoAcademico.getPeriodoAcademicoId()); // ID existente en tu tabla periodos_academicos
//         nuevo3Curso.setDocenteId(1);           // ID existente en tu tabla docentes
//         nuevo3Curso.setDescripcionCurso("Curso enfocado en los principios de POO usando Java.");

//         CursoDAO dao3 = new CursoDAO();
//         boolean insertado3 = dao3.insertarCurso(nuevo3Curso);

//         if (insertado3) {
//             System.out.println(" Curso insertado correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar el curso.");
//         }






// //---------------------------METODO PARA INSERTAR DATOS DOCENTE CURSO---------------------------------




//         // Crear una relación entre docente y curso
//         DocenteCurso relacion = new DocenteCurso();
//         relacion.setDocenteId(3); // Usa un ID de docente existente
//         relacion.setCursoId(3);   // Usa un ID de curso existente

//         DocenteCursoDAO dao5 = new DocenteCursoDAO();
//         boolean insertar = dao5.insertarDocenteCurso(relacion);

//         if (insertar) {
//             System.out.println(" Relación Docente-Curso insertada correctamente.");
//         } else {
//             System.out.println(" No se pudo insertar la relación Docente-Curso.");
//         }
//     }
// }


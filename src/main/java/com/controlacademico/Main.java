package com.controlacademico;

import com.controlacademico.dao.*;
import com.controlacademico.modelo.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        //---------------------------  INSERTAR ESTUDIANTE ---------------------------------
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setIdentificacion("375838752384");
        nuevoEstudiante.setNombre("Juan Sebastian Almendra Pechene Final2");
        nuevoEstudiante.setCorreoInstitucional("Pruebametodosfull@estudiante.uniajc.edu.co");
        nuevoEstudiante.setCorreoPersonal("metodosfull@gmail.com");
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
        nuevoDocente.setNombreDocente("JUAN PEREZ GOMEZ");
        nuevoDocente.setIdentificacion("142385275");
        nuevoDocente.setTipoIdentificacion("CC");
        nuevoDocente.setGenero("Femenino");
        nuevoDocente.setCorreo("metodosdocentefull.gomez@uniajc.edu.co");
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
            nuevoComponente.setCorteEvaluacionId(idCortegenerado); // Usar el ID del corte recién insertado
            nuevoComponente.setNombreComponente("Examen Parcial");
            nuevoComponente.setPorcentaje(50.0);    
            ComponenteEvaluacionDAO daoComponente = new ComponenteEvaluacionDAO();
            
             int idComponenteGenerado = daoComponente.insertarComponenteEvaluacionYObtenerid(nuevoComponente);

                if (idComponenteGenerado != -1) {
                    System.out.println(" Componente de evaluación insertado correctamente. ID generado: " + idComponenteGenerado);
                } else {
                    System.out.println(" No se pudo insertar el componente de evaluación.");
}


            //--------------------------------------------------------------------------------------------

          
 
  


//-------------------------------------------------------------------
    }
}
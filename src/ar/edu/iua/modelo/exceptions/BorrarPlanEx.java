package ar.edu.iua.modelo.exceptions;

public class BorrarPlanEx extends ObjetoEx{

    /**
     *
     */
    private static final long serialVersionUID = -877985127783683731L;

    public BorrarPlanEx(String e){
        System.err.println("Error al borrar el plan: "+e);
    }
}
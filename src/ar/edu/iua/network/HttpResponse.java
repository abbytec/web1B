/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.iua.network;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author CARLOMAGNO
 */

public class HttpResponse {

    PrintWriter salida;
    private String header;
    private String HTML_START = "<html><title>Servidor IW2 versión 1.0</title><body>";
    private String HTML_BODY = "<div style='background-color:#00B9FF ; width: 100%;font-weight:bold;font-size: 36px;font-family: Verdana, Geneva, sans-serif;height: 10%; text-align: center'>Servidor del colegio ver 1.0</div><br><br><hr>";
    private String HTML_LEYENDA = "<div style='background-color: #00FF95; width: 100%;font-weight:bold;font-size: 20px;font-family: Verdana, Geneva, sans-serif;height: 20%; text-align: left'>Los Servicios se han iniciado correctamente<br>Se reciben solicitudes GET con estructura:<br> /accion/?parametro1=valor1 .... <br>Ejemplo /buscar/objeto=jsonString</div><br><br><hr>";
    private String HTML_END = "</body></html>";
    private String NEW_LINE = "\r\n";

    public HttpResponse(Socket s) throws IOException {
        salida = new PrintWriter(s.getOutputStream()); // permite imprimir lineas

    }
    // Euncion para generar el Encabezado de la Respuesta
    public String getHeader() {
        header = "HTTP/1.0 200 OK" + NEW_LINE;
        header += "Access-Control-Allow-Origin:*" + NEW_LINE;
        header += "Content-Type:text/html; charset=UTF-8" + NEW_LINE;

        return header;
    }
    // Funcion para generar la página inicial 
    public String getInitPage(String innerHtml) {
        String PaginaInicial = NEW_LINE;
        PaginaInicial += this.HTML_START;
        PaginaInicial += this.HTML_BODY;
        PaginaInicial += this.HTML_LEYENDA;
        PaginaInicial += "<div style='background-color: #F9FF00; width: 100%;font-weight:bold;font-size: 20px;font-family: Verdana, Geneva, sans-serif;height: 70%; text-align: left;content-align: center'>";
        if (innerHtml != null) {
            PaginaInicial += innerHtml;
        }
        ;
        PaginaInicial += "</div>" + this.HTML_END;
        return PaginaInicial;
    }
    // Funcion Echo, devuelve un eco con la info recibida
    public String getEcho(String[] parametros) {
        // este método devuelve una página simple de bienvenida
        // donde se muestran los parámetros recibidos
        String paginaEcho = null;
        String responseHeader = this.getHeader();
        String infoRecibida = "";

        infoRecibida = "<table style='width: 50%;background-color: powderblue' border=2>";
        infoRecibida += "<th colspan=2> Información Recibida desde el Web Browser </th>";
        infoRecibida += "<tr><td align=center> parametro </td><td align=center> valor </td></tr>";

        for (String parametro : parametros) {
            String[] data;
            data = parametro.split("=");
            String filaTabla = "<tr><td>" + data[0] + "</td><td>" + data[1] + "</td></tr>";
            infoRecibida += filaTabla;
        }
        infoRecibida += "</table>";
        String initPage = this.getInitPage(infoRecibida);
        paginaEcho = responseHeader + NEW_LINE + initPage;
        return paginaEcho;
    }

    // impresion de control por el System Out
    public void imprimirSalida(String Mensaje) {
        System.out.println(Mensaje);
        salida.println(Mensaje);
    }

    public void cerrar() {
        salida.close();
    }

}

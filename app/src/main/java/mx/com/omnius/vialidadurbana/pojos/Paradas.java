
package mx.com.omnius.vialidadurbana.pojos;


public class Paradas {


    private Integer idparada;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;
   

    public Paradas() {
    }

    public Paradas(Integer idparada) {
        this.setIdparada(idparada);
    }

    public Paradas(Integer idparada, String nombre, String direccion, double latitud, double longitud) {
        this.setIdparada(idparada);
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setLatitud(latitud);
        this.setLongitud(longitud);
    }

    public Integer getIdparada() {
        return idparada;
    }

    public void setIdparada(Integer idparada) {
        this.idparada = idparada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}

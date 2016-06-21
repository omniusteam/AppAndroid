package mx.com.omnius.vialidadurbana.pojos;

import java.io.Serializable;
import java.util.Date;


public class Usuarios implements Serializable {


    private Integer idusuario;
    private Date tiemporegistro;
    private Double latitud;
    private Double longitud;
    private Integer idparada;
    private Integer idrutaespera;
    

    public Usuarios() {
    }

    public Usuarios(Integer idusuario) {
        this.setIdusuario(idusuario);
    }

    public Usuarios(Integer idusuario, Date tiemporegistro) {
        this.setIdusuario(idusuario);
        this.setTiemporegistro(tiemporegistro);
    }


    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Date getTiemporegistro() {
        return tiemporegistro;
    }

    public void setTiemporegistro(Date tiemporegistro) {
        this.tiemporegistro = tiemporegistro;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getIdparada() {
        return idparada;
    }

    public void setIdparada(Integer idparada) {
        this.idparada = idparada;
    }

    public Integer getIdrutaespera() {
        return idrutaespera;
    }

    public void setIdrutaespera(Integer idrutaespera) {
        this.idrutaespera = idrutaespera;
    }
}

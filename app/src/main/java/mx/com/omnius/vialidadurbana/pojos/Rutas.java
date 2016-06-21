
package mx.com.omnius.vialidadurbana.pojos;

import java.math.BigDecimal;

public class Rutas{


    private Integer idruta;
    private String nombre;
    private Integer idestatus;
    private BigDecimal idempresatransporte;

    public Rutas() {
    }

    public Rutas(Integer idruta) {
        this.setIdruta(idruta);
    }

    public Rutas(Integer idruta, String nombre) {
        this.setIdruta(idruta);
        this.setNombre(nombre);
    }


    public Integer getIdruta() {
        return idruta;
    }

    public void setIdruta(Integer idruta) {
        this.idruta = idruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdestatus() {
        return idestatus;
    }

    public void setIdestatus(Integer idestatus) {
        this.idestatus = idestatus;
    }

    public BigDecimal getIdempresatransporte() {
        return idempresatransporte;
    }

    public void setIdempresatransporte(BigDecimal idempresatransporte) {
        this.idempresatransporte = idempresatransporte;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

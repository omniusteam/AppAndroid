
package mx.com.omnius.vialidadurbana.pojos;

import java.io.Serializable;



public class Catalogo{

    private Integer idcatalogo;
    private String nombre;
    private Integer orden;
    private Integer idcategoria;
    
    

    public Catalogo() {
    }


    public Integer getIdcatalogo() {
        return idcatalogo;
    }

    public void setIdcatalogo(Integer idcatalogo) {
        this.idcatalogo = idcatalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }
}

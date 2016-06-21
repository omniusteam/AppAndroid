
package mx.com.omnius.vialidadurbana.pojos;


public class Pais {

    private Integer idpais;
    private String nombre;
    private String clave;

    public Pais() {
    }

    public Pais(Integer idpais) {
        this.setIdpais(idpais);
    }

    public Pais(Integer idpais, String nombre) {
        this.setIdpais(idpais);
        this.setNombre(nombre);
    }


    public Integer getIdpais() {
        return idpais;
    }

    public void setIdpais(Integer idpais) {
        this.idpais = idpais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}

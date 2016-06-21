
package mx.com.omnius.vialidadurbana.pojos;


public class Estado {


    private Integer idestado;
    private String nombre;
    private String clave;
    private Integer idpais;

    public Estado() {
    }

    public Estado(Integer idestado) {
        this.setIdestado(idestado);
    }

    public Estado(Integer idestado, String nombre, Integer idpais) {
        this.setIdestado(idestado);
        this.setNombre(nombre);
        this.setIdpais(idpais);
    }


    public Integer getIdestado() {
        return idestado;
    }

    public void setIdestado(Integer idestado) {
        this.idestado = idestado;
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

    public Integer getIdpais() {
        return idpais;
    }

    public void setIdpais(Integer idpais) {
        this.idpais = idpais;
    }
}

package mx.com.omnius.vialidadurbana.pojos;


public class Municipio{


    private Integer idmunicipio;
    private String nombre;
    private String clave;
    private Integer idestado;

    public Municipio() {
    }

    public Municipio(Integer idmunicipio) {
        this.setIdmunicipio(idmunicipio);
    }

    public Municipio(Integer idmunicipio, String nombre, Integer idestado) {
        this.setIdmunicipio(idmunicipio);
        this.setNombre(nombre);
        this.setIdestado(idestado);
    }


    public Integer getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Integer idmunicipio) {
        this.idmunicipio = idmunicipio;
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

    public Integer getIdestado() {
        return idestado;
    }

    public void setIdestado(Integer idestado) {
        this.idestado = idestado;
    }
}

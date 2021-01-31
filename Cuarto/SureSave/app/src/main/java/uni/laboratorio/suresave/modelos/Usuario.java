package uni.laboratorio.suresave.modelos;

public class Usuario {

    private String id;
    private String nombre;
    private String usuario;
    private String email;
    private String imagenUrl;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String usuario, String email, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.email = email;
        this.imagenUrl = imagenUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }


}

package uni.laboratorio.chitchat.Model;

public class Usuario {

    private String nombreCompleto;
    private String correo;
    private String nombreUsuario;
    private String imageUrl;
    private String id;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String correo, String nombreUsuario, String imageUrl, String id) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

package uni.laboratorio.chitchat.Model;

public class Usuario {

    private String nombreCompleto;
    private String Correo;
    private String Usuario;
    private String imageUrl;
    private String id;
    private String bio;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String correo, String usuario, String imageUrl, String id, String bio) {
        this.nombreCompleto = nombreCompleto;
        Correo = correo;
        Usuario = usuario;
        this.imageUrl = imageUrl;
        this.id = id;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        this.Correo = correo;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        this.Usuario = usuario;
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

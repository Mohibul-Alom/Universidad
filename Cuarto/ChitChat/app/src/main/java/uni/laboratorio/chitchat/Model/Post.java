package uni.laboratorio.chitchat.Model;

public class Post {

    private String descripcion;
    private String imageurl;
    private String postId;
    private String publicador;

    public Post() {
    }

    public Post(String descripcion, String imageurl, String postId, String publicador) {
        this.descripcion = descripcion;
        this.imageurl = imageurl;
        this.postId = postId;
        this.publicador = publicador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPublicador() {
        return publicador;
    }

    public void setPublicador(String publicador) {
        this.publicador = publicador;
    }
}

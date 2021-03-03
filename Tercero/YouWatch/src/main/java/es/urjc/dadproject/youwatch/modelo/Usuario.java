package es.urjc.dadproject.youwatch.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String biografia;
    private String email;
    private long fechaNacimiento;

    @OneToOne(cascade = CascadeType.ALL)
    private Membresia membresia;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario_vid", cascade = CascadeType.ALL)
    private List<Video> videos = new ArrayList<>();

    public Usuario() {

    }

    public Usuario(Integer id, String nombre, String biografia,
                   String email, long fechaNacimiento,
                   Membresia membresia, List<Comentario> comentarios,
                   List<Video> videos) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.membresia = membresia;
        this.comentarios = comentarios;
        this.videos = videos;
    }

    public long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

}
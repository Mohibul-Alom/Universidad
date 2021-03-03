package es.urjc.dadproject.youwatch.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @ManyToOne
    private Usuario usuario_vid;

    @ManyToMany(mappedBy = "videos")
    private List<Etiqueta> etiquetas = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    public Video() {
    }

    public Video(Integer id, String titulo, Usuario usuario_vid,
                 List<Etiqueta> etiquetas, List<Comentario> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.usuario_vid = usuario_vid;
        this.etiquetas = etiquetas;
        this.comentarios = comentarios;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String calificativo) {
        this.titulo = calificativo;
    }

    public Usuario getUsuario_vid() {
        return usuario_vid;
    }

    public void setUsuario_vid(Usuario usuario) {
        this.usuario_vid = usuario;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }
}

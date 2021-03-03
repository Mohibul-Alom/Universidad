package es.urjc.dadproject.youwatch.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String calificativo;

    @ManyToMany
    private List<Video> videos = new ArrayList<>();

    public Etiqueta() {
    }

    public Etiqueta(Integer id, String calificativo, List<Video> videos) {
        this.id = id;
        this.calificativo = calificativo;
        this.videos = videos;
    }

    public String getCalificativo() {
        return calificativo;
    }

    public void setCalificativo(String calificativo) {
        this.calificativo = calificativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}

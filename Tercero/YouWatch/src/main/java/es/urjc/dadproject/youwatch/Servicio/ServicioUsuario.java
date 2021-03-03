package es.urjc.dadproject.youwatch.Servicio;

import es.urjc.dadproject.youwatch.Repositorio.*;
import es.urjc.dadproject.youwatch.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class ServicioUsuario {

    @Autowired
    private IUsuarioRepositorio repoUsuario;

    @Autowired
    private IMembresiaRepositorio repoMembresia;

    @Autowired
    private IEtiquetaRepositorio repoEtiqueta;

    @Autowired
    private IComentarioRepositorio repoComentario;

    @Autowired
    private IVideoRepositorio repoVideo;



    @RequestMapping("/")
    public List<Usuario> getBlogs() throws Exception {
        return repoUsuario.findAll();
    }

    public void crear(){
        Usuario usuario = new Usuario();

        usuario.setNombre("alom");
        usuario.setFechaNacimiento(231548);
        usuario.setBiografia("estresado");
        usuario.setEmail("email");

        //crear la membresia
        Membresia membresia = new Membresia();
        membresia.setFechaInicio(123214);
        membresia.setFechaFin(2115412);
        membresia.setTipo(true);
        membresia.setPrecio(2.5);

        //poner el id de la membresia
        usuario.setMembresia(membresia);

        //crear comentario
        Comentario com1 = new Comentario();
        com1.setUsuario(usuario);
        com1.setMensaje("comentario 1");
        com1.setFecha(1566489);

        Comentario com2 = new Comentario();
        com2.setUsuario(usuario);
        com2.setMensaje("comentario 2");
        com2.setFecha(1524654);
        //hay que relacionar los videos

        //crear videos
        Video video = new Video();
        video.setTitulo("primer video");
        video.setUsuario_vid(usuario);
        video.getComentarios().add(com1);
        video.getComentarios().add(com2);

        //crear etiquetas
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setCalificativo("#estresado");
        etiqueta.getVideos().add(video);

        video.getEtiquetas().add(etiqueta);

        com1.setVideo(video);
        com2.setVideo(video);

        List <Comentario> lista_comen = new ArrayList<>();
        lista_comen.add(com1);
        lista_comen.add(com2);

        List <Video> lista_video = new ArrayList<>();
        lista_video.add(video);

        usuario.setVideos(lista_video);
        usuario.setComentarios(lista_comen);

        repoComentario.save(com1);
        repoComentario.save(com2);
        repoUsuario.save(usuario);
        repoMembresia.save(membresia);
        repoEtiqueta.save(etiqueta);
        repoVideo.save(video);
    }


}

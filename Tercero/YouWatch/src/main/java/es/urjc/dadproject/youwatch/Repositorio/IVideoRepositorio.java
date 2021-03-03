package es.urjc.dadproject.youwatch.Repositorio;

import es.urjc.dadproject.youwatch.modelo.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVideoRepositorio extends JpaRepository<Video,Integer> {
}

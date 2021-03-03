package es.urjc.dadproject.youwatch.Repositorio;

import es.urjc.dadproject.youwatch.modelo.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioRepositorio extends JpaRepository<Comentario,Integer> {
}

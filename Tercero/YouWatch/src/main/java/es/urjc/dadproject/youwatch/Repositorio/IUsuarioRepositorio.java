package es.urjc.dadproject.youwatch.Repositorio;

import es.urjc.dadproject.youwatch.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario,Integer> {
}

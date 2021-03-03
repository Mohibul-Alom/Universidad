package es.urjc.dadproject.youwatch.Repositorio;

import es.urjc.dadproject.youwatch.modelo.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtiquetaRepositorio extends JpaRepository<Etiqueta,Integer> {
}

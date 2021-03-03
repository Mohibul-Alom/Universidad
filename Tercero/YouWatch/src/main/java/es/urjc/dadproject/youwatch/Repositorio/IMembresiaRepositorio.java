package es.urjc.dadproject.youwatch.Repositorio;

import es.urjc.dadproject.youwatch.modelo.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMembresiaRepositorio extends JpaRepository<Membresia,Integer> {
}

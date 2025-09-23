package br.com.tria.mobilebackend.repository;

import br.com.tria.mobilebackend.model.Iot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IotRepository extends JpaRepository<Iot, Long> {
}



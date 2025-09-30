package br.com.tria.mobilebackend.repository;

import br.com.tria.mobilebackend.model.Moto;
import br.com.tria.mobilebackend.model.SetorEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);

    List<Moto> findBySetor(SetorEnum setor);

    @Query("select m from Moto m where m.iot.id = :iotId")
    Optional<Moto> findByIotId(@Param("iotId") Long iotId);
}



package br.com.tria.mobilebackend.controller;

import br.com.tria.mobilebackend.model.Moto;
import br.com.tria.mobilebackend.repository.MotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motos")
@CrossOrigin(origins = "*")
public class MotoController {

    private final MotoRepository motoRepository;

    public MotoController(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @GetMapping
    public List<Moto> listar() {
        return motoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscar(@PathVariable Long id) {
        return motoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Moto> criar(@RequestBody Moto moto) {
        Moto salvo = motoRepository.save(moto);
        return ResponseEntity.created(URI.create("/motos/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody Moto moto) {
        return motoRepository.findById(id)
                .map(existente -> {
                    existente.setModelo(moto.getModelo());
                    existente.setAno(moto.getAno());
                    existente.setPlaca(moto.getPlaca());
                    existente.setSetor(moto.getSetor());
                    return ResponseEntity.ok(motoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!motoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        motoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Moto> buscarPorPlaca(@PathVariable String placa) {
        return motoRepository.findByPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/setor/{setor}")
    public List<Moto> buscarPorSetor(@PathVariable String setor) {
        try {
            return motoRepository.findBySetor(Enum.valueOf(br.com.tria.mobilebackend.model.SetorEnum.class, setor));
        } catch (IllegalArgumentException ex) {
            return List.of();
        }
    }

    @GetMapping("/por-iot/{iotId}")
    public ResponseEntity<Moto> buscarPorIot(@PathVariable Long iotId) {
        return motoRepository.findByIotId(iotId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}



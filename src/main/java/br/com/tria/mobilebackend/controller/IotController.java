package br.com.tria.mobilebackend.controller;

import br.com.tria.mobilebackend.model.Iot;
import br.com.tria.mobilebackend.model.Moto;
import br.com.tria.mobilebackend.repository.IotRepository;
import br.com.tria.mobilebackend.repository.MotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/iots")
@CrossOrigin(origins = "*")
public class IotController {

    private final IotRepository iotRepository;
    private final MotoRepository motoRepository;

    public IotController(IotRepository iotRepository, MotoRepository motoRepository) {
        this.iotRepository = iotRepository;
        this.motoRepository = motoRepository;
    }

    @GetMapping
    public List<Iot> listar() {
        return iotRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iot> buscar(@PathVariable Long id) {
        return iotRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Iot> criar(@RequestBody Iot iot) {
        if (iot.getMoto() != null && iot.getMoto().getId() != 0) {
            Moto moto = motoRepository.findById(iot.getMoto().getId()).orElse(null);
            iot.setMoto(moto);
            if (moto != null) {
                moto.setIot(iot);
            }
        }
        Iot salvo = iotRepository.save(iot);
        return ResponseEntity.created(URI.create("/iots/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Iot> atualizar(@PathVariable Long id, @RequestBody Iot iot) {
        return iotRepository.findById(id)
                .map(existente -> {
                    if (iot.getMoto() != null && iot.getMoto().getId() != 0) {
                        Moto moto = motoRepository.findById(iot.getMoto().getId()).orElse(null);
                        existente.setMoto(moto);
                        if (moto != null) {
                            moto.setIot(existente);
                        }
                    } else {
                        existente.setMoto(null);
                    }
                    return ResponseEntity.ok(iotRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!iotRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        iotRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



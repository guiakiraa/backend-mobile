package br.com.tria.mobilebackend.controller;

import br.com.tria.mobilebackend.model.Moto;
import br.com.tria.mobilebackend.model.Setor;
import br.com.tria.mobilebackend.repository.MotoRepository;
import br.com.tria.mobilebackend.repository.SetorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    private final MotoRepository motoRepository;
    private final SetorRepository setorRepository;

    public MotoController(MotoRepository motoRepository, SetorRepository setorRepository) {
        this.motoRepository = motoRepository;
        this.setorRepository = setorRepository;
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
        if (moto.getSetor() != null && moto.getSetor().getId() != 0) {
            Setor setor = setorRepository.findById(moto.getSetor().getId()).orElse(null);
            moto.setSetor(setor);
        }
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
                    if (moto.getSetor() != null && moto.getSetor().getId() != 0) {
                        Setor setor = setorRepository.findById(moto.getSetor().getId()).orElse(null);
                        existente.setSetor(setor);
                    } else {
                        existente.setSetor(null);
                    }
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
}



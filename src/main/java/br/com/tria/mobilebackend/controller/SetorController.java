package br.com.tria.mobilebackend.controller;

import br.com.tria.mobilebackend.model.Iot;
import br.com.tria.mobilebackend.model.Moto;
import br.com.tria.mobilebackend.model.Setor;
import br.com.tria.mobilebackend.repository.IotRepository;
import br.com.tria.mobilebackend.repository.MotoRepository;
import br.com.tria.mobilebackend.repository.SetorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorRepository setorRepository;
    private final MotoRepository motoRepository;
    private final IotRepository iotRepository;

    public SetorController(SetorRepository setorRepository, MotoRepository motoRepository, IotRepository iotRepository) {
        this.setorRepository = setorRepository;
        this.motoRepository = motoRepository;
        this.iotRepository = iotRepository;
    }

    @GetMapping
    public List<Setor> listar() {
        return setorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setor> buscar(@PathVariable Long id) {
        return setorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Setor> criar(@RequestBody Setor setor) {
        Setor salvo = setorRepository.save(setor);
        return ResponseEntity.created(URI.create("/setores/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setor> atualizar(@PathVariable Long id, @RequestBody Setor setor) {
        return setorRepository.findById(id)
                .map(existente -> {
                    existente.setNome(setor.getNome());
                    existente.setDescricao(setor.getDescricao());
                    existente.setMotos(setor.getMotos());
                    return ResponseEntity.ok(setorRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!setorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        setorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-placa/{placa}")
    public ResponseEntity<Setor> buscarPorPlaca(@PathVariable String placa) {
        return motoRepository.findByPlaca(placa)
                .map(Moto::getSetor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-iot/{iotId}")
    public ResponseEntity<Setor> buscarPorIot(@PathVariable Long iotId) {
        return iotRepository.findById(iotId)
                .map(Iot::getMoto)
                .map(m -> m != null ? m.getSetor() : null)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}



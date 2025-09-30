package br.com.tria.mobilebackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "motos")
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ModeloMotoEnum modelo;
    private String placa;
    private int ano;

    @Enumerated(EnumType.STRING)
    private SetorEnum setor;

    @OneToOne(mappedBy = "moto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "moto-iot")
    private Iot iot;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModeloMotoEnum getModelo() {
        return modelo;
    }

    public void setModelo(ModeloMotoEnum modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public SetorEnum getSetor() {
        return setor;
    }

    public void setSetor(SetorEnum setor) {
        this.setor = setor;
    }

    public Iot getIot() {
        return iot;
    }

    public void setIot(Iot iot) {
        this.iot = iot;
        if (iot != null) {
            iot.setMoto(this);
        }
    }
}

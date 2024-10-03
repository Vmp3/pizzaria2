package projeto.pizzaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "sabores")
public class SaboresRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idsabor;

    private String sabor;
    private String descricao;
    private BigDecimal valor;
    private String tamanho;
    private String imagem;

    public SaboresRequestDTO() {
    }

    public SaboresRequestDTO(Long idsabor, String sabor, String descricao, BigDecimal valor, String tamanho, String imagem) {
        this.idsabor = idsabor;
        this.sabor = sabor;
        this.descricao = descricao;
        this.valor = valor;
        this.tamanho = tamanho;
        this.imagem = imagem;
    }

    // Getters e Setters

    public Long getIdsabor() {
        return idsabor;
    }

    public void setIdsabor(Long idsabor) {
        this.idsabor = idsabor;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

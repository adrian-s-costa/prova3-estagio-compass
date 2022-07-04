package com.github.pinkglb.estados.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.github.pinkglb.estados.modelo.Estado;
import com.github.pinkglb.estados.repository.EstadoRepository;

public class AtualizacaoEstadoForm {
	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty @Pattern(regexp = "//|Centro-Oeste|Nordeste|Norte|Sul|Sudeste|/g")
	private String regiao;
	@NotNull
	private int populacao;
	@NotNull @NotEmpty
	private String capital;
	@NotNull
	private float area;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public int getPopulacao() {
		return populacao;
	}
	public void setPopulacao(int populacao) {
		this.populacao = populacao;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public Estado atualizar(Long id, EstadoRepository estadoRepository) {
		Estado estado = estadoRepository.getReferenceById(id);
		estado.setNome(this.nome);
		estado.setRegiao(this.regiao);
		estado.setPopulacao(this.populacao);
		estado.setCapital(this.capital);
		estado.setArea(this.area);
		
		return estado;
	}
	
	
}

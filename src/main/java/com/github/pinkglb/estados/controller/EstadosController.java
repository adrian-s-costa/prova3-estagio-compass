package com.github.pinkglb.estados.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pinkglb.estados.controller.dto.EstadoDto;
import com.github.pinkglb.estados.controller.form.AtualizacaoEstadoForm;
import com.github.pinkglb.estados.controller.form.EstadoForm;
import com.github.pinkglb.estados.modelo.Estado;
import com.github.pinkglb.estados.repository.EstadoRepository;

@RestController
@RequestMapping("api/states")
public class EstadosController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	@Transactional
	public List<EstadoDto> lista(){
		List<Estado> estados = estadoRepository.carregarEstados();
		return EstadoDto.converter(estados);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<EstadoDto> cadastrar(@RequestBody @Valid EstadoForm form, UriComponentsBuilder uriBuilder) {
		Estado estado = form.converter();
		estadoRepository.save(estado);
		
		URI uri = uriBuilder.path("/api/state/{id}").buildAndExpand(estado.getId()).toUri();
		return ResponseEntity.created(uri).body(new EstadoDto(estado));
	}
	
	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<EstadoDto> detalhar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		if(estado.isPresent()) {
			return ResponseEntity.ok(new EstadoDto(estado.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<EstadoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoEstadoForm form){
		Optional<Estado> optional = estadoRepository.findById(id);
		if(optional.isPresent()) {
			Estado estado = form.atualizar(id, estadoRepository);
			return ResponseEntity.ok(new EstadoDto(estado));
		}
		return ResponseEntity.notFound().build();
	}
		
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<EstadoDto> remover(@PathVariable Long id){
		Optional<Estado> optional = estadoRepository.findById(id);
		if(optional.isPresent()) {
			estadoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}

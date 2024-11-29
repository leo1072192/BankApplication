package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	// Obtener todos los clientes
	@RequestMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		List<ClientDto> clients = clientService.getAll();
		return ResponseEntity.ok(clients);
	}

	// Obtener un cliente por id
	@RequestMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		ClientDto clientDto = clientService.getById(id);
		return ResponseEntity.ok(clientDto);
	}

	// Crear un nuevo cliente
	@RequestMapping("/create")
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		ClientDto createdClient = clientService.create(clientDto);
		return ResponseEntity.ok(createdClient);
	}

	// Actualizar un cliente existente
	@RequestMapping("/{id}/update")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		if (!id.equals(clientDto.getId())) {
			return ResponseEntity.badRequest().build();
		}
		ClientDto updatedClient = clientService.update(clientDto);
		return ResponseEntity.ok(updatedClient);
	}

	@RequestMapping("/{id}/partial-update")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		ClientDto updatedClient = clientService.partialUpdate(id, partialClientDto);
		return ResponseEntity.ok(updatedClient);
	}

	// Eliminar un cliente
	@RequestMapping("/{id}/delete")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();

	}
}

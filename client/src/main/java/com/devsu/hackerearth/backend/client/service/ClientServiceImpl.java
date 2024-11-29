package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll()
				.stream()
				.map(this::toDto) // Convertir a DTO
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		Optional<Client> clientOpt = clientRepository.findById(id);
		if (clientOpt.isPresent()) {
			return toDto(clientOpt.get());
		}
		throw new RuntimeException("Client not found with id " + id); // Lanza una excepción si no se encuentra
	}

	@Override
	@Transactional
	public ClientDto create(ClientDto clientDto) {
		Client client = new Client();
		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());

		client = clientRepository.save(client);
		return toDto(client); // Devuelve el DTO del cliente creado
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		Optional<Client> clientOpt = clientRepository.findById(clientDto.getId());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setDni(clientDto.getDni());
            client.setName(clientDto.getName());
            client.setPassword(clientDto.getPassword());
            client.setGender(clientDto.getGender());
            client.setAge(clientDto.getAge());
            client.setAddress(clientDto.getAddress());
            client.setPhone(clientDto.getPhone());
            client.setActive(clientDto.isActive());

            client = clientRepository.save(client);
            return toDto(client); // Devuelve el DTO del cliente actualizado
        }
        throw new RuntimeException("Client not found with id " + clientDto.getId()); // Lanza una excepción si no se encuentra
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            // Actualiza solo el campo isActive
            client.setActive(partialClientDto.isActive());

            client = clientRepository.save(client);
            return toDto(client); // Devuelve el DTO del cliente parcialmente actualizado
        }
        throw new RuntimeException("Client not found with id " + id); // Lanza una excepción si no se encuentra
	}

	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}

	// Método auxiliar para convertir una entidad Client a ClientDto
	private ClientDto toDto(Client client) {
		return new ClientDto(
				client.getId(),
				client.getDni(),
				client.getName(),
				client.getPassword(),
				client.getGender(),
				client.getAge(),
				client.getAddress(),
				client.getPhone(),
				client.isActive());
	}
}

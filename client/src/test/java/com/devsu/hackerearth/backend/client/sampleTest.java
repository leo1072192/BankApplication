package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);
    @Test
    void clientEntityCreationTest() {
       
        ClientDto client = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);

       
        assertEquals(1L, client.getId());
        assertEquals("Dni", client.getDni());
        assertEquals("Name", client.getName());
        assertEquals("Password", client.getPassword());
        assertEquals("Gender", client.getGender());
        assertEquals(1, client.getAge());
        assertEquals("Address", client.getAddress());
        assertEquals("9999999999", client.getPhone());
        assertTrue(client.isActive());
    }
    @Test
    void createClientTest() {
      
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        
     
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act: Realizamos la llamada al controlador para crear el cliente.
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert: Verificamos que la respuesta tenga el estado y el cuerpo correctos.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @Test
    void getClientTest() {
        // Arrange: Preparamos los datos de entrada y la respuesta esperada.
        ClientDto client = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        
      
        when(clientService.getById(1L)).thenReturn(client);

     
        ResponseEntity<ClientDto> response = clientController.get(1L);

        // Assert: Verificamos que la respuesta tenga el estado y el cuerpo correctos.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }
}

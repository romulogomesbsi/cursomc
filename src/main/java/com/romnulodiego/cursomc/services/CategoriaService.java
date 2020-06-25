package com.romnulodiego.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romnulodiego.cursomc.domain.Categoria;
import com.romnulodiego.cursomc.repositories.CategoriaRepository;
import com.romnulodiego.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria buscar(Integer id) { 
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
			      "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}

}

package com.colin.api.services;

import java.util.List; 
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.colin.api.controller.PersonController;
import com.colin.api.data.vo.v1.PersonVO;
import com.colin.api.data.vo.v2.PersonVOV2;
import com.colin.api.entities.Person;
import com.colin.api.exceptions.RequiredResourceIsNullException;
import com.colin.api.exceptions.ResourceNotFoundException;
import com.colin.api.mapper.DozerMapper;
import com.colin.api.mapper.custom.PersonMapper;
import com.colin.api.repositories.PersonRepository;

@Service
public class PersonServices {
	private final Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		logger.info("Finding All people!");
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons
		.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}	

	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO createPerson(PersonVO person) {
		if(person == null) throw new RequiredResourceIsNullException();
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO updatePerson(PersonVO person) {
		if(person == null) throw new RequiredResourceIsNullException();
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void deletePerson(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
				
	}
}

package com.colin.api.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.colin.api.data.vo.v2.PersonVOV2;
import com.colin.api.entities.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setGender(person.getGender());
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setBirthday(new Date());
		return vo;
	}
	
	public Person convertToVoEntity(PersonVOV2 person) {
		Person entity = new Person();
		entity.setGender(person.getGender());
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		//vo.setBirthday(new Date());
		return entity;
	}
	
}

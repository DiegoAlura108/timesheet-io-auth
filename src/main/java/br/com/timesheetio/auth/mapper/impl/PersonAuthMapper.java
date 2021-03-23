package br.com.timesheetio.auth.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.timesheetio.auth.domain.PersonAuthEntity;
import br.com.timesheetio.auth.dto.PersonAuthDTO;
import br.com.timesheetio.auth.mapper.IMapper;

@Component
public class PersonAuthMapper implements IMapper<PersonAuthEntity, PersonAuthDTO>{

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PersonAuthDTO convertEntityToDto(PersonAuthEntity e) {

		return modelMapper.map(e, PersonAuthDTO.class);
	}

	@Override
	public PersonAuthEntity convertDtoToEntity(PersonAuthDTO d) {

		return modelMapper.map(d, PersonAuthEntity.class);
	}
	
	public PersonAuthDTO convertDtoToDto(PersonAuthDTO d) {

		return modelMapper.map(d, PersonAuthDTO.class);
	}
}

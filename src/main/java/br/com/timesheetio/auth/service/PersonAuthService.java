package br.com.timesheetio.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.timesheetio.auth.domain.PersonAuthEntity;
import br.com.timesheetio.auth.dto.PersonAuthDTO;
import br.com.timesheetio.auth.exception.ObjectAlreadyExistsException;
import br.com.timesheetio.auth.exception.ObjectNotFoundException;
import br.com.timesheetio.auth.mapper.impl.PersonAuthMapper;
import br.com.timesheetio.auth.repository.PersonAuthRepository;

@Service
public class PersonAuthService implements UserDetailsService {

	private static final String MESSAGE_OBJECT_ALREADY_EXIST = "This Person Auth Object Already Exists.";

	private static final String MESSAGE_OBJECT_NOT_FOUND = "This Person Auth Object Not Found.";
	
	private static final String SEPARETED_ENCODER_PERSON = "<->";
	
	@Autowired
	private PersonAuthRepository personAuthRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
//	@Autowired
//	private AuthExchangeClient authExchangeClient;
	
	@Autowired
	private PersonAuthMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return personAuthRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(PersonAuthService.MESSAGE_OBJECT_NOT_FOUND));
	}
	
	public Page<PersonAuthDTO> findAll(Pageable pageable) {
		
		return personAuthRepository.findAll(pageable).map(personAuth -> mapper.convertEntityToDto(personAuth));
	}
	
	public PersonAuthDTO findById(Long id) {
		
		PersonAuthEntity personEntity = personAuthRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(PersonAuthService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return mapper.convertEntityToDto(personEntity);
	}
	
	public PersonAuthDTO findByUserKey(String personAuthUserKey) {
		
		PersonAuthEntity personEntity = personAuthRepository.findByPersonAuthUserKey(personAuthUserKey).orElseThrow(() -> new ObjectNotFoundException(PersonAuthService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return mapper.convertEntityToDto(personEntity);
	}
	
	public PersonAuthDTO findByEmail(String email) {
		
		PersonAuthEntity personEntity = personAuthRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(PersonAuthService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return mapper.convertEntityToDto(personEntity);
	}
	
	public PersonAuthDTO update(PersonAuthDTO personAuthDTO) {
		
		PersonAuthDTO personAuthFound = this.findByUserKey(personAuthDTO.getPersonAuthUserKey());
		
		if(personAuthFound == null) {
			
			throw new ObjectNotFoundException(PersonAuthService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		PersonAuthEntity personEntity = mapper.convertDtoToEntity(personAuthFound);
		
		return mapper.convertEntityToDto(personAuthRepository.save(personEntity));
	}
	
	public void delete(Long id) {
		
		PersonAuthDTO personAuthFound = this.findById(id);
		
		personAuthRepository.delete(mapper.convertDtoToEntity(personAuthFound));
	}
	
	public PersonAuthDTO save(PersonAuthDTO personAuthDTO) {
		
		if(personAuthDTO.getPersonAuthUserKey() != null &&
				personAuthDTO.getId() != null) {
			
			throw new ObjectAlreadyExistsException(PersonAuthService.MESSAGE_OBJECT_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		
		String password = personAuthDTO.getPassword();
		
		personAuthDTO.setPassword(bCryptPasswordEncoder.encode(password));
		personAuthDTO.setPersonAuthUserKey(encodePersonAuthUserKey(personAuthDTO, password));
		
		PersonAuthEntity personAuthEntity = mapper.convertDtoToEntity(personAuthDTO);
		
		return mapper.convertEntityToDto(personAuthRepository.save(personAuthEntity));
	}

	private String encodePersonAuthUserKey(PersonAuthDTO personAuthDTO, String password) {
		
		return bCryptPasswordEncoder.encode(personAuthDTO.getEmail() + SEPARETED_ENCODER_PERSON +bCryptPasswordEncoder.encode(password));
	}
	
	public PersonAuthDTO mergePersonDtoObjects(PersonAuthDTO source, PersonAuthDTO target) {
		
		return PersonAuthDTO.builder()
				 .id(source.getId())
				 .email(target.getEmail() != null ? target.getEmail() : source.getEmail())
				 .password(target.getPassword() != null ? target.getPassword() : source.getPassword())
				 .roles(target.getRoles() != null ? target.getRoles() : source.getRoles())
				 .personAuthUserKey(target.getPersonAuthUserKey() != null ? target.getPersonAuthUserKey() : source.getPersonAuthUserKey())
				 .accountNonExpired(source.isAccountNonExpired())
				 .accountNonLocked(source.isAccountNonLocked())
				 .credentialsNonExpired(source.isCredentialsNonExpired())
				 .enabled(source.isEnabled())
				 .build();
	}
	
//	public void sendMessageInCaseToFail(PersonAuthToPersonMessageDTO personAuthDTO) {
//
//		authExchangeClient.sendMessage(personAuthDTO);
//	}
}

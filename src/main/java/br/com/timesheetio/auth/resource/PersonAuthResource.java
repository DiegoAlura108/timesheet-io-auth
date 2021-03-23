package br.com.timesheetio.auth.resource;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.timesheetio.auth.configuration.SwaggerConfig;
import br.com.timesheetio.auth.dto.PersonAuthDTO;
import br.com.timesheetio.auth.dto.ResponseDTO;
import br.com.timesheetio.auth.service.PersonAuthService;
import io.swagger.annotations.Api;

@Api(tags = { SwaggerConfig.TAG_AUTH_OPERATION })
@RestController
@RequestMapping("/person-auth")
public class PersonAuthResource {

	private static final Logger logger = LoggerFactory.getLogger(PersonAuthResource.class);

	@Autowired
	private PersonAuthService personAuthService;
	
	private static final String VERB_HTTP_GET = "GET";
	private static final String VERB_HTTP_UPDATE = "DELETE";
	private static final String VERB_HTTP_DELETE = "DELETE";
	
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<PersonAuthDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			@RequestParam(value = "ordered", defaultValue = "id") String ordered){
		
		logger.info("FINDING ALL PERSON AUTH OBJECT BY PAGE, LIMIT, DIRECTION AND ORDERED ...: {} , {} , {} , {}", page, size, direction, ordered);
		
		Direction directionSelected = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(directionSelected, ordered));
		
		Page<PersonAuthDTO> pagePersons = personAuthService.findAll(pageable);
		
		ResponseDTO<Page<PersonAuthDTO>> response = new ResponseDTO<>();
		response.setData(pagePersons);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).findAll(page, size, direction, ordered)).withSelfRel());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> findById(@PathVariable("id") Long id) {
		
		logger.info("FINDING PERSON AUTH OBJECT BY ID ...: {}", id);
		
		PersonAuthDTO personFound = personAuthService.findById(id);
		
		ResponseDTO<PersonAuthDTO> response = new ResponseDTO<>();
		response.setData(personFound);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).findById(id)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).delete(id)).withRel(VERB_HTTP_DELETE));
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/user-key")
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> findByPersonAuthUserKey(@RequestBody PersonAuthDTO person) {
		
		logger.info("UPDATING PERSON AUTH OBJECT BY PERSON AUTH USER KEY ...: {}", person.getPersonAuthUserKey());
		
		PersonAuthDTO personFound = personAuthService.findByUserKey(person.getPersonAuthUserKey());
		PersonAuthDTO personUpdated = personAuthService.update(personAuthService.mergePersonDtoObjects(personFound, person));
		
		ResponseDTO<PersonAuthDTO> response = new ResponseDTO<>();
		response.setData(personUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).findById(personUpdated.getId())).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).delete(personUpdated.getId())).withRel(VERB_HTTP_DELETE));
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> save(@RequestBody PersonAuthDTO person) {
		
		logger.info("SAVING PERSON AUTH OBJECT ...: {}", person);
		
		PersonAuthDTO personAuthSaved = personAuthService.save(person);
		
		ResponseDTO<PersonAuthDTO> response = new ResponseDTO<>();
		response.setData(personAuthSaved);
		response.setStatus(HttpStatus.CREATED.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).findById(personAuthSaved.getId())).withRel(VERB_HTTP_GET));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).update(personAuthSaved)).withRel(VERB_HTTP_UPDATE));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).delete(personAuthSaved.getId())).withRel(VERB_HTTP_DELETE));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> update(@RequestBody PersonAuthDTO person) {
		
		logger.info("UPDATING PERSON AUTH OBJECT ...: {}", person);
		
		PersonAuthDTO personAuthUpdated = personAuthService.update(person);
		
		ResponseDTO<PersonAuthDTO> response = new ResponseDTO<>();
		response.setData(personAuthUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).findById(personAuthUpdated.getId())).withRel(VERB_HTTP_GET));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).update(personAuthUpdated)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonAuthResource.class).delete(personAuthUpdated.getId())).withRel(VERB_HTTP_DELETE));		
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){

		logger.info("DELETING PERSON AUTH OBJECT ...: {}", id);
		
		personAuthService.delete(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/me")
	public ResponseEntity<Principal> findById(Principal user) {
		
		return ResponseEntity.ok(user);
	}
}

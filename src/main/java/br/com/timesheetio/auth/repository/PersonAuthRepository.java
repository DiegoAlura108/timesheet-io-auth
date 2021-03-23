package br.com.timesheetio.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.timesheetio.auth.domain.PersonAuthEntity;

@Repository
public interface PersonAuthRepository extends JpaRepository<PersonAuthEntity, Long> {

	public Optional<PersonAuthEntity> findByPersonAuthUserKey(String personAuthUserKey);
	
	public Optional<PersonAuthEntity> findByEmail(String email);
}

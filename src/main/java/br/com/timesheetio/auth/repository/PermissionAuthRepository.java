package br.com.timesheetio.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.timesheetio.auth.domain.PermissionAuthEntity;

@Repository
public interface PermissionAuthRepository extends JpaRepository<PermissionAuthEntity, Long>{

}

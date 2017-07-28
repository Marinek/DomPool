package de.mediapool.server.entities.users.repository;

import org.springframework.data.repository.CrudRepository;

import de.mediapool.server.entities.users.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, String> {

}
 
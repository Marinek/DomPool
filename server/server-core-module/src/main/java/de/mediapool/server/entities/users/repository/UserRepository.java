package de.mediapool.server.entities.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.mediapool.server.entities.users.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

	public List<User> findAllByUsername(String username);

}

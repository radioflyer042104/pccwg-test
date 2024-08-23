package org.pccwg.test.api.repository;

import org.pccwg.test.api.entity.Uzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Uzer, Long> {

	public Uzer findByEmail(String email);
	public Uzer findByEmailAndIdNot(String email, long id);
}

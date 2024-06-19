package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRepository extends CrudRepository<Group,Long> {

    Group findByUsers(User user);

    Group findByName(String name);

    boolean existsByUsers(Set<User> users);
}

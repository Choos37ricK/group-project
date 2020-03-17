package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Optional<Person> findByEmail(String email);
}

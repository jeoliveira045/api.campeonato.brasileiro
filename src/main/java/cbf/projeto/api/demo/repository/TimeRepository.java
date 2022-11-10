package cbf.projeto.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cbf.projeto.api.demo.entity.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer>{ 
}

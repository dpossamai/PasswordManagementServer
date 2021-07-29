package br.com.passwordmanagement.repository;

import br.com.passwordmanagement.model.Password;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    @Query("Select p From Password p Where p.priority = ?1")
    List<Password> findMostRecentPasswordByPriority(boolean priority, Pageable page);

    @Query("Select p From Password p")
    List<Password> getNext(Pageable page);
}

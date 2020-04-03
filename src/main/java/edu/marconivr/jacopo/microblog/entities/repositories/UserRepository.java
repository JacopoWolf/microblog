package edu.marconivr.jacopo.microblog.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByUsername( String username );

    List<User> findByEmail(String email);

}
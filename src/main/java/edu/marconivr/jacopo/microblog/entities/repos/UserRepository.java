package edu.marconivr.jacopo.microblog.entities.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    List<User> findByUserName( String username );

    List<User> findByEmail(String email);
}
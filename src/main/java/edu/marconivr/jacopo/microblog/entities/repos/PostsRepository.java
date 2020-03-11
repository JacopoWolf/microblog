package edu.marconivr.jacopo.microblog.entities.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long>
{

    List<Post> findByAuthor( User author, Pageable pageable) ;

    List<Post> findByTitle( String title, Pageable pageable);

}
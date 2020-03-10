package edu.marconivr.jacopo.microblog.entities.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long>
{

    List<Post> findByAuthor_Id( Long author_id, Pageable pageable) ;

    List<Post> findByTitle( String title, Pageable pageable);

}
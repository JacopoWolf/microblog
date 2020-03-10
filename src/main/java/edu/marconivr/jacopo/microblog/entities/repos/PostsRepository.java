package edu.marconivr.jacopo.microblog.entities.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long>
{

    List<Post> findByAuthor_Id( Long userid );

    List<Post> findByTitle( String title );

}
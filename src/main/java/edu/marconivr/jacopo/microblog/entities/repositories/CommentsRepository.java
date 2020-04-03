package edu.marconivr.jacopo.microblog.entities.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;

@Repository
public interface CommentsRepository extends JpaRepository<Comment,Long>
{

    List<Comment> findAllByUnder( Post post, Sort sort );

}
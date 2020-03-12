package edu.marconivr.jacopo.microblog.entities;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "user" )
public class User
{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Basic
    @Column(name = "username", nullable = false, unique = true)
    public String username;

    @Basic
    public String email;

    @OneToMany(targetEntity = Post.class)
    @Getter @Setter
    private Set<Post> posts;

    @OneToMany(targetEntity = Comment.class)
    @Getter @Setter
    private Set<Comment> comments;


    public User () {}
    public User( String username, String email )
    {
        this.username = username;
        this.email = email;
    }

}
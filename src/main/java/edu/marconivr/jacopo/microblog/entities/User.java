package edu.marconivr.jacopo.microblog.entities;

import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;

@Entity
@Table( name = "user" )
public class User
{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;


    // public properties

    @Basic
    @Column(name = "username", nullable = false, unique = true, length = 30)
    public String username;

    @Basic
    public String email;


    // security properties
    private String password;
    private String token;


    // relations

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


    // non-serializing accessors

    public static String getPasswordOf( User user )
    {
        return user.password;
    }
    public static String getTokenOf( User user )
    {
        return user.token;
    }

    public static void setTokenOf( User user, String token ) 
    {
        user.token = token;
    }

}
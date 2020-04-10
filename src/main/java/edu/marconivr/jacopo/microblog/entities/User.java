package edu.marconivr.jacopo.microblog.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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

    //? those should be in a separate file. But who cares, that's an example.
    // security properties
    @Basic
    private String passwordHash;

    @Basic
    private String salt;

    @Column(name = "token", nullable = true, unique = true)
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
    //? that's a bit odd.

    public static String getHashedPasswordOf( User user )
    {
        return user.passwordHash;
    }
    public static String getSaltOf(User user)
    {
        return user.salt;
    }
    public static String getTokenOf( User user )
    {
        return user.token;
    }

    public static void setTokenOf( User user, String token ) 
    {
        user.token = token;
    }
    public static void setPasswordOf(User user, String password, String salt)
    {
        user.passwordHash = password;
        user.salt = salt;
    }

}
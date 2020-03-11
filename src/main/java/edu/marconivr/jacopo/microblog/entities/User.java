package edu.marconivr.jacopo.microblog.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name =  "utenti")
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

    @OneToMany
    @Getter @Setter
    private List<Post> posts;


    public User () {}
    public User( String username, String email )
    {
        this.username = username;
        this.email = email;
    }

}
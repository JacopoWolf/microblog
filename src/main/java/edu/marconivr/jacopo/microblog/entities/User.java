package edu.marconivr.jacopo.microblog.entities;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name =  "utenti")
public class User
{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Basic
    @Column(name = "username", nullable = false)
    public String userName;

    @Basic
    public String email;


    public User () {}
    public User( String username, String email )
    {
        this.userName = username;
        this.email = email;
    }

}
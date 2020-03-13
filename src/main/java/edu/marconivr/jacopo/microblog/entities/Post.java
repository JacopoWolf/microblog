package edu.marconivr.jacopo.microblog.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table( name = "post" )
public class Post
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    public String title;

    @Temporal( TemporalType.TIMESTAMP )
    public Date date;

    @Lob @Column
    public String content;

    @OneToMany
    private Set<Comment> comments;

    @ManyToOne
    public User author;


    public Long getId()
    {
        return this.id;
    }
}
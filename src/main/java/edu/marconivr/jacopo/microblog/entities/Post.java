package edu.marconivr.jacopo.microblog.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table( name = "posts" )
public class Post
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Basic
    public String title;

    @Temporal( TemporalType.DATE )
    public Date date;

    @Lob @Column
    public String content;

    @Basic 
    @Column( name = "imageurl" )
    public String imageurl;

    @ManyToOne
    public User author;

}
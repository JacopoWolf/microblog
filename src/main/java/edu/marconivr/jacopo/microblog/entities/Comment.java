package edu.marconivr.jacopo.microblog.entities;

import java.util.*;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
public class Comment
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Getter @Setter
    private Post under;

    @ManyToOne
    public User author;

    @Temporal( TemporalType.TIMESTAMP )
    public Date date;

    @Lob @Column
    public String content;


    public Comment(){}
    public Comment( Post under )
    {
        this.under = under;
    }

}
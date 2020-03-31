/**
 * represents a single post
 */
class Post
{
    id : number;
    title : string;
    date : Date;
    content : string;
    author : User

    constructor ( title : string, date : Date, content : string, author : User, id : number )
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.author = author;
    }
}

/**
 * represents the information of a single user
 */
class User
{
    username : string;
    email : string

    constructor(username : string, email : string) 
    {
        this.username = username;
        this.email = email;
    }

}
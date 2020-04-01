/**
 * represents the information of a single user
 */
type User =
    {
        username: string;
        email: string;
    };

/**
 * represents a single post
 */
type Post =
    {
        id: undefined | number;
        date: undefined | Date;
        author: User;
        title: string;
        content: string;
    };

/**
 * represents the comment to a post
 */
type PostComment =
    {
        author: User | undefined;
        date: Date | undefined;
        content: string;
    };
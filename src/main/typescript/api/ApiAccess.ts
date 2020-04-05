/**
 * manages access to the REST api at /rest/
 * completely asynchronous 
 */
class ApiAccess implements IApiAccess
{
    auth : Authentication

    public constructor( auth : Authentication )
    {
        this.auth = auth;
    }


    // GET methods
    async getPage(state: ApplicationState): Promise<Post[]>
    {
        let query =
            '/rest/posts' +
            '?count=' + state.pageSize +
            '&page=' + state.pageNumber +
            '&limitcontent=' + state.contentMaxLengthInView;

        return await $.get(query);
    }

    async getPost(postId: number): Promise<Post>
    {
        let query = '/rest/posts/' + postId;

        return await $.get(query);
    }

    async getPostComments(postId: number) : Promise<PostComment[]>
    {
        let query = '/rest/posts/' + postId + '/comments';
        return await $.get(query);
    }


    // POST methods
    //? those might use a callback for success or failure

    async submitUser(newUser: User): Promise<void>
    {
        return await $.ajax
            ({
                url: "/rest/users",
                type: "POST",
                data: JSON.stringify({ username: newUser.username, email: newUser.email }),
                contentType: "application/json",
                beforeSend: xhr => { this.addAuth(xhr); }
            });
    }

    async submitPost(newPost: Post): Promise<void>
    {
        return await $.ajax
            ({
                url: "/rest/posts?from=" + newPost.author?.username,
                type: "POST",
                data: JSON.stringify({ title: newPost.title, content: newPost.content }),
                contentType: "application/json",
                beforeSend: xhr => {this.addAuth(xhr);}
            });
    }

    async submitComment(from: User, underPostId: number, content: string): Promise<void>
    {
        //todo implement ajax call
    }


    private addAuth( xhr : JQuery.jqXHR<any> )
    {
        if (this.auth.isLoggedIn)
        {
            xhr.setRequestHeader( 'Authentication', <string>this.auth.token );
        }
    }


}
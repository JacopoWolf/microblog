/**
 * manages access to the REST api at /rest/
 * completely asynchronous 
 */
class ApiAccess implements IApiAccess
{
    auth: Authentication;
    cachedUser: User | null = null;

    public constructor(auth: Authentication)
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


    async getPostComments(postId: number): Promise<PostComment[]>
    {
        let query = '/rest/posts/' + postId + '/comments';
        return await $.get(query);
    }


    // POST methods
    //? those might use a callback for success or failure
    async submitPost(newPost: Post): Promise<any>
    {
        return await $.ajax
            ({
                url: "/rest/posts",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ title: newPost.title, content: newPost.content }),
                beforeSend: xhr => { this.addAuth(xhr); }
            });
    }

    async submitComment(from: User, underPostId: number, content: string): Promise<void>
    {
        //todo implement ajax call
    }



    async registerUser(user: User, password: string): Promise<void>
    {
        return await $.ajax
            ({
                url: "/rest/users",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ username: user.username, email: user.email }),
                beforeSend: xhr => { xhr.setRequestHeader("Password", password); }
            });
    }

    async getUserInfo( ): Promise<User>
    {
        if (this.cachedUser != null)
            return <User>this.cachedUser;

        await $.ajax
        ({
            url: "/rest/users",
            type: "GET",
            beforeSend: xhr => { this.addAuth(xhr); },
            success: (data,xhr,code) => { this.cachedUser = data }
        });

        if (this.cachedUser != null)
            return this.cachedUser; 
        throw new Error()
    }




    private addAuth(xhr: JQuery.jqXHR<any>)
    {
        if (this.auth.isLoggedIn)
        {
            xhr.setRequestHeader('Authorization', <string>this.auth.token);
        }
    }
}
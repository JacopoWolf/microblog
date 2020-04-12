/**
 * manages access to the REST api at /rest/
 * completely asynchronous 
 */
class ApiAccess implements IApiAccess
{
    auth: Authentication;

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
    async submitPost(newPost: Post): Promise<number>
    {
        let newPostId = 0 
        
        await $.ajax
            ({
                url: "/rest/posts",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ title: newPost.title, content: newPost.content }),
                beforeSend: xhr => { this.addAuth(xhr); },
                success: (data,status,xhr) => 
                    { 
                        newPostId = 
                            +(
                                xhr.getResponseHeader('Location')?.split('/').pop()
                                ?? 
                                -1
                            ) 
                    }
            });

        return newPostId;
    }

    async submitComment( underPostId: number, content: string ): Promise<void>
    {
        if ( ! this.auth.isLoggedIn)
        {
            alert("can't comment if login has not been performed")
            throw new Error("login required")
        }

        await $.ajax
            ({
                url: "/rest/posts/" + underPostId + "/comments",
                type: "POST",
                contentType: "text/plain",
                data: content,
                beforeSend: xhr => { this.addAuth(xhr); }
            });
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
        let user : User | undefined;

        await $.ajax
        ({
            url: "/rest/users",
            type: "GET",
            beforeSend: xhr => { this.addAuth(xhr); },
            success: (data,status,xhr) => { user = data },
            error: (xhr,exception) => {alert('error retrieving user infos!')}
        });

        return <User>user; 
    }




    public async login(username: string, password: string): Promise<void>
    {
        let data = username + ';' + password;
        await $.ajax
            ({
                url: "/rest/auth/login",
                type: "POST",
                contentType: "text/plain",
                cache: false,
                data: '' + username + ';' + password,
                success:  (data,status,xhr) => {this.auth.token = <string>data},
                error: (xhr, exception) => alert('ERROR ' + xhr.status )
            });
    }
    

    public async logout()
    {
        await $.ajax
            ({
                url: "/rest/auth/logout",
                type: "POST",
                cache: false,
                beforeSend: xhr => { this.addAuth(xhr); }
            });

        this.auth.token = null;
    }



    private addAuth(xhr: JQuery.jqXHR<any>)
    {
        if (this.auth.isLoggedIn)
        {
            xhr.setRequestHeader('Authorization', <string>this.auth.token);
        }
    }
}
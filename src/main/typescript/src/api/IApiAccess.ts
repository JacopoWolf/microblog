/**
 * access the API
 */
interface IApiAccess
{
    getPage(state: ApplicationState): Promise<Post[]>;

    getPost(id: number): Promise<Post>;

    submitPost(post: Post): Promise<void>;


    getPostComments(id: number): Promise<PostComment[]>;

    submitComment(user: User, underId: number, content: string): Promise<void>;


    registerUser( user: User, password: string ): Promise<void>

    getUserInfo( ) : Promise<User>


    login(username: string, password: string): Promise<void>

    logout(): Promise<void>
}
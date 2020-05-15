/**
 * access the API
 */
interface IApiAccess
{
    getPage(state: ApplicationState): Promise<Post[]>;

    getPost(id: number): Promise<Post>;

    submitPost(post: Post): Promise<number>;


    getPostComments(id: number): Promise<PostComment[]>;

    submitComment(underId: number, content: string): Promise<void>;


    registerUser( user: User, password: string ): Promise<void>

    getUserInfo( ) : Promise<User>


    login(username: string, password: string): Promise<void>

    logout(): Promise<void>
}
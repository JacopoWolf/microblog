/**
 * access the API
 */
interface IApiAccess
{
    getPage(state: ApplicationState): Promise<Post[]>;

    getPost(id: number): Promise<Post>;

    getPostComments(id: number): Promise<PostComment[]>;

    submitUser(user: User): Promise<void>;

    submitPost(post: Post): Promise<void>;

    submitComment(user: User, underId: number, content: string): Promise<void>;
}
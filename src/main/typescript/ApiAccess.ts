
type PageHandler = ( data : Post[] ) => void
type PostHandler = ( post : Post ) => void
type SuccessHandler = () => void

/**
 * manages access to the REST api at /rest/
 */
class ApiAccess
{
    state : ApplicationState;


    constructor ( state : ApplicationState )
    {
        this.state = state;
    }


    getPage( handler : PageHandler )
    {
        let query = 
            '/rest/posts' + 
            '?count=' + this.state.pageSize + 
            '&page=' + this.state.pageNumber + 
            '&limitcontent=' + this.state.contentMaxLengthInView;

        $.get( query, handler );
    }

    getPost( postId : number, handler : PostHandler )
    {
        let query = '/rest/posts/' + postId;

        $.get( query, handler );
    }


    submitPost( newPost : Post, handler : SuccessHandler )
    {
        $.ajax
        ({
            url : "/rest/posts?from=" + newPost.author.username,
            type: "POST",
            data: JSON.stringify({ title: newPost.title, content: newPost.content }),
            contentType: "application/json",
            success: handler
        });
    }

    submitComment( from : User, underPostId : number, content : string, handler : SuccessHandler )
    {
        //todo implement ajax call
    }

}
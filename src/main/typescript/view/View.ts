/**
 * controls the View state of the application
 */
class View
{
    _state: ApplicationState;
    _api: APIAccess;

    constructor(state: ApplicationState, access: APIAccess)
    {
        this._state = state;
        this._api = access;
    }



    //* changes between post view and post editor
    get currentLocation(): ViewLocation
    {
        return this._state.currentLocation;

    }
    set currentLocation(value: ViewLocation)
    {
        switch (value)
        {
            case 'single':
            case 'view':
                $(PAGE.VIEW.postContainer).css('display', 'block');
                $(PAGE.VIEW.postCreator).css('display', 'none');
                break;
            case 'create':
                $(PAGE.VIEW.postContainer).css('display', 'none');
                $(PAGE.VIEW.postCreator).css('display', 'block');
                break;
        }
        this._state.currentLocation = value;
    }

    //  DISPLAY FUNCTIONS


    displayMany(posts: Post[])
    {
        this.clear();

        // updates page number in the navbar
        $(PAGE.VIEW.pageNumber).html(this._state.pageNumber.toString());

        // displays every post
        $.each(posts, (i, post) =>
        {
            // clones the template
            let pt = $(PAGE.VIEW.templates).find(PAGE.POST.multiple).clone();

            // header
            pt.find(PAGE.POST.multi_header).html('<br>' + post.author?.username + '</b> on ' + post.date);
            // title
            pt.find(PAGE.POST.multi_title).html(post.title);
            pt.find(PAGE.POST.multi_title).click
                (
                    //* used to load a single post
                    async () => 
                    {
                        this.displaySingle(await this._api.getPost(<number>post.id));
                        this.displayComments(await this._api.getPostComments(<number>post.id));
                    }
                );
            // content
            let contentEnd = (post.content.length < this._state.contentMaxLengthInView) ? '' : '...';
            pt.find(PAGE.POST.multi_content).html(post.content + contentEnd);


            $(PAGE.VIEW.postContainer).append(pt);

        });

    }


    displaySingle(post: Post)
    {
        this.clear();

        //updates page count
        $(PAGE.VIEW.pageNumber).html('#' + post.id);

        // clones the template
        var pt = $(PAGE.VIEW.templates).find(PAGE.POST.single).clone();

        // header
        pt.find(PAGE.POST.single_date).html(<string>post.date?.toString());
        pt.find(PAGE.POST.single_username).html(<string>post.author?.username);
        // title
        pt.find(PAGE.POST.single_title).html(post.title);
        // content
        pt.find(PAGE.POST.single_content).html(post.content);


        $(PAGE.VIEW.postContainer).append(pt);

        this._state.lastPostId = <number>post.id;

    }

    displayComments(comments: PostComment[])
    {
        $.each(comments, (i, comment) =>
        {
            let cmt = $(PAGE.VIEW.templates).find(PAGE.COMMENT.class).clone();

            cmt.find(PAGE.COMMENT.header).html('<b>' + comment.author?.username + '</b> on ' + comment.date);
            cmt.find(PAGE.COMMENT.content).html(comment.content);

            $(PAGE.VIEW.postContainer).append(cmt);

        });
    }

    // utilities

    clear(): void
    {
        $(PAGE.VIEW.postContainer).html('');
    }



}
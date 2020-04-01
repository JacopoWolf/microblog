
class View
{
    _state: ApplicationState;
    _api: ApiAccess;

    constructor(state: ApplicationState, access: ApiAccess)
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
            case 'view':
                $('#postcontainer').css('display', 'block');
                $('#postcreator').css('display', 'none');
                break;
            case 'create':
                $('#postcontainer').css('display', 'none');
                $('#postcreator').css('display', 'block');
                break;
        }
        this._state.currentLocation = value;
    }

    //  DISPLAY FUNCTIONS


    displayMany(posts: Post[])
    {
        this.clear();

        // updates page number in the navbar
        $('#pagecount').html(this._state.pageNumber.toString());

        // displays every post
        $.each(posts, (i, post) =>
        {
            // clones the template
            let pt = $('#templates').find('.post').clone();

            // header
            pt.find('.postheader').html('<br>' + post.author?.username + '</b> on ' + post.date);
            // title
            pt.find('.postcontent_h').html(post.title);
            pt.find('.postcontent_h').click
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
            pt.find('.postcontent_p').html(post.content + contentEnd);


            $('#postcontainer').append(pt);

        });

    }


    displaySingle(post: Post)
    {
        this.clear();

        //updates page count
        $('#pagecount').html('<i><b>(' + this._state.pageNumber + ')</b></i>');

        // clones the template
        var pt = $('#templates').find('.singlepost').clone();

        // header
        pt.find('.singlepost_h').html(<string>post.date?.toString());
        pt.find('.singlepostheader_usrn').html(<string>post.author?.username);
        // title
        pt.find('.singlepostheader_t').html(post.title);
        // content
        pt.find('.singlepostcontent_p').html(post.content);


        $('#postcontainer').append(pt);

        this._state.lastPostId = <number>post.id;

    }

    displayComments(comments: PostComment[])
    {
        $.each(comments, (i, comment) =>
        {
            let cmt = $('#templates').find('.comment').clone();

            cmt.find('.postheader').html('<b>' + comment.author?.username + '</b> on ' + comment.date);
            cmt.find('.postcontent').html(comment.content);

            $('#postcontainer').append(cmt);

        });
    }

    // utilities

    clear(): void
    {
        $('#postcontainer').html('');
    }



}
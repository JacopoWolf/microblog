namespace PAGE
{

    /**
     * @static
     */
    export class BUTTONS
    {
        static backButton = '#btn_back'
        static forwardButton = '#btn_forward'
        static reloadButton = '#btn_reload'
        static resetButton = '#btn_reset'
        static createPostViewButton = '#btn_create'

        static normalViewButton = '#btn_view'
        static submitPostButton = '#btn_submitPost'
    }

    /**
     * @static
     */
    export class VIEW
    {
        // different views
        static postContainer = '#postContainer'
        static postCreator = '#postCreator'

        static templates = '#templates'

        static pageNumber = '#pageCount'
    }

    /**
     * @static
     */
    export class POST
    {
        static multiple = '.post'
        static single = '.singlePost'

        static multi_header = '.post_header'
        static multi_title = '.post_content_h'
        static multi_content = '.post_content_p'

        static single_date = '.singlePost_header_date'
        static single_username = '.singlePost_header_username'
        static single_title = '.singlePost_header_title'
        static single_content = '.singlePost_content_p'   
    }

    /**
     * @static
     */
    export class COMMENT
    {
        static class = '.comment'
        
        static header = PAGE.POST.multi_header
        static content = '.post_content'
    }

    /**
     * @static
     */
    export class CREATION
    {
        static username = '#c_username'
        static email = '#c_email'

        static title = '#c_title'
        static content = '#c_content'
    }
}
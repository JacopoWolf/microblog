class App
{
    _state: ApplicationState;
    _api: ApiAccess;
    _view: View;

    constructor()
    {
        // component initialization
        this._state = new ApplicationState();
        this._api = new ApiAccess();
        this._view = new View(this._state, this._api);

        // initializing view
        //* already loads with current state
        this._view.currentLocation = "view";
        this.updateView();


        //      EVENT BINDING
        
        // previous page, can't get below 0
        $('#btn_back').click(() => { if ( --this._state.pageNumber < 0) this._state.pageNumber = 0 ; this.updateView(); });
        // next page
        $('#btn_forw').click(() => { ++this._state.pageNumber; this.updateView(); });   
        // return to first page
        $('#pagespan').click(() => { this._state.pageNumber = 0; this.updateView(); });   
        // refresh
        $('#pagecount').click(() => { this.updateView(); });                            

        // post creation mode
        $('#btn_create').click(() => { this._view.currentLocation = "create"; });      
        // get back to viewing posts
        $('#btn_nocreate').click(() => { this._view.currentLocation = "view"; this.updateView() });      

        // submits a new user and post
        $('#btn_createpost').click(async () => { this.submitUserAndPost(); });         

    }


    updateView()
    {
        $.when(

            this._api
                .getPage(this._state)
                .then(posts => { this._view.displayMany(posts); })

        );
    }


    async submitUserAndPost()
    {
        let user: User =
        {
            username: <string>$('#c_username').val(),
            email: <string>$('#c_email').val()
        };

        try
        {
            await this._api.submitUser(user);
            alert('creato un novo utente!');
        }
        catch
        {
            console.log('utente esistente')
        }
        


        let post: Post =
        {
            title: <string>$('#c_title').val(),
            content: <string>$('#c_content').val(),
            author: user,

            // assigned by the Server
            date: undefined,
            id: undefined
        };

        await this._api.submitPost(post);
        alert('creato un nuovo post!');
    }    

}
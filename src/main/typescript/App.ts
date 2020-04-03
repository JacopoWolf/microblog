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
        $(PAGE.backButton).click(() => { if ( --this._state.pageNumber < 0) this._state.pageNumber = 0 ; this.updateView(); });
        // next page
        $(PAGE.forwardButton).click(() => { ++this._state.pageNumber; this.updateView(); });   
        // return to first page
        $(PAGE.resetButton).click(() => { this._state.pageNumber = 0; this.updateView(); });   
        // refresh
        $(PAGE.reloadButton).click(() => { this.updateView(); });                            

        // post creation mode
        $(PAGE.createPostViewButton).click(() => { this._view.currentLocation = "create"; });      
        // get back to viewing posts
        $(PAGE.normalViewButton).click(() => { this._view.currentLocation = "view"; this.updateView() });      

        // submits a new user and post
        $(PAGE.createPostViewButton).click(async () => { this.submitUserAndPost(); });         

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
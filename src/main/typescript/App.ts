class App
{
    appState: ApplicationState;
    api: APIAccess;
    view: View;

    constructor()
    {
        // component initialization
        this.appState = new ApplicationState();
        this.api = new APIAccess();
        this.view = new View(this.appState, this.api);

        // initializing view
        //* already loads with current state
        this.view.currentLocation = "view";
        this.updateView();
        this.eventBinding();
    }


    updateView()
    {
        // synchronously waits for the page to upload
        $.when(

            this.api
                .getPage(this.appState)
                .then(posts => { this.view.displayMany(posts); })

        );
    }

    /**
     * binds events and callbacks
     */
    eventBinding()
    {
        // previous page, can't get below 0
        $(PAGE.BUTTONS.backButton).click(() => { if ( --this.appState.pageNumber < 0) this.appState.pageNumber = 0 ; this.updateView(); });
        // next page
        $(PAGE.BUTTONS.forwardButton).click(() => { ++this.appState.pageNumber; this.updateView(); });   
        // return to first page
        $(PAGE.BUTTONS.resetButton).click(() => { this.appState.pageNumber = 0; this.updateView(); this.view.currentLocation = "view" });   
        // refresh
        $(PAGE.BUTTONS.reloadButton).click(() => { this.updateView(); });                            

        // post creation mode
        $(PAGE.BUTTONS.createPostViewButton).click(() => { this.view.currentLocation = "create"; });      
        // get back to viewing posts
        $(PAGE.BUTTONS.normalViewButton).click(() => { this.view.currentLocation = "view"; this.updateView() });      

        // submits a new user and post
        $(PAGE.BUTTONS.createPostViewButton).click(async () => { this.submitUserAndPost(); });     
    }

    //todo move to View
    async submitUserAndPost()
    {
        let user: User =
        {
            username: <string>$(PAGE.CREATION.username).val(),
            email: <string>$(PAGE.CREATION.email).val()
        };

        try
        {
            await this.api.submitUser(user);
            alert('creato un novo utente!');
        }
        catch
        {
            console.log('utente esistente')
        }
        


        let post: Post =
        {
            title: <string>$(PAGE.CREATION.title).val(),
            content: <string>$(PAGE.CREATION.content).val(),
            author: user,

            // assigned by the Server
            date: undefined,
            id: undefined
        };

        await this.api.submitPost(post);
        alert('creato un nuovo post!');
    }    

}
class App
{
    appState: ApplicationState;
    api: IApiAccess;
    auth: Authentication;
    view: View;

    constructor()
    {
        // component initialization
        this.appState = new ApplicationState();
        this.auth = new Authentication();
        this.api = new ApiAccess(this.auth);
        this.view = new View(this.appState, this.api);

        // initializing view
        this.view.currentLocation = "view";
        this.updateView();
        this.eventBinding();
    }


    updateView()
    {

        switch (this.appState.currentLocation)
        {
            case "view":

            // synchronously waits for the page to upload
                $.when(

                    this.api
                        .getPage(this.appState)
                        .then( posts => { this.view.displayMany(posts); } )

                );
                break;


            case "single":
                $.when(
                    this.api
                        .getPost( this.appState.lastPostId )
                        .then( post => { this.view.displaySingle(post); } )
                );
                $.when(
                    this.api
                        .getPostComments( this.appState.lastPostId )
                        .then( comments => { this.view.displayComments(comments); } )
                );
                break;

        }
        
    }

    /**
     * binds events and callbacks
     */
    eventBinding()
    {
        // previous page, can't get below 0
        $(PAGE.BUTTONS.backButton).click(
                () => 
                { 
                    if (--this.appState.pageNumber < 0) this.appState.pageNumber = 0; 
                    this.appState.currentLocation = "view"; 
                    this.updateView(); 
                });
        // next page
        $(PAGE.BUTTONS.forwardButton).click(() => { ++this.appState.pageNumber; this.appState.currentLocation = "view"; this.updateView(); });
        // return to first page
        $(PAGE.BUTTONS.resetButton).click(() => { this.appState.pageNumber = 0; this.appState.currentLocation = "view"; this.updateView();});
        // refresh
        $(PAGE.BUTTONS.reloadButton).click(() => { this.updateView(); });

        // post creation mode
        $(PAGE.BUTTONS.createPostViewButton).click(() => { this.view.currentLocation = "create"; });
        // get back to viewing posts
        $(PAGE.BUTTONS.normalViewButton).click(() => { this.view.currentLocation = "view"; this.updateView(); });

        // submits a new post
        $(PAGE.BUTTONS.submitPostButton).click(async () => { this.submitPost(); });

        // open login window
        $(PAGE.BUTTONS.loginStatusButton).click(() => { this.clickLogin() });
        // close login window
        $(PAGE.LOGIN.closeWindowButton).click(() => { this.view.setLoginWindowDisplay(false); });
        //todo submit login
        $(PAGE.LOGIN.submitButton).click(() => { this.doLogin(); });

    }


    async clickLogin()
    {
        if (this.auth.isLoggedIn)
        {
            if (window.confirm('do you really want to logout?'))
            {
                await this.api.logout()
                await this.view.updateLoginStatus( this.auth );
            }
        }
        else
        {
            this.view.setLoginWindowDisplay(true);
        }
    }

    async doLogin()
    {
        let username = <string>$(PAGE.LOGIN.username).val();
        let password = <string>$(PAGE.LOGIN.password).val();

        await this.api.login(username,password);
        await this.view.updateLoginStatus( this.auth );

        this.view.setLoginWindowDisplay(false);

        $(PAGE.LOGIN.password).val('');
    }


    async submitPost()
    {
        if (!this.auth.isLoggedIn)
        {
            alert("can't post if login has not been performed")
            throw new Error("login required")
        }
        

        let post: Post =
        {
            title: <string>$(PAGE.CREATION.title).val(),
            content: <string>$(PAGE.CREATION.content).val(),

            // assigned by the Server
            author: undefined,
            date: undefined,
            id: undefined
        };

        this.appState.lastPostId = await this.api.submitPost(post);

        alert('creato un nuovo post!');

        this.view.currentLocation = "single";
        this.updateView();
        //this.view.displaySingle( await this.api.getPost(newId) );
    }

}
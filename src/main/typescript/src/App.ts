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
        $(PAGE.BUTTONS.backButton).click(() => { if (--this.appState.pageNumber < 0) this.appState.pageNumber = 0; this.updateView(); });
        // next page
        $(PAGE.BUTTONS.forwardButton).click(() => { ++this.appState.pageNumber; this.updateView(); });
        // return to first page
        $(PAGE.BUTTONS.resetButton).click(() => { this.appState.pageNumber = 0; this.updateView(); this.view.currentLocation = "view"; });
        // refresh
        $(PAGE.BUTTONS.reloadButton).click(() => { this.updateView(); });

        // post creation mode
        $(PAGE.BUTTONS.createPostViewButton).click(() => { this.view.currentLocation = "create"; });
        // get back to viewing posts
        $(PAGE.BUTTONS.normalViewButton).click(() => { this.view.currentLocation = "view"; this.updateView(); });

        // submits a new user and post
        $(PAGE.BUTTONS.submitPostButton).click(async () => { this.submitUserAndPost(); });

        // open login window
        $(PAGE.BUTTONS.loginStatusButton).click(() => { this.clickLogin() });
        // close login window
        $(PAGE.LOGIN.closeWindowButton).click(() => { this.view.setLoginWindowDisplay(false); });
        //todo submit login
        $(PAGE.LOGIN.submitButton).click(() => { this.doLogin(); });

    }


    clickLogin()
    {
        if (this.auth.isLoggedIn)
        {
            if (window.confirm('do you really want to logout?'))
            {
                $.when(this.api.logout() );
                $.when(this.view.updateLoginStatus( this.auth ) );
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


    async submitUserAndPost()
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

        await this.api.submitPost(post);
        alert('creato un nuovo post!');
    }

}
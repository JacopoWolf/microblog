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
        $('#btn_back').click(() => { this._state.pageNumber--; this.updateView(); });   // previous page
        $('#btn_forw').click(() => { this._state.pageNumber++; this.updateView(); });   // next page
        $('#pagespan').click(() => { this._state.pageNumber = 0; this.updateView(); });   // return to main page
        $('#pagecount').click(() => { this.updateView(); });       //reload

        $('#btn_create').click(() => { this._view.currentLocation = "create"; });      // post creation mode
        $('#btn_nocreate').click(() => { this._view.currentLocation = "view"; });      // get back to viewing posts

        //todo move to its own function
        $('#btn_createpost').click(async () =>    // submits a new post
        {
            let user: User =
            {
                username: <string>$('#c_username').val(),
                email: <string>$('#c_email').val()
            };

            await this._api.submitUser(user);
            //alert('creato un novo utente!');


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
        });

    }


    updateView()
    {
        $.when(

            this._api
                .getPage(this._state)
                .then(posts => { this._view.displayMany(posts); })

        );
    }

}
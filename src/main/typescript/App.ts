class App
{
    state : ApplicationState;
    apiAccess : ApiAccess; 

    constructor ()
    {
        this.state = new ApplicationState();
        this.apiAccess = new ApiAccess( this.state );

        this.state.currentLocation = "view";

        //todo complete editing
        this.apiAccess.getPage( ( data : Post[] ) => 
        {
            $.each(data,(i,val)=> alert(val.id + ' ' + val.date));
            //! this is still a test
        } );
    }

}




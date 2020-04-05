/**
 * manages user authentication
 */
class Authentication
{
    private _user : (User | null) = null;
    private _token: (string | null) = null;


    public get user() : (User | null)
    {
        return this._user;
    }    

    public get token() : (string | null)
    {
        return this._token;
    }



    public get isLoggedIn(): boolean
    {
        return this._token != undefined;
    }


    public async login(username: string, password: string) 
    {
        //todo implement ajax query to login and set token

        //todo gather user info
    }

    public async logout()
    {
        this._user = null;
        //todo implement
    }

}
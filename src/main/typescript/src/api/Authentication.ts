/**
 * manages user authentication
 */
class Authentication
{
    private _token: (string | null) = null;


    public get token(): (string | null)
    {
        return this._token;
    }

    public get isLoggedIn(): boolean
    {
        return this._token != null;
    }


    public async login(username: string, password: string): Promise<void>
    {
        let data = username + ';' + password;
        await $.ajax
            ({
                url: "/rest/auth/login",
                type: "POST",
                contentType: "text/plain",
                cache: false,
                data: '' + username + ';' + password,
                success:  (data,status,xhr) => {this._token = <string>data},
                error: (xhr, exception) => alert('ERROR ' + xhr.status )
            });
    }

    public async logout()
    {
        this._token = null;
        //todo implement
    }

}
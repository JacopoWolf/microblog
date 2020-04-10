/**
 * manages user authentication
 */
class Authentication
{
    token: (string | null) = null;


    public get isLoggedIn(): boolean
    {
        return this.token != null;
    }

}
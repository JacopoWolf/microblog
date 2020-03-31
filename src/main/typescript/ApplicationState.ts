/**
 * defines the different views the application can have
 */
type ViewLocation = 'create' | 'view';

/**
 * represents the application state
 */
class ApplicationState
{
    public pageSize    = 10;
    public pageNumber  = 0;
    public contentMaxLengthInView = 40;

    private _currentLocation : ViewLocation = 'create';

    public lastPostId  = -1;



    get currentLocation() : ViewLocation
    {
        return this._currentLocation;
        
    }
    set currentLocation( value : ViewLocation )
    {
        switch (value)
        {
            case 'view':
                //todo add view stuff
                break;
            case 'create':
                //todo add create stuff
                break;
            
        }
        this._currentLocation = value;

    }
}
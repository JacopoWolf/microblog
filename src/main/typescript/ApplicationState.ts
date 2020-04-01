/**
 * defines the different views the application can have
 */
type ViewLocation = 'create' | 'view';

/**
 * represents the application state
 */
class ApplicationState
{
    pageSize    = 10;
    pageNumber  = 0;
    contentMaxLengthInView = 40;

    currentLocation : ViewLocation = 'create';

    lastPostId  = -1;
}
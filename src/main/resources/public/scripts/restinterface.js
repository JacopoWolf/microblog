
var REST = 
{
    // loading methods

    loadpage : function ( page, forw )
    {
        if (page != null)
            APP.page = page;

        // controlla la validità delle variabili
        if (forw != null)
            if (forw)
                APP.page+=1;
            else
                APP.page-=1;

        if (APP.page < 0)
            APP.page = 0;

        var p = parseInt(APP.page);
        var s = parseInt(APP.pagesize);

            
        $.get( '/rest/posts?count=' + s + '&page=' + p + '&limitcontent=' + 40 , function(data)
        {
            DISPLAY.displayMany( data )
        });

    },

    loadpost : function ( id )
    {
        $.get( '/rest/posts/' + id, function(data) 
        {   
            DISPLAY.displayPost( data );
        });

        $.get('/rest/posts/' + id + '/comments', function(data)
        {
            DISPLAY.displayComments( data );
        });
    },


    // creation methods

    submituser : function ()
    {
        var name = $('#c_username').val();
        var mail = $('#c_email').val();

        $.ajax
        ({
            async: false,
            url : "/rest/users",
            type: "POST",
            data: JSON.stringify({ username: name, email: mail }),
            contentType: "application/json",
            success: function()
                {
                    alert("nuovo utente creato!")
                }
        });
    },

    submitpost : function()
    {
        var name = $('#c_username').val();
        var title = $('#c_title').val();
        var content = $('#c_content').val();


        if (title == "")
            title = "no title";

        $.ajax
        ({
            url : "/rest/posts?from=" + name,
            type: "POST",
            data: JSON.stringify({ title: title, content: content }),
            contentType: "application/json",
            success: function()
                {
                    alert("nuovo post creato!")
                }
        });
    },

    submitcomment : function()
    {
        //todo implement
    }

}
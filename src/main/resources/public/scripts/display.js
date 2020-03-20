
var DISPLAY = 
{
    changecontext : function(context)
    {        
        switch (context) {
            case 'view':
                REST.loadpage(null,null);
                $('#postcontainer').css('display','block');
                $('#postcreator').css('display','none');
                APP.currentpage = context;
                break;

            case 'create':
                $('#postcontainer').css('display','none');
                $('#postcreator').css('display','block');
                APP.currentpage = context;
                break;
            default:
                break;
        }
    },
    
    // display methods

    clear : function ()
    {
        $("#postcontainer").html("");
    },

    displayPost : function( post )
    {
        DISPLAY.clear();
        $('#pagecount').html( '<i><b>(' + APP.page + ')</b></i>' );

        var pt = $('#templates').find('.singlepost').clone();

        pt.find('.singlepost_h').html( post['date'] );
        pt.find('.singlepostheader_usrn').html( post['author']['username'] );
        pt.find('.singlepostheader_t').html( post['title'] );
        pt.find('.singlepostcontent_p').html( post['content'] );

        $('#postcontainer').append( pt );

        APP.currentpost = post['id'];
    },

    displayComments : function( data )
    {
        $.each( data, function(i,v)
        {
            var cmt = $('#templates').find('.comment').clone();

            cmt.find('.postheader').html( '<b>' + v['author']['username'] + '</b> on ' + v['date'] );
            cmt.find('.postcontent').html( v['content'] )

            $('#postcontainer').append( cmt )

        });
    },

    displayMany : function ( data, comments )
    {
        DISPLAY.clear();  
        $('#pagecount').html(APP.page);
        
        $.each(data,function(i,v)
        {
            var pt = $('#templates').find('.post').clone();

            pt.find('.postheader').html( '<b>' + v['author']['username'] + '</b> on ' + v['date'] );

            pt.find('.postcontent_h').html(v['title'])
            pt.find('.postcontent_h').click( function(){REST.loadpost(v['id'])} );

            pt.find('.postcontent_p').html(v['content'] + '...');

            $('#postcontainer').append( pt );
        });
    },


    // for malefic testing purposes. 
    test : function()
    {
        alert('funzionaaaaa mhuhuhahaha');
    }

}
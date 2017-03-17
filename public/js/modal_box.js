/**
 * Call modal popup when a user want to save the shape on the map
 * @param feature - the shape object
 * @param callback - determines which the button was pressed
 */
function createModalBox(feature, callback) {
    var state = true;

    addBlockPage();
    addPopupBox();
    addStyle();
    $('.modal-box').fadeIn();

    /**
     * Makes style for elements of popup
     */
    function addStyle() {
        $('.modal-box').css({
            'position':'absolute',
            'left':'30%',
            'top': '20%',
            'display':'none',
            'height': '200px',
            'width': '300px',
            'border':'1px solid #fff',
            'border-radius':'10px',
            'background': '#f2f2f2'
        });

        var pageHeight = $(document).height();
        var pageWidth = $(window).width();

        $('.block-page').css({
            'position':'absolute',
            'top':'0',
            'left':'0',
            'background-color':'rgba(0,0,0,0.6)',
            'height':pageHeight,
            'width':pageWidth,
            'z-index':'10'
        });

        $('.modal-content').css({
            'background-color':'#fff',
            'height':'150px',
            'width':'250px',
            'padding':'10px',
            'margin':'15px',
            'border-radius':'10px',
        });
    }

    /**
     * Create block page for popup
     */
    function addBlockPage() {
        var blockPage = document.createElement('div');
        $(blockPage).attr('class','block-page');
        $(blockPage).appendTo('body');
    }

    /**
     * Create popup
     */
    function addPopupBox() {
        var popup = document.createElement('div');
        $(popup).attr('class', 'modal-box');
        var content = document.createElement('div');
        $(content).attr('class', 'modal-content');
        var form = document.createElement('form');
        var labelShapeName = document.createElement('label');
        $(labelShapeName).html('Название');
        var inputShapeName = document.createElement('input');
        $(inputShapeName).attr({
            type: 'text',
            id: 'shape-name'
        });
        var labelShapeDesc = document.createElement('label');
        $(labelShapeDesc).html('Описание');
        var inputShapeDesc = document.createElement('input');
        $(inputShapeDesc).attr({
            type: 'text',
            id: 'shape-desc'
        });
        var btnOK = document.createElement('button');
        $(btnOK).html('Сохранить');
        $(btnOK).attr('type', 'button');
        $(btnOK).prop('disabled', true);
        var btnCancel = document.createElement('button');
        $(btnCancel).html('Отменить');
        $(btnCancel).attr('type', 'button');
        $(form).append(labelShapeName, inputShapeName, labelShapeDesc, inputShapeDesc, btnOK, btnCancel);
        $(content).append(form);
        $(popup).append(content);
        $(popup).appendTo('.block-page');

        /**
         * Validates the form
         */
        $(form).keyup(function() {
            $(btnOK).prop('disabled', true);
            var name = $('#shape-name').val().trim();
            if (name != '') {
                $(btnOK).prop('disabled', false);
            }
        });

        $(btnOK).click(function() {
            feature.setProperties({
                'name': $('#shape-name').val(),
                'description': $('#shape-desc').val()
            })
            closeBox();
        });

        $(btnCancel).click(function() {
            state = false;
            closeBox();
        });

        /**
         * Removes the popup
         */
        function closeBox() {
            $('.modal-box').fadeOut().remove();
            $('.block-page').fadeOut().remove();
            callback(state);
        }
    }
}


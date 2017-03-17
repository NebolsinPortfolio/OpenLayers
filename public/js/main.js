window.onload = function() {

    var gDiv = document.getElementById('google-map');

    var gmap = new google.maps.Map(gDiv,{
        disableDefaultUI: true,
        keyboardShortcuts: false,
        draggable: false,
        disableDoubleClickZoom: true,
        scrollwheel: false,
        streetViewControl: false
    });

    var shapeStyle = new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.2)'
        }),
        stroke: new ol.style.Stroke({
            color: '#ffcc33',
            width: 2
        }),
        image: new ol.style.Icon(({
            anchor: [0.5, 46],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            src: './resources/point-map.png'
        }))
    });

    var features = new ol.Collection();
    var featureOverlay = new ol.layer.Vector({
        source: new ol.source.Vector({features: features}),
        style: shapeStyle
    });

    var layerOSM = new ol.layer.Tile({
        source: new ol.source.OSM()
    });

    var view = new ol.View({
        maxZoom: 21  // restriction GMap
    });

    // Need for GMap
    view.on('change:center', function() {
        var center = ol.proj.transform(view.getCenter(), 'EPSG:3857', 'EPSG:4326');
        gmap.setCenter(new google.maps.LatLng(center[1], center[0]));
    });
    // Need for GMap
    view.on('change:resolution', function() {
        gmap.setZoom(view.getZoom());
    });

    var olDiv = document.getElementById('ol-map');  // for OSM

    var map = new ol.Map({
        target: olDiv,
        view: view
    });

    layerOSM.setMap(map);
    featureOverlay.setMap(map);

    var drawType;
    var draw; // draw interaction
    var selectedShape;
    createShapePanel();
    view.setCenter(ol.proj.fromLonLat([33.53, 44.62]));
    view.setZoom(9);
    layerOSM.setVisible(false);
    olDiv.parentNode.removeChild(olDiv);
    gmap.controls[google.maps.ControlPosition.TOP_LEFT].push(olDiv); //

    var layerSelect = document.getElementById('layer-select');
    layerSelect.addEventListener('change', changeLayer);

    var arrayOfFeatures;
    var geojson  = new ol.format.GeoJSON();

    /**
     * Event handler click for button "Сохранить"
     */
    $('#btn-save-map').click(function() {
        if (features.getLength() !== 0) {
            arrayOfFeatures = features.getArray();
            console.log(geojson.writeFeatures(arrayOfFeatures));
            $.ajax({
                type: 'POST',
                contentType: 'application/json, charset=UTF-8',
                url:  'http://localhost/api/MainServlet',
                dataType: 'text',
                data: geojson.writeFeatures(arrayOfFeatures),
                success: function () {
                    alert("Фигуры сохранены в базе");
                },
                error: function () {
                    alert("Ошибка");
                }
            })
        }
    });

    /**
     * Event handler click for button "Загрузить"
     */
    $('#btn-load-map').click(function() {
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url:  'http://localhost/api/MainServlet',
            dataType: 'json',
            success: function (data) {
                arrayOfFeatures = geojson.readFeatures(data);
                console.log(arrayOfFeatures);
                features.clear();
                $.each(arrayOfFeatures, function (index, value) {
                    features.push(value);
                });
            },
            error: function () {
                alert("Ошибка");
            }
        })
    });

    /**
     * Event handler click for button "Сохранить" at edit form
     */
    $('#btn-save-shape').click(function() {
        selectedShape.setProperties({
            'name': $('#name-shape').val(),
            'description': $('#desc-shape').val()
        });
        selectedShape.name = $('#name-shape').val();
    });

    /**
     * Validates field "Имя"
     */
    $('#shape-form').keyup(function() {
        $('#btn-save-shape').prop('disabled', true);
        var name = $('#name-shape').val().trim();
        if (name != '') {
            $('#btn-save-shape').prop('disabled', false);
        }
    });

    /**
     * Adds a management panel at left part of the map
     */
    function createShapePanel() {
        var btnCursor = document.createElement('button');
        btnCursor.id = 'btn-cursor';
        btnCursor.addEventListener('click', handleModifyShape, false);
        var imgBtnCursor = document.createElement('img');
        $(imgBtnCursor).attr({
            src: './resources/cursor.png',
            alt: 'C'
        });
        btnCursor.appendChild(imgBtnCursor);
        var btnDelete = document.createElement('button');
        btnDelete. id = 'btn-delete';
        $(btnDelete).prop('disabled', true);
        btnDelete.addEventListener('click', handleDeleteShape, false);
        var imgBtnDelete = document.createElement('img');
        $(imgBtnDelete).attr({
            src: './resources/delete.png',
            alt: 'R'
        });
        btnDelete.appendChild(imgBtnDelete);
        var btnAddMarker = document.createElement('button');
        btnAddMarker.id = 'Point';
        btnAddMarker.addEventListener('click', handleCreateShape, false);
        var imgBtnAddMarker = document.createElement('img');
        $(imgBtnAddMarker).attr({
            src: './resources/point.png',
            alt: 'M'
        });
        btnAddMarker.appendChild(imgBtnAddMarker);
        var btnAddPolyline = document.createElement('button');
        btnAddPolyline.id = 'LineString';
        btnAddPolyline.addEventListener('click', handleCreateShape, false);
        var imgBtnAddPolyline = document.createElement('img');
        $(imgBtnAddPolyline).attr({
            src: './resources/polyline.png',
            alt: 'L'
        });
        btnAddPolyline.appendChild(imgBtnAddPolyline);
        var btnAddPolygon = document.createElement('button');
        btnAddPolygon.id = 'Polygon';
        btnAddPolygon.addEventListener('click', handleCreateShape, false);
        var imgBtnAddPolygon = document.createElement('img');
        $(imgBtnAddPolygon).attr({
            src: './resources/polygon.png',
            alt: 'P'
        });
        btnAddPolygon.appendChild(imgBtnAddPolygon);
        var managerShapes = document.createElement('div');
        managerShapes.className = 'manage-custom ol-unselectable ol-control';
        managerShapes.appendChild(btnCursor);
        managerShapes.appendChild(btnDelete);
        managerShapes.appendChild(btnAddMarker);
        managerShapes.appendChild(btnAddPolyline);
        managerShapes.appendChild(btnAddPolygon);
        var managerShapesControl = new ol.control.Control({element: managerShapes});
        map.addControl(managerShapesControl);
    }

    /**
     * Swithes between Gmap and OSM
     */
    function changeLayer() {
        if (layerSelect.value === 'google') {
            olDiv.parentNode.appendChild(gDiv);
            olDiv.parentNode.removeChild(olDiv);
            gmap.controls[google.maps.ControlPosition.TOP_LEFT].push(olDiv);
            layerOSM.setVisible(false);
            map.render();
        }
        else {
            gmap.controls[google.maps.ControlPosition.TOP_LEFT].clear();
            gDiv.parentNode.appendChild(olDiv);
            $('#ol-map').css({
                'position':'relative'
            });
            gDiv.parentNode.removeChild(gDiv);
            layerOSM.setVisible(true);
            map.render();
        }
    }

    var selectInteraction;  // select interaction
    var modify;             // modify interaction

    /**
     * Draws shapes on the map
     */
    function handleCreateShape() {
        $('#for-shape-form').hide();
        drawType = this.id;
        map.removeInteraction(draw);
        map.removeInteraction(selectInteraction);
        map.removeInteraction(modify);

        draw = new ol.interaction.Draw({
            features: features,
            type: drawType
        });
        map.addInteraction(draw);

        /**
         * Calls modal form for save shape
         */
        draw.on('drawend', function(event) {
            var feature = event.feature;
            createModalBox(feature, function(state) {
                if (state === false) {
                    featureOverlay.getSource().removeFeature(feature);
                }
            });
        });
    }

    /**
     * Modifies shape
     */
    function handleModifyShape() {
        map.removeInteraction(draw);
        map.removeInteraction(selectInteraction);
        map.removeInteraction(modify);
        $('#for-shape-form').hide();
        selectInteraction = new ol.interaction.Select();
        modify = new ol.interaction.Modify({
            features: selectInteraction.getFeatures()
        });
        map.addInteraction(selectInteraction);
        map.addInteraction(modify);

        /**
         * Selects shape and calls edit form
         */
        selectInteraction.on('select', function(event) {
            if (event.selected.length != 0) {
                $('#btn-delete').prop('disabled', false);
                selectedShape = event.selected[0];
                $('#name-shape').val(selectedShape.get('name'));
                $('#desc-shape').val(selectedShape.get('description'));
                $('#for-shape-form').show();
            }
            else {
                $('#for-shape-form').hide();
            }
        });
    }

    /**
     * Deletes shape
     */
    function handleDeleteShape() {
        featureOverlay.getSource().removeFeature(selectInteraction.getFeatures().item(0));
        map.removeInteraction(selectInteraction);
        $('#for-shape-form').hide();
        $('#btn-delete').prop('disabled', true);
    }

};



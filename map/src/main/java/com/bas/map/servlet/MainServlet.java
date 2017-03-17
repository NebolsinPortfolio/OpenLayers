package com.bas.map.servlet;


import com.bas.map.service.FeatureService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.geojson.geometry.Geometry;
import org.geojson.object.FeatureCollection;
import org.geojson.util.GeometryMixin;

/**
 * Servlet from buttons "Загрузить", "Сохранить"
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(MainServlet.class);
    private static FeatureService featureService;
    private static FeatureCollection featureCollection;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        featureService = new FeatureService();
        featureCollection = new FeatureCollection(featureService.getAllFeaturesFromDB());
        ObjectMapper serializer = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = serializer.writeValueAsString(featureCollection);
            response.getWriter().write(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        StringBuilder json = new StringBuilder();
        String requestString;
        featureCollection = new FeatureCollection();
        try {
            while ((requestString = request.getReader().readLine()) != null) {
                json.append(requestString);
            }
            ObjectMapper deserializer = new ObjectMapper();
            deserializer.addMixInAnnotations( Geometry.class, GeometryMixin.class);
            featureCollection = deserializer.readValue(json.toString(), FeatureCollection.class);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        featureService = new FeatureService();
        featureService.createAllFeaturesToDB(featureCollection.getFeatures());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write("SUCCESS");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

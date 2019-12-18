/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myprojects.moviemanager.shared;

import com.myprojects.moviemanager.login.DB_CONNECTION;
import com.myprojects.moviemanager.main.Movie;
import com.myprojects.moviemanager.main.MovieQuery;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author "Daniel Mic"
 */
public class XMLManipulation {
    private int USER_ID;
    
    public XMLManipulation(int id){
        this.USER_ID = id;
    }
    
    public boolean exportXML() {
        
        boolean isCreated = true;
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        MovieQuery movieQuery = new MovieQuery(USER_ID);
        ArrayList<Movie> movieList = movieQuery.getMovieList();
        
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element mainRootElement = document.createElement("Movies");
            document.appendChild(mainRootElement);
            
            //appending childs too mainRootElement
            for(int i = 0; i < movieList.size(); i++){
                mainRootElement.appendChild(getMovies(document,String.valueOf(movieList.get(i).getId()),movieList.get(i).getName(),
                        movieList.get(i).getCategory(), movieList.get(i).getReleasedate(), String.valueOf(movieList.get(i).getRating()),
                        String.valueOf(movieList.get(i).getImdbscore()), movieList.get(i).getImagePth(),String.valueOf(USER_ID)));
            }
            
            //output the DOM XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("MovieSecureCopy.xml");
            transformer.transform(source, result);
            
            JOptionPane.showMessageDialog(null,"XML DOM Created Successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
        return isCreated;
    }
    
    public boolean importXML(){
        
        boolean isImported = true;
        
        Connection connection = DB_CONNECTION.getConnection();
        PreparedStatement stmt;
        String fileName = "MovieSecureCopy.xml";
        File file = new File(fileName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document xmlDoc = null;
        Object res = null;
        
        try {
            connection.createStatement().execute("CREATE TABLE moviesBackup(\n" +
         " id integer primary key,\n" +
         " name varchar(100) not null,\n" +
         " category varchar(55) not null,\n" +
         " releasedate varchar(55) not null,\n" +
         " rating float not null,\n" +
         " imdbscore float not null,\n" +
         " image_path text not null\n" +
         " userid text not null\n" +
         ")");
        } catch (SQLException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            
            builder = factory.newDocumentBuilder();
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            xmlDoc = builder.parse(file);
            
        } catch (SAXException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            
            res = xpath.evaluate("/Movies/Movies", xmlDoc, XPathConstants.NODESET);
            
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            stmt = connection.prepareStatement("INSERT INTO moviesBackup(\n" +
                    " id, name, category, releasedate, rating, imdbscore, image_path, userid \n" +         
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException ex) {
            Logger.getLogger(XMLManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
//        for (int i = 0 ; i < nlist.getLength() ; i++) {
//            Node node = nlist.item(i);
//            List<String> columns = Arrays.asList(getAttrValue(node, "id"),
//                getTextContent(node, "author"),
//                getTextContent(node, "title"),
//                getTextContent(node, "genre"),
//                getTextContent(node, "price"),
//                getTextContent(node, "publish_date"),
//                getTextContent(node, "description"));
//            for (int n = 0 ; n < columns.size() ; n++) {
//            stmt.setString(n+1, columns.get(n));
//            }
//            stmt.execute();
//        }
//        
        return isImported; 
    }
    
    
    static private String getAttrValue(Node node,String attrName) {
        if ( ! node.hasAttributes() ) return "";
        NamedNodeMap nmap = node.getAttributes();
        if ( nmap == null ) return "";
        Node n = nmap.getNamedItem(attrName);
        if ( n == null ) return "";
        return n.getNodeValue();
    }

    static private String getTextContent(Node parentNode,String childName) {
        NodeList nlist = parentNode.getChildNodes();
        for (int i = 0 ; i < nlist.getLength() ; i++) {
        Node n = nlist.item(i);
        String name = n.getNodeName();
        if ( name != null && name.equals(childName) )
            return n.getTextContent();
        }
        return "";
    }
    
    private static Node getMovies(Document document, String id, String name, String category, String releaseDate,
            String rating, String imdbRating, String image_path, String user_id) {
        
        Element movies = document.createElement("Movies");
        movies.setAttribute("id", id);
        movies.appendChild(getMovieElements(document,movies,"Name",name));
        movies.appendChild(getMovieElements(document,movies,"Category",category));
        movies.appendChild(getMovieElements(document,movies,"ReleaseDate",releaseDate));
        movies.appendChild(getMovieElements(document,movies,"rating",rating));
        movies.appendChild(getMovieElements(document,movies,"IMDbRating",imdbRating));
        movies.appendChild(getMovieElements(document,movies,"Image_path",image_path));
        movies.appendChild(getMovieElements(document,movies,"User_id",user_id));
        
        return movies;
    }
    
     // utility method to create text node
     private static Node getMovieElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}

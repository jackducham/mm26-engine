package mech.mania.engine.domain.game.board;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReadBoardFromXMLFile {

    Map<Integer, Boolean> isWalkable = new HashMap();
    Board board = null;

    //Handler Classes
    public class TileSetHandler extends DefaultHandler {

        int currentID = -1;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equalsIgnoreCase("tile")) {
                currentID = Integer.parseInt(attributes.getValue("id"));
            } else if(qName.equalsIgnoreCase("property")) {
                if(currentID >= 0) {
                    isWalkable.put(currentID, Boolean.parseBoolean(attributes.getValue("value")));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (qName.equalsIgnoreCase("tile")) {
                currentID = -1;
            }
        }
    }

    public class MapHandler extends DefaultHandler {

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase("map")) {
                board = new Board(Integer.parseInt(attributes.getValue("width")), Integer.parseInt(attributes.getValue("height")));
            } else if (qName.equalsIgnoreCase("")) {

            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

        }
    }

    public void updateTileSet(String tileSetFileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            TileSetHandler tileSetHandler = new TileSetHandler();
            saxParser.parse(new File(tileSetFileName), tileSetHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBoard(String mapFileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapHandler mapHandler = new MapHandler();
            saxParser.parse(new File(mapFileName), mapHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Board extractBoard() {
        return board;
    }
}

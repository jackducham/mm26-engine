package mech.mania.engine.domain.game.board;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.game.items.Weapon;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadBoardFromXMLFile {

    private Map<Integer, Tile> tileSet = new HashMap();
    private Map<Integer, PseudoMonster> monsterSet = new HashMap();
    private Map<Integer, DataLayer> dataSet = new HashMap();
    private Board board = null;
    private List<Monster> monsterList = new ArrayList<>();

    //internal class used to represent each type of monster loaded from an XML file
    private static class PseudoMonster {
        protected int attack;
        protected int defense;
        protected int maxHealth;
        protected int level;
        protected String name;
        protected int speed;

        @Override
        public String toString() {
            String toReturn = "PseudoMonster: \n";
            toReturn += "   Name: " + name + "\n ";
            toReturn += "   HP: " + maxHealth + "\n";
            toReturn += "   Attack: " + attack + "\n";
            toReturn += "   Defense: " + defense + "\n";
            toReturn += "   Level: " + level + "\n";
            toReturn += "   Speed: " + speed + "\n";
            return toReturn;
        }
    }

    //internal class used to represent each layer of data loaded form an XML file
    private static class DataLayer {
        protected int width;
        protected int height;
        protected int[][] data;
    }



    //========Handler Classes========
    public class TileSetHandler extends DefaultHandler {

        int currentID = -1;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equalsIgnoreCase("tile")) {
                currentID = Integer.parseInt(attributes.getValue("id"));
            } else if(qName.equalsIgnoreCase("property")) {
                if(currentID >= 0) {
                    //The current "tile" is a tile
                    if(attributes.getValue("name").equalsIgnoreCase("Walkable")) {
                        Tile newTile = new Tile();
                        if(attributes.getValue("value").equalsIgnoreCase("true")) {
                            newTile.setType(Tile.TileType.BLANK);
                        } else {
                            newTile.setType(Tile.TileType.IMPASSIBLE);
                        }
                        tileSet.put(currentID, newTile);
                    } else {
                        //The current "tile" is a monster

                        //setup a new monster if we haven't started work on this one yet.
                        if(!monsterSet.containsKey(currentID)) {
                            monsterSet.put(currentID, new PseudoMonster());
                        }

                        //set the stat we just opened
                        if(attributes.getValue("name").equalsIgnoreCase("Attack")) {
                            monsterSet.get(currentID).attack = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("Defense")) {
                            monsterSet.get(currentID).defense = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("HP")) {
                            monsterSet.get(currentID).maxHealth = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("Level")) {
                            monsterSet.get(currentID).level = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("Name")) {
                            monsterSet.get(currentID).name = attributes.getValue("value");
                        } else if (attributes.getValue("name").equalsIgnoreCase("Speed")) {
                            monsterSet.get(currentID).speed = Integer.parseInt(attributes.getValue("value"));
                        }
                    }
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

        int currentLayerIndex = -1;
        boolean bdata = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase("map")) {
                board = new Board(Integer.parseInt(attributes.getValue("width")), Integer.parseInt(attributes.getValue("height")));
            } else if (qName.equalsIgnoreCase("layer")) {
                currentLayerIndex = Integer.parseInt(attributes.getValue("name"));
                dataSet.put(currentLayerIndex, new DataLayer());
                dataSet.get(currentLayerIndex).height = Integer.parseInt(attributes.getValue("height"));
                dataSet.get(currentLayerIndex).width = Integer.parseInt(attributes.getValue("width"));
            } else if (qName.equalsIgnoreCase("data")) {
                bdata = true;
            }
        }

        @Override
        public void characters(char ch[], int start, int length) {
            if (bdata && currentLayerIndex >= 0) {
                DataLayer currentLayer = dataSet.get(currentLayerIndex);
                String rawData = new String(ch, start, length); //this is the full set of data in a single string
                String[] rowData = rawData.split("\n", 0); //splits the data string up into each individual row

                for(int x = 0; x < currentLayer.width; ++x) {//splits each of the rows into individual data points
                    String[] currentRowData = rowData[x].split(",", 0);
                    for(int y = 0; y < currentLayer.height; ++y) {//puts each of those data points into the layer
                        currentLayer.data[x][y] = Integer.parseInt(currentRowData[y]);
                    }
                }
                bdata = false;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equalsIgnoreCase("layer")) {
                currentLayerIndex = -1;
            }
        }
    }


    //========helper functions========
    public void loadTileData(String tileSetFileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            TileSetHandler tileSetHandler = new TileSetHandler();
            saxParser.parse(new File(tileSetFileName), tileSetHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBoardData(String mapDataFileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapHandler mapHandler = new MapHandler();
            saxParser.parse(new File(mapDataFileName), mapHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBoardAndMonsters(String tileSetFileName, String mapDataFileName, String boardName) {
        loadTileData(tileSetFileName);
        loadBoardData(mapDataFileName);
        for(int x = 0; x < dataSet.get(0).width; ++x) {
            for(int y = 0; y < dataSet.get(0).height; ++y) {
                //set BLANK or IMPASSIBLE
                if(tileSet.get(dataSet.get(0).data[x][y]).getType() == Tile.TileType.BLANK && tileSet.get(dataSet.get(1).data[x][y]).getType() == Tile.TileType.BLANK) {
                    board.getGrid()[x][y].setType(Tile.TileType.BLANK);
                } else {
                    board.getGrid()[x][y].setType(Tile.TileType.IMPASSIBLE);
                }

                //Add monsters to the list of monsters.
                //TODO: this currently passes the monster's level as its xp. I think the monster class needs to have level instead of xp.
                int monsterIndex = dataSet.get(3).data[x][y];
                if(monsterIndex != 0 && monsterSet.get(monsterIndex) != null) {
                    PseudoMonster toCopy = monsterSet.get(monsterIndex);
                    Monster newMonster = new Monster(toCopy.name, toCopy.speed, toCopy.maxHealth, toCopy.attack, toCopy.defense,
                    toCopy.level, new Position(x, y, boardName), null, new ArrayList<Item>());
                    monsterList.add(newMonster);
                }

            }
        }
    }

    public Board extractBoard() {
        return board;
    }

    public List<Monster> extractMonsters() {
        return monsterList;
    }
}

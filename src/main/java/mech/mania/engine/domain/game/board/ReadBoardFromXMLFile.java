package mech.mania.engine.domain.game.board;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.game.items.StatusModifier;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.*;

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

        DataLayer(int width, int height) {
            this.width = width;
            this.height = height;
            data = new int[width][height];
        }

        @Override
        public String toString() {
            String output = "";
            for(int y = 0; y < height; ++y) {
                output += data[0][y];
                for(int x = 1; x < width; ++x) {
                    output += (", " + data[x][y]);
                }
                output += "\n";
            }

            return output;
        }
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
        StringBuffer dataContentBuffer;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase("map")) {
                board = new Board(Integer.parseInt(attributes.getValue("width")), Integer.parseInt(attributes.getValue("height")));
            } else if (qName.equalsIgnoreCase("layer")) {
                currentLayerIndex = Integer.parseInt(attributes.getValue("name"));
                dataSet.put(currentLayerIndex, new DataLayer( Integer.parseInt(attributes.getValue("width")), Integer.parseInt(attributes.getValue("height")) ));
            } else if (qName.equalsIgnoreCase("data")) {
                bdata = true;
                dataContentBuffer = new StringBuffer();
            }
        }

        @Override
        public void characters(char ch[], int start, int length) {

            if (dataContentBuffer != null) {
                dataContentBuffer.append(new String(ch, start, length));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equalsIgnoreCase("layer")) {
                currentLayerIndex = -1;
            } else if (bdata && currentLayerIndex >= 0) {
                DataLayer currentLayer = dataSet.get(currentLayerIndex);
                String rawData = dataContentBuffer.toString().trim(); //this is the full set of data in a single string
                //System.out.println("rawData: \n" + rawData);
                String[] rowData = rawData.split("\n", 0); //splits the data string up into each individual row
                //System.out.println("rowData[0]: \n" + rowData[0]);
                //System.out.println("rowData[1]: \n" + rowData[1]);
                //System.out.println("rowData[29]: \n" + rowData[29]);

                for(int ri = 0; ri < currentLayer.height; ++ri) {//splits each of the rows into individual data points
                    String[] currentRowData = rowData[ri].split(",", 0);
                    for(int ewr = 0; ewr < currentLayer.width; ++ewr) {//puts each of those data points into the layer
                        currentLayer.data[ewr][ri] = Integer.parseInt(currentRowData[ewr]);
                        //System.out.println("Set (" + y + ", " + x + ") to " + Integer.parseInt(currentRowData[y]));
                    }
                }
                bdata = false;
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

    public void updateBoardAndMonsters(String tileSetFileName, String mapDataFileName, String boardName) throws TileIDNotFoundException {
        loadTileData(tileSetFileName);

        /*
        for(int key: tileSet.keySet()) {
            System.out.println(key);
        }
        */

        loadBoardData(mapDataFileName);
        //System.out.println(dataSet.get(2).toString());
        Map<Integer, Integer> monstersQuantityOfEachID = new HashMap<>();
        for(int y = 0; y < dataSet.get(0).height; ++y) {
            for(int x = 0; x < dataSet.get(0).width; ++x) {
                //set BLANK or IMPASSIBLE
                if(dataSet.get(0).data[x][y] != 0 && tileSet.get(dataSet.get(0).data[x][y]) == null) {
                    throw new TileIDNotFoundException(
                            "Could not locate tile with ID = " + dataSet.get(0).data[x][y] + " in current dataSet. This tile was requested by Data Layer 0 at Position (" + x + ", " + y + ")");
                    //board.getGrid()[x][y].setType(Tile.TileType.IMPASSIBLE);
                } else if (dataSet.get(1).data[x][y] != 0 && tileSet.get(dataSet.get(1).data[x][y]) == null) {
                    throw new TileIDNotFoundException(
                            "Could not locate tile with ID = " + dataSet.get(1).data[x][y] + " in current dataSet. This tile was requested by Data Layer 1 at Position (" + x + ", " + y + ")");
                    //board.getGrid()[x][y].setType(Tile.TileType.IMPASSIBLE);
                } else if ((dataSet.get(0).data[x][y] == 0 || tileSet.get(dataSet.get(0).data[x][y]).getType() == Tile.TileType.BLANK)
                        && (dataSet.get(1).data[x][y] == 0 || tileSet.get(dataSet.get(1).data[x][y]).getType() == Tile.TileType.BLANK)) {
                    board.getGrid()[x][y].setType(Tile.TileType.BLANK);
                } else {
                    board.getGrid()[x][y].setType(Tile.TileType.IMPASSIBLE);
                }

                //Add monsters to the list of monsters.
                //TODO: figure out exactly how monster data gets put into monster object. particularly the damage stat vs weapon damage.
                int monsterIndex = dataSet.get(2).data[x][y];
                if(monsterIndex != 0) {
                    if(monsterSet.get(monsterIndex) == null) {
                        throw new TileIDNotFoundException("Could not locate monster with ID = " + dataSet.get(2).data[x][y] + " in current dataSet. This tile was requested by Data Layer 2 at Position (" + x + ", " + y + ")");
                    } else {

                        monstersQuantityOfEachID.merge(monsterIndex, 1, Integer::sum);

                        PseudoMonster toCopy = monsterSet.get(monsterIndex);
                        StatusModifier zeroStats = new StatusModifier(0, 0, 0,
                                0, 0, 0, 0,
                                0, 0, 0, 0);
                        TempStatusModifier zeroOnHit = new TempStatusModifier(0, 0,
                                0, 0, 0, 0,
                                0, 0, 0, 0,
                                0, 0, 0);
                        Monster newMonster = new Monster(toCopy.name + (monstersQuantityOfEachID.get(monsterIndex) - 1), toCopy.speed, toCopy.maxHealth, toCopy.attack, toCopy.defense,
                                toCopy.level, new Position(x, y, boardName), new Weapon(zeroStats, 1, 0, 0, zeroOnHit), new ArrayList<Item>());
                        monsterList.add(newMonster);
                    }
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

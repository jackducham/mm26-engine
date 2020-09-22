package mech.mania.engine.domain.game.board;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.StatusModifier;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.*;

public class ReadBoardFromXMLFile {

    private Map<Integer, Tile> tileSet = new HashMap<>();
    private Map<Integer, PseudoMonster> monsterSet = new HashMap<>();
    private Map<Integer, DataLayer> dataSet = new HashMap<>();
    private Board board = null;
    private List<Monster> monsterList = new ArrayList<>();
    private String boardName;

    //internal class used to represent each type of monster loaded from an XML file
    private static class PseudoMonster {
        protected String sprite;

        //base monster stats
        protected int attack;
        protected int defense;
        protected int maxHealth;
        protected int level;
        protected String name;
        protected int speed;

        //weapon stats
        protected int weaponRange;
        protected int weaponSplashRadius;
        protected int weaponDamage;

        //on hit effect stats
        protected int flatSpeedChange;
        protected double percentSpeedChange;
        protected int flatHealthChange;
        protected double percentHealthChange;
        protected int flatExperienceChange;
        protected double percentExperienceChange;
        protected int flatAttackChange;
        protected double percentAttackChange;
        protected int flatDefenseChange;
        protected double percentDefenseChange;

        protected int regenPerTurn;
        protected int duration;
        protected int damagePerTurn;

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
                // Add 1 to translate from local to global IDs
                currentID = Integer.parseInt(attributes.getValue("id")) + 1;
            } else if(qName.equalsIgnoreCase("property")) {
                if(currentID >= 0) {
                    // The current "tile" is a tile
                    if(attributes.getValue("name").equalsIgnoreCase("Walkable")) {
                        Tile newTile = new Tile();
                        if(attributes.getValue("value").equalsIgnoreCase("true")) {
                            newTile.setType(Tile.TileType.BLANK);
                        } else {
                            newTile.setType(Tile.TileType.IMPASSIBLE);
                        }
                        tileSet.put(currentID, newTile);
                    }
                    else if(attributes.getValue("name").equalsIgnoreCase("isPortal")){
                        if(attributes.getValue("value").equalsIgnoreCase("true")) {
                            // This tile is a portal
                            tileSet.get(currentID).setType(Tile.TileType.PORTAL);
                        }
                    }
                    else {
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
                        } else if (attributes.getValue("name").equalsIgnoreCase("weapon_attack")) {
                            monsterSet.get(currentID).weaponDamage = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("range")) {
                            monsterSet.get(currentID).weaponRange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("splash_radius")) {
                            monsterSet.get(currentID).weaponSplashRadius = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_attack_change")) {
                            monsterSet.get(currentID).flatAttackChange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("percent_attack_change")) {
                            monsterSet.get(currentID).percentAttackChange = Double.parseDouble(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_defense_change")) {
                            monsterSet.get(currentID).flatDefenseChange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("percent_defense_change")) {
                            monsterSet.get(currentID).percentDefenseChange = Double.parseDouble(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_experience_change")) {
                            monsterSet.get(currentID).flatExperienceChange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("percent_experience_change")) {
                            monsterSet.get(currentID).percentExperienceChange = Double.parseDouble(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_health_change")) {
                            monsterSet.get(currentID).flatHealthChange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("percent_health_change")) {
                            monsterSet.get(currentID).percentHealthChange = Double.parseDouble(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_speed_change")) {
                            monsterSet.get(currentID).flatSpeedChange = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("percent_speed_change")) {
                            monsterSet.get(currentID).percentSpeedChange = Double.parseDouble(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("flat_regen_per_turn")) {
                            monsterSet.get(currentID).regenPerTurn = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("damage_per_turn")) {
                            monsterSet.get(currentID).damagePerTurn = Integer.parseInt(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("duration")) {
                            monsterSet.get(currentID).duration = Integer.parseInt(attributes.getValue("value"));
                        }
                    }
                }
            }
            else if (qName.equalsIgnoreCase("image")){
                // Check that we're in a tile
                if(currentID >= 0){
                    // Check if this is a monster or tile
                    if(monsterSet.containsKey(currentID)){
                        // We're a monster
                        monsterSet.get(currentID).sprite = attributes.getValue("source");
                    }
                    else if(tileSet.containsKey(currentID)) {
                        // We're a tile
                        // Since we don't know what layer we're on, set both sprites to the same source
                        tileSet.get(currentID).groundSprite = attributes.getValue("source");
                        tileSet.get(currentID).aboveSprite = attributes.getValue("source");
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
        public void characters(char[] ch, int start, int length) {

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
        this.boardName = boardName;

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
                } else {
                    // Set tile types
                    if (dataSet.get(0).data[x][y] != 0){
                        board.getGrid()[x][y].setType(tileSet.get(dataSet.get(0).data[x][y]).getType());
                    }
                    if (dataSet.get(1).data[x][y] != 0){
                        Tile.TileType type = tileSet.get(dataSet.get(1).data[x][y]).getType();
                        board.getGrid()[x][y].setType(type);

                        // Add portals to portal list
                        if(type == Tile.TileType.PORTAL){
                            board.addPortal(new Position(x, y, boardName));
                        }
                    }
                }

                // Add sprites for tile
                if(dataSet.get(0).data[x][y] != 0) {
                    board.getGrid()[x][y].groundSprite = tileSet.get(dataSet.get(0).data[x][y]).groundSprite;
                }
                if(dataSet.get(1).data[x][y] != 0) {
                    board.getGrid()[x][y].aboveSprite = tileSet.get(dataSet.get(1).data[x][y]).aboveSprite;
                }

                //Add monsters to the list of monsters.
                int monsterIndex = dataSet.get(2).data[x][y];
                if(monsterIndex != 0) {
                    if(monsterSet.get(monsterIndex) == null) {
                        throw new TileIDNotFoundException("Could not locate monster with ID = " + dataSet.get(2).data[x][y] + " in current dataSet. This tile was requested by Data Layer 2 at Position (" + x + ", " + y + ")");
                    } else {

                        monstersQuantityOfEachID.merge(monsterIndex, 1, Integer::sum);

                        PseudoMonster toCopy = monsterSet.get(monsterIndex);
                        TempStatusModifier onHit = new TempStatusModifier(toCopy.flatSpeedChange, toCopy.percentSpeedChange,
                                toCopy.flatHealthChange, toCopy.percentHealthChange, toCopy.flatExperienceChange,
                                toCopy.percentExperienceChange, toCopy.flatAttackChange, toCopy.percentAttackChange,
                                toCopy.flatDefenseChange, toCopy.percentDefenseChange, toCopy.regenPerTurn, toCopy.duration,
                                toCopy.damagePerTurn);
                        StatusModifier zeroStats = new StatusModifier(0, 0,
                                0, 0, 0, 0,
                                0, 0, 0, 0,
                                0);

                        // Monster weapons don't need a sprite because they are only used internally for range/splash radius
                        Monster newMonster = new Monster(toCopy.name + (monstersQuantityOfEachID.get(monsterIndex) - 1),
                                toCopy.sprite, toCopy.speed, toCopy.maxHealth, toCopy.attack, toCopy.defense, toCopy.level,
                                new Position(x, y, boardName),
                                new Weapon(zeroStats, toCopy.weaponRange, toCopy.weaponSplashRadius, toCopy.weaponDamage, onHit, ""),
                                0, new ArrayList<>());
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

    public String getBoardName(){ return boardName; }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}

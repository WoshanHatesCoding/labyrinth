package labirithm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameGUI extends JPanel{
    GameEngine game= new GameEngine(this);
    HashMap<GameEngine.CELLS, BufferedImage> images = new HashMap<>();

    public GameGUI(){
        try {
            images.put(GameEngine.CELLS.PLAYER,ImageIO.read(new File("./data/img/player.png")));
            images.put(GameEngine.CELLS.DRAGON,ImageIO.read(new File("./data/img/dragon.png")));
            images.put(GameEngine.CELLS.EXIT,ImageIO.read(new File("./data/img/exit.png")));
            images.put(GameEngine.CELLS.WALL,ImageIO.read(new File("./data/img/wall.png")));
            images.put(GameEngine.CELLS.EMPTY,ImageIO.read(new File("./data/img/black.jpg")));

        }
        catch (IOException e){};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var size = Math.min(getSize().width, getSize().height) / game.getDimensions();
        ArrayList<ArrayList<GameEngine.CELLS>> map = game.getMap();

        for (int i = 0; i < map.size(); i++) {
            var row = map.get(i);
            for (int j = 0; j < row.size(); j++) {
                int x = j * size;
                int y = i * size;

                g.drawRect(x, y, size, size);
                g.drawImage(images.get(GameEngine.CELLS.EMPTY), x, y, size, size, this);
                var p = game.getPlayer();
                if(Math.abs(i-p[0]) <=3  && Math.abs(j-p[1]) <=3){
                    g.drawImage(images.get(row.get(j)), x, y, size, size, this);
                }

            }
        }
    }

}

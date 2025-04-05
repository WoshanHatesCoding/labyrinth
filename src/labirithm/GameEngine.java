package labirithm;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

public class GameEngine {
        public enum CELLS{WALL,EXIT,DRAGON,PLAYER,EMPTY};
        private ArrayList<ArrayList<CELLS>> map = new ArrayList<>();
        private int[] player;
        private int[] dragon;
        private  int[] dragon_direction;
        int dimensions = 0;
        int levels_passed = 0;
        long elapsedTime =0;
        long startTIme=System.currentTimeMillis();
        Timer timer = new Timer(100,new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                elapsedTime = System.currentTimeMillis() - startTIme;
            }
            }
            );

        GameGUI panel;
        private Random rand = new Random();
        public GameEngine(GameGUI panel) {
            timer.start();
            this.panel = panel;
            panel.setFocusable(true);
            panel.requestFocusInWindow();
            InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = panel.getActionMap();

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W_PRESSED");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A_PRESSED");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S_PRESSED");
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D_PRESSED");

            actionMap.put("W_PRESSED", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    step(new int[]{-1, 0});
                }
            });

            actionMap.put("A_PRESSED", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    step(new int[]{0, -1});
                }
            });

            actionMap.put("S_PRESSED", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    step(new int[]{1, 0});
                }
            });

            actionMap.put("D_PRESSED", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    step(new int[]{0, 1});
                }
            });
            restart();
        }
        private void endOfGame(){
            timer.stop();
            String name = JOptionPane.showInputDialog("Game over. You won "+levels_passed+" levels and were playing " + elapsedTime+"ms");
            DB.newResult(name,levels_passed,elapsedTime);
            Labirithm.toMainMenu();
        }
    private void restart(){
                map = new ArrayList<>();


                int level = rand.nextInt(10);
                String filePath = "./data/levels/" + level + ".txt";
                int y = 0;
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        y++;
                        int x = 0;
                        ArrayList<CELLS> row = new ArrayList<>();
                        System.out.println("Line: " + line);
                        for (char c : line.toCharArray()) {
                            x++;
                            switch (c) {
                                case '#':
                                    row.add(CELLS.WALL);
                                    break;
                                case '0':
                                    row.add(CELLS.EMPTY);
                                    break;
                                case 'E':
                                    row.add(CELLS.EXIT);
                                    break;
                                case 'P':
                                    row.add(CELLS.PLAYER);
                                    player = new int[]{y-1, x-1};
                                    break;
                                case 'D':
                                    row.add(CELLS.DRAGON);
                                    dragon = new int[]{y-1, x-1};
                                    break;
                            }
                        }
                        dimensions = Math.max(Math.max(y, x), dimensions);
                        map.add(row);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true){
                    y = rand.nextInt(map.size());
                    int x = rand.nextInt(map.get(y).size());
                    var new_postion = new int[]{y, x};
                    if (map.get(y).get(x) == CELLS.EMPTY &&
                            !((Math.abs(player[0] - new_postion[0]) <= 2 && Math.abs(player[1] - new_postion[1]) <=2)
                                    )
                    ){
                        dragon = new_postion;
                        map.get(y).set(x,CELLS.DRAGON);
                        break;
                    }
                }

                getNewDragonDirection();
        panel.repaint();
        panel.validate();

    }
    private void getNewDragonDirection(){
            ArrayList<int[]> possible_positions = new ArrayList<>();
        for (var direction : new int[][]{{0,1},{0,-1},{1,0},{-1,0}}) {
            CELLS item = CELLS.DRAGON;
            var new_postion = new int[]{this.dragon[0] + direction[0],this.dragon[1] + direction[1]};
            try {
                item = map.get(new_postion[0]).get(new_postion[1]);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            if (item == CELLS.EMPTY) {
                possible_positions.add(direction);
            }
        }
        dragon_direction = possible_positions.get(rand.nextInt(possible_positions.size()));
    }
    private void step(int[] direction){

        var new_postion = new int[]{this.player[0] + direction[0],this.player[1] + direction[1]};
        CELLS item;
        try {
            item = map.get(new_postion[0]).get(new_postion[1]);
        }
        catch (IndexOutOfBoundsException e){
            return;
        }

        if (item == CELLS.WALL){
            return;
        }
        if( item== CELLS.EXIT){
            levels_passed++;
            restart();
            return;
        }
        if ((Math.abs(dragon[0] - new_postion[0]) <= 1 && Math.abs(dragon[1] - new_postion[1]) ==0)
            || (Math.abs(dragon[0] - new_postion[0]) ==0 && Math.abs(dragon[1] - new_postion[1]) <=1)
        ){
            endOfGame();
            return;
        }
        map.get(player[0]).set(player[1],CELLS.EMPTY);
        player = new_postion.clone();

        map.get(player[0]).set(player[1],CELLS.PLAYER);
        map.get(dragon[0]).set(dragon[1],CELLS.EMPTY);
        new_postion = new int[]{this.dragon[0] + dragon_direction[0],this.dragon[1] + dragon_direction[1]};
        try {
            item = map.get(new_postion[0]).get(new_postion[1]);
            if (item != CELLS.EMPTY){
                getNewDragonDirection();
                new_postion = new int[]{this.dragon[0] + dragon_direction[0],this.dragon[1] + dragon_direction[1]};
            }
        }
        catch (IndexOutOfBoundsException e){
            getNewDragonDirection();
            new_postion = new int[]{this.dragon[0] + dragon_direction[0],this.dragon[1] + dragon_direction[1]};
        }

        dragon = new_postion;
        map.get(dragon[0]).set(dragon[1],CELLS.DRAGON);

        if ((Math.abs(dragon[0] - player[0]) <= 1 && Math.abs(dragon[1] - player[1]) ==0)
                || (Math.abs(dragon[0] - player[0]) ==0 && Math.abs(dragon[1] - player[1]) <=1)
        ){
            endOfGame();
            return;

        }

        panel.repaint();
        panel.validate();

    }

    public int[] getPlayer(){
        return player;
    }
    
    public int getDimensions() {
        return dimensions;
    }

    public ArrayList<ArrayList<CELLS>> getMap(){
            return map;
    }
}

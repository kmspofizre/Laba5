package svink;

import components.User;
import utils.TCPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Visualisation extends JFrame implements MouseListener {
    Canvas canvas;
    TCPClient tcpClient;
    User user;
    Container content;
    JPanel canvasPanel;
    int w, h;
    String[][] data;
    HashMap<Long, int[]> trigx;
    HashMap<Long, int[]> trigy;
    public Visualisation(int w, int h, TCPClient providedTCPClient, User providedUser, String[][] currentData){
        super("Визуализация");
        this.trigx = new HashMap<>();
        this.trigy = new HashMap<>();
        data = currentData.clone();
        tcpClient = providedTCPClient;
        user = providedUser;
        setSize(600, 600); // устанавливаем размер главного окна
        this.setBackground(Color.orange);
        this.addMouseListener(this);
        addMouseListener(this);

        setVisible(true);

    }
    @Override
    public void paint(Graphics g){
        for (String[] elem : data){
            String[] coords = elem[2].split(", ");
            int x = Math.round(Float.parseFloat(coords[0].toString()));
            int y = Integer.parseInt(coords[1].toString());
            int[] xs = {y + x, y + x + 50, y + x + 100};
            int[] ys = {y + x, y + x + 100, y + x};
            trigx.put(Long.parseLong(elem[0]), xs);
            trigy.put(Long.parseLong(elem[0]), ys);
            if (Boolean.parseBoolean(elem[6])){
                g.setColor(Color.CYAN);
            }
            else {
                g.setColor(Color.magenta);
            }
            g.drawPolygon(xs, ys, 3);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int eventX = e.getX();
        int eventY = e.getY();
        for (Map.Entry<Long, int[]> elem : this.trigx.entrySet()){
            if ((Arrays.stream(elem.getValue()).min().getAsInt() < eventX) & (eventX < Arrays.stream(elem.getValue()).max().getAsInt())){
                int[] yArr = this.trigy.get(elem.getKey());
                if ((Arrays.stream(yArr).min().getAsInt() < eventY) & (eventY < Arrays.stream(yArr).max().getAsInt())){
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

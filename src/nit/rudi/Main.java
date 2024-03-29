package nit.rudi;
import javax.swing.*;

import nit.rudi.controller.SrankController;
import nit.rudi.controller.SrankCoreProcess;

import nit.rudi.views.Home;

import java.io.IOException;

public class Main {
    /**
     * @author : Amirhesam Rayatnia (rayatnia@stu.nit.ac.ir)
     * @deprecated : None
     * @param args: String list that indicate input from terminal
     *
     * */
    public static void main(String[] args) {
        ImageIcon img = new ImageIcon("~/Desktop/noshirvani.png");
        JFrame frame = new JFrame("Srank");
        frame.setIconImage(img.getImage());
        frame.setContentPane(new Home().homeView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // write your code here
    }
}

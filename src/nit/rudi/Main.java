package nit.rudi;
import javax.swing.JFrame;
import nit.rudi.views.Home;

public class Main {
    /**
     * @author : Amirhesam Rayatnia (rayatnia@stu.nit.ac.ir)
     * @deprecated : None
     * @param args: String list that indicate input from terminal
     *
     * */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Home().homeView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // write your code here
    }
}

package Frames;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class HelpFrame extends JFrame
{
    public HelpFrame(){
        setResizable(false);
        setSize(350,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("О Программе");
        setLocationRelativeTo(null);
        try{
            BufferedImage image = ImageIO.read(new File
                    ("D:\\kursach\\out\\artifacts" +
                            "\\kursach_jar\\static\\Comb_sort.gif"));
            ImageIcon icon = new ImageIcon
                    (image.getScaledInstance(200,200,
                            Image.SCALE_SMOOTH));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        add(new myComponent());
        setVisible(true);
    }

    /**
     *<strong>Child class myComponent, witch is inherited from the class JComponent</strong>
     *
     * <i>In this class implemented component for {@link HelpFrame}</i>
     */
    static class myComponent extends JComponent{

        protected void paintComponent(Graphics g){
            Font font = new Font("TimesRoman", Font.BOLD, 12);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setFont(font);
            graphics.drawString(" Программа позволяет:", 20, 240);
            graphics.drawString(" 1. Создавать " +
                    "массив случайных чисел", 20, 260);
            graphics.drawString(" 2. Выбрать " +
                    "порядок сортировки", 20, 280);
            graphics.drawString(" 3. Сортировать " +
                    "массив числовых данных", 20, 300);
            graphics.drawString(" 4. Записать в файл данные", 20, 320);
            graphics.drawString(" Версия программы: 1.0", 20,340);
        }
    }
}

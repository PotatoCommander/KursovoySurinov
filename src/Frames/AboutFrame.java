package Frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *<strong>Child class AboutFrame, which is inherited from the class JFrame</strong>
 *
 * <i>In this class implemented Frame about author and it's components</i>
 */
public class AboutFrame extends JFrame
{
    public AboutFrame(){
        setResizable(false);
        setSize(350,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Об авторе");
        setLocationRelativeTo(null);
        try{
            BufferedImage image = ImageIO.read(new File
                    ("D:\\kursach\\out\\artifacts" +
                            "\\kursach_jar\\static\\Author.jpg"));
            ImageIcon icon = new ImageIcon
                    (image.getScaledInstance
                            (200,200, Image.SCALE_SMOOTH));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        add(new myComponent());
        setVisible(true);
    }

    /**
     *<strong>Child class myComponent, which is inherited from the class JComponent</strong>
     *
     * <i>In this class implemented component for {@link AboutFrame}</i>
     */
    static class myComponent extends JComponent{
        protected void paintComponent(Graphics g){
            Font font = new Font("TimesRoman", Font.BOLD, 12);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setFont(font);
            graphics.drawString(" Автор:" +
                    " Суринов Артём", 20, 240);
            graphics.drawString(" E-Mail: " +
                    "surinov@outlook.com", 20, 260);
            graphics.drawString(" Об Авторе: " +
                    "Студент белорусского национального", 20, 280);
            graphics.drawString(" национального " +
                    "технического университета", 20, 300);
            graphics.drawString(" обучается на " +
                    "Факультете информационных", 20, 320);
            graphics.drawString(" технологий и робототехники", 20,340);

        }
    }
}

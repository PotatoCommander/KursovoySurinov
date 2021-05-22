package Frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public
class SplashFrame extends JFrame
{

    public SplashFrame(String headerText)
    {
        setResizable(false);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle(headerText);
        setLocationRelativeTo(null);

        JLabel iconLabel = new JLabel();
        JButton jbtStart = new JButton("Далее");
        JButton jbtExit = new JButton("Выход");

        add(iconLabel);
        add(jbtStart);
        add(jbtExit);

        jbtStart.setBounds(80, 500, 285, 30);
        jbtExit.setBounds(410, 500, 285, 30);
        iconLabel.setSize(800, 600);


        try
        {
            BufferedImage image = ImageIO.read(new File("Student.jpg"));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(180, 180, Image.SCALE_SMOOTH));
            iconLabel.setIcon(icon);
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        jbtStart.addActionListener(e -> {
            dispose();
            new MainFrame("Калькулятор расхода воды");
        });
        jbtExit.addActionListener(e -> System.exit(EXIT_ON_CLOSE));
        add(new myComponent());

        setVisible(true);
    }

    class myComponent extends JComponent
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            Font font1 = new Font("TimesRoman", Font.BOLD, 14);
            g.setFont(font1);
            g.drawString(" Белорусский национальный технический университет", 200, 30);
            g.drawString(" Выполнил: студент гр.10702318", 530, 300);
            g.drawString(" Суринов Артём Александрович", 530, 320);
            g.drawString(" Проверил: к.ф.-м.н.,доц.", 530, 370);
            g.drawString(" Сидорик Валерий Владимирович", 530, 390);

            Font font2 = new Font("TimesRoman", Font.BOLD, 12);
            g.setFont(font2);
            g.drawString(" Факультет информационных технологий и робототехники", 230, 60);
            g.drawString(" Кафедра программного обеспечения информационных систем и технологий", 172, 80);
            g.drawString(" Минск, 2021", 350, 480);

            Font font3 = new Font("TimesRoman", Font.BOLD, 25);
            g.setFont(font3);
            g.drawString(" Курсовая работа", 280, 140);
            g.drawString(" Калькулятор расхода воды", 145, 200);


            Font font4 = new Font("TimesRoman", Font.BOLD, 16);
            g.setFont(font4);
            g.drawString(" По дисциплине 'Программирование на Java' ", 210, 165);

        }
    }
}
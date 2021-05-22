package Model;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FloatKeyAdapter extends KeyAdapter
{
    @Override
    public void keyTyped(KeyEvent e)
    {
        char c = e.getKeyChar();
        var text = ((JTextField)e.getSource()).getText();
        var length = text.length();
        if(length == 0 && c == '0')
        {
            e.consume();
        }
        else
        {
            if (!Character.isDigit(c))
            {
                if (c != '.' || text.contains("."))
                {
                    e.consume();
                }
            }
        }
    }
}

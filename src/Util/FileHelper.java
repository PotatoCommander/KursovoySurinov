package Util;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHelper
{
    private JFileChooser fileChooser;
    private Component parent;
    public FileHelper(Component parent)
    {
        this.parent = parent;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter filter = new FileNameExtensionFilter("CSV","CSV");
        fileChooser.setFileFilter(filter);
    }
    public String openFileDialog()
    {
        fileChooser.setDialogTitle("Выберите файл для открытия");
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            var path = selectedFile.getAbsolutePath();
            if (!path.toLowerCase().endsWith( ".csv" )) path += ".csv";
            return path;
        }
        return null;
    }
    public String saveAsFileDialog()
    {
        fileChooser.setDialogTitle("Сохранить как...");
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            var path = fileToSave.getAbsolutePath();
            if (!path.toLowerCase().endsWith( ".csv" )) path += ".csv";
            return path;
        }
        return null;
    }
    public String newFileDialog()
    {
        fileChooser.setDialogTitle("Создать новый файл...");
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            var path = fileToSave.getAbsolutePath();
            if (!path.toLowerCase().endsWith( ".csv" )) path += ".csv";
            return path;
        }
        return null;
    }
}

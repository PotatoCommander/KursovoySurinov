package Frames;

import Model.CSVModel;
import Model.FloatKeyAdapter;
import Model.Month;
import Model.Payment;
import Util.FileHelper;
import Util.PaymentCSVRepository;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;


@SuppressWarnings("DuplicatedCode")
public class MainFrame extends JFrame
{
    private final JMenuBar menuBar;
    private final FileHelper fileHandler;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JTextField hotWaterCurrTextBox;
    private JTextField coldWaterCurrTextBox;
    private JTextField hotPriceTextBox;
    private JTextField coldPriceTextBox;
    private JTextField coldWaterSumTextBox;
    private JTextField hotWaterSumTextBox;
    private JTextField coldWaterPrevTextBox;
    private JTextField hotWaterPrevTextBox;
    private JComboBox monthPrevComboBox;
    private JComboBox monthCurrComboBox;
    private JButton solveButton;
    private JButton aboutProgramButton;
    private JButton aboutAuthorButton;
    private JButton exitButton;
    private JButton fromFileButton;
    private JButton toFileButton;
    private FileHelper fileHelper;
    private JTextField sumTextBox;
    private JButton dropSelectionButton;
    private PaymentCSVRepository repository;
    private ArrayList<CSVModel> list;
    private JMenuItem aboutAuthorButtonMenu;
    private JMenuItem aboutProgramButtonMenu;

    public MainFrame(String title)
    {
        repository = new PaymentCSVRepository();
        list = new ArrayList<>();
        menuBar = new JMenuBar();
        setResizable(false);
        setTitle(title);
        setSize(500, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        menuBar.add(createHelpMenuBar());
        setJMenuBar(menuBar);
        fileHelper = new FileHelper(this);
        setVisible(true);
        var panel1 = createPrevMonthInputPanel();
        add(panel1);
        var panel2 = createCurrentMonthInputPanel();
        add(panel2);
        var panel3 = CostPanelCreate();
        add(panel3);
        var panel4 = SumCostPanel();
        add(panel4);
        var panel5 = BottomPanelCreate();
        add(panel5);
        var panel6 = createRightButtonPanel();
        add(panel6);

        monthPrevComboBox.setSelectedIndex(0);
        monthCurrComboBox.setSelectedIndex(1);
        initActionListeners();
        fileHandler = new FileHelper(this);
    }

    private void initActionListeners()
    {
        solveButton.addActionListener(e -> handleSolveButtonClick());
        toFileButton.addActionListener(e -> handleToFileButtonClick());
        fromFileButton.addActionListener(e -> handleFromFileButtonClick());
        dropSelectionButton.addActionListener(e -> dropSelectionButtonClick());
        monthPrevComboBox.addActionListener(e -> monthPrevComboBoxChanged());
        monthCurrComboBox.addActionListener(e -> monthCurrComboBoxChanged());
        coldWaterPrevTextBox.addKeyListener(new FloatKeyAdapter());
        hotWaterPrevTextBox.addKeyListener(new FloatKeyAdapter());
        coldWaterCurrTextBox.addKeyListener(new FloatKeyAdapter());
        hotWaterCurrTextBox.addKeyListener(new FloatKeyAdapter());
        hotPriceTextBox.addKeyListener(new FloatKeyAdapter());
        coldPriceTextBox.addKeyListener(new FloatKeyAdapter());
        exitButton.addActionListener(e -> System.exit(EXIT_ON_CLOSE));
        aboutProgramButtonMenu.addActionListener(e->new HelpFrame());
        aboutProgramButton.addActionListener(e-> new HelpFrame());
        aboutAuthorButtonMenu.addActionListener(e -> new AboutFrame());
        aboutAuthorButton.addActionListener(e-> new AboutFrame());
    }

    private void monthPrevComboBoxChanged()
    {
        var selected = monthPrevComboBox.getSelectedIndex();
        if (selected == 11)
        {
            monthCurrComboBox.setSelectedIndex(0);
            return;
        }
        monthCurrComboBox.setSelectedIndex(selected + 1);
    }

    private void monthCurrComboBoxChanged()
    {
        var selected = monthCurrComboBox.getSelectedIndex();
        if (selected == 0)
        {
            monthPrevComboBox.setSelectedIndex(11);
            UpdateUI(monthCurrComboBox.getSelectedIndex());
            return;
        }
        UpdateUI(monthCurrComboBox.getSelectedIndex());
        monthPrevComboBox.setSelectedIndex(selected - 1);

    }

    private void dropSelectionButtonClick()
    {
        hotWaterCurrTextBox.setText("");
        coldWaterCurrTextBox.setText("");
        hotPriceTextBox.setText("");
        coldPriceTextBox.setText("");
        coldWaterSumTextBox.setText("");
        hotWaterSumTextBox.setText("");
        coldWaterPrevTextBox.setText("");
        hotWaterPrevTextBox.setText("");
        sumTextBox.setText("");
    }

    private void handleSolveButtonClick()
    {
        var hotSum = (Float.parseFloat(hotWaterCurrTextBox.getText())
                - Float.parseFloat(hotWaterPrevTextBox.getText())) * Float.parseFloat(hotPriceTextBox.getText());
        var coldSum = (Float.parseFloat(coldWaterCurrTextBox.getText())
                - Float.parseFloat(coldWaterPrevTextBox.getText())) * Float.parseFloat(coldPriceTextBox.getText());
        hotWaterSumTextBox.setText(String.valueOf(hotSum));
        coldWaterSumTextBox.setText(String.valueOf(coldSum));
        sumTextBox.setText(String.valueOf(hotSum + coldSum));

        var data = createFromTextBoxes();

        var csvPayment = new CSVModel();
        csvPayment.id = data.MonthTo;
        csvPayment.coldWater = data.ColdWaterCurrMonthVolume;
        csvPayment.hotWater = data.HotWaterCurrMonthVolume;
        csvPayment.coldWaterPrice = data.ColdWaterCost;
        csvPayment.hotWaterPrice = data.HotWaterCost;
        csvPayment.hotWaterSum = data.HotWaterSum;
        csvPayment.coldWaterSum = data.ColdWaterSum;
        csvPayment.sum = data.TotalSum;

        var csvPartialPayment = new CSVModel();
        csvPartialPayment.id = data.MonthFrom;
        csvPartialPayment.coldWater = data.ColdWaterPrevMonthVolume;
        csvPartialPayment.hotWater = data.HotWaterPrevMonthVolume;

        if (! isContainId(monthCurrComboBox.getSelectedIndex()))
        {
            list.add(csvPayment);
        }
        else
        {
            var index = getIndexById(monthCurrComboBox.getSelectedIndex());
            list.set(index, csvPayment);
        }

        if (!isContainId(monthPrevComboBox.getSelectedIndex()))
        {
            list.add(csvPartialPayment);
        }
        else
        {
            var index = getIndexById(monthPrevComboBox.getSelectedIndex());
            var item = list.get(index);
            item.id = data.MonthFrom;
            item.coldWater = data.ColdWaterPrevMonthVolume;
            item.hotWater = data.HotWaterPrevMonthVolume;
            list.set(index, item);
        }


    }

    private void handleToFileButtonClick()
    {
        //handleSolveButtonClick();
        var path = fileHelper.saveAsFileDialog();
        repository.setFilePath(path);
        repository.SaveAs(list);
    }

    private void handleFromFileButtonClick()
    {
        var path = fileHelper.openFileDialog();
        repository.setFilePath(path);
        list = repository.GetAll();
        if (! list.isEmpty())
        {
            UpdateUI(-1);
        }
    }

    private void UpdateUI(int monthId)
    {
        var curr = 0;
        if (monthId < 0)
        {
            monthId = list.get(curr).id;
        }
        else
        {
            curr = getIndexById(monthId);
        }
        var prevId = 0;
        if (monthId == 0)
        {
            prevId = 11;
        }
        else
        {
            prevId = monthId - 1;
        }
        if (isContainId(monthId))
        {
            coldWaterSumTextBox.setText(String.valueOf(list.get(curr).coldWaterSum));
            hotWaterSumTextBox.setText(String.valueOf(list.get(curr).hotWaterSum));
            sumTextBox.setText(String.valueOf(list.get(curr).sum));
            monthCurrComboBox.setSelectedIndex(list.get(curr).id);
            hotWaterCurrTextBox.setText(String.valueOf(list.get(curr).hotWater));
            coldWaterCurrTextBox.setText(String.valueOf(list.get(curr).coldWater));
            hotPriceTextBox.setText(String.valueOf(list.get(curr).hotWaterPrice));
            coldPriceTextBox.setText(String.valueOf(list.get(curr).coldWaterPrice));
        }
        else
        {
            clearCurrAndSum();
        }

        if (isContainId(prevId))
        {
            monthPrevComboBox.setSelectedIndex(prevId);
            var prevRecord = list.get(getIndexById(prevId));
            coldWaterPrevTextBox.setText(String.valueOf(prevRecord.coldWater));
            hotWaterPrevTextBox.setText(String.valueOf(prevRecord.hotWater));
        }
        else
        {
            clearPrev();
        }
    }

    private boolean isContainId(int identifier)
    {
        return list.stream().anyMatch(o -> o.id == identifier);
    }

    private int getIndexById(int identifier)
    {
        return IntStream.range(0, list.size())
                .filter(i -> list.get(i).id == identifier)
                .findFirst().orElse(- 1);
    }

    private void clearCurrAndSum()
    {
        hotWaterCurrTextBox.setText("");
        coldWaterCurrTextBox.setText("");
        hotPriceTextBox.setText("");
        coldPriceTextBox.setText("");
        coldWaterSumTextBox.setText("");
        hotWaterSumTextBox.setText("");
        sumTextBox.setText("");
    }

    private void clearPrev()
    {
        coldWaterPrevTextBox.setText("");
        hotWaterPrevTextBox.setText("");
    }
    public void clearSum()
    {
        coldWaterSumTextBox.setText("");
        hotWaterSumTextBox.setText("");
        sumTextBox.setText("");
    }

    private Payment createFromTextBoxes()
    {
        var payment = new Payment();
        payment.ColdWaterCost = Float.parseFloat(coldPriceTextBox.getText());
        payment.ColdWaterCurrMonthVolume = Float.parseFloat(coldWaterCurrTextBox.getText());
        payment.ColdWaterPrevMonthVolume = Float.parseFloat(coldWaterPrevTextBox.getText());
        payment.ColdWaterSum = Float.parseFloat(coldWaterSumTextBox.getText());
        payment.HotWaterCost = Float.parseFloat(hotPriceTextBox.getText());
        payment.HotWaterCurrMonthVolume = Float.parseFloat(hotWaterCurrTextBox.getText());
        payment.HotWaterPrevMonthVolume = Float.parseFloat(hotWaterPrevTextBox.getText());
        payment.HotWaterSum = Float.parseFloat(hotWaterSumTextBox.getText());
        payment.MonthFrom = monthPrevComboBox.getSelectedIndex();
        payment.MonthTo = monthCurrComboBox.getSelectedIndex();
        payment.TotalSum = payment.HotWaterSum + payment.ColdWaterSum;
        return payment;
    }

    private JPanel createPrevMonthInputPanel()
    {
        var panel = new JPanel(null);
        InputPanelCreate(panel);
        coldWaterPrevTextBox = new JTextField(20);
        hotWaterPrevTextBox = new JTextField(20);
        coldWaterPrevTextBox.setBounds(110, 30, 100, 20);
        hotWaterPrevTextBox.setBounds(110, 60, 100, 20);

        monthPrevComboBox = new JComboBox(Month.values());
        monthPrevComboBox.setBounds(215, 30, 120, 20);

        var monthLabel = new JLabel("Показания за предыдущий месяц");
        monthLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        monthLabel.setForeground(new Color(88, 41, 41));
        monthLabel.setBounds(10, 5, 300, 20);
        panel.add(monthLabel);

        panel.add(monthPrevComboBox);
        panel.add(coldWaterPrevTextBox);
        panel.add(hotWaterPrevTextBox);

        panel.setBounds(10, 10, 350, 90);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }

    private JPanel createRightButtonPanel()
    {
        var panel = new JPanel(null);
        fromFileButton = new JButton("Из файла");
        toFileButton = new JButton("В файл");
        fromFileButton.setBounds(10, 20, 90, 60);
        fromFileButton.setMargin(new Insets(10, 5, 10, 5));
        toFileButton.setBounds(10, 110, 90, 60);
        panel.add(toFileButton);
        panel.add(fromFileButton);
        panel.setBounds(370, 10, 110, 190);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }

    private JPanel createCurrentMonthInputPanel()
    {
        var panel = new JPanel(null);
        InputPanelCreate(panel);
        coldWaterCurrTextBox = new JTextField(20);
        hotWaterCurrTextBox = new JTextField(20);
        coldWaterCurrTextBox.setBounds(110, 30, 100, 20);
        hotWaterCurrTextBox.setBounds(110, 60, 100, 20);

        monthCurrComboBox = new JComboBox(Month.values());
        monthCurrComboBox.setBounds(215, 30, 120, 20);

        var monthLabel = new JLabel("Показания за текущий месяц");
        monthLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        monthLabel.setForeground(new Color(88, 41, 41));
        monthLabel.setBounds(10, 5, 300, 20);
        panel.add(monthLabel);

        panel.add(monthCurrComboBox);
        panel.add(coldWaterCurrTextBox);
        panel.add(hotWaterCurrTextBox);

        panel.setBounds(10, 110, 350, 90);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }

    private JPanel CostPanelCreate()
    {
        var panel = new JPanel(null);
        var coldPriceLabel = new JLabel("Холодная");
        var hotPriceLabel = new JLabel("Горячая");
        var rubLabel1 = new JLabel("Руб");
        var rubLabel2 = new JLabel("Руб");
        var priceLabel = new JLabel("Цена за единицу");
        dropSelectionButton = new JButton("Сбросить");
        hotPriceTextBox = new JTextField(4);
        coldPriceTextBox = new JTextField(4);
        coldPriceLabel.setBounds(10, 30, 100, 20);
        hotPriceLabel.setBounds(10, 60, 100, 20);
        rubLabel1.setBounds(140, 30, 30, 20);
        rubLabel2.setBounds(140, 60, 30, 20);
        priceLabel.setBounds(60, 5, 100, 20);
        dropSelectionButton.setBounds(30, 90, 120, 30);
        priceLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        priceLabel.setForeground(new Color(88, 41, 41));
        priceLabel.setBounds(10, 5, 300, 20);
        coldPriceTextBox.setBounds(80, 30, 50, 20);
        hotPriceTextBox.setBounds(80, 60, 50, 20);
        panel.add(coldPriceLabel);
        panel.add(hotPriceLabel);
        panel.add(hotPriceTextBox);
        panel.add(coldPriceTextBox);
        panel.add(rubLabel1);
        panel.add(rubLabel2);
        panel.add(priceLabel);
        panel.add(dropSelectionButton);
        panel.setBounds(10, 210, 180, 140);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        return panel;
    }

    private JPanel BottomPanelCreate()
    {
        var panel = new JPanel(null);

        solveButton = new JButton("Рассчитать");
        aboutProgramButton = new JButton("О программе");
        aboutProgramButton.setMargin(new Insets(10, 5, 10, 0));
        aboutAuthorButton = new JButton("Об авторе");
        aboutAuthorButton.setMargin(new Insets(10, 5, 10, 0));
        exitButton = new JButton("Выйти");
        solveButton.setBounds(10, 5, 180, 50);
        aboutProgramButton.setBounds(200, 5, 90, 50);
        aboutAuthorButton.setBounds(300, 5, 90, 50);
        exitButton.setBounds(400, 5, 80, 50);
        solveButton.setBackground(new Color(145, 220, 164));
        exitButton.setBackground(new Color(231, 164, 164));
        panel.add(solveButton);
        panel.add(aboutAuthorButton);
        panel.add(aboutProgramButton);
        panel.add(exitButton);

        panel.setBounds(0, 355, 500, 70);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return panel;

    }

    private JPanel SumCostPanel()
    {
        var panel = new JPanel(null);
        var coldPriceLabel = new JLabel("Холодная");
        var hotPriceLabel = new JLabel("Горячая");
        var rubLabel1 = new JLabel("Руб");
        var rubLabel2 = new JLabel("Руб");
        var rubLabel3 = new JLabel("Руб");
        var priceLabel = new JLabel("Оплата за воду");
        var sumLabel = new JLabel("Сумма");
        sumTextBox = new JTextField(20);
        coldWaterSumTextBox = new JTextField(20);
        hotWaterSumTextBox = new JTextField(20);
        coldPriceLabel.setBounds(10, 30, 100, 20);
        hotPriceLabel.setBounds(10, 60, 100, 20);
        rubLabel1.setBounds(240, 30, 30, 20);
        rubLabel2.setBounds(240, 60, 30, 20);
        rubLabel3.setBounds(240, 90, 30, 20);
        priceLabel.setBounds(60, 5, 100, 20);
        priceLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        priceLabel.setForeground(new Color(88, 41, 41));
        priceLabel.setBounds(10, 5, 300, 20);
        coldWaterSumTextBox.setBounds(80, 30, 150, 20);
        hotWaterSumTextBox.setBounds(80, 60, 150, 20);
        sumTextBox.setBounds(80, 90, 150, 20);
        sumLabel.setBounds(10, 90, 100, 20);
        panel.add(coldPriceLabel);
        panel.add(hotPriceLabel);
        panel.add(coldWaterSumTextBox);
        panel.add(hotWaterSumTextBox);
        panel.add(sumTextBox);
        panel.add(rubLabel3);
        panel.add(sumLabel);
        panel.add(rubLabel1);
        panel.add(rubLabel2);
        panel.add(priceLabel);
        panel.setBounds(200, 210, 280, 140);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        return panel;
    }

    private void InputPanelCreate(JPanel panel)
    {
        var coldWaterLabel = new JLabel("Холодная вода");
        var hotWaterLabel = new JLabel("Горячая вода");
        coldWaterLabel.setBounds(10, 30, 100, 20);
        hotWaterLabel.setBounds(10, 60, 100, 20);

        panel.add(coldWaterLabel);
        panel.add(hotWaterLabel);
    }


    private JMenu createHelpMenuBar()
    {
        var helpMenu = new JMenu("Помощь");

        aboutProgramButtonMenu = new JMenuItem("Помощь");
        aboutAuthorButtonMenu = new JMenuItem("Об авторе");

        helpMenu.add(aboutProgramButtonMenu);
        helpMenu.add(aboutAuthorButtonMenu);

        return helpMenu;
    }
}

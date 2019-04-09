package Frames;

import Buttons.OptionButton;
import Files.Files;
import Listeners.AddBag;
import Listeners.RefreshPrice;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class MainFrame implements Runnable {

    private JFrame mframe;
    private DefaultTableModel tablem;
    private JLabel total;
    private HashMap<String, HashMap<String,String>> tabledata;

    public MainFrame() {
        tabledata = new HashMap<>();
    }

    @Override
    public void run() {

        mframe = new JFrame();
        mframe.setPreferredSize(new Dimension(850,400));
        mframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mframe.setTitle("BagFolio");

        createComponent(mframe.getContentPane());

        mframe.pack();
        mframe.setVisible(true);

        Files.setupMainFolder();
    }


    private JPanel createTotalPanel() {

        JPanel totalScreen = new JPanel();
        totalScreen.setBackground(Color.BLACK);

        FlowLayout totalLayout = new FlowLayout();
        totalLayout.setAlignment(FlowLayout.RIGHT);
        totalScreen.setLayout(totalLayout);

        total = new JLabel("0.0");
        total.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        total.setForeground(Color.WHITE);

        totalScreen.add(total);
        totalScreen.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        return totalScreen;
    }

    private JPanel createOptionPanel() {

        JPanel optionScreen = new JPanel();
        optionScreen.setBackground(Color.black);

        FlowLayout optionLayout = new FlowLayout();
        optionLayout.setAlignment(FlowLayout.LEFT);
        optionScreen.setLayout(optionLayout);

        OptionButton addBag = new OptionButton("+Bag");
        addBag.addActionListener(new AddBag(getMframe(), tablem, tabledata));

        OptionButton refresh = new OptionButton("Refresh");
        refresh.addActionListener(new RefreshPrice(tablem, tabledata));

        optionScreen.add(addBag);
        optionScreen.add(refresh);

        optionScreen.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        return optionScreen;

    }

    private JPanel createTopPanel() {

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.black);

        GridLayout topPanelLayout = new GridLayout(1,2);
        topPanel.setLayout(topPanelLayout);

        topPanel.add(createOptionPanel());
        topPanel.add(createTotalPanel());

        return topPanel;

    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();

        GridLayout tableLayout = new GridLayout();
        tablePanel.setLayout(tableLayout);

        tablePanel.setBackground(Color.BLACK);

        String[] columnNames = {"Exchange","Pair", "Amount", "Current Price", "Bought Price", "Profit/Loss"};

        tablem = new DefaultTableModel(0, columnNames.length);
        tablem.setColumnIdentifiers(columnNames);

        JTable table = new JTable(tablem);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);
        table.setEnabled(false);
        table.setGridColor(Color.gray);
        table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setBorder(new MatteBorder(1,1,0,1, Color.BLACK));
        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerR);
        table.getColumnModel().getColumn(1).setCellRenderer(centerR);

        DefaultTableCellRenderer rightR = new DefaultTableCellRenderer();
        rightR.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightR);
        table.getColumnModel().getColumn(3).setCellRenderer(rightR);
        table.getColumnModel().getColumn(4).setCellRenderer(rightR);
        table.getColumnModel().getColumn(5).setCellRenderer(rightR);

        table.getTableHeader().setReorderingAllowed(false);

        tablePanel.add(scrollPane);
        Files.loadDataFile(tabledata, tablem);
        return tablePanel;
    }

    private void createComponent(Container container) {

        BorderLayout mainLayout = new BorderLayout();
        container.setLayout(mainLayout);
        container.setBackground(Color.BLACK);

        JPanel tablePanel = createTablePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,createTopPanel(), tablePanel);
        splitPane.setDividerSize(1);

        container.add(splitPane, BorderLayout.CENTER);
    }


    public JFrame getMframe() {
        return mframe;
    }
}

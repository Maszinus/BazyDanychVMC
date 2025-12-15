import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Tablica tableModel = new Tablica();
    private JLabel statusLabel = new JLabel("Gotowe");
    private JButton loadButton = new JButton("Wczytaj Zadania");
    private JButton addButton = new JButton("Dodaj Zadanie");
    private JProgressBar progressBar = new JProgressBar();

    public Main() {
        super("Prosty Menedżer Zadań");

        DBU.initDatabase();

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);

        JPanel buttons = new JPanel();
        buttons.add(loadButton);
        buttons.add(addButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);
        add(progressBar, BorderLayout.EAST);

        loadButton.addActionListener(e -> loadTasks());
        addButton.addActionListener(e -> addTask());

        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void loadTasks() {
        statusLabel.setText("Ładowanie danych... Proszę czekać.");
        loadButton.setEnabled(false);
        progressBar.setVisible(true);

        new LoadTask(tableModel, statusLabel, loadButton, progressBar).execute();
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog(this, "Tytuł zadania:");
        if (title == null || title.isBlank()) return;

        String desc = JOptionPane.showInputDialog(this, "Opis zadania:");

        new AddTask(title, desc, statusLabel, this::loadTasks).execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

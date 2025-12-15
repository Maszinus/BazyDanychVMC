import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoadTask extends SwingWorker<List<Task>, Void> {

    private Tablica tableModel;
    private JLabel statusLabel;
    private JButton loadButton;
    private JProgressBar progressBar;

    public LoadTask(Tablica tableModel,
                    JLabel statusLabel,
                    JButton loadButton,
                    JProgressBar progressBar) {
        this.tableModel = tableModel;
        this.statusLabel = statusLabel;
        this.loadButton = loadButton;
        this.progressBar = progressBar;
    }

    @Override
    protected List<Task> doInBackground() throws Exception {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DBU.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("is_done")
                ));
            }
        }

        Thread.sleep(4000); // symulacja długiej operacji
        return tasks;
    }

    @Override
    protected void done() {
        try {
            List<Task> tasks = get();
            tableModel.setTasks(tasks);
            statusLabel.setText("Gotowe. Wczytano " + tasks.size() + " zadań.");
        } catch (Exception e) {
            statusLabel.setText("Błąd podczas wczytywania danych.");
        }

        loadButton.setEnabled(true);
        progressBar.setVisible(false);
    }
}

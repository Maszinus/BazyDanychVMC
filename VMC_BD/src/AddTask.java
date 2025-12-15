import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddTask extends SwingWorker<Boolean, Void> {

    private String title;
    private String description;
    private JLabel statusLabel;
    private Runnable reloadCallback;

    public AddTask(String title,
                   String description,
                   JLabel statusLabel,
                   Runnable reloadCallback) {
        this.title = title;
        this.description = description;
        this.statusLabel = statusLabel;
        this.reloadCallback = reloadCallback;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try (Connection conn = DBU.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO tasks(title, description, is_done) VALUES (?, ?, ?)"
             )) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setBoolean(3, false);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                statusLabel.setText("Zadanie dodane.");
                reloadCallback.run();
            }
        } catch (Exception e) {
            statusLabel.setText("Błąd zapisu zadania.");
        }
    }
}

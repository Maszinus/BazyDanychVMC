import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBU {

    private static final String URL =
            "jdbc:mysql://localhost:3306/taskmanager?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Brak sterownika MySQL JDBC", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS tasks (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description TEXT,
                            is_done BOOLEAN NOT NULL
                        )
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

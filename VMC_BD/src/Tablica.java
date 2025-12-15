import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class Tablica extends AbstractTableModel {

    private List<Task> tasks = new ArrayList<>();
    private final String[] columns = {"ID", "Tytuł", "Opis", "Wykonane"};

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task t = tasks.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> t.getId();
            case 1 -> t.getTitle();
            case 2 -> t.getDescription();
            case 3 -> t.isDone();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}

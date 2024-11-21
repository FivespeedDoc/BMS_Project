package gui.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

public class Table extends JTable {
    public Table(TableModel dm) {
        super(dm);
        setFillsViewportHeight(true);
        JTableHeader tableHeader = getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setRowHeight(20);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}

package labirithm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Leader extends JPanel {

    public Leader() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Leaderboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        var button1 = new JButton("Back");

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.SOUTH);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Labirithm.toMainMenu();
            }
        });

        var resultSet = DB.getLeaderBoard();
        try {
            JTable table = new JTable(buildTableModel(resultSet));
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int column = 1; column <= columnCount; column++) {
            columnNames[column - 1] = metaData.getColumnName(column);
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        int rowNumber = 1;
        while (resultSet.next() && rowNumber <= 10) {
            Object[] rowData = new Object[columnCount];
            rowData[0] = rowNumber++;
            for (int i = 1; i <= columnCount; i++) {
                rowData[i-1] = resultSet.getObject(i);
            }
            model.addRow(rowData);
        }

        return model;
    }
}

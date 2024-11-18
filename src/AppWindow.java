import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class AppWindow extends JFrame {
	// constants
	private static final String[] CATEGORIES = { "Choose", "Action", "Adventure", "Biography", "Comedy", "Crime",
			"Documentary", "Drama", "History" };

	// fields
	private JTextField titleField;
	private JTextField authorField;
	private JComboBox<String> categoryComboBox;
	private JTextField priceField;
	private JTextField searchField;
	private JTable table;
	private JButton addButton;
	private JButton updateButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton cancelButton;
	private JButton searchButton;

	// controller
	private BookController bookController;

	private int selectedTableRecordId;
	private int selectedTableRow;
	private String oldTitle = "";

	public JTextField getTitleField() {
		return titleField;
	}

	public JTextField getAuthorField() {
		return authorField;
	}

	public JComboBox<String> getCategoryComboBox() {
		return categoryComboBox;
	}

	public JTextField getPriceField() {
		return priceField;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public String getOldTitle() {
		return oldTitle;
	}

	public void setOldTitle(String newTitle) {
		this.oldTitle = newTitle;
	}

	private void initFrame() {
		this.setTitle("Book Inventory Management System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 400));
		this.setIconImage(new ImageIcon("src/images/logo.png").getImage());
		this.setLayout(new GridBagLayout());
	}

	private void initComponents() {
		GridBagConstraints gbc = new GridBagConstraints();

		// form panel
		JPanel formPanel = createFormPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(formPanel, gbc);

		// table panel
		JPanel tablePanel = createTablePanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(tablePanel, gbc);
	}

	// form panel
	private JPanel createFormPanel() {
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setPreferredSize(new Dimension(300, formPanel.getPreferredSize().height));
		formPanel.setMinimumSize(new Dimension(300, 0));
		formPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

		formPanel
				.setBorder(
						BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 0),
								BorderFactory.createCompoundBorder(
										new TitledBorder(new LineBorder(Color.GRAY, 1), "Add New Book",
												TitledBorder.CENTER, TitledBorder.TOP, null, null),
										new EmptyBorder(10, 10, 10, 10))));

		// title
		formPanel.add(createTextFormField("Title", titleField = new JTextField()));
		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// author
		formPanel.add(createTextFormField("Author", authorField = new JTextField()));
		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// category
		formPanel.add(createComoboBoxFormField("Categories", categoryComboBox = new JComboBox<>(CATEGORIES)));
		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// price
		formPanel.add(createTextFormField("price", priceField = new JTextField()));
		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// form action panel
		formPanel.add(formActionPanel());

		return formPanel;
	}

	// text form field
	private JPanel createTextFormField(String label, JTextField field) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel fieldLabel = new JLabel(label);
		fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		field.setMargin(new Insets(4, 8, 4, 8));
		field.setPreferredSize(new Dimension(0, 28));
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		field.setAlignmentX(Component.LEFT_ALIGNMENT);

		panel.add(fieldLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(field);

		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

		return panel;
	}

	// combobox form field
	private JPanel createComoboBoxFormField(String label, JComboBox<String> field) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel fieldLabel = new JLabel("Category");
		fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		field.setBackground(Color.WHITE);
		field.setPreferredSize(new Dimension(0, 28));
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		field.setAlignmentX(Component.LEFT_ALIGNMENT);

		panel.add(fieldLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(field);

		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

		return panel;
	}

	// form action panel
	private JPanel formActionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(new EmptyBorder(15, 0, 0, 0));

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookController.addBook();
			}
		});

		updateButton = new JButton("Update");
		updateButton.setVisible(false);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookController.updateBook(selectedTableRecordId);
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookController.resetForm();
			}
		});

		panel.add(addButton);
		panel.add(updateButton);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(cancelButton);

		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

		return panel;
	}

	// table panel
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(20, 20, 20, 10));

		// search panel
		panel.add(createSearchPanel(), BorderLayout.NORTH);

		// table
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) {
				selectedTableRow = table.getSelectedRow();
				if (selectedTableRow != -1) {
					selectedTableRecordId = Integer.parseInt(table.getValueAt(selectedTableRow, 0).toString());
					bookController.enableTableActionButtons();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);

		// action panel
		panel.add(createTableActionPanel(), BorderLayout.SOUTH);

		return panel;
	}

	// search panel
	private JPanel createSearchPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(280, 28));
		searchField.setMaximumSize(new Dimension(280, 28));
		searchField.setMargin(new Insets(4, 8, 4, 8));
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = searchField.getText().trim();
				bookController.searchBooks(query);
			}
		});

		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(Box.createHorizontalGlue());
		panel.add(searchField);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(searchButton);

		return panel;
	}

	// action panel
	private JPanel createTableActionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(new EmptyBorder(10, 0, 0, 0));

		editButton = new JButton("Edit");
		editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int categoryIndex = Arrays.asList(CATEGORIES).indexOf(table.getValueAt(selectedTableRow, 3).toString());

				addButton.setVisible(false);
				updateButton.setVisible(true);
				titleField.setText(table.getValueAt(selectedTableRow, 1).toString());
				authorField.setText(table.getValueAt(selectedTableRow, 2).toString());
				categoryComboBox.setSelectedIndex(categoryIndex);
				priceField.setText(table.getValueAt(selectedTableRow, 4).toString());

				oldTitle = table.getValueAt(selectedTableRow, 1).toString();

				bookController.disableTableActionButtons();
			}
		});

		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(
						null, "Are you sure you want to delete the book: "
								+ table.getValueAt(selectedTableRow, 1).toString() + "?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					bookController.deleteBook(selectedTableRecordId);
				}

			}
		});

		panel.add(editButton);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(deleteButton);

		return panel;
	}

	public AppWindow() {
		bookController = new BookController(this);
		initFrame();
		initComponents();

		try {
			bookController.loadTableData(null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				AppWindow frame = new AppWindow();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}

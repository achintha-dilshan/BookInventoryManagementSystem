import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class AppWindow extends JFrame {

	public AppWindow() {
		// frame settings
		this.setTitle("Book Inventory Management System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 400));

		// frame logo
		ImageIcon logo = new ImageIcon("src/images/logo.png");
		this.setIconImage(logo.getImage());
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		// form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		formPanel.setPreferredSize(new Dimension(300, formPanel.getPreferredSize().height));
		formPanel.setMinimumSize(new Dimension(300, 0));
		formPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

		formPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 0),
				BorderFactory.createCompoundBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Add New Book"),
						new EmptyBorder(10, 10, 10, 10))));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(formPanel, gbc);

		// title
		JPanel formTitlePanel = new JPanel();
		formTitlePanel.setLayout(new BoxLayout(formTitlePanel, BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel("Title");
		titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JTextField titleField = new JTextField();
		titleField.setPreferredSize(new Dimension(0, 28));
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		titleField.setAlignmentX(Component.LEFT_ALIGNMENT);

		formTitlePanel.add(titleLabel);
		formTitlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
		formTitlePanel.add(titleField);

		formTitlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formTitlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formTitlePanel.getPreferredSize().height));
		formPanel.add(formTitlePanel);

		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// category
		String[] categories = { "Action", "Adventure", "Biography", "Comedy", "Crime", "Documentary", "Drama",
				"History" };

		JPanel formCategoryPanel = new JPanel();
		formCategoryPanel.setLayout(new BoxLayout(formCategoryPanel, BoxLayout.Y_AXIS));

		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JComboBox<String> categoryCombox = new JComboBox<>(categories);
		categoryCombox.setPreferredSize(new Dimension(0, 28));
		categoryCombox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		categoryCombox.setAlignmentX(Component.LEFT_ALIGNMENT);

		formCategoryPanel.add(categoryLabel);
		formCategoryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		formCategoryPanel.add(categoryCombox);

		formCategoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formCategoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formCategoryPanel.getPreferredSize().height));
		formPanel.add(formCategoryPanel);

		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// price
		JPanel formPricePanel = new JPanel();
		formPricePanel.setLayout(new BoxLayout(formPricePanel, BoxLayout.Y_AXIS));

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JTextField priceField = new JTextField();
		priceField.setPreferredSize(new Dimension(0, 28));
		priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		priceField.setAlignmentX(Component.LEFT_ALIGNMENT);

		formPricePanel.add(priceLabel);
		formPricePanel.add(Box.createRigidArea(new Dimension(0, 5)));
		formPricePanel.add(priceField);

		formPricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPricePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formPricePanel.getPreferredSize().height));
		formPanel.add(formPricePanel);

		formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// form actions
		JPanel formActionPanel = new JPanel();
		formActionPanel.setLayout(new BoxLayout(formActionPanel, BoxLayout.X_AXIS));
		formActionPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

		JButton addButton = new JButton("Add");
		JButton cancelButton = new JButton("Cancel");

		formActionPanel.add(addButton);
		formActionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		formActionPanel.add(cancelButton);

		formActionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		formActionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formActionPanel.getPreferredSize().height));
		formPanel.add(formActionPanel);

		// table panel
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBorder(new EmptyBorder(20, 20, 20, 10));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(tablePanel, gbc);

		// search panel
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

		JTextField searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(280, 28));
		searchField.setMaximumSize(new Dimension(280, 28));
		searchField.setMargin(new Insets(4, 8, 4, 8));
		JButton searchButton = new JButton("Search");

		searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		searchPanel.add(Box.createHorizontalGlue());
		searchPanel.add(searchField);
		searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		searchPanel.add(searchButton);

		tablePanel.add(searchPanel, BorderLayout.NORTH);

		// table
		String[] columns = { "Id", "Title", "Category", "Price" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		tablePanel.add(scrollPane, BorderLayout.CENTER);

		// bottom action panel
		JPanel bottomActionPanel = new JPanel();
		bottomActionPanel.setLayout(new BoxLayout(bottomActionPanel, BoxLayout.X_AXIS));
		bottomActionPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");

		bottomActionPanel.add(editButton);
		bottomActionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		bottomActionPanel.add(deleteButton);

		tablePanel.add(bottomActionPanel, BorderLayout.SOUTH);

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow frame = new AppWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

package cafe1;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
public class cafeteriaGUI extends JFrame {
    JMenu mnuCustomer, mnuMenu;
    JMenuItem cIns, cDel, cDis, mIns, mDel,mDis;
    JLabel lbl1, lbl2, lbl3, lbl, lbl4, lbl5, lbl6, lbl7, lbl8;
    JTextField txt1, txt2, txt3, txt4, txt5, txt6;
    JButton btn1,btn2, btn3, btn4, btn5, btn6;
    JComboBox cCustId, cCategoryId;

    public cafeteriaGUI() {
        getContentPane().setBackground(Color.green);
        setSize(1200, 800);
        //menu
        JMenuBar mb = new JMenuBar();
        mnuCustomer = new JMenu("Customer");
        mnuMenu = new JMenu("     Food Menu");
        cIns = new JMenuItem("Insert");
        cDel = new JMenuItem("Delete");
        cDis= new JMenuItem("Display");
        mIns = new JMenuItem("Insert");
        mDel = new JMenuItem("Delete");
        mDis = new JMenuItem("Display");
        mnuCustomer.add(cIns);
        mnuCustomer.add(cDel);
        mnuCustomer.add(cDis);
        mnuMenu.add(mIns);
        mnuMenu.add(mDel);
        mnuMenu.add(mDis);
        mb.add(mnuCustomer);
        mb.add(mnuMenu);
        setJMenuBar(mb);
        getContentPane().removeAll();
        repaint();
        JLabel welcome = new JLabel("                WELCOME TO CAFETERIA MANAGEMENT SYSTEM");
        welcome.setFont(new Font("Verdana", Font.BOLD, 28));
        add(welcome);
        //Add actionListener for display button in customer
        cDis.addActionListener((ActionEvent e) -> {
            getContentPane().removeAll();
            repaint();
            getContentPane().setBackground(Color.GREEN);
            lbl = new JLabel("Display Customer table ");
            lbl.setFont(new Font("Verdana", Font.BOLD, 20));
            lbl.setBounds(30, 20, 340, 50);
            add(lbl);
            try {
                //to display all rows in customers table
                JTextArea micDisplayContent1 = new JTextArea(40, 70);
                micDisplayContent1.setBounds(100, 100, 800, 400);
                micDisplayContent1.setFont(new Font("Verdana", Font.BOLD, 12));
                add(micDisplayContent1);
                micDisplayContent1.setEditable(false);
                micDisplayContent1.setText("ID  \tFIRSTNAME \t  LASTNAME \n\n");
                String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "JVDB2022";
                String password = "123456";
                String DRIVER = "oracle.jdbc.driver.OracleDriver";
                Connection connection = DriverManager.getConnection(URL, username, password);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from customer");
                while (rs.next()) {
                    micDisplayContent1.append(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + "\n");
                }
                connection.close();
            } catch (SQLException se) {
            }
        });
        //Add actionListener to insert button in customer
        cIns.addActionListener((ActionEvent e) -> {
            getContentPane().removeAll();
            repaint();
            getContentPane().setBackground(Color.yellow);
            lbl = new JLabel("Insert into Customer table ");
            lbl.setFont(new Font("Verdana", Font.BOLD, 20));
            lbl.setBounds(30, 20, 340, 50);
            add(lbl);
            try {
                lbl1= new JLabel("Enter customer ID:");
                lbl1.setBounds(50, 80, 110, 30);
                add(lbl1);
                txt1 = new JTextField(10);
                txt1.setBounds(220, 80, 190, 30);
                add(txt1);
                lbl2 = new JLabel("Enter customer's FirstName:");
                lbl2.setBounds(50, 130, 190, 30);
                add(lbl2);
                txt2 = new JTextField(10);
                txt2.setBounds(220, 130, 190, 30);
                add(txt2);
                lbl3 = new JLabel("Enter customer's LastName:");
                lbl3.setBounds(50, 180, 190, 30);
                add(lbl3);
                txt3 = new JTextField(10);
                txt3.setBounds(220, 180, 190, 30);
                add(txt3);
                //button for inserting a customer
                btn3 = new JButton("Submit");
                btn3.setBounds(120, 500, 100, 30);
                btn3.setBackground(Color.green);
                add(btn3);
                btn3.addActionListener((ActionEvent e1) -> {
                    try {
                        String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                        String username = "JVDB2022";
                        String password = "123456";
                       String DRIVER = "oracle.jdbc.driver.OracleDriver";
                       Connection connection = DriverManager.getConnection(URL, username, password);
                        String id = txt1.getText();
                        String fn = txt2.getText();
                        String ln = txt3.getText();
                        String sql = "INSERT INTO CUSTOMER VALUES(?,?,?)";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setString(1, id);
                        stmt.setString(2, fn);
                        stmt.setString(3, ln);
                        int rows = stmt.executeUpdate();
                        stmt.executeUpdate("commit");
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(cafeteriaGUI.this, "Inserted succesfully.");
                        }
                        connection.close();
                    } catch (SQLException se) {
                        JOptionPane.showMessageDialog(cafeteriaGUI.this, "Sorry,couldn't insert, !" + se);
                        System.out.println("could not insert" + se);
                    }
                });
            } catch (Exception se) {
            }
        });
        //add actionListener to delete button in customer
        cDel.addActionListener((ActionEvent e) -> {
            getContentPane().removeAll();
            getContentPane().setBackground(Color.cyan);
            repaint();
            lbl = new JLabel("Delete from Customer table ");
            lbl.setFont(new Font("Verdana", Font.BOLD, 20));
            lbl.setBounds(30, 15, 340, 50);
            add(lbl);
            try {
                String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "JVDB2022";
                String password = "123456";
                String DRIVER = "oracle.jdbc.driver.OracleDriver";
                Connection connection = DriverManager.getConnection(URL, username, password);
                String sql = "select id from customer";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);

                String ids[] = new String[20];
                int i = 0;
                while (rs.next()) {
                    ids[i] = rs.getString("id");
                    i++;
                }
                int j = 0, count = 0;
                while (ids[j] != null) {
                    count++;
                    j++;
                }
                String id[] = new String[count];
                for (j = 0; j < count; j++) {
                    id[j] = ids[j];
                }
                //label and jcombobox for selecting an id to be deleted
                lbl7 = new JLabel("Select Customer Id:");
                lbl7.setBounds(50, 70, 170, 30);
                lbl7.setFont(new Font("Verdana", Font.BOLD, 12));
                add(lbl7);
                cCustId = new JComboBox(id);
                cCustId.setBounds(220, 70, 180, 30);
                cCustId.setFont(new Font("Verdana", Font.BOLD, 12));
                add(cCustId);
                btn2 = new JButton("Show Customer Details");
                btn2.setBounds(120, 110, 170, 30);
                btn2.setBackground(Color.orange);
                add(btn2);
                lbl1 = new JLabel("Enter customer ID:");
                lbl1.setBounds(50, 150, 110, 30);
                add(lbl1);
                txt1 = new JTextField(10);
                txt1.setBounds(220, 150, 190, 30);
                add(txt1);
                lbl2 = new JLabel("Enter customer's FirstName:");
                lbl2.setBounds(50, 200, 190, 30);
                add(lbl2);
                txt2 = new JTextField(10);
                txt2.setBounds(220, 200, 190, 30);
                add(txt2);
                lbl3 = new JLabel("Enter customer's LastName:");
                lbl3.setBounds(50, 250, 190, 30);
                add(lbl3);
                txt3 = new JTextField(10);
                txt3.setBounds(220, 250, 190, 30);
                add(txt3);
                //button for DELETE a customer
                btn1 = new JButton("Delete");
                btn1.setBounds(120, 550, 100, 30);
                btn1.setBackground(Color.green);
                add(btn1);
                btn2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String sid1 = (String) cCustId.getSelectedItem();
                            String sql2 = "select * from customer where ID=" + sid1;
                            PreparedStatement stmt2 = connection.prepareStatement(sql2);
                            stmt2.executeUpdate();
                            ResultSet rs2 = stmt2.executeQuery(sql2);
                            while (rs2.next()) {
                                txt1.setText(rs2.getString(1));
                                txt2.setText(rs2.getString(2));
                                txt3.setText(rs2.getString(3));
                            }
                        } catch (Exception ae) {
                            System.out.println("error");
                        }
                    }
                });
                btn1.addActionListener((ActionEvent e1) -> {
                    try {
                        String sid = (String) cCustId.getSelectedItem();
                        String sqlDelete = "delete from customer S where S.id=?";
                        PreparedStatement stmt3 = connection.prepareStatement(sqlDelete);
                        stmt3.setString(1, sid);
                        int rows = stmt3.executeUpdate();
                        stmt.executeUpdate("commit");
                        if (rows > 0) {
                            txt1.setText("");
                            txt2.setText("");
                            txt3.setText("");
                            JOptionPane.showMessageDialog(cafeteriaGUI.this, "Deleted succesfully.");
                        }
                        connection.close();
                    } catch (SQLException se) {
                        JOptionPane.showMessageDialog(cafeteriaGUI.this, "Sorry, something went wrong couldn't delete");
                        System.out.println("could not delete" + se);
                    }
                });
            } catch (SQLException se) {
            }
        });
        //Add actionListener for display button in menu
        mDis.addActionListener((ActionEvent e) -> {
            getContentPane().removeAll();
            repaint();
            getContentPane().setBackground(Color.green);
            lbl = new JLabel("Display Food Menu table ");
            lbl.setFont(new Font("Verdana", Font.BOLD, 20));
            lbl.setBounds(30, 20, 340, 50);
            add(lbl);
            try {
                JTextArea mimDisplayContent1 = new JTextArea(40, 70);
                mimDisplayContent1.setBounds(100, 100, 800, 600);
                mimDisplayContent1.setFont(new Font("Verdana", Font.BOLD, 12));
                add(mimDisplayContent1);
                mimDisplayContent1.setEditable(false);
                mimDisplayContent1.setText("CATEGORY_ID \t FOOD_NAME  \t COST \n\n");
                String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "JVDB2022";
                String password = "123456";
                String DRIVER = "oracle.jdbc.driver.OracleDriver";
                Connection connection = DriverManager.getConnection(URL, username, password);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from menu");
                while (rs.next()) {
                    mimDisplayContent1.append(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\n");
                }
                connection.close();
            } catch (SQLException se) {
            }
        });
        mIns.addActionListener((ActionEvent e) -> {
            getContentPane().removeAll();
            getContentPane().setBackground(Color.yellow);
            repaint();
            lbl = new JLabel("Insert into Food Menu table ");
            lbl.setFont(new Font("Verdana", Font.BOLD, 20));
            lbl.setBounds(30, 20, 340, 50);
            add(lbl);
            try {
                lbl4 = new JLabel("Enter category ID:");
                lbl4.setBounds(50, 130, 110, 30);
                add(lbl4);
                txt4 = new JTextField(10);
                txt4.setBounds(220, 130, 190, 30);
                add(txt4);
                //label and textfield for food name
                lbl5 = new JLabel("Enter Food Name:");
                lbl5.setBounds(50, 180, 190, 30);
                add(lbl5);
                txt5 = new JTextField(10);
                txt5.setBounds(220, 180, 190, 30);
                add(txt5);
                lbl6 = new JLabel("Enter cost:");
                lbl6.setBounds(50, 280, 190, 30);
                add(lbl6);
                txt6 = new JTextField(10);
                txt6.setBounds(220, 280, 190, 30);
                add(txt6);
                btn6 = new JButton("Submit");
                btn6.setBounds(120, 330, 100, 30);
                btn6.setBackground(Color.green);
                add(btn6);
                btn6.addActionListener((ActionEvent e1) -> {
                    try {
                        String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "JVDB2022";
                String password = "123456";
               String DRIVER = "oracle.jdbc.driver.OracleDriver";
                        Connection connection = DriverManager.getConnection(URL, username, password);
                        String cid = txt4.getText();
                        String fn = txt5.getText();
                        String c = txt6.getText();
                        String sql = "INSERT INTO Mytable(id,fn,ln) VALUES(?,?,?)";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setString(1, cid);
                        stmt.setString(2, fn);
                        stmt.setString(3, c);
                        int rows = stmt.executeUpdate();
                        stmt.executeUpdate("commit");
                        
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(cafeteriaGUI.this, "Inserted succesfully.");
                        }
                        connection.close();
                    } catch (SQLException se) {
                        JOptionPane.showMessageDialog(cafeteriaGUI.this, "Sorry,couldn't insert, " + se);
                        System.out.println("could not insert" + se);
                    }
                });
            } catch (Exception se) {
            }
        });
        //add actionListener to delete button in food menu
        mDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                repaint();
                getContentPane().setBackground(Color.pink);
                lbl = new JLabel("Delete from Food Menu table ");
                lbl.setFont(new Font("Verdana", Font.BOLD, 20));
                lbl.setBounds(30, 15, 340, 50);
                add(lbl);
                try {
                    String URL = "jdbc:oracle:thin:@localhost:1521:xe";
                    String username = "JVDB2022";
                    String password = "123456";
                    String DRIVER = "oracle.jdbc.driver.OracleDriver";
                    Connection connection = DriverManager.getConnection(URL, username, password);
                    String sql = "select category_id from menu";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    String ids[] = new String[20];
                    int i = 0;
                    while (rs.next()) {
                        ids[i] = rs.getString("category_id");
                        i++;
                    }
                    int j = 0, count = 0;
                    while (ids[j] != null) {
                        count++;
                        j++;
                    }
                    String id[] = new String[count];
                    for (j = 0; j < count; j++) {
                        id[j] = ids[j];
                    }
                    //label and jcombobox for selecting an id to be deleted
                    lbl8 = new JLabel("Select Category Id:");
                    lbl8.setBounds(50, 100, 170, 30);
                    lbl8.setFont(new Font("Verdana", Font.BOLD, 12));
                    add(lbl8);
                    cCategoryId = new JComboBox(id);
                    cCategoryId.setBounds(220, 100, 170, 30);
                    cCategoryId.setFont(new Font("Verdana", Font.BOLD, 12));
                    add(cCategoryId);
                    //button for show details a menu
                    btn4 = new JButton("Show FoodMenu Details");
                    btn4.setBounds(120, 150, 200, 30);
                    btn4.setBackground(Color.orange);
                    add(btn4);
                    //label and textfield for category ID
                    lbl4 = new JLabel("Enter category ID:");
                    lbl4.setBounds(50, 250, 110, 30);
                    add(lbl4);
                    txt4 = new JTextField(10);
                    txt4.setBounds(220, 250, 190, 30);
                    add(txt4);
                    lbl5 = new JLabel("Enter Food Name:");
                    lbl5.setBounds(50, 300, 190, 30);
                    add(lbl5);
                    txt5 = new JTextField(10);
                    txt5.setBounds(220, 300, 190, 30);
                    add(txt5);
                    lbl6 = new JLabel("Enter cost:");
                    lbl6.setBounds(50, 400, 190, 30);
                    add(lbl6);
                    txt6 = new JTextField(10);
                    txt6.setBounds(220, 400, 190, 30);
                    add(txt6);
                    btn5 = new JButton("Delete");
                    btn5.setBounds(120, 450, 100, 30);
                    btn5.setBackground(Color.green);
                    add(btn5);
                    btn4.addActionListener((ActionEvent e1) -> {
                        try {
                            String sid1 = (String) cCategoryId.getSelectedItem();
                            String sql2 = "select category_id,food_name,cost from menu where CATEGORY_ID=" + sid1;
                            PreparedStatement stmt2 = connection.prepareStatement(sql2);
                            stmt2.executeUpdate();
                            ResultSet rs2 = stmt2.executeQuery(sql2);
                            while (rs2.next()) {
                                txt4.setText(rs2.getString(1));
                                txt5.setText(rs2.getString(2));
                                txt6.setText(rs2.getString(3));
                            }
                        } catch (Exception ae) {
                            System.out.println("error");
                        }
                    });
                    btn5.addActionListener((ActionEvent e1) -> {
                        try {
                            String sid = (String) cCategoryId.getSelectedItem();
                            String sqlDelete = "delete from menu where category_id=?";
                            PreparedStatement stmt3 = connection.prepareStatement(sqlDelete);
                            stmt3.setString(1, sid);
                            int rows = stmt3.executeUpdate();
                            stmt.executeUpdate("commit");
                            if (rows > 0) {
                                txt4.setText("");
                                txt5.setText("");
                                txt6.setText("");
                                JOptionPane.showMessageDialog(cafeteriaGUI.this, "Deleted succesfully.");
                            }
                            connection.close();
                        } catch (SQLException se) {
                            JOptionPane.showMessageDialog(cafeteriaGUI.this, "Sorry, something went wrong couldn't delete");
                            System.out.println("could not delete" + se);
                        }
                    });
                } catch (SQLException se) {
                }
            }
        }); 
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        cafeteriaGUI h = new cafeteriaGUI();
        h.setTitle("Cafeteria Management System");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lancelot
 */
public class GUI extends javax.swing.JFrame {
    List<String> ID = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> departmentList = new ArrayList<>();
    List<String> monthList = new ArrayList<>();
    List<String> dayList = new ArrayList<>();
    List<String> yearList = new ArrayList<>();
    
    DefaultTableModel model;
    
    int count = 0;
    int del;
    String update;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        model = (DefaultTableModel) jTable1.getModel();
        readDataFromFile();
        refreshTable();
        
        // Center the JFrame on the screen
        this.setLocationRelativeTo(null);
        
        // constructors
        this.Name = Name;
        this.EMP_ID = EMP_ID;
        this.Department = Department;
        this.Month = Month;
        this.Day = Day;
        this.Year = Year;
        this.jTextFieldID = jTextFieldID;
    }
    
    // method to read the employee records from the text file and populate the table
    public void readDataFromFile() {
    try (BufferedReader br = new BufferedReader(new FileReader("employee_records.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(","); // Assuming CSV format (ID,Name,Department,Month,Day,Year)
            model.addRow(data);
            ID.add(data[0]);
            name.add(data[1]);
            departmentList.add(data[2]);
            monthList.add(data[3]);
            dayList.add(data[4]);
            yearList.add(data[5]);
            
            // increment count to increase variable count of rows
            count++;
        }
    } catch (IOException e) {
        e.printStackTrace(); }
    }
    
    // method to write the the employee record to the text file when adding a new employee 
    public void writeDataToFile(String data) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee_records.txt", true))) {
        bw.write(data);
        bw.newLine();
    } catch (IOException e) {
        e.printStackTrace(); }
    }
    
     // method to write the encoded data to the file (per department)
    public void encodeDepartment(String encodedData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("per_department.txt"))) {
            
//            String formatStr = "%-20s %-15s %-15s %-15s%n";
//
//            bw.write(String.format(formatStr,"ID", "Name", "Department", "Date Hired"));
            bw.write(encodedData);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(); }
    }
    
    // method to write the the employee record to the text file when adding a new employee 
    public void encodeLessThan2(String data) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("hired_two_years.txt"))) {
        bw.write(data);
        bw.newLine();
    } catch (IOException e) {
        e.printStackTrace(); }
    }
    
    // method to update the text file when the record is needed to be updated
    public void updateDataInFile(List<String> ID, List<String> name, List<String> departmentList,
                             List<String> monthList, List<String> dayList, List<String> yearList) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee_records.txt"))) {
        for (int i = 0; i < ID.size(); i++) {
            String record = ID.get(i) + "," + name.get(i) + "," + departmentList.get(i) + ","
                    + monthList.get(i) + "," + dayList.get(i) + "," + yearList.get(i);
            bw.write(record);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace(); }
    }
    
    // method to remove a record/data/employee from the text file when deleted 
    public void deleteRecordFromFile(String ID) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("employee_records.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith(ID + ",")) {
                lines.add(line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("employee_records.txt"))) {
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace(); }
    }
    
    // method to write the encoded data to the file
    public void writeEncodedDataToFile(String encodedData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("encoded_employee_records.txt"))) {
            bw.write(encodedData);
        } catch (IOException e) {
            e.printStackTrace(); }
    }
    
    // method to see the correct data in real-time without having to rerun the program
    private void refreshTable() {
        // Clear the table first
        model.setRowCount(0);

        // Sort the ID list from lowest to largest
        List<Integer> sortedID = new ArrayList<>();
        for (String id : ID) {
            sortedID.add(Integer.parseInt(id));
        }
        Collections.sort(sortedID);

        // Update the table with sorted data
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        for (int id : sortedID) {
            int index = ID.indexOf(String.valueOf(id));
            String empno = ID.get(index);
            String ename = name.get(index);
            String department = departmentList.get(index);
            String month = monthList.get(index);
            String day = dayList.get(index);
            String year = yearList.get(index);

            try {
                Date date = DateFor.parse(month + "/" + day + "/" + year);
                model.addRow(new Object[]{empno, ename, department, DateFor.format(date)});
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    
    // getters - encapsulation
    public JTextField getjTextField1() {
        return Name;
    }

    public JTextField getjTextField2() {
        return EMP_ID;
    }

    public JTextField getjTextField3() {
        return Department;
    }

    public JTextField getjTextField4() {
        return Month;
    }

    public JTextField getjTextField5() {
        return Day;
    }

    public JTextField getjTextField6() {
        return Year;
    }

    public JTextField getjTextFieldID() {
        return jTextFieldID;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Name = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        EMP_ID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Department = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Month = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Day = new javax.swing.JTextField();
        Year = new javax.swing.JTextField();
        Add = new javax.swing.JButton();
        ResetText = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextFieldID = new javax.swing.JTextField();
        Update = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(254, 210, 132));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("EMPLOYEE INFORMATION"));

        Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Employee ID:");

        EMP_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EMP_IDKeyTyped(evt);
            }
        });

        jLabel3.setText("Department:");

        jLabel5.setText("month (mm)");

        jLabel6.setText("day (dd)");

        jLabel7.setText("year (yyyy)");

        Month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonthActionPerformed(evt);
            }
        });
        Month.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                MonthKeyTyped(evt);
            }
        });

        jLabel4.setText("Date Hired:");

        Day.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DayKeyTyped(evt);
            }
        });

        Year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearActionPerformed(evt);
            }
        });
        Year.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                YearKeyTyped(evt);
            }
        });

        Add.setBackground(new java.awt.Color(204, 255, 204));
        Add.setForeground(new java.awt.Color(0, 0, 0));
        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        ResetText.setText("Reset Text");
        ResetText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Day, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(Department, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(EMP_ID, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(Add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResetText)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(EMP_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add)
                    .addComponent(ResetText))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Department", "Date Hired"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(255, 204, 102));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pick Category", "ID", "Name", "Department", "Date Hired" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextFieldID.setText("ID Number");
        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });

        Update.setBackground(new java.awt.Color(153, 204, 255));
        Update.setForeground(new java.awt.Color(51, 51, 51));
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        Delete.setBackground(new java.awt.Color(255, 51, 51));
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        Save.setBackground(new java.awt.Color(153, 204, 255));
        Save.setForeground(new java.awt.Color(51, 51, 51));
        Save.setText("Generate Report");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Update))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(Delete))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(Save)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Update))
                .addGap(18, 18, 18)
                .addComponent(Delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Save)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("EMPLOYEE PROFILE MANAGEMENT SYSTEM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(184, 184, 184)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        // Encode the data and save it to a file

        // all employees
        StringBuilder encodedData = new StringBuilder();
        String formatAllEmployees = "%-10s%-20s%-20s%-8s%-6s%-5s\n";
        for (int i = 0; i < ID.size(); i++) {
            String encodedRecord = Base64.getEncoder().encodeToString(
                String.format(formatAllEmployees,
                    ID.get(i), name.get(i), departmentList.get(i),
                    monthList.get(i), dayList.get(i), yearList.get(i)
                ).getBytes()
            );
            encodedData.append(encodedRecord).append("\n");
        }

        // per department
        List<String> doneData = new ArrayList<>();
        StringBuilder wholeText = new StringBuilder();
        String formatPerDepartment = "%-10s%-20s%-20s%-7s%-6s%-5s\n";
        outerloop:
        for (int i = 0; i < ID.size(); i++) {
            for (String x : doneData) {
                if (departmentList.get(i) == null ? x == null : departmentList.get(i).equals(x)) {
                    continue outerloop;
                }
            }

            StringBuilder departmentText = new StringBuilder("\nID        Name                Department          Month  Day   Year\n");
            String newDepartmentRecord = String.format(formatPerDepartment,
                ID.get(i), name.get(i), departmentList.get(i),
                monthList.get(i), dayList.get(i), yearList.get(i)
            );
            departmentText.append(newDepartmentRecord);

            doneData.add(departmentList.get(i)); // add done data
            for (int j = i + 1; j < ID.size(); j++) {
                if (doneData.get(i) == null ? departmentList.get(j) == null : doneData.get(i).equals(departmentList.get(j))) {
                    String newDepartmentRecord2 = String.format(formatPerDepartment,
                        ID.get(j), name.get(j), departmentList.get(j),
                        monthList.get(j), dayList.get(j), yearList.get(j)
                    );
                    departmentText.append(newDepartmentRecord2);
                }
            } // end of for-loop for similar department

            wholeText.append(departmentText);
        } // end of outerloop

        // employees less than 2 years
        StringBuilder less2Text = new StringBuilder("ID        Name                Department          Month  Day   Year\n");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < ID.size(); i++) {
            if ((Integer.parseInt(yearList.get(i)) >= (currentYear - 2)) && (Integer.parseInt(yearList.get(i)) <= currentYear)) {
                String newDepartmentRecord = String.format(formatPerDepartment,
                    ID.get(i), name.get(i), departmentList.get(i),
                    monthList.get(i), dayList.get(i), yearList.get(i)
                );
                less2Text.append(newDepartmentRecord);
            }
        }

        // Debug output
        System.out.println("Encoded data:\n" + encodedData.toString());
        writeEncodedDataToFile(encodedData.toString()); // writes all employee data
        encodeDepartment(wholeText.toString()); // writes all employee data per department
        encodeLessThan2(less2Text.toString()); // writes all employee data hired within 2 years
        System.out.println("Data saved to file.");
        }

        private void writeEncodedDataToFile(String filename, String data) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                writer.write(data);
                writer.close();
            } catch (IOException e) {
                System.err.println("Error writing data to file: " + e.getMessage());
            }
    }//GEN-LAST:event_SaveActionPerformed

    private void ResetTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetTextActionPerformed
        // TODO add your handling code here:
        //        jTextField1.setText("");
        //        jTextField2.setText("");
        //        jTextField3.setText("");
        //        jTextField4.setText("");
        //        jTextField5.setText("");
        //        jTextField6.setText("");

        Clear clear = new Clear(this);

        Thread th1 = new Thread(clear);

        th1.start();
    }//GEN-LAST:event_ResetTextActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        // inheritance + polymorphism
        class ToDelete extends Funcs{
            public void doFunction() {
                del = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Row ID to Delete: ", "DELETE", JOptionPane.QUESTION_MESSAGE));

                for (int i = 0; i < ID.size(); i++) {
                    if (Integer.parseInt(ID.get(i)) == del) {
                        // Remove the data from the lists
                        ID.remove(i);
                        name.remove(i);
                        departmentList.remove(i);
                        monthList.remove(i);
                        dayList.remove(i);
                        yearList.remove(i);

                        // Decrement the count variable
                        count--;

                        break;
                    }
                }

                // Update the table with the new data after deletion
                refreshTable();
            }
        }

        ToDelete doDelete = new ToDelete(); // composition
        doDelete.doFunction();

        // Remove the data from the text file
        deleteRecordFromFile(String.valueOf(del));
    }//GEN-LAST:event_DeleteActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        // TODO add your handling code here:

        int num = 0;
        int i = 0;

        do{
            if(Integer.parseInt(ID.get(i)) == Integer.parseInt(jTextFieldID.getText())){
                num = i;
                break;
            }
            else
            i++; // increment i value
        }while(i < count); // modified for-loop where num value is incremented only after second iteration

        if(jComboBox1.getSelectedItem().toString() == "ID"){
            iFactory factory = new iFactory();
            FuncAbstract updateID = factory.createObject(1);
            update = updateID.DoFunction();

            //            update = JOptionPane.showInputDialog(null, "Enter New ID: ", "UPDATE", JOptionPane.QUESTION_MESSAGE);
            ID.set(num, update);

            // change table value
            model.setValueAt(update, num, 0);
        }
        if(jComboBox1.getSelectedItem().toString() == "Name"){
            iFactory factory = new iFactory();
            FuncAbstract updateName = factory.createObject(2);
            update = updateName.DoFunction();

            //            update = JOptionPane.showInputDialog(null, "Enter New Name: ", "UPDATE", JOptionPane.QUESTION_MESSAGE);
            name.set(num, update);

            // change table value
            model.setValueAt(update, num, 1);
        }
        if(jComboBox1.getSelectedItem().toString() == "Department"){
            iFactory factory = new iFactory();
            FuncAbstract updateDep = factory.createObject(3);
            update = updateDep.DoFunction();

            //            update = JOptionPane.showInputDialog(null, "Enter New Department: ", "UPDATE", JOptionPane.QUESTION_MESSAGE);
            departmentList.set(num, update);

            // change table value
            model.setValueAt(update, num, 2);
        }
        if(jComboBox1.getSelectedItem().toString() == "Date Hired"){
            //            update = JOptionPane.showInputDialog(null, "Enter New Date: ", "UPDATE", JOptionPane.QUESTION_MESSAGE);

            JTextField monthField = new JTextField(5);
            JTextField dayField = new JTextField(5);
            JTextField yearField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Month(MM):"));
            myPanel.add(monthField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Day(dd):"));
            myPanel.add(dayField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Year(yyyy):"));
            myPanel.add(yearField);

            update = String.valueOf(JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter New Date", JOptionPane.OK_CANCEL_OPTION));

        monthList.set(num, monthField.getText());
        dayList.set(num, dayField.getText());
        yearList.set(num, yearField.getText());

        // change table value
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        try{

            Date date = DateFor.parse(monthList.get(num) + "/" + dayList.get(num) + "/" + yearList.get(num));
            model.setValueAt(DateFor.format(date), num, 3);

            // try
            System.out.println(monthList.get(num) +"/" + dayList.get(num) + "/" + yearList.get(num));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        }

        // remove ID Text Field value
        jTextFieldID.setText("ID Number");

        // update data in text file
        updateDataInFile(ID, name, departmentList, monthList, dayList, yearList);

        // Refresh the table after adding a new record
        refreshTable();
    }//GEN-LAST:event_UpdateActionPerformed

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        
        int empno = Integer.parseInt(EMP_ID.getText());
        String ename = Name.getText();
        String department = Department.getText();
        int month = Integer.parseInt(Month.getText());
        int day = Integer.parseInt(Day.getText());
        int year = Integer.parseInt(Year.getText());
      
        ID.add(String.valueOf(empno));
        name.add(ename);
        departmentList.add(department);
        monthList.add(String.valueOf(month));
        dayList.add(String.valueOf(day));
        yearList.add(String.valueOf(year));

        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date date = DateFor.parse(monthList.get(count) + "/" + dayList.get(count) + "/" + yearList.get(count));
            model.insertRow(model.getRowCount(), new Object[]{ID.get(count),name.get(count),departmentList.get(count),DateFor.format(date)});
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // clear fields
        Name.setText("");
        EMP_ID.setText("");
        Department.setText("");
        Month.setText("");
        Day.setText("");
        Year.setText("");

        // increment variable
        count++;

        // write the new record to the text file
        String newRecord = empno + "," + ename + "," + department + "," + month + "," + day + "," + year;
        writeDataToFile(newRecord);

        // write data to per_department.txt
        //        String newDepartmentRecord = empno + "," + ename + "," + department + "," + month + "," + day + "," + year;
        //        encodeDepartment(newDepartmentRecord);

        // Refresh the table after adding a new record
        refreshTable();
    }//GEN-LAST:event_AddActionPerformed

    private void YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearActionPerformed

    private void MonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonthActionPerformed

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameActionPerformed

    private void EMP_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EMP_IDKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_EMP_IDKeyTyped

    private void MonthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MonthKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if((!Character.isDigit(c))){
            evt.consume();
        }
        if(Month.getText().length() >= 2)
            evt.consume();
    }//GEN-LAST:event_MonthKeyTyped

    private void DayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DayKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if((!Character.isDigit(c))){
            evt.consume();
        }
        if(Day.getText().length() >= 2)
            evt.consume();
    }//GEN-LAST:event_DayKeyTyped

    private void YearKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YearKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if((!Character.isDigit(c))){
            evt.consume();
        }
        if(Year.getText().length() >= 4)
            evt.consume();
    }//GEN-LAST:event_YearKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTextField Day;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField Department;
    private javax.swing.JTextField EMP_ID;
    private javax.swing.JTextField Month;
    private javax.swing.JTextField Name;
    private javax.swing.JButton ResetText;
    private javax.swing.JButton Save;
    private javax.swing.JButton Update;
    private javax.swing.JTextField Year;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldID;
    // End of variables declaration//GEN-END:variables
}

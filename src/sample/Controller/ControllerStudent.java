package sample.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.FileManager.FileManager;
import sample.Students.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerStudent implements Initializable {
    List<Student> studentList = new ArrayList<>();
    private static Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    public boolean validEmail(String regex) {
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validPhoneNumber(String regex) {
        pattern = Pattern.compile(PHONE_REGEX);
        matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    @FXML
    Stage stage;

    @FXML
    TableView<Student> tableView;

    @FXML
    TableColumn<Student, String> idColumn;

    @FXML
    TableColumn<Student, String> firstColumn;

    @FXML
    TableColumn<Student, String> lastColumn;

    @FXML
    TableColumn<Student, String> classColumn;

    @FXML
    TableColumn<Student, String> emailColumn;

    @FXML
    TableColumn<Student, String> phoneColumn;

    @FXML
    TextField txtStudentID;

    @FXML
    TextField txtFirstName;

    @FXML
    TextField txtLastName;

    @FXML
    TextField txtClass;

    @FXML
    TextField txtEmail;

    @FXML
    TextField txtPhone;

    @FXML
    TextField txtSearch;
@FXML
Button btnEdit;
    @FXML
    ChoiceBox<String> choiceBox;
    String[] choiceBoxList = {"Java", "PHP"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        btnEdit.setDisable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableView.getItems().addAll(studentList);
        choiceBox.getItems().addAll(choiceBoxList);
//        loadStudent();
    }

    public void loadStudent() {
        tableView.getItems().clear();
        for (Student student : studentList) {
            tableView.getItems().add(student);
        }
    }

    public void writeFile() throws Exception {
        FileManager<Student> studentFileManager = new FileManager<>();
        studentFileManager.writeFile("../Students/students.txt", studentList);
    }

    private void readFile() {
        FileManager<Student> studentFileManager = new FileManager<>();
        studentList.clear();
        studentList.addAll(studentFileManager.readFile("../Students/students.txt"));
    }



    public void backMenu(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
        Scene sceneMenu = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
    }


    public void editStudent(ActionEvent actionEvent) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        if (student == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You choose no student. Please choose again");
        } else {
            student.setFirstName(txtFirstName.getText());
            student.setLastName(txtLastName.getText());
            student.setClassName(txtClass.getText());
            student.setEmailAddress(txtEmail.getText());
            student.setPhoneNumber(txtPhone.getText());
            loadStudent();
        }
    }

    public void deleteStudent(ActionEvent actionEvent) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        studentList.remove(student);
        loadStudent();
    }

    public void addStudent(ActionEvent actionEvent) throws Exception {
        Student student = new Student();
        student.setStudentId(txtStudentID.getText());
        student.setFirstName(txtFirstName.getText());
        student.setLastName(txtLastName.getText());
        student.setClassName(txtClass.getText());
        student.setEmailAddress(txtEmail.getText());
        student.setPhoneNumber(txtPhone.getText());
        for (Student std : studentList) {
            boolean exist = std.getStudentId().equals(txtStudentID.getText()) || std.getEmailAddress().equals(txtEmail.getText()) || std.getPhoneNumber().equals(txtPhone.getText());
            boolean blank = txtStudentID.getText().equals("") || txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtClass.getText().equals("") || txtEmail.getText().equals("") || txtPhone.getText().equals("");
            if (exist || blank) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("System Information");
                alert.setContentText("This student is exist or something is blank! Please try again");
                alert.show();
                return;
            }
        }
        if ((validEmail(txtEmail.getText())) && (validPhoneNumber(txtPhone.getText()))) {
            studentList.add(student);
            loadStudent();
            writeFile();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("System Information");
            alert.setContentText("Invalid Email Or Phone number! Please try again");
            alert.show();
        }
    }


    public void searchStudent(ActionEvent actionEvent) {
        String search = txtSearch.getText();
        if (search.equals("")) {
            loadStudent();
        } else {
            String studentID = search.toLowerCase();
            tableView.getItems().clear();
            for (Student student : studentList) {
                if (student.getStudentId().toLowerCase().equals(studentID)) {
                    tableView.getItems().add(student);
                }
            }
        }
    }
}

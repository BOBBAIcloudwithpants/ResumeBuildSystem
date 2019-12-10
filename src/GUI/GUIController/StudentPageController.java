package GUI.GUIController;

import Controller.UserController;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import model.Award;
import model.User;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {

    @FXML
    private Text username;

    @FXML
    private Text grade;

    @FXML
    private Text admin;

    @FXML
    private TableView<ScoreInformation> scoretable;

    @FXML
    private TableColumn<ScoreInformation, String> subject;

    @FXML
    private TableColumn<ScoreInformation, Integer> score;

    @FXML
    private TableColumn<ScoreInformation, Integer> rank;

    private final ObservableList<ScoreInformation> scoreData = FXCollections.observableArrayList();
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
    private final ObservableList<AwardInformation> awardData = FXCollections.observableArrayList();

    @FXML
    private TableView<AwardInformation> awardtable;

    @FXML
    private TableColumn<AwardInformation, String> awardtitle;

    @FXML
    private TableColumn<AwardInformation, String> awardtime;

    @FXML
    private PieChart scorechart;

    @FXML
    private TextArea description;

    @FXML
    private Button submit;

    @FXML
    private Button createFileButton;

    @FXML
    void createFile(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("请选择文件保存位置");
        File directory = null;
        while(directory == null){
            directory = chooser.showDialog(null);
        }
        File file = new File(directory+"/"+"Resume.txt");
        PrintWriter outFile = null;
        try{
            outFile = new PrintWriter(file);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        model.File text = new model.File(userName);
        //outFile.println(text.getFile());
        outFile.println("cxt");
        outFile.close();
    }

    private UserController usercontroller;

    private String userName;

    //public static PassParameter passParameter = new PassParameter();

    void setUserName(String name){
        userName = name;
    }

    @FXML
    void submitdescriptionandaward(ActionEvent event) {
        List<AwardInformation> tc = awardtable.getItems();
        ArrayList<Award> newAwards = new ArrayList<Award>();
        for(int i = 0; i < tc.size(); i++){
            newAwards.add(tc.get(i).toAward());
        }

        System.out.println(newAwards.toString());

        usercontroller.setDescriptionByUsername(userName, description.getText());
    }

    public void reset(){

        //User user = usercontroller.getUserByUsername(userName);
        List<Integer> grades = new ArrayList<Integer>();
        grades.add(99);
        grades.add(73);
        List<Integer> ranks = new ArrayList<Integer>();
        ranks.add(1);
        ranks.add(3);
        List<Award> awards = new ArrayList<Award>();
        awards.add(new Award("acm","2000-05-09"));


        User user = new User("bob","123",0,"I'm bob.",1, grades, ranks, awards);

        setInformation(user);
        setDescription(user);
        setScoretable(user);
        setChart(user);
        setAwardtable(user);

    }

    public void setInformation(User user){
        username.setText("姓名："+user.getUsername());
        grade.setText("班级："+user.getGroupID());
        if(user.getIsAdmin()==1){
            admin.setText("身份：老师");
        }
        else{
            admin.setText("身份：学生");
        }
    }

    public void setDescription(User user){
        description.setText(user.getDescription());
    }

    public void setScoretable(User user) {
        List<String> subjects = new ArrayList<String>();
        subjects.add("语文");
        subjects.add("英语");
        List<Integer> grades = user.getGrades();
        List<Integer> ranks = user.getRanks();
        for(int i = 0; i < 2; i++){
            scoreData.add(new ScoreInformation(subjects.get(i), grades.get(i), ranks.get(i)));
        }
        subject.setCellValueFactory(scoreData->scoreData.getValue().getTsubject());
        score.setCellValueFactory(scoreData->scoreData.getValue().getTscore().asObject());
        rank.setCellValueFactory(scoreData->scoreData.getValue().getTrank().asObject());
        scoretable.setItems(scoreData);
    }

    public void setChart(User user){
        int count[] = new int[5];
        List<Integer> grades = user.getGrades();
        for(int i = 0; i < 2; i++){
            if(grades.get(i)<60){
                count[0]++;
            }
            else if(grades.get(i)<70){
                count[1]++;
            }
            else if(grades.get(i)<80){
                count[2]++;
            }
            else if(grades.get(i)<90){
                count[3]++;
            }
            else{
                count[4]++;
            }
        }

        for(int i = 0; i < 5; i++){
            if(count[i]>0){
                String interval;
                if(i<4){
                    interval = (60+10*i) +"-"+ (69+10*i);
                }
                else{
                    interval = "90-100";
                }
                double percentage = (double)count[i]/2*100;
                int percent = (int) percentage;
                chartData.add(new PieChart.Data(interval,percent));
            }
        }
        scorechart.setData(chartData);
        scorechart.setTitle("成绩分布表");
    }

    public void setAwardtable(User user){
        List<Award> awards = user.getAwards();
        for(int i = 0; i < 4; i++){
            if(i<awards.size()){
                awardData.add(new AwardInformation(awards.get(i).getTitle(), awards.get(i).getTime()));
            }
            else{
                awardData.add(new AwardInformation("", ""));
            }
        }

        awardtitle.setCellValueFactory(awardData->awardData.getValue().getName());
        awardtime.setCellValueFactory(awardData->awardData.getValue().getTime());
        awardtable.setItems(awardData);
        awardtable.setEditable(true);
        awardtitle.setCellFactory(TextFieldTableCell.forTableColumn());
        awardtime.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        usercontroller = new UserController();

    }
}
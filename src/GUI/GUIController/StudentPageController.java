package GUI.GUIController;

import Controller.UserController;
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

    private UserController usercontroller;

    private String userName;

    private final ObservableList<ScoreInformation> scoreData = FXCollections.observableArrayList();

    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

    private final ObservableList<AwardInformation> awardData = FXCollections.observableArrayList();

    //生成简历文件
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
        User user = usercontroller.getUserByUsername(userName);
        //outFile.println(user.getResume());
        outFile.close();
    }

    //设置本页面的学生username
    void setUserName(String name){
        userName = name;
    }

    //提交个人简介和获奖记录
    @FXML
    void submitdescriptionandaward(ActionEvent event) {
        ArrayList<Award> newAwards = new ArrayList<Award>();
        for(int i = 0; i < awardData.size(); i++){
            newAwards.add(awardData.get(i).toAward());
        }
        usercontroller.resetAwardsByUsername(userName, newAwards);
        usercontroller.setDescriptionByUsername(userName, description.getText());
    }

    //设置本页面各部分显示的内容
    public void reset(){

        User user = usercontroller.getUserByUsername(userName);

        setInformation(user);
        setDescription(user);
        setScoretable(user);
        setChart(user);
        setAwardtable(user);
    }

    //设置顶部个人信息，包括姓名、班级、身份
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

    //设置个人简介
    public void setDescription(User user){
        description.setText(user.getDescription());
    }

    //设置成绩表格
    public void setScoretable(User user) {
        List<String> subjects = new ArrayList<String>();
        subjects.add("Java");
        subjects.add("计组");
        subjects.add("数值");
        subjects.add("概统");
        subjects.add("Web");
        List<Integer> grades = user.getGrades();
        List<Integer> ranks = user.getRanks();
        for(int i = 0; i < grades.size(); i++){
            scoreData.add(new ScoreInformation(subjects.get(i), grades.get(i), ranks.get(i)));
        }
        subject.setCellValueFactory(scoreData->scoreData.getValue().getTsubject());
        score.setCellValueFactory(scoreData->scoreData.getValue().getTscore().asObject());
        rank.setCellValueFactory(scoreData->scoreData.getValue().getTrank().asObject());
        scoretable.setItems(scoreData);
    }

    //设置成绩分布饼图
    public void setChart(User user){
        int count[] = new int[5];
        List<Integer> grades = user.getGrades();
        for(int i = 0; i < grades.size(); i++){
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
                if(i<4 && i>1){
                    interval = (50+10*i) +"-"+ (59+10*i);
                }
                else if(i==0){
                    interval = "<60";
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

    //设置获奖记录表格
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
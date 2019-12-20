
# 数据分析与简历生成系统
## BY：1834xxxx 刘心怡 1834xxxx 赵佳怡 18342001 白家栋

# 功能：
## 学生用户：
1. 数据分析：输入成绩，进行数据分析（成绩分布，成绩向前比对查看趋势...），可在前端页面生成分数分布饼图。
2. 简历生成：根据个人信息（个人描述，奖项，分数，排名）生成简历，支持导出为txt文件和markdown文件
3. 横向比对：获取成绩在班级中的排名

## 老师：
1. 群组创建：创建班级群，可以查看被加入班级群的学生的成绩，个人信息等。
2. 数据分析：统计班级成绩，进行数据分析，可生成以班级为单位的成绩单，支持导出为txt文件和markdown文件

# UML图

# 项目环境说明
- 操作系统：Windows 10
- JAVA版本：推荐java 11

# 项目结构说明
- 目录树：
```
.
├── README.md
├── ResumeBuildSystem.iml
├── lib // 项目驱动文件
│   └── mysql-connector-java-8.0.18.jar
├── out
│   └── production
│       └── ResumeBuildSystem
│           ├── Controller 
│           │   ├── GroupController.class 
│           │   └── UserController.class 
│           ├── GUI
│           │   ├── GUIController
│           │   │   ├── AwardInformation.class
│           │   │   ├── MainApp.class
│           │   │   ├── MenuPageController.class
│           │   │   ├── RootLayoutController.class
│           │   │   ├── ScoreInforForTeacher.class
│           │   │   ├── ScoreInformation.class
│           │   │   ├── Server.class
│           │   │   ├── ServerThread.class
│           │   │   ├── SignInController.class
│           │   │   ├── SignUpController.class
│           │   │   ├── StudentPageController.class
│           │   │   └── TeacherPageController.class
│           │   └── view
│           │       ├── MenuPage.fxml
│           │       ├── RootLayout.fxml
│           │       ├── SignInController.fxml
│           │       ├── SignUpController.fxml
│           │       ├── StudentPageController.fxml
│           │       └── TeacherPageController.fxml
│           ├── META-INF
│           │   └── ResumeBuildSystem.kotlin_module
│           ├── database
│           │   ├── Mysql.class 
│           │   └── MysqlManager.class 
│           └── model
│               ├── Award.class
│               ├── File.class
│               ├── Group.class
│               └── User.class
└── src
    ├── Controller// 后台接口
    │   ├── GroupController.java // 群组相关接口
    │   └── UserController.java // 用户相关接口
    ├── GUI
    │   ├── GUIController
    │   │   ├── MainApp.java
    │   │   ├── MenuPageController.java
    │   │   ├── RootLayoutController.java
    │   │   ├── Server.java
    │   │   ├── ServerThread.java
    │   │   ├── SignInController.java
    │   │   ├── SignUpController.java
    │   │   ├── StudentPageController.java
    │   │   └── TeacherPageController.java
    │   └── view
    │       ├── MenuPage.fxml
    │       ├── RootLayout.fxml
    │       ├── SignInController.fxml
    │       ├── SignUpController.fxml
    │       ├── StudentPageController.fxml
    │       └── TeacherPageController.fxml
    ├── database
    │   ├── Mysql.java // 数据库操作接口
    │   └── MysqlManager.java // 数据库连接
    └── model
        ├── Award.java
        ├── File.java
        ├── Group.java
        └── User.java
```

# 后台介绍
该项目的后台采用了mysql作为存储信息的数据库，数据库运行在云服务器上。后台的接口也基本与数据库的增删查改相关。
## 数据表


### group表
- 表结构
![QX7jCF.png](https://s2.ax1x.com/2019/12/21/QX7jCF.png)
![QXbSJS.png](https://s2.ax1x.com/2019/12/21/QXbSJS.png)

### user表
- 表结构
![QXq9k6.png](https://s2.ax1x.com/2019/12/21/QXq9k6.png)
![QXqB3F.md.png](https://s2.ax1x.com/2019/12/21/QXqB3F.md.png)

# model 
## 学生：
### 属性：
- username
- password
- class(班级)
- description(个人描述)
- isAdmin
- grade(成绩)
- subject(科目)
- rank(排名)  
- awardName(奖项名称)
- awardTime(获奖时间)  
### 方法：

### 老师

#### 属性：
- username
- password
- description
- class
- isAdmin

## 接口文档

### User
| 序号 | 功能                             | 函数声明                                                               | 解释                                                              |
| ---- | -------------------------------- | ---------------------------------------------------------------------- | ----------------------------------------------------------------- |
| 1    | 判断用户是否在数据库中           | User getUserByUsername(String username)                                | 该用户存在则返回User实体，不存在则返回null                        |
| 2    | 用户登陆                         | boolean userLogin(String username, String password)                    | 登陆成功则返回true，失败则返回false                               |
| 3    | 用户根据用户名和组号注册         | boolean registerUser(String username, String password, int isAdmin)    | 注册成功则返回true，没有则返回false                               |
| 4    | 判断用户是否为管理员             | boolean isAdmin (String username)                                      | 该用户不存在或者不是管理员则返回false，否则返回true               |
| 5    | 将用户加入某个组                 | boolean appendUserIntoGroup (String username, int id)                  | 加入成功则返回true，没有则返回false                               |
| 6    | 根据用户姓名和科目查询成绩       | int getGradeByUsernameAndSubject (String username, int id)             | 返回成绩                                                          |
| 7    | 根据用户姓名查询个人描述         | String getDescriptionByUsername (String username)                      | 返回描述                                                          |
| 8    | 获取全部学生                     | List<User> getAllStudents ()                                           | 获取全部学生列表                                                  |
| 9    | 获取某一个组中的全部学生列表     | List<User> getStudentsByGroupID (int groupID)                          | 获取组中全部学生的列表                                            |
| 10   | 添加学生某门课的成绩             | boolean appendGradeOfStudent (String username, int id, int grade)      | **科目索引从1开始**, 添加成功返回true, 失败返回false              |
| 11   | 为学生添加描述                   | boolean setDescriptionByUsername (String username, String description) | 传入学生姓名，若该用户不为学生或者不存在则返回false, 否则返回true |
| 12   | 根据学生姓名和奖项名获取获奖时间 | String getAwardtimeByName (String username, String awardname)          | 根据学生姓名和奖项名获取获奖时间                                  |
| 13   | 为学生设置奖项                   | boolean setAwardsByUsername (String username, List<Award> awards)      | 设置成功为true, 否则为false                                       |
| 14   | 重置学生奖项并设置               | boolean resetAwardsByUsername (String username, List<Award> awards)    | 传入学生姓名和重置后的奖项, 成功为true, 否则为false               |
| 15   | 获取学生的简历文档               | String getStudentFile (String username)                                | 传入学生姓名, 获取简历字符串                                      |
### Group
| 序号 | 功能                | 函数声明                                            | 解释                                   |
| ---- | ------------------- | --------------------------------------------------- | -------------------------------------- |
| 1    | 根据id获取group信息 | Group getGroupById (String username, int id)        | 传入id, 要验证                         |
| 2    | 根据学生姓名        | boolean appendUserInGroup (String username, int id) | 传入学生姓名和组号，该学生和组需要存在 |
|      |                     |                                                     |                                        |






   



        
   






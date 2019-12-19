
# java课程大作业
## 名称：数据分析与用户简历生成系统
## 功能：
### 学生用户：
1. 数据分析：输入成绩，进行数据分析（成绩分布，成绩向前比对查看趋势...），可生成成绩报告单。
2. 简历生成：根据个人信息套用简历模版生成简单的个人简历，成绩报告单。
### 老师：
1. 群组创建：创建班级群，可以查看被加入班级群的学生的成绩，信息等。
2. 数据分析：统计班级成绩，进行数据分析，可生成成绩报告单

## 项目启动说明
本地要配置mysql环境，启动mysql服务以后创建名为“test”（实际名字里没有引号）的schema。

## 项目结构说明
idea项目如何在eclipse中打开可以参考网上的博客。（如果打不开的话就下个idea吧，很好用哒）
1. model用来存放学生，老师，班级等自定义的类。
2. gui用来存放gui界面设计。
3. controller用来处理gui端(前端)发送的请求。
4. lib用来存放jar包以及依赖的文件。

## model 
### 学生：
#### 属性：
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
#### 方法：

### 老师

#### 属性：
- username
- password
- description
- class
- isAdmin

#### 全部接口

##### User
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
##### Group
| 序号 | 功能                | 函数声明                                            | 解释                                   |
| ---- | ------------------- | --------------------------------------------------- | -------------------------------------- |
| 1    | 根据id获取group信息 | Group getGroupById (String username, int id)        | 传入id, 要验证                         |
| 2    | 根据学生姓名        | boolean appendUserInGroup (String username, int id) | 传入学生姓名和组号，该学生和组需要存在 |
|      |                     |                                                     |                                        |



##### Grade
在表结构中有5个字段，用于存放成绩。未录入的成绩默认为0
3. 前端：上传成绩信息请求：     
   服务端：       
   后台：1. 判断该用户是否是管理员：bool isAdmin(String username), 是则true，不是则false       
        2. 传入用户成绩信息：bool postGrade(String username, List<String> subjects, List<int> grades) subjects为科目，grades为成绩，科目与成绩的索引应保持一致，全部传入成功则返回true，没有则返回false

4. 前端：查询某门课的成绩：       
   服务端：        
   后台：
        1. 查询成绩：int getGradeByUsernameAndSubject(String username, String subject) 查询到则返回该成绩，没有查询到则返回null
        
5. 前端：修改某门课的成绩：     
   服务端：      
   后台：1. 判断该用户是否是管理员：bool isAdmin(String username), 是则true，不是则false       
        2. 修改成绩：bool modifyGradeByUsernameAndSubject(String username, String subject, int grade) 修改成功则true，修改失败则false。

6. 前端：导出某用户的信息作为简历：    
   服务端：
   后台：      
        1. 导出 File getProfileByUsername(String username) 导出成功则返回File本身，失败则返回null。
        
##### Group

1. 前端：根据ID查询组     
   服务端：     
   后台：1. Group getGroupById(int id)     

2. 前端：将用户添加到组中     
   服务端：     
   后台：1. boolean appendUserInGroup(String username,int id)    
   
3. 前端：将用户从某个组中移除
   服务端：
   后台：1. boolean removeUserFromGroup(String username, int id)
   
4. 前端：删除某个组中的全部用户
   服务端：
   后台：1. void deleteAllUserInGroup(int id);


#### Award

在表结构中留有5个位置，存放奖项标题和获奖时间

1. 前端：为某个用户添加奖项     
    服务端：     
    后台：boolean appendAwardByUsername(String username, String title, String time) 添加成功返回true，否则返回false

2. 前端：删除某个用户的奖项     
    服务端:     
    后台: boolean deleteAwardByUsernameAndAwardname(String username, String awardname) 删除成功返回true，否则返回false

3. 前端：删除某个用户的全部奖项     
    服务端：     
    后台：void deleteAllAwardsByUsername(String username) 


#### Rank

在表结构中有5个位置，当且仅当用户被加入某个group中排名有效，否则排名无效，为0

在将用户加入group或从group中删除后自动变化。

1


   



        
   






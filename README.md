
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
- id(根据id登陆)
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
- id
- password
- description
- class
- isAdmin

#### 接口

##### User
1. 前端：用户登录请求：     
   服务端：     
   后台：1. 判断用户是否在数据库中的接口：bool findUserByName(String username) 在数据库中则返回true，没有则返回false     
        2. 用户登陆：bool userLogin(String username, String password) 登陆成功则返回true，失败则返回false

2. 前端：用户注册请求：     
   服务端：     
   后台：1. 注册用户并写入数据库：bool registerUser(String username, String password, int isAdmin) 注册成功则返回true，没有则返回false

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
   
   



        
   






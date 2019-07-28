## SpringBoot自学

## 资料

[spring文档](https://spring.io/guides)
[thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
[前端](https://spring.io/guides/gs/serving-web-content/)
[创建key的方法](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[对标地址](https://elasticsearch.cn/explore)
[GitHub OAuth授权登录](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
[MySQL数据库学习](https://www.runoob.com/mysql/mysql-create-tables.html)
[spring](https://docs.spring.io/spring/docs/5.1.6.RELEASE/spring-framework-reference/web.html)
##工具

https://git-scm.com/download
[Visual Paradigm](https://www.visual-paradigm.com)

[前端框架：使用BootStrap](https://v3.bootcss.com/getting-started/)
[java模拟post：使用Okhttp](https://square.github.io/okhttp)
[mybatis](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)

## git具体使用

用户现在github上建立一个仓库springboot

git init 初始化仓库

git add . ：将修改的内容放在缓存中

vim .git/config:在该文件中添加自己的工作区
[user]
name = github用户名
email = 自己邮箱

git remote add origin https://github.com/tinggirl00/springboot.git
git push -u origin master


多次提交的具体步骤：
git status
git add .
git status
git commit -m "备注"
(git pull)将远端的代码拉下来
git push

## 脚本
```sql
create table user
(
  id           int auto_increment
    primary key,
  account_id   varchar(100) not null,
  name         varchar(50)  not null,
  token        char(36)     null,
  gmt_create   bigint       null,
  gmt_modified bigint       null
);
```






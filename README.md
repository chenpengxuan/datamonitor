# how to use

## 1.eclipse
   使用 cmd 进入源码目录   
  ```gradlew.bat eclipse```
  
## 2.idea
   使用 cmd 进入源码目录   
   ```gradlew.bat idea```   
   或直接导入build.gradle
   
## 3.说明：第一次使用gradlew.bat 会下载 gradle-2.4-bin.zip 将会下载到当前用户根目录.gradle下


## 4.打包发布

   **windows**:   
   ```gradlew.bat clean release```
   
   **linux**:   
   ```gradlew clean release```
   
   命令执行完后会生成build文件夹   
   build  
   |     --- classes  
   |     --- dependency-cache  
   |     --- dist  
   |     --- --- bin  
   |     --- --- --- datamonitor.sh  
   |     --- --- --- start.sh  
   |     --- --- --- stop.sh  
   |     --- --- conf  
   |     --- --- lib  
   |     --- distributions  
   |     --- --- datamonitor.zip   
   
  
   取dist 文件夹所有内容进行发布 或者使用datamonitor.zip
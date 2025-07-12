# spring-algebric-equation-masterclass

## A Spring Boot application to execute Algebraic Equations

### Software Required
* [Java 17](https://www.openlogic.com/openjdk-downloads?page=0)
* [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows) or [Spring tool Suite](https://spring.io/tools) or [Eclipse](https://www.eclipse.org/downloads/packages/)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Git Bash](https://git-scm.com/downloads)
* [Postman](https://www.postman.com/downloads/)

### Steps to clone and run the application
* Install Java 17.
* Open Git Bash or even you can open Command Prompt (if you are using Windows) or Terminal (if you are using MAC) in your machine
* Clone the application from github.com as   
  <code>git clone https://github.com/c86amik/spring-algebric-equation-masterclass.git</code>
* Open <strong>IntelliJ</strong> and import the application as <strong>Maven</strong> project
* After the application got successfully imported in <strong>IntelliJ</strong>
* Right Click on the application, select the <strong>Run As</strong> option, and then select <strong>Spring Boot App</strong>
* The application will start in the port <strong>7126</strong>
* Open the Postman and test the REST Endpoints

### Testing using Postman
<ol>
<li><strong>Store an Algebraic Equation</strong> - <strong>POST API</strong> - http://localhost:7126/api/equations/store</li>
<li><strong>Retrieve Stored Equations</strong> - <strong>GET API</strong> - http://localhost:7126/api/equations</li>
<li><strong>Evaluate an Equation</strong> - <strong>POST API</strong> - http://localhost:7126/api/equations/{equationId}}/evaluate</li>
</ol>

#### Dummy JSON object
* Body for the <strong>POST</strong> method for Store an Algebraic Equation    
  <code>{
  "equation": "x^2 + y^2 - 4"
  }</code>
* Body for the <strong>POST</strong> method for Evaluate an Equation    
    <code>{
  "variables": {
  "x": 2,
  "y": 3,
  "z": 1
  }
  }</code>
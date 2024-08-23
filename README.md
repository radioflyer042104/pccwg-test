<h1>This is a multi-module Maven project with the following modules:</h1>

1. [Rest API](./pccwg-test-api/api-docs.yaml) <br>
1.1. Paths <br>
  1.1.1.  __/api/register__  - Adds a new user to the H2 database then publishes a new event to a Kafka topic <br>
  1.1.2.  __/api/list__  - Lists all users <br>
  1.1.3.  __/api/update__  - Updates users <br>
  1.1.4.  __/api/delete__  - Marks users as deleted <br>
2. Topic Initializer - Initializes a Kafka Topic to decouple user management and email sending <br>
3. Event Consumer - Consumes events from the Kafka Topic then sends welcome emails to new users <br>
4. Web UI - Simple interface for the Rest API

<h1>How to Build and Run</h1>
<h3>Configuration</h3>

1. Update the following properties in [Event Consumer properties file](./pccwg-test-consumer/src/main/resources/application.properties) to be able to send emails.  This application was tested using Gmail's smtp server.

`spring.mail.host` <br>
`spring.mail.port` <br>
`spring.mail.username` <br>
`spring.mail.password` <br>

<h3>Building and Running</h3>
<h4>Pre-requisites</h4>
<ol>
<li> Java 17 </li>
<li> Docker and Docker Compose CLI</li>
</ol>

<h4>Linux</h4>

1. Update path to JAVA_HOME in [build.sh](build.sh)
2. Open terminal then run ./build.sh
3. NOTE: I included the script that I used during testing to cleanup [shutdown.sh](./shutdown.sh)

<h4>Windows</h4>

1. Update path to JAVA_HOME in [build.bat](build.bat)
2. Open cmd or Powershell then run build.bat

The following network and containers should be created and started: <br>

`✔ Network pccwg-test_pccwg-test-network  Created` <br>
`✔ Container zookeeper-test               Started` <br>
`✔ Container kafka-test                   Started` <br>
`✔ Container init-topic                   Started` <br>
`✔ Container kafka-consumer               Started` <br>
`✔ Container pccwg-test-api               Started` <br>
`✔ Container pccwg-test-web               Started` <br>
    
<h1>Using the Rest API</h1>

The OpenAPI docs can be found [here](./pccwg-test-api/api-docs.yaml). This can also be viewed in the pccwg-test-api container via the path /api-docs or /swagger-ui/index.html.  The Rest API container exposes port **9090**.

<h3>Sample requests</h3>

1.1.1.  __/api/register__ <br>
`method: POST` <br>
`content: application/json` <br>
`body: ` <br>
`{` <br>
`    "id": null,` <br>
`    "name": "Audric Olivar",` <br>
`    "email": "audric.olivar@yahoo.com",` <br>
`    "password": "secret",` <br>
`    "deleted": false` <br>
`}` <br>

  1.1.2.  __/api/list__  - Lists all users <br>
`method: GET` <br>  

  1.1.3.  __/api/update__  - Updates users <br>
`method: PUT` <br>
`content: application/json` <br>
`body: ` <br>
`{` <br>
`    "id": 1,` <br>
`    "name": "Audric C. Olivar",` <br>
`    "email": "audric.olivar@hotmail.com",` <br>
`    "password": "super secret",` <br>
`    "deleted": false` <br>
`}` <br>
  
  1.1.4.  __/api/delete__  - Marks users as deleted <br>
`method: DELETE` <br>
`content: application/json` <br>
`body: ` <br>
`[1,2]` <br>

<h1>Using the Web UI</h1>

The Web UI can be accessed via localhost:8080.  The landing page is the list of users which will be blank at the start. <br>
1. Click on the  __Register__  button to register a new user
2. Check a checkbox then click on the  __Update__  button to update an existing user
3. Check one or more checkboxes then click on the  __Delete__  button to (soft) delete user/s
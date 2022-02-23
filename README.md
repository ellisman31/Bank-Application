# Bank-Application

## Story

This application is a demo for a company who asked for a bank application backend.
###### The basic requirements were:
  - **Opening accounts(creating new accounts)**
  - **Depositing amount to account** and **Withdraw amount from account**
  - **Transferring amount between accounts** and **requesting the current balance of account**
  - **Traceability** and **historical data**, for instance but it was not really a requirement

## Technical details

The backend was made with **_Java_** and **_Spring Boot_**. **_H2_** Database was used in the application with **_JPA_** design pattern to handle/request data.
Creating frontend **_was not a requirement_** but in the future I would like to deal with creating one with **_React_** but the process is currently **_suspended_**.

## Starting the application

This is a **_maven_** project, so first thing to do is installing the **_pom.xml_** **dependencies** to run this application.

###### 1. To start the application run:
  > ``` (Type this command in the terminal in the root directory of the project.) ```
  > 
    mvn install

## Request data from API calls

You can use any API platform for **building, using and testing** out APIs.

You can find all the **_API calls_** in the **_Controller_** folder.

> I would recommend to use **_Postman_** (Website for Postman: https://www.postman.com/)

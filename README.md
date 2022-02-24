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

There are two users available to test out the functions. The users can be find on ```localhost:8080/h2```.
**The configuration for H2** is available in the **_application.properties at the resource folder_**.  

## Login

After successful login you will receive an **Access Token**. With the access token you are able to check your own balance, your own information, do transactions(deposit and withdraw) and transfer money through email adress (like in Paypal).

  - **To use your access token you have to provide it e.g. in Postman at the Authorization panel and choose Bearer token and paste your access token. (The token will expire so be sure if the current access token is alive)** 
 
![image](https://user-images.githubusercontent.com/73363394/155432800-4d6621c2-72a0-4c20-bd22-869d02f032b9.png)

  - **Important note for transaction:**
    - **If you would like to do transaction be sure the transactionType is all capital!**


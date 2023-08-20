# Business Trip Reimbursement Calculation Application

This project aims to create a Java application to manage the reimbursement of expenses for a user's business trip.

![Alt Text](https://raw.githubusercontent.com/kszczep5/reimbursement-app/main/preview.png?raw=true)

## Project Setup and Execution

### Backend (Java)

1. Clone the GitHub repository: 
```sh
git clone https://github.com/kszczep5/reimbursement-app.git
```
2. Navigate to the backend directory:
```sh
cd reimbursement-app-backend
```
3. Build the application:
```sh
mvn clean package
```
4. Run the application: 
```sh
mvn exec:java
```

### Frontend (React)

1. Navigate to the frontend directory: 
```sh
cd reimbursement-app-frontend
```
2. Install dependencies:
```sh
npm install
```
3. Start the frontend: 
```sh
npm start
```
4. You can also create a production build: 
```sh
npm run build
```
5. The build folder is ready to be deployed. You may serve it with a static server: 
```sh
npm install -g serve
serve -s build
```

## Using the App

To use the Business Trip Reimbursement Calculation Application, follow the steps below. Make sure you have both the backend and frontend components up and running.

### Accessing the App

Once both the backend and frontend are running, you can access the application using your web browser. Open your browser and go to the following address:

```
http://localhost:3000
```

### Login Credentials

To access different views of the application, use the following login credentials:

**Admin Dashboard:**
- Username: admin
- Password: admin

**User Dashboard:**
- Username: user
- Password: user


## Running Tests

### Backend Tests

To ensure the reliability and accuracy of the backend logic, a suite of unit tests has been implemented. These tests cover various scenarios and use cases to validate the functionality of the application's backend components. The testing framework utilized is JUnit, along with the `mockito-testng` library for creating mock objects.

To run the backend tests, open a terminal and navigate to the "backend" directory of the project:

```sh
cd reimbursement-app-backend
```

Then, execute the following command to initiate the tests:

```sh
mvn test
```

This will trigger the execution of all the defined unit tests. The test results, including passed and failed tests, will be displayed in the terminal output.

### Frontend Tests

For the frontend, a suite of tests has been created to verify the behavior of the user interface components and interactions. These tests ensure that the frontend elements function as expected and provide a smooth user experience. The chosen frontend framework is React.

To run the frontend tests, open a terminal and navigate to the "frontend" directory of the project:

```sh
cd reimbursement-app-frontend
```

Execute the following command to start the tests:

```sh
npm test
```

This command will launch the frontend test suite and display the results in the terminal. You'll be able to see which tests have passed and any potential failures or issues.


## Technologies Used in App Development

During the development of the reimbursement calculation application, a combination of backend and frontend technologies were employed to create a seamless and functional user experience. Here's an overview of the technologies used in both the backend and frontend components:

### Backend:

- **Apache Maven 3.9.4:** Maven was used as the build automation and project management tool. It simplified the process of managing dependencies, compiling code, and packaging the application.

- **Java 11.0.20:** The core programming language used for the backend development. Java 11 provided a stable and mature foundation for implementing the application's business logic.

- **JUnit 5.10.0:** JUnit was chosen as the testing framework for creating unit tests. It facilitated the creation of comprehensive test suites to ensure the correctness of the application's logic.

- **mockito-testng 0.5.0:** The `mockito-testng` library was utilized for creating mock objects and simulating behavior during testing. It enhanced the testing process by isolating components and enabling thorough testing scenarios.

- **Jackson ObjectMapper:** Jackson's ObjectMapper was employed for handling JSON serialization and deserialization. This was particularly useful for converting data between the frontend and backend components.

### Frontend:

- **React 18.2.0:** The frontend was developed using React, a popular JavaScript library for building user interfaces. React's component-based architecture allowed for the creation of modular and reusable UI elements.

- **npm 9.8.0:** npm (Node Package Manager) was used to manage and install frontend dependencies. It streamlined the process of including external libraries and tools required for frontend development.
Sure, here's the updated section with the addition of Node.js:

- **Node.js 20.4.0:** Node.js is the runtime environment used for executing JavaScript code on the server-side. It provided the necessary runtime for building and running the React frontend application.



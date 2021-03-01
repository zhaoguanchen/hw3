# JavaFX Project


## How to run
### 1. Set JDK 11
Go to File -> Project Structure -> Project, and set the project SDK to 11. You can also set the language level to 11.

### 2. Create a library
Go to File -> Project Structure -> Libraries and add the libs/ as a library to the project.



### 3. Add VM options

Go to Preferences (File -> Settings) -> Appearance & Behavior -> Path Variables, and define the name of the variable as PATH_TO_FX, and browse to the lib folder of the libs/ to set its value, and click apply. 


click on Run -> Edit Configurations... and add these VM options:

```
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml
```

### 3. Run the project

Click Run -> Run... to run the project, now it should work fine.  
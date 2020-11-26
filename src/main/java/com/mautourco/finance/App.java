package com.mautourco.finance;

import java.io.IOException;
import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;

	@Override
	public void start(Stage primaryStage) throws IOException {

		Locale.setDefault(Locale.ENGLISH);

		primaryStage.setScene(new Scene(loadFXML("FinanceModule"), 1370, 700));
		primaryStage.setTitle("Finance Module");
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("icons/mautourco2.jpg")));
		primaryStage.show();
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {

		launch();

	}

}
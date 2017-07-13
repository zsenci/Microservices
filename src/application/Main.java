package application;
	
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	private Label label = new Label();
	private Button but = new Button();
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GridPane grid = new GridPane();			
			label.setText("Hello".toString());
			label.setStyle("-fx-padding: -100;");	
			but.setStyle("-fx-padding: 18;");
			grid.setStyle("-fx-padding: 150;");			
			but.setText("Hello".toString());
			grid.add(but,1,1);
			grid.add(label, 1, 3);
			root.getChildren().add(grid);
			primaryStage.setScene(scene);
			primaryStage.show();
			but.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				    new EventHandler<MouseEvent>() {
				        @Override public void handle(MouseEvent e) {
				        	labelchangetext();
				           
				        }
				});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void labelchangetext(){
		 
		 String domain = "https://www.google.ro/";
		 String appId = "1485083531552689";
		 
		 String url2 = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain+"&scope=user_birthday, user_religion_politics, user_relationships, user_relationship_details, user_hometown, user_location, user_likes, user_education_history, user_work_history, user_website, user_events, user_photos, user_videos, user_friends, user_about_me, user_status, user_games_activity, user_tagged_places, user_posts, email, publish_actions, read_custom_friendlists, user_actions.books, user_actions.music, user_actions.video, user_actions.news, user_actions.fitness, public_profile";
		 System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get(url2);
		 String accessToken;
		 Version version = Version.LATEST;
		 while(true){
			 if(!driver.getCurrentUrl().contains("facebook.com")){
				 String url = driver.getCurrentUrl();
				 accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");				 
				 FacebookClient client = new DefaultFacebookClient(accessToken, version);		 
				 User me = client.fetchObject("me", User.class);
				 String message = "";
				 message = message+me.getName() + " -> " + me.getId();
				 label.setText(message);
				 driver.quit();
				 break;
			 }
		 }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

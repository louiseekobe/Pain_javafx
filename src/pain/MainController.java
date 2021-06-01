package pain;

import java.awt.image.BufferedImage;
import java.io.File;
//pain.MainController
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private Button fermer;
	@FXML
	private StackPane pane;
	@FXML
	private Canvas canvas;
	@FXML
	private ColorPicker color1, color2,color21, color3;
	@FXML
	private JFXSlider brushSize, opacity,widthStroke, opacityImage, opacityText;
	@FXML
	private TextField textf, sizet, px, py;
	@FXML
	private JFXComboBox  <String> combox1;
	@FXML
	private JFXComboBox  <String> JFpolice;


	public GraphicsContext graphic;
	public double x = 0, y = 0, width = 0, heigth = 0, rayon = 0;
	int choice = 0;
	public String s1 = " ", textPolice;

	
	
	
/**
 * PERMET DE FERMER L'APPLIQUATION VIA LE BUTTON DE FERMETURE
 * @param e
 */
	public void fermer(ActionEvent e) {
		Stage stage = (Stage) fermer.getScene().getWindow();
		stage.close();
	}

	
/**
 * METHODE QUI PERMET D'ENREGISTER LES FICHIERS SOUS EXTENSION png et jpg
 */
	public void onsave() {
		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("PNG files (*.jpg)", "*.jpg");
			fileChooser.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);
			Image snapshot = canvas.snapshot(null, null);
			File file = fileChooser.showSaveDialog(pane.getScene().getWindow() );
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
		} catch (Exception e) {
			
		}
	}
	
	
	
	/*
	 * METHODE QUI CHANGE LA COULEUR DU CANVAS
	 */
	public void canvasColor() {
		Color value = color1.getValue();
		pane.setStyle("-fx-background-color: " + value.toString().replace("0x", "#"));
	}

	
	
	/**
	 * NETTOYER LE CANVAS
	 */
	public void clearCanvas() {
		graphic = canvas.getGraphicsContext2D();
		graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

	}
	
	
	
/**
 * PERMET D'ECRIRE DU TEXTE DANS LE CANVAS 
 * EN DEFINISSANT LA COULEUR ET LE TYPE DE POLICE DES UTILISATEURS
 * @param n
 */
	public void ecrireText(int n) {
		graphic = canvas.getGraphicsContext2D();
			canvas.setOnMouseClicked(e -> {
				if(n == 1) {
					try {
						double sizeT = Double.parseDouble(sizet.getText());
						//gestion de l'opacité
						graphic.setGlobalAlpha((opacityText.getValue())/100);
						x = e.getX();
						y = e.getY();
						textPolice = chosePolice();
						graphic.setFill(color3.getValue());
						graphic.setFont(Font.font(textPolice, sizeT));
						graphic.fillText(textf.getText(), x, y);	
					}catch (Exception ex) {
					}	
				}
			});
	}

	
	
	
/**
 * METHODE QUI VA GERER LES FORMES DE NOTRE PAINT APP
 * AINSI ON A UNE LIGNE UN RECTANGLE UN CARRE 
 * UN CERCLE ET LE TRAIN CONTINUE DU CANVAS
 * @param n
 */
//

	public void drawsharpe(int n) {
		graphic = canvas.getGraphicsContext2D();
		canvas.setOnMousePressed(e ->{
			//couleur de remplissage
			graphic.setFill(color2.getValue());
			//couleur de contour
			graphic.setStroke(color21.getValue());
			//taille de du trait
			graphic.setLineWidth(widthStroke.getValue()/5);
			//gestion de l'opacité
			graphic.setGlobalAlpha((opacity.getValue())/100);
			x = e.getX();
			y = e.getY();
			rayon = 0;
			if(n == 3) {
				width = e.getX();
				heigth = e.getY();
			}
			//Pour les rectangles et les carrées pleins avec contour
			 if(n == 4 || n == 5) {
				width = 0;
				heigth = 0;
				graphic.fillRect(x, y, width, heigth);
				graphic.strokeRect(x, y, width, heigth);
			}
			//Pour les rectangles et carrées pleins sans coutours
			 if(n == 8 || n == 10) {
					width = 0;
					heigth = 0;
					graphic.fillRect(x, y, width, heigth);
					graphic.strokeRect(x, y, width, heigth);
				}
			 //Pour les rectangles et carrées creux avec contours
			 if(n == 7 || n == 9) {
					width = 0;
					heigth = 0;
					graphic.fillRect(x, y, width, heigth);
					graphic.strokeRect(x, y, width, heigth);
				}
	
		});
		canvas.setOnMouseDragged(e->{
			rayon = Math.abs(x-e.getX());
			if(n == 2) {
				double size = brushSize.getValue();
				x = e.getX() - size / 2 ;
				y = e.getY() - size / 2 ;
				graphic.setFill(color2.getValue());
				graphic.fillRect(x, y, size, size);
				
			}
			if(n == 13) {
				x = e.getX();
				y = e.getY();
				graphic.setLineWidth(2);
				graphic.setStroke(color21.getValue());
				graphic.strokeLine(x, y, x, y);
			}
			if((n >= 3 && n <= 5) || (n >= 7 && n <= 10)) {
				width = e.getX();
				heigth = e.getY();
			}
			/*if(n == 6) {
				rayon = Math.abs(x-e.getX());
			}*/
		
		});
		canvas.setOnMouseReleased(e->{
			width = e.getX();
			heigth = e.getY();
			if(n == 3) {
				graphic.strokeLine(x, y, width, heigth);
			}
			else if(n == 4) {
				if(width >= x && heigth >= y) {
					graphic.fillRect(x, y, width-x, heigth-y);
					graphic.strokeRect(x, y, width-x, heigth-y);
				}else if(width >= x && heigth <= y) {
					graphic.fillRect(x, heigth, x-width, y-heigth);
					graphic.strokeRect(x, heigth, x-width, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.fillRect(width, y, x-width, heigth-y);
					graphic.strokeRect(width, y, x-width, heigth-y);
				}else if(width <= x && heigth <= y) {
					graphic.fillRect(width, heigth, x-width, y-heigth);
					graphic.strokeRect(width, heigth, x-width, y-heigth);
				}else{
					graphic.fillRect(x, y, width-x, heigth-y);
					graphic.strokeRect(x, y, width-x, heigth-y);
				}
			}
			else if(n == 7) {
				if(width >= x && heigth >= y) {
					graphic.strokeRect(x, y, width-x, heigth-y);
				}else if(width >= x && heigth <= y) {
					graphic.strokeRect(x, heigth, x-width, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.strokeRect(width, y, x-width, heigth-y);
				}else if(width <= x && heigth <= y) {
					graphic.strokeRect(width, heigth, x-width, y-heigth);
				}else{
					graphic.strokeRect(x, y, width-x, heigth-y);
				}
			}
			else if(n == 8) {
				if(width >= x && heigth >= y) {
					graphic.fillRect(x, y, width-x, heigth-y);
				}else if(width >= x && heigth <= y) {
					graphic.fillRect(x, heigth, x-width, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.fillRect(width, y, x-width, heigth-y);
				}else if(width <= x && heigth <= y) {
					graphic.fillRect(width, heigth, x-width, y-heigth);
				}else{
					graphic.fillRect(x, y, width-x, heigth-y);
				}
			}
			else if ( n == 5) {
				if(width >= x && heigth >= y) {
					graphic.fillRect(x, y, width-x,width-x);
					graphic.strokeRect(x, y, width-x,width-x);
				}else if(width >= x && heigth <= y) {
					graphic.fillRect(x, heigth, y-heigth, y-heigth);
					graphic.strokeRect(x, heigth, y-heigth, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.fillRect(width, y, x-width, x-width);
					graphic.strokeRect(width, y, x-width, x-width);
				}else if(width <= x && heigth <= y) {
					graphic.fillRect(width, heigth, x-width,x-width);
					graphic.strokeRect(width, heigth, x-width,x-width);
				}else{
					graphic.fillRect(x, y,heigth-x, heigth-y);
					graphic.strokeRect(x, y,heigth-x, heigth-y);
				}
			}
			else if ( n == 9) {
				if(width >= x && heigth >= y) {
					graphic.strokeRect(x, y, width-x,width-x);
				}else if(width >= x && heigth <= y) {
					graphic.strokeRect(x, heigth, y-heigth, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.strokeRect(width, y, x-width, x-width);
				}else if(width <= x && heigth <= y) {
					graphic.strokeRect(width, heigth, x-width,x-width);
				}else{
					graphic.strokeRect(x, y,heigth-x, heigth-y);
				}
			}
			else if ( n == 10) {
				if(width >= x && heigth >= y) {
					graphic.fillRect(x, y, width-x,width-x);
				}else if(width >= x && heigth <= y) {
					graphic.fillRect(x, heigth, y-heigth, y-heigth);
				}else if(width <= x && heigth >= y) {
					graphic.fillRect(width, y, x-width, x-width);
				}else if(width <= x && heigth <= y) {
					graphic.fillRect(width, heigth, x-width,x-width);
				}else{
					graphic.fillRect(x, y,heigth-x, heigth-y);
				}
			}
			else if (n == 6) {
				rayon = Math.abs(x-e.getX());
				graphic.strokeOval(x, y, rayon, rayon);
				graphic.fillOval(x, y, rayon, rayon);
			}
			else if (n == 11) {
				rayon = Math.abs(x-e.getX());
				graphic.strokeOval(x, y, rayon, rayon);
			}
			else if (n == 12) {
				rayon = Math.abs(x-e.getX());
				graphic.fillOval(x, y, rayon, rayon);
			}
		});
		
	}

	
	
	
/**
 * PERMET DE DEFINIR LES ITEMS PRESENTS DANS NOS COMBOXBOX
 */
	public void addItems() {
		combox1.getItems (). add ("NONE");	
		combox1.getItems (). add ("DRAW TEXT");
		combox1.getItems (). add ("DRAW ON  CANVAS");
		combox1.getItems().add("DRAW WITH PENCIL");
		combox1.getItems (). add ("DRAW LINE");
		combox1.getItems (). add ("DRAW RECTANGLE");
		combox1.getItems (). add ("DRAW CARRE");
		combox1.getItems (). add ("DRAW CIRCLE");	
		combox1.getItems (). add ("DRAW STROKE RECTANGLE");
		combox1.getItems (). add ("DRAW FILL RECTANGLE");	
		combox1.getItems (). add ("DRAW STROKE CARRE");
		combox1.getItems (). add ("DRAW FILL CARRE");	
		combox1.getItems (). add ("DRAW STROKE CERCLE");
		combox1.getItems (). add ("DRAW FILL CERCLE");	
		
		JFpolice.getItems().add("Arial");
		JFpolice.getItems().add("Arial Narrow");
		JFpolice.getItems().add("Corbel Light");
		JFpolice.getItems().add("Dubai");
		JFpolice.getItems().add("Gigi");
		JFpolice.getItems().add("Harrington");
		JFpolice.getItems().add("Oswald");
		JFpolice.getItems().add("Papyrus");
		JFpolice.getItems().add("Times New Roman");
		JFpolice.getItems().add("Verdana");
		
		
	}

	
	
	
/**
 * PERMET A FONCTION DE L'ITEM SELECTIONNER 
 * RETOUR L'ENTIER CORRESPONDANT
 * @return un entier
 */
	public int  selectItem() {
				int n = 0;
				s1 = combox1.getValue();
				if(s1 != null) {
					if(s1.equals("DRAW TEXT")) {
						n =  1;
					}else if(s1.equals("DRAW ON  CANVAS")){
						n = 2;
					}else if(s1.equals("DRAW LINE")) {
						n = 3;
					}else if(s1.equals("DRAW RECTANGLE")) {
						n = 4;
					}else if(s1.equals("DRAW CARRE")) {
						n = 5;
					}else if(s1.equals("DRAW CIRCLE")) {
						n = 6;
					}else if(s1.equals("DRAW STROKE RECTANGLE")) {
						n = 7;
					}else if(s1.equals("DRAW FILL RECTANGLE")) {
						n = 8;
					}else if(s1.equals("DRAW STROKE CARRE")) {
						n = 9;
					}else if(s1.equals("DRAW FILL CARRE")) {
						n = 10;
					}else if(s1.equals("DRAW STROKE CERCLE")) {
						n = 11;
					}else if(s1.equals("DRAW FILL CERCLE")) {
						n = 12;	
					}else if(s1.equals("DRAW WITH PENCIL")) {
						n = 13;
					}else if(s1.equals("NONE")) {
						n = 0;
					}
				}else {
					n = 0;
				}
				return n;
	}
	
	
	/**
	 * METHODE POUR DEFINIR LA POLICE DU TEXT
	 * @return une chaine de caractere
	 */
	
	public String chosePolice() {
		if(JFpolice.getValue().isEmpty()) {
			return "Calibri";		
		}else {
			return JFpolice.getValue();
		}
		
	}
	
	
	
	/**
	 * PERMET EN FONCTION DE L'ENTIER RETOURNER PAR 
	 * LA METHODE SELECT ITEM DE DEFINIR L'ACTION QUI SERA MENEE 
	 * DANS LE CANVAS
	 */
	public void draw() {
		choice = selectItem();
		ecrireText(choice);
		drawsharpe(choice);
		
	}
	
	
	
	
/**
 * METHODE POUR CHARGER UNE IMAGE DANS LE CANVAS
 * ON VA DEFINIR SA POSITION EN FONCTION D'UN POINT
 * DONC ON VA DEFINIR L'ABCISSE ET L'ORDONNEE
 * SINON LA VALEUR PAR DEFAUT EST ZERO
 */
	public void chargerImage() {
		double tx = 0, ty = 0;
		 GraphicsContext graphic = canvas.getGraphicsContext2D();
		//gestion de l'opacité
			graphic.setGlobalAlpha((opacityImage.getValue())/100);
			 FileChooser fileChooser = new FileChooser();
	            //Set extension filter
	            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG); 
	            //Show open file dialog
	            File file = fileChooser.showOpenDialog(null);
	            try {
	            	tx = Double.parseDouble(px.getText());
	            	ty = Double.parseDouble(py.getText());
	            }catch (Exception e) {
					tx = 0;
					ty = 0;
				}
	                   
	            try {
	                BufferedImage bufferedImage = ImageIO.read(file);
	                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
	                graphic.drawImage(image, tx, ty);
	            } catch (Exception ex) {}

	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addItems();
		
	}

}

module pain {
	requires  transitive javafx.graphics; //rendre visible le package javafx.graphic
	requires javafx.controls;//rendre visible le package javafx.controls
	requires javafx.base;//rendre visible le package javafx.base
	requires javafx.fxml;//rendre visible le package javafx.fxml
	requires com.jfoenix;//rendre visible le package com.jfoenix
	requires java.desktop;//rendre visible le package javafx.desktop
	requires javafx.swing;//rendre visible le package javafx.swing
	exports pain;//rendre visible le package pain pour les bibliothèque externe
	opens pain to javafx.fxml;//rendre visible le package pain visible pour le package javafx.fxml
}
module com.mautourco.finance {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;

	opens com.mautourco.finance to javafx.fxml;
	opens com.mautourco.finance.controller to javafx.fxml;
	opens com.mautourco.finance.model to javafx.base;

	exports com.mautourco.finance;
}
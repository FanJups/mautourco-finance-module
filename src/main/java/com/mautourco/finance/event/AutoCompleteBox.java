package com.mautourco.finance.event;

import java.util.Optional;

import com.mautourco.finance.model.ComboBoxItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class AutoCompleteBox implements EventHandler<Event> {
	private ComboBox<ComboBoxItem> comboBox;
	private final ObservableList<ComboBoxItem> data;
	private Integer sid;
	private final StringConverter<ComboBoxItem> filterCriteriaStringConverter = new StringConverter<ComboBoxItem>() {

		@Override
		public String toString(ComboBoxItem filterCriteria) {
			if (filterCriteria == null) {
				return "";
			} else {
				return filterCriteria.getName();
			}
		}

		@Override
		public ComboBoxItem fromString(String string) {
			Optional<ComboBoxItem> optionalFilterCriteria = getComboBox().getItems().stream()
					.filter(filterCriteria -> filterCriteria.getName().contains(string)).findFirst();
			return optionalFilterCriteria.orElseGet(() -> getComboBox().getItems().get(0));
		}
	};

	public AutoCompleteBox(final ComboBox<ComboBoxItem> comboBox) {
		this.comboBox = comboBox;
		this.data = comboBox.getItems();

		this.comboBox.setConverter(filterCriteriaStringConverter);

		displayNameOfTheCustomObjectComboBoxItem(this.comboBox);
		doAutoCompleteBox();

	}

	private void doAutoCompleteBox() {
		this.comboBox.setEditable(true);
		this.comboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {// mean onfocus
				this.comboBox.show();
			}
		});

		this.comboBox.getEditor().setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				if (event.getClickCount() == 2) {
					return;
				}
			}
			this.comboBox.show();
		});

		this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			moveCaret(this.comboBox.getEditor().getText().length());
		});

		this.comboBox.setOnKeyPressed(t -> comboBox.hide());

		this.comboBox.setOnKeyReleased(AutoCompleteBox.this);

		if (this.sid != null)
			this.comboBox.getSelectionModel().select(this.sid);
	}

	@Override
	public void handle(Event event) {

		KeyEvent keyEvent = (KeyEvent) event;
		if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN
				|| keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT
				|| keyEvent.getCode() == KeyCode.HOME || keyEvent.getCode() == KeyCode.END
				|| keyEvent.getCode() == KeyCode.TAB) {
			return;
		}

		if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
			String str = this.comboBox.getEditor().getText();
			if (str != null && str.length() > 0) {
				str = str.substring(0, str.length() - 1);
			}
			if (str != null) {

				this.comboBox.getEditor().setText(str);

				moveCaret(str.length());
			}
			this.comboBox.getSelectionModel().clearSelection();
		}

		if (keyEvent.getCode() == KeyCode.ENTER && comboBox.getSelectionModel().getSelectedIndex() > -1)
			return;

		setItems();
	}

	private void setItems() {
		ObservableList<ComboBoxItem> list = FXCollections.observableArrayList();

		for (ComboBoxItem datum : this.data) {
			String s = this.comboBox.getEditor().getText().toLowerCase();
			if (datum.getName().toLowerCase().contains(s)) {
				list.add(datum);
			}
		}

		if (list.isEmpty())
			this.comboBox.hide();

		this.comboBox.setItems(list);
		this.comboBox.show();
	}

	private void moveCaret(int textLength) {
		this.comboBox.getEditor().positionCaret(textLength);
	}

	private void displayNameOfTheCustomObjectComboBoxItem(ComboBox<ComboBoxItem> c) {

		// use custom object in ComboBox javafx
		// Display an attribute of the custom object instead of toString
		Callback<ListView<ComboBoxItem>, ListCell<ComboBoxItem>> cellFactory = new Callback<ListView<ComboBoxItem>, ListCell<ComboBoxItem>>() {

			@Override
			public ListCell<ComboBoxItem> call(ListView<ComboBoxItem> l) {
				return new ListCell<ComboBoxItem>() {

					@Override
					protected void updateItem(ComboBoxItem item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getName());
						}
					}
				};
			}
		};

		// Just set the button cell here:
		c.setButtonCell(cellFactory.call(null));
		c.setCellFactory(cellFactory);

	}

	private ComboBox<ComboBoxItem> getComboBox() {
		return comboBox;
	}

}

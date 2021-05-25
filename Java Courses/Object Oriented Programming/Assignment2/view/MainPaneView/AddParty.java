package view.MainPaneView;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.ViewGUI;

public class AddParty extends MainPane {
    private HBox hbName, hbSection, hbCreateDate;
    private VBox vbSection;
    private TextField tfName;
    private int currentYear;
    private ComboBox day, month, year;
    private RadioButton rightSection, leftSection, centerSection;
    private ToggleGroup tg;

    public AddParty(int year) {
        super();
        setHeadline("Add Party");
        currentYear = year;
        setFields();
    }

    private void setFields() {
        setNameField();
        setCreateDateField();
        setSectionField();
    }

    private void setNameField() {
        hbName = new HBox();
        tfName = new TextField();
        tfName.setPromptText("Enter Party Name");
        tfName.setFont(buttonsFont);
        tfName.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        hbName.getChildren().addAll(tfName);
    }

    private void setCreateDateField() {
        hbCreateDate = new HBox();
        Text txtCreateDate = new Text("Create Date :");
        txtCreateDate.setFont(buttonsFont);

        day = new ComboBox();
        month = new ComboBox();
        year = new ComboBox();

        // Day
        day.setPromptText("Day");
        for (int i = 1; i <= 31; i++)
            day.getItems().add(i);

        // Month
        month.setPromptText("Month");
        for (int i = 1; i <= 12; i++)
            month.getItems().add(i);

        // Year
        year.setPromptText("Year");
        for (int i = 1900; i <= currentYear; i++)
            year.getItems().add(i);

        hbCreateDate.getChildren().addAll(txtCreateDate, day, month, year);
        hbCreateDate.setSpacing(ViewGUI.SPACING);
    }

    private void setSectionField() {
        vbSection = new VBox();
        hbSection = new HBox();
        tg = new ToggleGroup();
        Text sectionTxt = new Text("Section :");
        sectionTxt.setFont(buttonsFont);

        rightSection = new RadioButton("Right");
        rightSection.setFont(buttonsFont);
        rightSection.setToggleGroup(tg);

        centerSection = new RadioButton("Center");
        centerSection.setFont(buttonsFont);
        centerSection.setToggleGroup(tg);

        leftSection = new RadioButton("Left");
        leftSection.setFont(buttonsFont);
        leftSection.setToggleGroup(tg);

        vbSection.getChildren().addAll(rightSection, centerSection, leftSection);
        vbSection.setMargin(centerSection, new Insets(5, 0, 0, 0));
        vbSection.setMargin(leftSection, new Insets(5, 0, 0, 0));

        hbSection.getChildren().addAll(sectionTxt, vbSection);
        hbSection.setSpacing(15);
    }

    public String getPartyName() {
        return tfName.getText();
    }

    public String getDay() {
        return day.getSelectionModel().getSelectedItem().toString();
    }

    public String getMonth() {
        return month.getSelectionModel().getSelectedItem().toString();
    }

    public String getYear() {
        return year.getSelectionModel().getSelectedItem().toString();
    }

    public String getSection() {
        String section = null;
        if (rightSection.isSelected())
            section = rightSection.getText();
        else if (leftSection.isSelected())
            section = leftSection.getText();
        else if (centerSection.isSelected())
            section = centerSection.getText();

        return section;
    }

    @Override
    public VBox update() {
        super.update();
        mainView.getChildren().addAll(hbName, hbCreateDate, hbSection, hbSubmit);
        mainView.setMargin(hbName, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbCreateDate, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSection, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSubmit, ViewGUI.DEFAULT_INSETS);

        return mainView;
    }
}

package view.showMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Party;
import view.ViewGUI;

import java.util.HashMap;
import java.util.Optional;

public class ShowResults extends Show {
    private PieChart chart;
    private HashMap<Party, Integer> allParties;

    public ShowResults(HashMap<Party, Integer> parties) {
        super();
        allParties = parties;
        setHeadline("Show Vote Results");
        chartResults();
    }

    public void chartResults() {
        ObservableList<PieChart.Data> pieChartResults = FXCollections.observableArrayList();
        for(Party party: allParties.keySet()) {
            pieChartResults.add(new PieChart.Data(party.getName(), allParties.get(party)));
        }

        chart = new PieChart(pieChartResults) {
            @Override
            protected void layoutChartChildren(double top, double left, double cw, double ch) {
                if (getLabelsVisible()) {
                    getData().forEach(d -> {
                        Optional<Node> opTextNode = chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
                        if (opTextNode.isPresent()) {
                            ((Text) opTextNode.get()).setText(d.getName() + " " + (int)(d.getPieValue()));
                        }
                    });
                }
                super.layoutChartChildren(top, left, cw, ch);
            }
        };
        chart.setTitle("Election Votes");

        scrollPaneVB.getChildren().add(chart);
        scrollPaneVB.setMargin(chart, ViewGUI.DEFAULT_INSETS);
    }

    @Override
    public VBox update() {
        super.update();
        assignScrollPane();

        return mainView;
    }
}

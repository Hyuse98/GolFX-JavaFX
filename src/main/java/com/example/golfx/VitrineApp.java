package com.example.golfx;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VitrineApp extends Application {

    private AnchorPane pane;
    private TextField txPesquisa;
    private TableView<ItensProperty> tbVitrine;
    private TableColumn<ItensProperty, String> columnProduto;
    private TableColumn<ItensProperty, Double> columnPreco;
    private static ObservableList<ItensProperty> listItens = FXCollections.observableArrayList();
    private static Carrinho carrinho;

    @Override
    public void start(Stage stage) throws Exception {
        initComponets();
        initListeners();
        initItems();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("CUZIN");
        stage.show();
        initLayout();
    }

    public static void main(String[] args) {
        launch(args);

    }

    private void initComponets(){

        pane = new AnchorPane();
        pane.setPrefSize(800, 600);

        txPesquisa = new TextField();
        txPesquisa.setPromptText("Digite o item para pesquisa");
        pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");


        tbVitrine = new TableView<ItensProperty>();
        tbVitrine.setPrefSize(780, 550);

        columnProduto = new TableColumn<ItensProperty, String>();
        columnPreco = new TableColumn<ItensProperty, Double>();

        tbVitrine.getColumns().addAll(columnProduto, columnPreco);
        tbVitrine.setItems(listItens);

        pane.getChildren().addAll(txPesquisa, tbVitrine);
        carrinho = new Carrinho();
        columnProduto.setCellValueFactory(new PropertyValueFactory<ItensProperty, String>("produto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<ItensProperty, Double>("preco"));
    }
    private void initLayout(){

        txPesquisa.setLayoutX(590);
        txPesquisa.setLayoutY(10);
        txPesquisa.setPrefWidth(200);
        txPesquisa.setPromptText("Search");

        tbVitrine.setLayoutX((pane.getWidth() - tbVitrine.getWidth())/2);
        tbVitrine.setLayoutY(40);

    }
    private void initListeners(){

        txPesquisa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txPesquisa.getText().equals("")) {
                    tbVitrine.setItems(findItems());
                } else {
                    tbVitrine.setItems(listItens);
                }
            }
        });
    }

    private void initItems(){
        Vitrine v = new Vitrine();
        v.addProdutos(
                new Produto("Bola Topper", 15.00),
                new Produto("Luvas Umbro", 9.00),
                new Produto("Camisa Esportiva", 40.00),
                new Produto("Chuteira Nike Mercurial", 199.00),
                new Produto("Caneleira Topper", 10.00)
        );
        for (Produto p : v.getProdutos())
            listItens.add(new ItensProperty(p.getProduto(), p.getPreco()));
    }


    //Inner Class
    public class ItensProperty {
        private SimpleStringProperty produto;
        private SimpleDoubleProperty preco;
        public ItensProperty(String produto, double preco) {
            this.produto = new SimpleStringProperty(produto);
            this.preco = new SimpleDoubleProperty(preco);
        }

        public String getProduto() {
            return produto.get();
        }
        public void setProduto(String produto) {
            this.produto.set(produto);
        }
        public double getPreco() {
            return preco.get();
        }
        public void setPreco(double preco) {
            this.preco.set(preco);
        }
    }

    private ObservableList<ItensProperty> findItems() {
        ObservableList<ItensProperty> itensEncontrados = FXCollections
                .observableArrayList();
        for (ItensProperty itens : listItens) {
            if (itens.getProduto().contains(txPesquisa.getText())) {
                itensEncontrados.add(itens);
            }
        }
        return itensEncontrados;
    }
}

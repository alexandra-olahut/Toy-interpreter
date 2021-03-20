package GUI;

import Controller.Controller;
import Model.ProgramState.PrgState;
import Model.Values.StringValue;
import Model.Values.Value;
import View.Command;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerProgramWindow implements Initializable {

    private final Controller controller;
    private final Command program;

    @FXML
    private TextField nrPrgStates;
    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<String> output;
    @FXML
    private ListView<Integer> prgId;
    @FXML
    private ListView<String> exeStack;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableAddress;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableValue;

    @FXML
    private TableView<Map.Entry<StringValue, BufferedReader>> fileTable;
    @FXML
    private TableColumn<Map.Entry<StringValue, BufferedReader>, String> fileTableFd;
    @FXML
    private TableColumn<Map.Entry<StringValue, BufferedReader>, String> fileTableFilename;

    @FXML
    private TableView<Map.Entry<String, Value>> symTable;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableVar;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableValue;


    public ControllerProgramWindow(Command p, Controller c) {program = p; controller = c; controller.setExecutor();}


    @FXML
    void changeSelectedThread(MouseEvent event) {
        populateSymTable();
        populateExeStack();
    }

    @FXML
    void oneStep(ActionEvent event) {

        if(controller.getRepo().getPrgList().size() == 0)
        {
            program.reset();
            this.oneStepButton.setDisable(true);
            controller.getRepo().resetLogFile();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Yay");
            alert.setHeaderText(null);
            alert.setContentText("The program is finished");
            alert.show();
            return;
        }

        try {
            controller.runOneStep();
        } catch (Exception e) {

            this.oneStepButton.setDisable(true);
            controller.getRepo().resetLogFile();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ooops");
            alert.setHeaderText("Exception happened");
            alert.setContentText(e.getMessage());
            alert.show();
        }

        if(controller.getRepo().getPrgList().size() > 0)
            populateAll();

        controller.getRepo().setPrgList(controller.removeCompletedPrg(controller.getRepo().getPrgList()));

        //        populatePrgId();
        /*
               if(example.getController().getPrograms().size() > 0)
            example.getController().executeOneStep();
        if(example.getController().getPrograms().size() > 0)
            populate();
        else
            mainPrglist.getItems().remove(example);
            mainPrglist.getSelectionModel().clearSelection();

         */
    }


//    public void setController(Controller c) {controller = c;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        controller.getRepo().resetLogFile();
        program.reset();

        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));

        fileTableFilename.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        fileTableFd.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));

        symTableVar.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));

        populateAll();
        controller.logState();
    }


    public void populateAll() {
        setTextField();
        populateHeapTable();
        populateOutput();
        populateFileTable();
        populatePrgId();
        if(prgId.getSelectionModel().getSelectedItem() == null) {
            prgId.getSelectionModel().selectFirst();
        }
        populateSymTable();
        populateExeStack();
    }

    public void setTextField() {
        nrPrgStates.setText(String.valueOf(controller.getRepo().getPrgList().size()));
    }

    public void populateHeapTable(){
        ObservableList<Map.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        heapTableList.addAll(controller.getRepo().getPrgList().get(0).getHeap().getContent().entrySet());
        heapTable.setItems(heapTableList);
        heapTable.refresh();
    }

    public void populateOutput(){
        ObservableList<String> outputList = FXCollections.observableArrayList();
        controller.getRepo().getPrgList().get(0).getOut().getContent().forEach(o -> outputList.add(o.toString()));
        output.setItems(outputList);
    }

    public void populateFileTable(){
        ObservableList<Map.Entry<StringValue, BufferedReader>> fileTableList = FXCollections.observableArrayList();
        fileTableList.addAll(controller.getRepo().getPrgList().get(0).getFileTable().getContent().entrySet());
        fileTable.setItems(fileTableList);
        fileTable.refresh();
    }

    public void populatePrgId(){
        ObservableList<Integer> prgIdList = FXCollections.observableArrayList();
        prgIdList.addAll(controller.getRepo().getPrgList().stream().map(PrgState::getId).collect(Collectors.toList()));
        prgId.setItems(prgIdList);
    }

    public void populateSymTable(){
        Integer currentPrgId = prgId.getSelectionModel().getSelectedItem();
        PrgState currentPrg = controller.getRepo().getPrgList().stream().filter(p->p.getId()==currentPrgId).collect(Collectors.toList()).get(0);

        ObservableList<Map.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        symTableList.addAll(currentPrg.getSymTable().getContent().entrySet());
        symTable.setItems(symTableList);
        symTable.refresh();
    }

    public void populateExeStack(){
        Integer currentPrgId = prgId.getSelectionModel().getSelectedItem();
        PrgState currentPrg = controller.getRepo().getPrgList().stream().filter(p->p.getId()==currentPrgId).collect(Collectors.toList()).get(0);

        ObservableList<String> exeStackList = FXCollections.observableArrayList(currentPrg.getExeStack().getContent().stream().map(Object::toString).collect(Collectors.toList()));
        Collections.reverse(exeStackList);
        exeStack.setItems(exeStackList);
    }
}

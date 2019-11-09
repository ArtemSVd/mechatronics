package com.company.gui.controllers;

import com.company.gui.drawers.CenterMassDrawer;
import com.company.gui.drawers.JointDrawer;
import com.company.gui.drawers.SegmentDrawer;
import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.FileService;
import com.company.logic.service.Point;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.utils.Utility.getDoubleValue;
import static com.company.utils.Utility.isIntersected;

public class EditController {

    @FXML
    public TextArea info;
    @FXML
    public TextField limit;
    @FXML
    public TextField weightSegment;
    @FXML
    public TextField weightJoint;
    @FXML
    public CheckBox invisibleCheck;
    @FXML
    public CheckBox ephemeralCheck;
    @FXML
    public Button set;
    @FXML
    public AnchorPane canvas;
    @FXML
    public CheckBox centerMassCheck;
    @FXML
    public Slider lengthSlider;
    @FXML
    public Slider angleSlider;

    private int selectedElements;
    private double scale = 1;
    private List<Line> listLine = new ArrayList<>();
    private MultiLinkSystem system = MultiLinkSystem.getInstance();

    @FXML
    public void slideAngle() {
        if (selectedElements != 0) {
            Segment segment = (Segment) system.getElement(selectedElements);

            segment.setAngle(angleSlider.getValue());

            system.updateFrom(selectedElements - 1);

            showInfo();
            scale = 1;
            redrawing();
        }
    }
    @FXML
    public void slideLength() {
        if (selectedElements != 0) {
            Segment segment = (Segment) system.getElement(selectedElements);

            try {
                segment.setLength(lengthSlider.getValue());
            } catch (OutOfValueRangeException e) {
                e.printStackTrace();
            }

            system.updateFrom(selectedElements - 1);

            showInfo();
            scale = 1;
            redrawing();
        }
    }

    /**
     * Метод для удаления выбранного по нажатию мышки элемента.
     */
    @FXML
    public void deleteSelectedElem(){
        system.removeElementFromSystem(selectedElements-1);
        scale = 1;
        redrawing();
    }

    /**
     * Метод для открытия формы, предназначенной для добавления новых элементов.
     */
    @FXML
    public void addElement(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/add_form.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add segment");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            redrawing();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An unexpected error has occurred");
            alert.showAndWait();
        }
    }

    /**
     * Метод для сохранения дерева элементов в файл.
     */
    @FXML
    public void saveElements() {
        try {
            FileService.writeSegmentsMapToFile(system.getAllElements());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для чтения дерева элементов из файла.
     */
    @FXML
    public void writeElements() {
        try {
            system.setAllElements(FileService.readSegmentsMapToFile());

            scale = 1;

            redrawing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для обработки введенных значений.
     */
    @FXML
    public void handleButtonSetParametrs() {
        Segment segment = (Segment) system.getElement(selectedElements);
        Joint joint = (Joint) system.getElement(selectedElements - 1);

        try {
            segment.setWeight(getDoubleValue(this.weightSegment));
            segment.setInvisibility(invisibleCheck.isSelected());
            segment.setEphemeral(ephemeralCheck.isSelected());

            joint.setWeight(getDoubleValue(this.weightJoint));
            joint.setAngleLimit(getDoubleValue(this.limit));

            system.updateFrom(selectedElements - 1);

            redrawing();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Write correct number (Example: 2.7)");
            alert.showAndWait();
        }
    }

    /**
     * Метод для автоматического отображения центра масс при нажатии на CheckBox.
     */
    @FXML
    public void drawCenterMassOnClick() {
            redrawing();
    }

    /**
     * Метод для проверки на выход за пределы рабочей зоны.
     * @param line линия, которая будет проверяться
     */
    private void checkBorder(Line line){
        double height = canvas.getHeight();
        double width = canvas.getWidth();

        double endX = (line.getEndX()) * scale;
        double endY = (line.getEndY()) * scale;
        while(endX >= width-40 || endY >= height-40 || endX <= 40 || endY <= 0) {

            if(scale < 0.5) break;
            scale -= 0.01;
            endX = (line.getEndX()) * scale;
            endY = (line.getEndY()) * scale;

        }
        canvas.setScaleX(scale);
        canvas.setScaleY(scale);
        canvas.setScaleZ(scale);
    }

    /**
     * Метод для перерисовки всех элементов относительно середины рабоей зоны.
     */
    private void redrawing() {
        deleteElementsFromCanvas();
        Point point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
        drawAllElem(canvas, point);
    }

    /**
     * Удаление всех элементов с рабочей зоны.
     */
    private void deleteElementsFromCanvas(){
        canvas.getChildren().clear();
    }

    /**
     * Метод для отображении информации о выбранном по нажатию мыши элементе.
     * Данные выводятся в информационное окно и в поля ввода.
     */
    private void showInfo() {
        Segment segmentInfo = (Segment) system.getElement(selectedElements);
        Joint jointInfo = (Joint) system.getElement(selectedElements-1);

        info.setText(segmentInfo.toString()+"\n"+jointInfo.toString());

        setParametersForSegment(segmentInfo);
        setParametersForJoint(jointInfo);
    }

    /**
     * Установка значений выбранного сегмента в окна ввода.
     * @param segment выбранный сегмент
     */
    private void setParametersForSegment(Segment segment){
        lengthSlider.setValue(segment.getLength());

        angleSlider.setMin(0);
        angleSlider.setMax(segment.getAngleLimitFromJoint());
        angleSlider.setValue(180*segment.getAngle()/Math.PI);

        weightSegment.setText(String.valueOf(segment.getWeight()));
        invisibleCheck.setSelected(segment.isInvisible());
        ephemeralCheck.setSelected(segment.isEphemeral());
    }

    /**
     * Установка значений дочернего сочленения для выбранного сегмента в окна ввода.
     * @param joint дочернее сочленение выбранного сегмента
     */
    private void setParametersForJoint(Joint joint){
        weightJoint.setText(String.valueOf(joint.getWeight()));
        limit.setText(String.valueOf(180*joint.getAngleLimit()/Math.PI));
    }

    /**
     * Метод для отрисовки всех необходимых элементов.
     * @param root рабочая зона для отрисовки
     * @param startCoordinate точка начала координат
     */
    private void drawAllElem(AnchorPane root,Point startCoordinate){
        drawSegments(root, startCoordinate);
        drawJoints(root, startCoordinate);
        drawCenterMass(root, startCoordinate);

        if(listLine.size() >= 2) {
            checkIntersects();
        }
    }

    /**
     * Метод для отрисовки сегментов.
     * @param root рабочая зона для отрисовки
     * @param startCoordinate точка начала координат
     */
    private void drawSegments(AnchorPane root, Point startCoordinate) {
        SegmentDrawer segmentDrawer = new SegmentDrawer(startCoordinate);
        listLine.clear();
        for(int i = 2; i <= system.getAllElements().size();i +=2) {
            if (system.getElement(i) instanceof Segment) {
                Segment segment = (Segment) system.getElement(i);

                Line line = segmentDrawer.createLine(i,segment);
                checkBorder(line);

                line.setOnMouseClicked(mouseEvent -> {
                    selectedElements = Integer.parseInt(line.getId());
                    showInfo();
                });

                listLine.add(line);

                root.getChildren().add(line);
            }
        }
    }

    /**
     * Метод для отрисовки сочленений.
     * @param root рабочая зона для отрисовки
     * @param startCoordinate точка начала координат
     */
    private void drawJoints(AnchorPane root, Point startCoordinate) {
        JointDrawer jointDrawer = new JointDrawer(startCoordinate);

        for(int i = 1; i <= system.getAllElements().size();i+=2) {
            if(system.getElement(i) instanceof Joint){
                Joint joint = (Joint) system.getElement(i);

                Circle circle = jointDrawer.createCircle(i,joint);

                root.getChildren().add(circle);
            }
        }
    }

    /**
     * Метод для отрисовки центра масс.
     * @param root рабочая зона для отрисовки
     * @param startCoordinate точка начала координат
     */
    private void drawCenterMass(AnchorPane root, Point startCoordinate) {
        if(centerMassCheck.isSelected()){
            CenterMassDrawer centerMassDrawer = new CenterMassDrawer(startCoordinate);

            Circle centerMass = centerMassDrawer.createCenterMass();

            root.getChildren().add(centerMass);
        }
    }

    /**
     * Проверка всех элементов на пересечение,
     * если пересекаются и элемент не эфемерный,
     * замена его цвета на желтый.
     */
    private void checkIntersects() {
        for(int i = 0; i < listLine.size(); i++) {
            for(int j = i+1; j < listLine.size(); j++) {
                Line firstLine = listLine.get(i);
                Line secondLine = listLine.get(j);

                if (isIntersected(firstLine, secondLine)) {
                    if(isNotEphemeral(firstLine)) {
                        firstLine.setStroke(Color.YELLOW);
                    }
                    if(isNotEphemeral(secondLine)) {
                        secondLine.setStroke(Color.YELLOW);
                    }
                }
            }
        }
    }

    /**
     * Проверка на эфемерность.
     * @param line линия, которая хранит идентификатор
     *             соотвествующего элемента в дереве элементов
     * @return true - если эфемерный
     */
    private boolean isNotEphemeral(Line line) {
        Segment segment = (Segment) system.getElement(Integer.parseInt(line.getId()));
        return !segment.isEphemeral();
    }
}

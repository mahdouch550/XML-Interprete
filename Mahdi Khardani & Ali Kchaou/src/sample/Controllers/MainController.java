package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import org.xml.sax.InputSource;
import sample.ConsoleOutputCapturer;
import sample.FileGesture;
import sample.HowToUse;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class MainController {

    //The button that runs the execution of the xquery file on the xml File
    @FXML
    private Button run;

    @FXML
    private Button dropXqueryFileButton;

    @FXML
    private Button saveResultOutputButton;

    @FXML
    private Button dropXmlFileButton;

    //The button responsible on saving the xml file if changed/written
    @FXML
    private Button saveXmlFileButton;

    //The button responsible on saving the xquery file if changed/written
    @FXML
    private Button saveXQUERYFileButton;

    //The textArea that shows the query result
    @FXML
    private TextArea resultTextArea;

    //The imageView that indicates if an xml file is successfully loaded
    @FXML
    private ImageView xmlLoaded;

    //The imageView that indicates if an xquery file is successfully loaded
    @FXML
    private ImageView xqueryLoaded;

    //The textArea that contains the xml File content
    @FXML
    private TextArea xmlFileContentTextArea;

    //The textArea that contains the xquery File content
    @FXML
    private TextArea xqueryFileContentTextArea;

    //The label above the evaluation text area
    @FXML
    private Label evaluationLabel;

    //The save xml Menu Item (same role as save xml button)
    @FXML
    private MenuItem saveXmlMenuItem;

    //The save xquery Menu Item (same role as save xquery button)
    @FXML
    private MenuItem saveXqueryMenuItem;

    @FXML
    private MenuItem dropXmlFileMenuItem;

    @FXML
    private MenuItem dropXqueryFileMenuItem;

    @FXML
    private MenuItem saveResultOutPutMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem editXmlFileMenuItem;

    @FXML
    private MenuItem editXqueryFileMenuItem;

    @FXML
    private MenuItem howToUseMenuItem;

    @FXML
    private Button viewHtmlResultButton;

    //The loaded/created xquery File
    private File xQueryFile;

    //The loaded/created xml File
    private File xmlFile;

    //The accepted image File (to fill the xmlLoaded/xquery loaded ImageView)
    private File acceptedImageFile= new File("C:/Users/Mahdi Khardani/IdeaProjects/untitled/accept-circle-128.png");

    //The accepted image File path
    private final String acceptedImageFilePath=acceptedImageFile.toURI().toString();

    //The refused image File (to fill the xmlLoaded/xquery loaded ImageView)
    private File refusedImageFile= new File("C:/Users/Mahdi Khardani/IdeaProjects/untitled/téléchargement.png");

    //The refused image File path
    private final String refusedImageFilePath = refusedImageFile.toURI().toString();

    //The concrete accepted image
    private Image acceptedImage = new Image(acceptedImageFilePath);

    //The concrete refused image
    private Image refusedImage = new Image(refusedImageFilePath);

    //The object responsible of capturing the console output
    private ConsoleOutputCapturer coc = new ConsoleOutputCapturer();

    //The object responsible of the xml file gesture
    private FileGesture xmlFileGesture;

    //The object responsible of the xquery file gesture
    private FileGesture xqueryFileGesture;

    //The main xml file content
    private String mainXmlFileContent;

    //The main xquery file content
    private String mainXqueryFileContent;

    //The current xml file content(if any changes)
    private String currentXmlFileTexAreaTextValue;

    //The current xquery file content(if any changes)
    private String currentXqueryFileTexAreaTextValue;

    private String result="";

    @FXML
    void aboutMenuItemClicked(ActionEvent event) {

    }

    @FXML
    void editXmlFileMenuItemClicked(ActionEvent event) {
        xmlFileContentTextArea.requestFocus();
    }

    @FXML
    void editXqueryFileMenuItemClicked(ActionEvent event) {
        xqueryFileContentTextArea.requestFocus();
    }

    @FXML
    void howToUseMenuItemClicked(ActionEvent event) throws Exception {
        HowToUse howToUse=new HowToUse();
        howToUse.start(new Stage());
    }

    //Importing, getting and displaying the xml file content
    @FXML
    void addXmlMenuItemClicked(ActionEvent event) throws IOException {
        FileChooser xmlFileChooser = new FileChooser();
        xmlFileChooser.setInitialDirectory(new File("D:\\BUREAU\\el 9raya\\MRI 1\\Semestre2\\Documents semi-structurés\\Compte Rendu\\Files"));
        xmlFileChooser.setTitle("Select XML files");
        xmlFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        xmlFile=xmlFileChooser.showOpenDialog(null);
        if(xmlFile!=null) {
            xmlLoaded.setImage(acceptedImage);
            dropXmlFileButton.setDisable(false);
            dropXmlFileMenuItem.setDisable(false);
            if(xQueryFile!=null){
                run.setDisable(false);
            }
            xmlFileGesture =new FileGesture(xmlFile);
            xmlFileGesture.open();
            xmlFileContentTextArea.setText(xmlFileGesture.read());
            mainXmlFileContent= xmlFileGesture.read();
            xmlFileGesture.close();
        }
        else{
            xmlLoaded.setImage(refusedImage);
            run.setDisable(true);
        }
        run.requestFocus();
    }

    //Importing, getting and displaying the xquery file content
    @FXML
    void addXQUERYMenuItemClicked(ActionEvent event) throws IOException {
        FileChooser xqueryFileChooser = new FileChooser();
        if(xmlFile!=null)
            xqueryFileChooser.setInitialDirectory(new File(xmlFile.getParent()));
        else
        xqueryFileChooser.setInitialDirectory(new File("D:\\BUREAU\\el 9raya\\MRI 1\\Semestre2\\Documents semi-structurés\\Compte Rendu\\Files/"));
        xqueryFileChooser.setTitle("Select Xquery files");
        xqueryFileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Xquery Files", "*.xquery"));
        xQueryFile=xqueryFileChooser.showOpenDialog(null);
        if(xQueryFile!=null) {
            xqueryLoaded.setImage(acceptedImage);
            dropXqueryFileButton.setDisable(false);
            dropXqueryFileMenuItem.setDisable(false);
            if(xmlFile!=null){
                run.setDisable(false);
            }
            xqueryFileGesture = new FileGesture(xQueryFile);
            xqueryFileGesture.open();
            xqueryFileContentTextArea.setText(xqueryFileGesture.read());
            mainXqueryFileContent= xqueryFileGesture.read();
            xqueryFileGesture.close();
        }
        else{
            xqueryLoaded.setImage(refusedImage);
            run.setDisable(true);
        }
        run.requestFocus();
    }

    //Closing the application definitively via the button
    @FXML
    void closeButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    //Closing the application definitively via the menu item
    @FXML
    void closeMenuItemClicked(ActionEvent event) {
        System.exit(0);
    }

    //resetting all fields, text areas, files and labels
    @FXML
    void resetButtonClicked(MouseEvent event) {
        xmlFile=null;
        xQueryFile=null;
        resultTextArea.setText("");
        xmlLoaded.setImage(refusedImage);
        xqueryLoaded.setImage(refusedImage);
        run.setDisable(true);
        xmlFileContentTextArea.setText("");
        xqueryFileContentTextArea.setText("");
        saveXmlFileButton.setDisable(true);
        saveXQUERYFileButton.setDisable(true);
        saveXqueryMenuItem.setDisable(true);
        saveXmlMenuItem.setDisable(true);
        evaluationLabel.setTextFill(Paint.valueOf("red"));
        dropXqueryFileButton.setDisable(true);
        dropXqueryFileMenuItem.setDisable(true);
        dropXmlFileButton.setDisable(true);
        dropXmlFileMenuItem.setDisable(true);
        saveResultOutputButton.setDisable(true);

    }

    //Checking files and their contents, handling the user interface then running the xquery file on the xml file
    @FXML
    void runButtonClicked(MouseEvent event) throws IOException {

        //Start capturing the console outputs
        coc.start();
        //the Q.xquery file
        InputStream queryStream=null;

        //print the resultTextArea to the console
        OutputStream destStream=System.out;

        //compile the XQuery expression
        XQueryExpression exp=null;

        //create a Configuration object
        Configuration C= new Configuration();

        //static and dynamic context
        StaticQueryContext SQC=new StaticQueryContext(C);
        DynamicQueryContext DQC=new DynamicQueryContext(C);

        //indentation
        Properties props=new Properties();
        props.setProperty(OutputKeys.METHOD, "html");
        props.setProperty(OutputKeys.INDENT, "yes");


        try{
            queryStream=new FileInputStream(xQueryFile);
            SQC.setBaseURI(xQueryFile.toURI().toString());
            //compilation
            exp=SQC.compileQuery(queryStream,null);
            SQC=exp.getStaticContext();
        }catch(net.sf.saxon.trans.XPathException e)
        {System.err.println(e.getMessage());
            result+=e.getMessage();
        }catch(java.io.IOException e)
        {System.err.println(e.getMessage());
            result+=e.getMessage();}

        //get the XML ready
        try{
            File XMLStream=xmlFile;
            InputSource XMLSource=new InputSource(XMLStream.toURI().toString());
            SAXSource SAXs=new SAXSource(XMLSource);
            DocumentInfo DI=SQC.buildDocument(SAXs);
            DQC.setContextNode(DI);
            StreamResult sR = new StreamResult(destStream);
            //evaluating
            exp.run(DQC, sR, props);
            destStream.close();
            result=coc.stop();
            run.setDisable(true);
        }catch(net.sf.saxon.trans.XPathException e)
        {System.err.println(e.getMessage());
        result=e.getMessage();
        }catch (java.io.IOException e)
        {System.err.println(e.getMessage());
            result+=e.getMessage();}
        finally {
                resultTextArea.setText(result);
            if(!resultTextArea.getText().contains("lineNumber")) {
                saveResultOutputButton.setDisable(false);
                saveResultOutPutMenuItem.setDisable(false);
            }
        }
    }

    //Dragging the xml file over the xml specific text area
    @FXML
    void draggingOverXmlFileContentTextArea(DragEvent event) {
        Dragboard board = event.getDragboard();
        if (board.hasFiles()) {
            if(board.getFiles().get(0).getAbsolutePath().endsWith(".xml"))
                event.acceptTransferModes(TransferMode.ANY);
        }
    }

    //Dropping the xml file int the xquery specific text area
    @FXML
    void droppingOnXmlFileContentTextArea(DragEvent event) {
        try {
            Dragboard board = event.getDragboard();
            List<File> phil = board.getFiles();
            FileInputStream fis;
            fis = new FileInputStream(phil.get(0));

            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1){
                builder.append((char)ch);
            }

            fis.close();

            xmlFileContentTextArea.setText(builder.toString());
            xmlFile=phil.get(0);
            xmlLoaded.setImage(acceptedImage);
            if(xQueryFile!=null){
                run.setDisable(false);
            }
            mainXmlFileContent=xmlFileContentTextArea.getText();
            dropXmlFileButton.setDisable(false);
            dropXmlFileMenuItem.setDisable(false);
            run.requestFocus();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Dragging the xquery file over the xml specific text area
    @FXML
    void draggingOverXQUERYFileContentTextArea(DragEvent event) {
        Dragboard board = event.getDragboard();
        if (board.hasFiles()) {
            if(board.getFiles().get(0).getAbsolutePath().endsWith(".xquery"))
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    //Dropping the xquery file int the xquery specific text area
    @FXML
    void droppingOnXQUERYFileContentTextArea(DragEvent event) {
        try {
            Dragboard board = event.getDragboard();
            List<File> phil = board.getFiles();
            FileInputStream fis;
            fis = new FileInputStream(phil.get(0));

            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1){
                builder.append((char)ch);
            }

            fis.close();

            xqueryFileContentTextArea.setText(builder.toString());
            xQueryFile=phil.get(0);
            xqueryLoaded.setImage(acceptedImage);
            if(xmlFile!=null){
                run.setDisable(false);
            }
            mainXqueryFileContent=xqueryFileContentTextArea.getText();
            dropXqueryFileButton.setDisable(false);
            dropXqueryFileMenuItem.setDisable(false);
            run.requestFocus();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Saving the xquery file typed in the xquery specific text area via the button
    @FXML
    void saveXQUERYFileButtonClicked(MouseEvent event) throws IOException {
        String xQueryTextAreaText=xqueryFileContentTextArea.getText();
        if (xQueryFile != null) {
            xqueryFileGesture = new FileGesture(xQueryFile);
            xqueryFileGesture.open();
            xqueryFileGesture.write(xQueryTextAreaText);
            run.setDisable(false);
        } else {
            FileChooser directoryChooser = new FileChooser();
            if(xmlFile!=null)
                directoryChooser.setInitialDirectory(new File(xmlFile.getParent()));
            directoryChooser.setTitle("Save a new XQUERY file");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XQUERY FILES (*.xquery)", "*.xquery");
            directoryChooser.getExtensionFilters().add(extFilter);
            File newXQueryFile = directoryChooser.showSaveDialog(null);
            if (!newXQueryFile.canRead()) {
                newXQueryFile.setReadable(true);
            }
            xQueryFile=newXQueryFile;
            xqueryFileGesture = new FileGesture(xQueryFile);
            xqueryFileGesture.open();
            xqueryFileGesture.write(xQueryTextAreaText);
            xqueryLoaded.setImage(acceptedImage);
            if(xmlLoaded.getImage()==acceptedImage)
                run.setDisable(false);
        }
        xqueryFileGesture.close();
        saveXQUERYFileButton.setDisable(true);
        saveXqueryMenuItem.setDisable(true);
        dropXqueryFileButton.setDisable(false);
    }

    //Saving the xml file typed in the xml specific text area via the button
    @FXML
    void saveXmlFileButtonClicked(MouseEvent event) throws IOException {
        String xmlTextAreaText=xmlFileContentTextArea.getText();
        if (xmlFile != null) {
            xmlFileGesture =new FileGesture(xmlFile);
            xmlFileGesture.open();
            xmlFileGesture.write(xmlTextAreaText);
            run.setDisable(false);
            xmlFileGesture.close();
        } else {
            FileChooser directoryChooser = new FileChooser();
            directoryChooser.setTitle("Save a new XML file");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML FILES (*.xml)", "*.xml");
            directoryChooser.getExtensionFilters().add(extFilter);
            File newXmlFile = directoryChooser.showSaveDialog(null);
            if(!newXmlFile.canRead())
            {
                newXmlFile.setReadable(true);
            }
            xmlFile=newXmlFile;
            xmlFileGesture =new FileGesture(xmlFile);
            xmlFileGesture.open();
            xmlFileGesture.write(xmlTextAreaText);
            xmlFileGesture.close();
            xmlLoaded.setImage(acceptedImage);
            if(xqueryLoaded.getImage()==acceptedImage)
                run.setDisable(false);
        }
        saveXmlFileButton.setDisable(true);
        saveXmlMenuItem.setDisable(true);
        dropXmlFileButton.setDisable(false);

    }

    //Managing every and any typed key in the xml specific text area
    @FXML
    void xmlFileContentTextAreaKeyTyped(KeyEvent event) {
        if(xmlFileContentTextArea.getText().equals("")){
            xmlLoaded.setImage(refusedImage);
            saveXmlFileButton.setDisable(true);
            saveXmlMenuItem.setDisable(true);
        }else {
            String local = xmlFileContentTextArea.getText();
            if (!event.getCharacter().equals("\b"))
                currentXmlFileTexAreaTextValue = xmlFileContentTextArea.getText() + event.getCharacter();
            else {
                currentXmlFileTexAreaTextValue = local;
            }
            boolean t = currentXmlFileTexAreaTextValue.equals(mainXmlFileContent);
            saveXmlFileButton.setDisable(t);
            saveXmlMenuItem.setDisable(t);
        }
    }

    //Managing every and any typed key in the xquery specific text area
    @FXML
    void xqueryFileContentTextAreaKeyTyped(KeyEvent event) {
        if(xqueryFileContentTextArea.getText().equals("")){
            xqueryLoaded.setImage(refusedImage);
            saveXQUERYFileButton.setDisable(true);
            saveXqueryMenuItem.setDisable(true);
        }else {
            String local = xqueryFileContentTextArea.getText();
            if (!event.getCharacter().equals("\b"))
                currentXqueryFileTexAreaTextValue = xqueryFileContentTextArea.getText() + event.getCharacter();
            else {
                currentXqueryFileTexAreaTextValue = local;
            }
            boolean t = currentXqueryFileTexAreaTextValue.equals(mainXqueryFileContent);
            saveXQUERYFileButton.setDisable(t);
            saveXqueryMenuItem.setDisable(t);
        }
    }

    //Saving the xml file typed in the xml specific text area via the button
    @FXML
    void saveXmlMenuItemClicked(ActionEvent event) throws IOException {
        String xmlTextAreaText=xmlFileContentTextArea.getText();
        if (xmlFile != null) {
            xmlFileGesture =new FileGesture(xmlFile);
            xmlFileGesture.open();
            xmlFileGesture.write(xmlTextAreaText);
        } else {
            FileChooser directoryChooser = new FileChooser();
            directoryChooser.setTitle("Save a new XML file");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML FILES (*.xml)", "*.xml");
            directoryChooser.getExtensionFilters().add(extFilter);
            File newXmlFile = directoryChooser.showSaveDialog(null);
            if(!newXmlFile.canRead())
            {
                newXmlFile.setReadable(true);
            }
            xmlFile=newXmlFile;
            xmlFileGesture =new FileGesture(xmlFile);
            xmlFileGesture.open();
            xmlFileGesture.write(xmlTextAreaText);
            xmlLoaded.setImage(acceptedImage);
            if(xqueryLoaded.getImage()==acceptedImage)
                run.setDisable(false);
        }
        xmlFileGesture.close();
        saveXmlFileButton.setDisable(true);
        saveXmlMenuItem.setDisable(true);
        dropXmlFileButton.setDisable(false);
    }

    //Saving the xquery file typed in the xquery specific text area via the button
    @FXML
    void saveXqueryMenuItemClicked(ActionEvent event) throws IOException {
        String xQueryTextAreaText=xqueryFileContentTextArea.getText();
        if (xQueryFile != null) {
            xqueryFileGesture = new FileGesture(xQueryFile);
            xqueryFileGesture.open();
            xqueryFileGesture.write(xQueryTextAreaText);
        } else {
            FileChooser directoryChooser = new FileChooser();
            directoryChooser.setTitle("Save a new XQUERY file");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XQUERY FILES (*.xquery)", "*.xquery");
            directoryChooser.getExtensionFilters().add(extFilter);
            File newXQueryFile = directoryChooser.showSaveDialog(null);
            if (!newXQueryFile.canRead()) {
                newXQueryFile.setReadable(true);
            }
            xQueryFile=newXQueryFile;
            xqueryFileGesture = new FileGesture(xQueryFile);
            xqueryFileGesture.open();
            xqueryFileGesture.write(xQueryTextAreaText);
            xqueryLoaded.setImage(acceptedImage);
            if(xmlLoaded.getImage()==acceptedImage)
                run.setDisable(false);
        }
        xqueryFileGesture.close();
        saveXQUERYFileButton.setDisable(true);
        saveXqueryMenuItem.setDisable(true);
        dropXqueryFileButton.setDisable(false);
    }

    public Image getRefusedImage() {
        return refusedImage;
    }

    public ImageView getXmlLoaded() {
        return xmlLoaded;
    }

    public ImageView getXqueryLoaded() {
        return xqueryLoaded;
    }

    @FXML
    public void dropXmlFileButtonClicked(MouseEvent mouseEvent) {
        if(xmlFile==null)
        {
            dropXmlFileButton.setDisable(true);
            dropXmlFileMenuItem.setDisable(true);
        }
        else
        {
            xmlFile=null;
            run.setDisable(true);
            evaluationLabel.setTextFill(Paint.valueOf("red"));
            resultTextArea.setText("");
            xmlFileContentTextArea.setText("");
            dropXmlFileButton.setDisable(true);
            dropXmlFileMenuItem.setDisable(true);
            xmlLoaded.setImage(refusedImage);
        }

    }

    @FXML
    public void dropXqueryFileButtonClicked(MouseEvent mouseEvent) {
        if(xQueryFile==null)
        {
            dropXqueryFileButton.setDisable(true);
            dropXqueryFileMenuItem.setDisable(true);
        }
        else
        {
            xQueryFile=null;
            run.setDisable(true);
            evaluationLabel.setTextFill(Paint.valueOf("red"));
            resultTextArea.setText("");
            xqueryFileContentTextArea.setText("");
            dropXqueryFileButton.setDisable(true);
            dropXqueryFileMenuItem.setDisable(true);
            xqueryLoaded.setImage(refusedImage);
        }
    }

    @FXML
    public void saveResultOutputButtonClicked(MouseEvent mouseEvent) throws IOException {
        FileGesture fg;
        FileChooser directoryChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter("XML FILES (*.xml)", "*.xml");
        FileChooser.ExtensionFilter htmlExtensionFilter = new FileChooser.ExtensionFilter("HTML FILES (*.html)", "*.html");
        directoryChooser.getExtensionFilters().add(xmlExtensionFilter);
        directoryChooser.getExtensionFilters().add(htmlExtensionFilter);
        directoryChooser.setTitle("Save a new file");
        File newFile = directoryChooser.showSaveDialog(null);
        newFile.createNewFile();
        fg=new FileGesture(newFile);
        fg.open();
        fg.write(resultTextArea.getText());
        saveResultOutputButton.setDisable(true);
        saveResultOutPutMenuItem.setDisable(true);
        viewHtmlResultButton.setDisable(false);
        fg.close();
    }

    @FXML
    public void dropXmlFileMenuItemClicked(ActionEvent actionEvent) {
        if(xmlFile==null)
        {
            dropXmlFileButton.setDisable(true);
            dropXmlFileMenuItem.setDisable(true);
        }
        else
        {
            xmlFile=null;
            run.setDisable(true);
            evaluationLabel.setTextFill(Paint.valueOf("red"));
            resultTextArea.setText("");
            xmlFileContentTextArea.setText("");
            dropXmlFileButton.setDisable(true);
            dropXmlFileMenuItem.setDisable(true);
            xmlLoaded.setImage(refusedImage);
        }

    }

    @FXML
    public void dropXqueryFileMenuItemClicked(ActionEvent actionEvent) {
        if(xQueryFile==null)
        {
            dropXqueryFileButton.setDisable(true);
            dropXqueryFileMenuItem.setDisable(true);
        }
        else
        {
            xQueryFile=null;
            run.setDisable(true);
            evaluationLabel.setTextFill(Paint.valueOf("red"));
            resultTextArea.setText("");
            xqueryFileContentTextArea.setText("");
            dropXqueryFileButton.setDisable(true);
            dropXqueryFileMenuItem.setDisable(true);
            xqueryLoaded.setImage(refusedImage);
        }
    }

    @FXML
    public void saveResultOutPutMenuItemClicked(ActionEvent actionEvent) throws IOException {
        FileGesture fg;
        FileChooser directoryChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter("XML FILES (*.xml)", "*.xml");
        FileChooser.ExtensionFilter htmlExtensionFilter = new FileChooser.ExtensionFilter("HTML FILES (*.html)", "*.html");
        directoryChooser.getExtensionFilters().add(xmlExtensionFilter);
        directoryChooser.getExtensionFilters().add(htmlExtensionFilter);
        directoryChooser.setTitle("Save a new file");
        File newFile = directoryChooser.showSaveDialog(null);
        newFile.createNewFile();
        fg=new FileGesture(newFile);
        fg.open();
        fg.write(resultTextArea.getText());
        saveResultOutputButton.setDisable(true);
        saveResultOutPutMenuItem.setDisable(true);
        viewHtmlResultButton.setDisable(false);
        fg.close();
    }
}
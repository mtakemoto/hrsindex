/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.awt.Desktop;
import javafx.scene.layout.Priority;

/**
 *
 * @author Matt
 */
public class HRSIndex extends Application {

    private static final String HHCA_URL = "http://www.capitol.hawaii.gov/hrscurrent/Vol01_Ch0001-0042F/06-HHCA/";
    private static final String HRS171_URL = "http://www.capitol.hawaii.gov/hrscurrent/Vol03_Ch0121-0200D/HRS0171/";
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    @Override
    public void start(Stage window) {
        
        Link[] hhcaSections = compileStatute(HHCA_URL, "hhca");
        Link[] hrs171Sections = compileStatute(HRS171_URL, "hrs");
        String workingDir = System.getProperty("user.dir").replace("\\", "/");
        //Browser Init
        webEngine.load(hhcaSections[0].getURL());

        //Sidebar Init
        Accordion sidebar = new Accordion();
        TitledPane tpHhca = new TitledPane("HHCA", addLinks(hhcaSections, window));
        TitledPane tpHrs = new TitledPane("HRS171", addLinks(hrs171Sections, window));
        TitledPane tpRules = new TitledPane("Rules", addFiles(workingDir + "/resources/rules"));
        sidebar.getPanes().addAll(tpHhca, tpHrs, tpRules);
        
        //Menu bar init
        MyMenu menuBar = new MyMenu();
        
        //Main init
        SplitPane mainContainer = new SplitPane();
        mainContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        mainContainer.getItems().addAll(sidebar, browser);
        mainContainer.setDividerPositions(0.25f);
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, mainContainer);
          
        //Settings
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
        sidebar.setMaxWidth(300);
        sidebar.setMinWidth(150);
        sidebar.setExpandedPane(tpHhca);
        window.setHeight(600); 
        window.setWidth(900);
        
        //Run program
        Scene scene = new Scene(root);
        window.setTitle("Hawaii Revised Statutes");
        window.setScene(scene);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Link[] compileStatute(String url, String filter) {
        Link[] statute = new Link[0];
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a:contains(" + filter + ")");
            statute = new Link[links.size()];
            int i = 0;
            for (Element section : links) {
                statute[i] = new Link(section);
                i++;
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.exit(0);
        }
        return statute;
    }

    private ScrollPane addLinks(final Link[] statuteList, Stage stage) {
        ScrollPane scrollPane = new ScrollPane();
        VBox container = new VBox();
        scrollPane.setContent(container);
        for (Link statute : statuteList) {
            final Hyperlink section = new Hyperlink(statute.getSection());
            Separator hDivider = new Separator();
            setHyperlinkWeb(section, statute, stage);
            container.getChildren().addAll(section, hDivider);
        }
        return scrollPane;
    }

    private void setHyperlinkWeb(final Hyperlink hyperlink, final Link statute, final Stage stage) {
        hyperlink.setOnAction(e -> {
            webEngine.load(statute.getURL());
            stage.setTitle("Hawaii Revised Statutes: "+ statute.getName());
        });
    }
    
    private void setHyperlinkFile(Hyperlink hyperlink, final File file) {
        hyperlink.setOnAction(e -> {
            try {
                Desktop.getDesktop().open(file);
            } catch(IOException ex) {
                System.out.println("Failed to open file");
                System.exit(-1);
            }
        });
    }

    private ScrollPane addFiles(String directory) {
        ScrollPane scrollPane = new ScrollPane();
        VBox container = new VBox();
        scrollPane.setContent(container);
        File folder = new File(directory);
        File[] fileList = folder.listFiles();

        if(fileList == null) {
            System.err.println("Static resource path " + directory + " not found");
            System.exit(-1);
        }

        for (File aFileList : fileList) {
            if (aFileList.isFile()) {
                String fileName = aFileList.getName();
                Hyperlink hyperlink = new Hyperlink(fileName.substring(0, fileName.indexOf(".")));
                Separator hDivider = new Separator();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1); //Search for "." from right to left

                if (ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("html")) {
                    //setHyperlinkWeb(hyperlink, directory + fileName);
                } else { //Open in designated external app
                    setHyperlinkFile(hyperlink, aFileList);
                }
                container.getChildren().addAll(hyperlink, hDivider);
            }
        }
        return scrollPane;
    }

    /*private String searchSections(Link[] statute, String secNum) {
     for(int i = 0; i < statute.length; i++) {
     if (statute[i].getSection().equals(secNum)) {
     return statute[i].getURL();
     }
     }
     return "Bad section request";
     }*/
}

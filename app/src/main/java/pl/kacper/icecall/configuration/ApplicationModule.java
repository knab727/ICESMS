package pl.kacper.icecall.configuration;

import android.app.Application;
import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kacper on 29.11.15.
 * Class used with dagger in order to provide dependency injection
 */
@Module(
        library = true
)
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    /**
     * Metohod for providing application context
     * @return application context
     */
    @Provides
    @Singleton
    Context provideApplicationContext(){
        return application;
    }

    /**
     * Provides the file used for XML persistence. If necessary creates it.
     * @return file for persistence
     */
    @Provides
    @Singleton
    File providePersistenceFile(){
        String path = application.getFilesDir().getAbsolutePath()+"/RemoteMedPersistancev10.xml";
        File file = new File(path);
        if(file.exists())
            return file;
        else {
            file = new File(application.getFilesDir(), "RemoteMedPersistancev10.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element rootElement = document.createElement("users");
                document.appendChild(rootElement);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult streamResult = new StreamResult(file);
                transformer.transform(source, streamResult);
                return file;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            return file;
        }
    }

}

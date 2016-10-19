package pl.kacper.icecall.dagger;

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
 */
@Module(
        library = true
)
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    File providePersistanceFile(){
        String path = application.getFilesDir().getAbsolutePath()+"/ICEPersistance.xml";
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

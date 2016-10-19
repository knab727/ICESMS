package pl.kacper.icecall.persistance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by kacper on 29.11.15.
 */
public class FileManagerUtil {
    @Inject
    File file;

    public FileManagerUtil(File file) {
        this.file = file;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    }




}

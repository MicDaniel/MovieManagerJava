/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myprojects.moviemanager.shared;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author "Daniel Mic"
 */
public class SharedFunctions {
    public ImageIcon resizePic(String path,byte[] blob, int width, int hight) {
        ImageIcon imageIcon;
        
        if(path != null) {
            imageIcon = new ImageIcon(path);
        }
        else {
            imageIcon  = new ImageIcon(blob);
        }
        
        Image image = imageIcon.getImage().getScaledInstance(width, hight, Image.SCALE_SMOOTH);
        ImageIcon finalImage = new ImageIcon(image);
        
        return finalImage;
    }
    
    public String browseImage(JLabel label){
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        //file extension filter
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.Images","jpg","png");
        fileChooser.addChoosableFileFilter(fileFilter);
        
        int fileState = fileChooser.showSaveDialog(null);
        
        if(fileState == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();  
            path = selectedFile.getAbsolutePath();
            
            label.setIcon(resizePic(path, null, label.getWidth(), label.getHeight()));
            
        }
        
        return path;
    }
}
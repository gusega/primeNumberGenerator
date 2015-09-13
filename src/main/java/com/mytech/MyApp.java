package com.mytech;

import com.mytech.controller.MainController;

public class MyApp {
    public static void main(String[] args) {
        MainController.PresentationModel presentationModel = new MainController().execute(args);
        System.out.println(presentationModel.getStatus());
        System.out.println(presentationModel.getText());
    }
}

package com.app.uninstaller.appuninstaller212.model;

import java.util.PropertyResourceBundle;

public class SettingModel  {
    private int id;
    private String mode;
    private String statusBar;
    private String statusBarIcon;

    public SettingModel() {
    }

    public String getStatusBarIcon() {
        return statusBarIcon;
    }

    public void setStatusBarIcon(String statusBarIcon) {
        this.statusBarIcon = statusBarIcon;
    }

    public String getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(String statusBar) {
        this.statusBar = statusBar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

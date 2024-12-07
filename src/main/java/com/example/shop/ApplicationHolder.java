package com.example.shop;

import javafx.application.Application;
import java.net.URL;

public final class ApplicationHolder {

    public static ApplicationHolder INSTANCE = new ApplicationHolder();
    private Application application = null;

    private ApplicationHolder() {

    }

    public URL getResource(String res) {
        return application.getClass().getResource(res);
    }

    public void inject(Application application) {
        this.application = application;
    }
}

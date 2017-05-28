package ru.stqa.pft.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Julia on 5/28/2017.
 */
public class FTPHelper {
    ApplicationManager app;
    private FTPClient ftp;

    public FTPHelper(ApplicationManager applicationManager) {
        this.app = app;
        this.ftp = new FTPClient();
    }

    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(backup);
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file));
        ftp.disconnect();
    }

    public void restore(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(target);
        ftp.rename(backup, target);
        ftp.disconnect();
    }
}

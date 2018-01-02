package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
 
/**
 * Executes the file upload in a background thread and updates progress to
 * listeners that implement the java.beans.PropertyChangeListener interface.
 * @author www.codejava.net
 *
 */
public class UploadTask extends SwingWorker<Void, Integer> {
    private String uploadURL;
    private File uploadFile;
 
    public UploadTask(String uploadURL, File uploadFile) {
        this.uploadURL = uploadURL;
        this.uploadFile = uploadFile;
    }
 
    /**
     * Executed in background thread
     */
    @Override
    protected Void doInBackground() throws Exception {
        try {
            MultipartUploadUtility util = new MultipartUploadUtility(uploadURL,
                    "UTF-8");
            util.addFilePart("uploadFile", uploadFile);
 
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            long totalBytesRead = 0;
            int percentCompleted = 0;
            long fileSize = uploadFile.length();
 
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                util.writeFileBytes(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
                setProgress(percentCompleted);
            }
 
            inputStream.close();
            util.finish();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);           
            ex.printStackTrace();
            setProgress(0);
            cancel(true);
        }
 
        return null;
    }
 
    /**
     * Executed in Swing's event dispatching thread
     */
    @Override
    protected void done() {
        if (!isCancelled()) {
            JOptionPane.showMessageDialog(null,
                    "File has been uploaded successfully!", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            int id =0;
            new AddRepo(id).setVisible(false);
        }
    }
}
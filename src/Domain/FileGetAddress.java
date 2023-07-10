package Domain;

import javax.swing.JFileChooser;

public class FileGetAddress {

    private String address = "";

    public FileGetAddress() {
    }

    public String fileAddress() {
        JFileChooser jChooser = new JFileChooser();
        jChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jChooser.setAcceptAllFileFilterUsed(true);

        int option = jChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            address = jChooser.getSelectedFile().getAbsolutePath();
        }

        return address;
    }
}


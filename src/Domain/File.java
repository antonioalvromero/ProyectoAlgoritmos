package Domain;

import javax.swing.JFileChooser;

public class File {

    String address = "";
//filechooser para la ventana de guardar 

    public File() {
    }

    public String fileAddress() {
        JFileChooser jChooser = new JFileChooser();
        jChooser.setFileSelectionMode(jChooser.DIRECTORIES_ONLY);
        jChooser.setAcceptAllFileFilterUsed(true);
        jChooser.setMultiSelectionEnabled(true);

        int option = jChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            address = jChooser.getSelectedFile().getAbsolutePath();
        }

        return address;

    }

}

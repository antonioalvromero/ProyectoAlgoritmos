/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.opciones;

public final class Op7 extends javax.swing.JPanel {

    public Op7(String data) {
        initComponents();
        jTextArea1.append(data);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titu7 = new javax.swing.JLabel();
        titu6 = new javax.swing.JLabel();
        titu8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(780, 540));
        setPreferredSize(new java.awt.Dimension(780, 540));
        setRequestFocusEnabled(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titu7.setBackground(new java.awt.Color(0, 102, 102));
        titu7.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        titu7.setForeground(new java.awt.Color(0, 0, 0));
        titu7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titu7.setText("exitosamente.");
        titu7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(titu7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, -1, -1));

        titu6.setBackground(new java.awt.Color(0, 102, 102));
        titu6.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        titu6.setForeground(new java.awt.Color(0, 0, 0));
        titu6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titu6.setText("Fragmentos Listado");
        titu6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(titu6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, -1, -1));

        titu8.setBackground(new java.awt.Color(0, 102, 102));
        titu8.setFont(new java.awt.Font("Franklin Gothic Demi", 3, 65)); // NOI18N
        titu8.setForeground(new java.awt.Color(0, 0, 0));
        titu8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titu8.setText("Listar Fragmentos");
        titu8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(titu8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, 90));

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 700, 160));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel titu6;
    private javax.swing.JLabel titu7;
    private javax.swing.JLabel titu8;
    // End of variables declaration//GEN-END:variables

//encapsulamiento
}

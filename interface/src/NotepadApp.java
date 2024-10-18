import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class NotepadApp {
    private JFrame frame;
    private JTextArea noteArea;
    private JTextField titleField;
    private JTextField emailField;
    private JButton sendButton;

    public NotepadApp() {
        frame = new JFrame("Enviar e-Mail");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Titulo:");
        titleLabel.setBounds(20, 20, 100, 20);
        frame.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(100, 20, 250, 20);
        frame.add(titleField);

        JLabel emailLabel = new JLabel("Enviar para:");
        emailLabel.setBounds(20, 50, 100, 20);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 50, 250, 20);
        frame.add(emailField);

        noteArea = new JTextArea();
        noteArea.setBounds(20, 80, 330, 120);
        frame.add(noteArea);

        sendButton = new JButton("Enviar e-mail");
        sendButton.setBounds(120, 210, 150, 30);
        frame.add(sendButton);

       
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String content = noteArea.getText();
                String email = emailField.getText();

                try {
                    sendNoteToServer(title, content, email);
                    JOptionPane.showMessageDialog(frame, "e-mail enviado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao enviar e-mail: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }

   
    private void sendNoteToServer(String title, String content, String email) throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL("http://localhost:8080/notepad/save?email=" + email);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = String.format("{\"title\": \"%s\", \"content\": \"%s\"}", title, content);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Erro no envio: " + responseCode);
        }
    }

}

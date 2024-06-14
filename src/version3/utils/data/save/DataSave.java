package version3.utils.data.save;

import java.io.*;

import version3.user.management.UserManagement;

public class DataSave {


    // TODO
    public static boolean csvDeleteLine(String filePath, int lineNumber) {
        File inputFile = new File(filePath);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;
            int currentLineNumber = 0;

            while ((currentLine = reader.readLine()) != null) {
                // Skip the line to be deleted
                if (currentLineNumber != lineNumber) {
                    writer.write(currentLine);
                    writer.newLine();
                }
                currentLineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Supprime le fichier original
        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
            return false;
        }

        // Rename the new file to the original file
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temporary file");
            return false;
        }

        return true;
    }

    public static UserManagement loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (UserManagement) in.readObject();
        }
    }

    // Méthodes de sérialisation et de désérialisation
    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    
}

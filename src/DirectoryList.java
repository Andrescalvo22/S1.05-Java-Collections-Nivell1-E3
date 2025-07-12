import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DirectoryList {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a directory path.");
            return;
        }

        String basePath = System.getProperty("user.dir");
        String fullPath = basePath + File.separator + args[0];

        File dir = new File(fullPath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("The path is not a valid directory: " + args[0]);
            return;
        }

        StringBuilder content = new StringBuilder();
        listDirectory(dir, "", content);

        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        String fileName = "listing_" + date + ".txt";

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content.toString());
            writer.close();
            System.out.println("File saved as: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void listDirectory(File folder, String indent, StringBuilder content) {
        File[] items = folder.listFiles();
        if (items == null) return;

        // Sort manually
        Arrays.sort(items, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        for (File item : items) {
            String type = item.isDirectory() ? "[D]" : "[F]";
            String modifiedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.lastModified());

            content.append(indent)
                    .append(type).append(" ")
                    .append(item.getName())
                    .append(" - ")
                    .append(modifiedDate).append("\n");

            if (item.isDirectory()) {
                listDirectory(item, indent + "    ", content);
            }
        }
    }
}

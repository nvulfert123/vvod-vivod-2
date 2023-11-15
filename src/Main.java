import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
    String path1 = "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save1.dat";
    String path2 = "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save2.dat";
    String path3 = "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save3.dat";

    GameProgress gameProgress1 = new GameProgress(100, 1, 88, 32123.2);
    GameProgress gameProgress2 = new GameProgress(50, 2, 5, 873.9);
    GameProgress gameProgress3 = new GameProgress(28, 3, 23, 666.6);

    saveGame(path1, gameProgress1);
    saveGame(path2, gameProgress2);
    saveGame(path3, gameProgress3);
    zipFiles("C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\savegames.zip", Arrays.asList(
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save1.dat",
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save2.dat",
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save3.dat"));
    deleteFiles(Arrays.asList(
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save1.dat",
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save2.dat",
            "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\save3.dat"));
    }
    private static void saveGame(String path, GameProgress gameProgress){
        try(FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        }
    private static void zipFiles(String zipFilePath, List<String> filesToZip) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String filePath : filesToZip) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }

                    zos.closeEntry();
                    System.out.println(filePath + " добавлен в архив.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(" все файлы добавлены в архив " + zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private static void deleteFiles(List<String> filesToKeep) {
    String savegamesPath = "C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames";

    try {
        Files.list(Path.of(savegamesPath))
                .filter(path -> !filesToKeep.contains(path.getFileName().toString()))
                .filter(path -> !path.toString().equals("C:\\Users\\kikeg\\IdeaProjects\\ustanovka\\Games\\savegames\\savegames.zip"))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                        System.out.println("Файл удалён: " + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
/*
 добавить:
  сохранение формата файла
  графическую часть
  ввод нового расположения
 */
import java.io.*;

public class FileRename {

    public static void main(String[] args) {
        System.out.println("Write filename...");
        String fileName  = "C:\\Users\\Альберт\\YandexDisk\\Сочи 2016\\";
        File folderPath = new File(fileName);
        File [] filesList = folderPath.listFiles();

        for (File fl : filesList
        ) {
            String firstFile = fl.getAbsolutePath();
            System.out.println(firstFile);
            String oneLine = LineRead(firstFile);
            String searchedDate = new String();

            if (oneLine!= null) {
                System.out.print(fl.getName() + " Date searching... ");
                searchedDate = DateSearch(oneLine);
                if (searchedDate != null) {
                    System.out.println(searchedDate + " found");
                    Renaming(fl, fileName + searchedDate);
                }else System.out.println("Empty Date");
            } else System.out.println("Empty File");
            //System.out.println(folderPath.getName() + " _ " + fl.getName() + " _ " + searchedDate);

        }


    }

    private static String LineRead (String FileName) {
        try (FileInputStream FileStream = new FileInputStream(FileName)){
            BufferedReader br = new BufferedReader(new InputStreamReader(FileStream));
            String strLine;
            strLine = br.readLine();
            if (!strLine.contains("Exif")) return null;
            //System.out.println(strLine);
            for (int i = 0; i < 8; i++) {
                if ((!strLine.contains(":")) || (strLine.contains("Photoshop")) || (strLine.contains("Viewer"))) {
                    strLine = br.readLine();
                    //System.out.println(strLine);
                } else break;
            }
            return strLine;

        }catch (IOException e){
            System.out.println("Reading Error");
        }
        return null;
    }

    private static String DateSearch (String oneLine) {
        //System.out.println(oneLine);
        int position = oneLine.indexOf(":",3);
        if ((position - 3 < 0) && (position + 19 < oneLine.length())) {
            //System.out.println();
            System.out.println("Date not found");
            return null;
        }
        char [] charArr = new char [19];
        oneLine.getChars(position - 4, position + 15, charArr,0);
        StringBuilder str = new StringBuilder().append(charArr,0,4).append(charArr,5,2)
                .append(charArr,8,2).append('_').append(charArr,11,2)
                .append(charArr,14,2).append(charArr,17,2);
        return str.toString();
    }

    private static void Renaming (File file, String newFileName) {
        //boolean success = false;
        try {
            File newFile = new File(newFileName + ".jpg");
            //System.out.println(newFile.getName());
            if (!(newFile.exists()) && (file.renameTo(newFile))){
                System.out.println("File " + file.getName() + " successfully renamed to " + newFile.getName());
            } else System.out.println("Renaming wrong");
        }catch (Exception e){
            System.out.println("Renaming Error");
        }
    }
}

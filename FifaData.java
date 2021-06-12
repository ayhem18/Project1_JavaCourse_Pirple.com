package ayhem_bouabid_yeyeMan;

import java.io.*;


public class FifaData {

    public static void  main (String[] args){

        try {
            InputStream inputStream = new FileInputStream("data.cvs");

            OutputStream outputStream= new FileOutputStream("fifa-tab.tsv");

            // the inputStream reads the files byte by byte which enables to replace every comma character by a tab (code ascii==9)
            // the outputStream writes to files
            while (inputStream.available() > 0) {
                int data=inputStream.read();
                // if the char read is different  comma (code ascii != 44) then we write that particular character

                if(data!=44)
                outputStream.write(data);

                // otherwise we write a tab
                else{
                    outputStream.write((char)(9));
                }

            }

            inputStream.close();
            outputStream.close();

        } catch(IOException e){
            System.out.println("Sorry for that !!");
        }

    }
}

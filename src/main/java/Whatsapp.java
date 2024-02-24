import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Whatsapp {

    public static void copyPasteText() throws InterruptedException, AWTException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text you want to send");
        //String text = scanner.nextLine();
        String text = "Sahil is a hoshiyaar person";

        StringSelection selectionString = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selectionString, null);

        TimeUnit.MILLISECONDS.sleep(2000); // till 2 sec open where you want to paste text

        Robot robot = new Robot();
        // press control + V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        // release control + V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

}

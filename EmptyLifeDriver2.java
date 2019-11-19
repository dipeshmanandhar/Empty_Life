import javax.swing.JFrame;
import java.io.*;
import java.util.*;
import java.awt.*;
public class EmptyLifeDriver2 extends JFrame
{
   public EmptyLifeDriver2() throws IOException
   {
      setSize(Toolkit.getDefaultToolkit().getScreenSize());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      EmptyLifePanel2 deep = getPanel();
      deep.setFocusable(true);
      deep.grabFocus();
      deep.requestFocus();
      setContentPane(deep);
      setVisible(true);
   }
   public static EmptyLifePanel2 getPanel() throws IOException
   {
      Scanner input = new Scanner(System.in);
      int w,h;
      String opt;
      System.out.print("Enter Maze Width: ");
      w=input.nextInt();
      while(w<1)
      {
         System.out.println("Invalid. Try again: ");
         w=input.nextInt();
      }
      System.out.print("Enter Maze Height: ");
      h=input.nextInt();
      while(h<1)
      {
         System.out.println("Invalid. Try again: ");
         h=input.nextInt();
      }
      System.out.print("Turn on kid mode? ");
      opt=input.next().toLowerCase();
      while(!opt.equals("y") && !opt.equals("n")&& !opt.equals("yes")&& !opt.equals("no"))
      {
         System.out.println("Invalid. Try again: ");
         opt=input.next().toLowerCase();
      }
      return new EmptyLifePanel2(h,w,opt.equals("y")||opt.equals("yes"));
   }
   public static void main(String[] arg) throws IOException
   {
      new EmptyLifeDriver2();
   }
}
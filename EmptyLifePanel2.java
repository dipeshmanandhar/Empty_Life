import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class EmptyLifePanel2 extends JPanel implements KeyListener
{
   private final boolean EASY;
   private String[][] board;
   private int endRow,endCol,SIZE;
   public EmptyLifePanel2(int r,int c,boolean diff)
   {
      EASY=diff;
      Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
      if(r>=c/1.5)
         SIZE=(int)(screenSize.getHeight()-15/(r*2+3));
      else
         SIZE=(int)((screenSize.getWidth()-15)/(c*2+3));
      addKeyListener(this);
      board=new String[r*2+1][c*2+1];
      initialize(board);
      setUp(board);
      endRow=board.length-1-getRow(board," o ");
      endCol=board[0].length-1-getCol(board," o ");
      while(SIZE*(board.length+6)>=screenSize.getHeight())
         SIZE--;
   }
   private void initialize(String[][] board)
   {
      for(int r=0;r<board.length;r++)
      {
         for(int c=0;c<board[0].length;c++)
         {
            if(r%2==0)
            {
               if(c%2==0)
                  board[r][c]="+";
               else
                  board[r][c]="---";
            }
            else
            {
               if(c%2==0)
                  board[r][c]="|";
               else
                  board[r][c]="   ";
            }
         }
      }
   }
   private void setUp(String[][] board)
   {
      int r,c;
      int[] wallRows=new int[0];
      int[] wallCols=new int[0];
      if(Math.random()<0.5)
      {
         r=1;
         c=(int)(Math.random()*(board[0].length-1)/2)*2+1;
         board[0][c]="   ";
         board[board.length-1][board[0].length-1-c]="   ";
      }
      else
      {
         r=(int)(Math.random()*(board.length-1)/2)*2+1;
         c=1;
         board[r][0]=" ";
         board[board.length-1-r][board[0].length-1]=" ";
      }
      board[r][c]=" A ";
      if(r>1 && board[r-1][c].equals("---"))
      {
         wallRows=append(wallRows,r-1);
         wallCols=append(wallCols,c);
      }
      if(r<board.length-2 && board[r+1][c].equals("---"))
      {
         wallRows=append(wallRows,r+1);
         wallCols=append(wallCols,c);
      }
      if(c>1 && board[r][c-1].equals("|"))
      {
         wallRows=append(wallRows,r);
         wallCols=append(wallCols,c-1);
      }
      if(c<board[0].length-2 && board[r][c+1].equals("|"))
      {
         wallRows=append(wallRows,r);
         wallCols=append(wallCols,c+1);
      }
      while(wallRows.length!=0)
      {
         int count=0;
         int index=(int)(Math.random()*wallRows.length);
         r=wallRows[index];
         c=wallCols[index];
         if(board[r][c].equals("---"))
         {
            if(board[r-1][c].equals("   "))
               count++;
            if(board[r+1][c].equals("   "))
               count++;
         }
         else if(board[r][c].equals("|"))
         {
            if(board[r][c-1].equals("   "))
               count++;
            if(board[r][c+1].equals("   "))
               count++;
         }
         if(count==1)
         {
            if(board[r][c].equals("---"))
            {
               board[r][c]="   ";
               if(board[r-1][c].equals("   "))
                  r--;
               else
                  r++;
            }
            else if(board[r][c].equals("|"))
            {
               board[r][c]=" ";
               if(board[r][c-1].equals("   "))
                  c--;
               else
                  c++;
            }
            if(r>1 && board[r-1][c].equals("---"))
            {
               wallRows=append(wallRows,r-1);
               wallCols=append(wallCols,c);
            }
            if(r<board.length-2 && board[r+1][c].equals("---"))
            {
               wallRows=append(wallRows,r+1);
               wallCols=append(wallCols,c);
            }
            if(c>1 && board[r][c-1].equals("|"))
            {
               wallRows=append(wallRows,r);
               wallCols=append(wallCols,c-1);
            }
            if(c<board[0].length-2 && board[r][c+1].equals("|"))
            {
               wallRows=append(wallRows,r);
               wallCols=append(wallCols,c+1);
            }
            board[r][c]=" 1 ";
         }
         wallRows=deleteAt(wallRows,index);
         wallCols=deleteAt(wallCols,index);
      }
      for(int row=0;row<board.length;row++)
         for(int col=0;col<board[0].length;col++)
            if(board[row][col].equals(" 1 "))
               board[row][col]="   ";
      board[getRow(board," A ")][getCol(board," A ")]=" o ";
   }
   private int[] append(int[] a,int toAdd)
   {
      int[] result=new int[a.length+1];
      for(int i=0;i<a.length;i++)
         result[i]=a[i];
      result[result.length-1]=toAdd;
      return result;
   }
   private int[] deleteAt(int[] a,int index)
   {
      int[] result=new int[a.length-1];
      for(int i=0;i<result.length;i++)
         if(i<index)
            result[i]=a[i];
         else
            result[i]=a[i+1];
      return result;
   }
   private int getRow(String[][] board,String search)
   {
      for(int r=0;r<board.length;r++)
         for(int c=0;c<board[0].length;c++)
            if(board[r][c].equals(search))
               return r;
      return -1;
   }
   private int getCol(String[][] board,String search)
   {
      for(int r=0;r<board.length;r++)
         for(int c=0;c<board[0].length;c++)
            if(board[r][c].equals(search))
               return c;
      return -1;
   }
   private void swap(String[][] board,int r1,int c1,int r2,int c2)
   {
      if(!EASY)
      {
         String temp;
         temp=board[r1][c1];
         board[r1][c1]=board[r2][c2];
         board[r2][c2]=temp;
      }
      else
      {
         board[r2][c2]=board[r1][c1];
         board[r1][c1]=" x ";
      }
   }
   public void paintComponent(Graphics g)
   {
      Graphics2D g2=(Graphics2D)g;
      super.paintComponent(g);
      g2.setStroke(new BasicStroke(SIZE/4));
      for(int r=0;r<board.length;r++)
      {
         for(int c=0;c<board[0].length;c++)
         {
            if(board[r][c].equals("---"))
            {
               g2.setColor(Color.BLACK);
               g2.drawLine(c*SIZE,(r+1)*SIZE,(c+2)*SIZE,(r+1)*SIZE);
            }
            else if(board[r][c].equals("|"))
            {
               g2.setColor(Color.BLACK);
               g2.drawLine((c+1)*SIZE,r*SIZE,(c+1)*SIZE,(r+2)*SIZE);
            }
            else if(board[r][c].equals(" o "))
            {
               g2.setColor(Color.BLUE);
               g2.fillOval(c*SIZE+SIZE/2,r*SIZE+SIZE/2,SIZE,SIZE);
            }
            else if(board[r][c].equals(" x "))
            {
               g2.setColor(Color.RED);
               g2.fillOval(c*SIZE+SIZE/2,r*SIZE+SIZE/2,SIZE,SIZE);
            }
         }
      }
   }
   public void keyPressed(KeyEvent e)
   {
      int k,r,c;
      boolean exit=false;
      k=e.getKeyCode();
      r=getRow(board," o ");
      c=getCol(board," o ");
      if(k==KeyEvent.VK_UP && r>1 && board[r-1][c].equals("   "))
      {
         swap(board,r,c,r-2,c);
      }
      else if(k==KeyEvent.VK_DOWN && board[r+1][c].equals("   "))
      {
         if(r==endRow && c==endCol && r==board.length-2)
            exit=true;
         else if(r<board.length-2)
            swap(board,r,c,r+2,c);
      }
      else if(k==KeyEvent.VK_LEFT && c>1 && board[r][c-1].equals(" "))
      {
         swap(board,r,c,r,c-2);
      }
      else if(k==KeyEvent.VK_RIGHT && board[r][c+1].equals(" "))
      {
         if(r==endRow && c==endCol && c==board[0].length-2)
            exit=true;
         else if(c<board[0].length-2)
            swap(board,r,c,r,c+2);
      }
      else if(k==KeyEvent.VK_ESCAPE)
      {
         System.out.println("Thank you for playing >.<");
         System.exit(1);
      }
      if(exit)
      {
         System.out.println("YOU WIN @.@");
         System.exit(1);
      }
      repaint();
   }
   public void keyReleased(KeyEvent e)
   {
   }
   public void keyTyped(KeyEvent e)
   {
   }
}
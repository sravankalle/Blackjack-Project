import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.io.File;

public class GamePanel extends JPanel implements ActionListener{
    

    /**
     *
     */
    Random random=new Random();
    private static final long serialVersionUID = 1L;
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT=500;
    Image backgroundImage,card_behind,card_behind_mod;
    Image user_cards[]=new Image[10];
    Image dealer_cards[]=new Image[10];
    int user_card_count=0;
    int dealer_card_count=0;
    JButton hit;
    JButton stand;
    JButton startgame;
    int cardIndex;
    String pickedCard;
    int user_card_value;
    String All_Cards[]={"AH.png","2H.png","3H.png","4H.png","5H.png","6H.png","7H.png","8H.png","9H.png","10H.png","JH.png","QH.png","KH.png",
                        "AD.png","2D.png","3D.png","4D.png","5D.png","6D.png","7D.png","8D.png","9D.png","10D.png","JD.png","QD.png","KD.png",
                        "AC.png","2C.png","3C.png","4C.png","5C.png","6C.png","7C.png","8C.png","9C.png","10C.png","JC.png","QC.png","KC.png",
                        "AS.png","2S.png","3S.png","4S.png","5S.png","6S.png","7S.png","8S.png","9S.png","10H.png","JS.png","QS.png","KS.png"};
    static int user_hit_cardX=100;
    static int user_hit_cardY=380;
    static int dealer_hit_cardX=100;
    static int dealer_hit_cardY=200;
    final String path = "C:/Users/srava/DocumentsJava Project/Blackjack-Project/Black Jack Game/images/PNG";
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        backgroundImage= new ImageIcon(path + "/bjtable.jpg").getImage();
        // card_behind=new ImageIcon("D://java online//Black Jack Game//images//PNG//red_back.png").getImage();
        // card_behind_mod=card_behind.getScaledInstance(50, 76, java.awt.Image.SCALE_SMOOTH);
        hit=new JButton("Hit");
        stand=new JButton("Stand");
        startgame=new JButton("Start Game");
        // hit.setLayout(null);
        // hit.setBounds(600,400,50,20);
        this.add(startgame);
        this.add(hit);
        this.add(stand);

        hit.addActionListener(this);
        stand.addActionListener(this);
    }
    
    public void startGame()
    {
        System.out.println("StartGame");
        dealer_cards[dealer_card_count]=(new ImageIcon(path + "/red_back.png").getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        dealer_card_count++;
        dealer_hit_card();
        for(int i=0;i<2;i++)
        {
            user_hit_card();
        }
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D=(Graphics2D) g;
        g2D.drawImage(backgroundImage,-50,0,null);
        for(int i=0;i<user_card_count;i++)
        {
            g2D.drawImage(user_cards[i],user_hit_cardX,user_hit_cardY,null);
            user_hit_cardX+=20;
        }
        for(int i=0;i<dealer_card_count;i++)
        {
            g2D.drawImage(dealer_cards[i],dealer_hit_cardX,dealer_hit_cardY,null);
            dealer_hit_cardX+=20;
        }
        dealer_hit_cardX=100;
        user_hit_cardX=100;
       
    }
    public void dealer_hit_card()
    {
        cardIndex=random.nextInt(All_Cards.length);
        pickedCard=All_Cards[cardIndex];
        dealer_cards[dealer_card_count]=(new ImageIcon(path + "/"+  pickedCard).getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        dealer_card_count++;
    }
    public void user_hit_card()
    {
        cardIndex=random.nextInt(All_Cards.length);
        pickedCard=All_Cards[cardIndex];
        System.out.println(pickedCard);
        user_card_value+=get_card_value();
        user_cards[user_card_count]=(new ImageIcon(path + "/" + pickedCard).getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        user_card_count++;
        /* printArray(user_cards); */
        System.out.println(user_card_value);
    }
    public void user_stand(){
        System.out.println("Stand clicked");
    }
    public int get_card_value()
    {
        char card_value_str=pickedCard.charAt(0);
        int val;
        if(card_value_str=='A')
        {
            return 11;
        }
        else if(card_value_str=='K' || card_value_str=='Q' || card_value_str=='J' || card_value_str=='1')
        {
            return 10;
        }
        else 
        {
            val=Character.getNumericValue(card_value_str);
            return val;
        }
    }
    public void checkConditions(){
        if(user_card_value > 21){
            System.out.println("Game over!");
        }
        if(user_card_value == 21){
            System.out.println("You win!");
        }
    }
    public void actionPerformed(ActionEvent ae)
    {
        String command=ae.getActionCommand();
        if(command=="Hit")
        {  
            checkConditions();
            user_hit_card();
            repaint();
        }else if(command == "Stand"){
            checkConditions();
            user_stand();
        }else if(command=="Start Game"){
            startGame();
            repaint();
        }
    }

    //Debug Methods

    <T> void printArray(T[] arr){
        for(int i = 0; i< arr.length; i++){
            System.out.println(">>"  + arr[i]);
        }
    }
}

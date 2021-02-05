import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener{
    

    /**
     *
     */
    Random random=new Random();
    private static final long serialVersionUID = 1L;// i dont know what this line of code does, but it was a quick fix to some error.
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT=500;
    Image backgroundImage,card_behind,card_behind_mod;
    Image user_cards[]=new Image[20];
    Image dealer_cards[]=new Image[20];
    int user_card_count=0;
    int dealer_card_count=0;
    JButton hit;
    JButton stand;
    JButton startgame;
    int cardIndex;
    String pickedCard;
    String user_card_names[]=new String[20];
    String dealer_card_names[]=new String[20];
    int user_card_value;
    int dealer_card_value;
    String All_Cards[]={"AH.png","2H.png","3H.png","4H.png","5H.png","6H.png","7H.png","8H.png","9H.png","10H.png","JH.png","QH.png","KH.png",
                        "AD.png","2D.png","3D.png","4D.png","5D.png","6D.png","7D.png","8D.png","9D.png","10D.png","JD.png","QD.png","KD.png",
                        "AC.png","2C.png","3C.png","4C.png","5C.png","6C.png","7C.png","8C.png","9C.png","10C.png","JC.png","QC.png","KC.png",
                        "AS.png","2S.png","3S.png","4S.png","5S.png","6S.png","7S.png","8S.png","9S.png","10H.png","JS.png","QS.png","KS.png"};
    static int user_hit_cardX=40;
    static int user_hit_cardY=310;
    static int dealer_hit_cardX=40;
    static int dealer_hit_cardY=60;
    static int SHIFT_VALUE=50;
    boolean running=true,can_hit=false,can_start=true,can_stand=false,can_compare=false;
    static final String path="images//PNG//";
    //constructor to add all the action listeners and their postions
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setLayout(null);
        backgroundImage= new ImageIcon(path+"bjtable.jpg").getImage();
        card_behind=(new ImageIcon(path+"red_back.png").getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        hit=new JButton("Hit");
        stand=new JButton("Stand");
        startgame=new JButton("Start Game");
        startgame.setBounds(550,350,100,30);
        stand.setBounds(550,400,100,30);
        hit.setBounds(550,450,100,30);
        this.add(startgame);
        this.add(hit);
        this.add(stand);
        running=true;
        hit.addActionListener(this);
        stand.addActionListener(this);
        startgame.addActionListener(this);
    }
    
    //Starts the game
    public void startGame()
    {
        if(can_start)
        {
            dealer_cards[dealer_card_count]=(new ImageIcon(path+"red_back.png").getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
            dealer_card_names[dealer_card_count]=pickedCard;
            // dealer_card_value+=get_card_value();
            dealer_card_count++;
            dealer_hit_card();
            for(int i=0;i<2;i++)
            {
                user_hit_card();
            }
            can_start=false;
            can_hit=true;
            can_stand=false;

            //To test for black jack in user cards(first win situation,the following code is not needed in the final implementation)
                // pickedCard="AS_mod.png";
                // user_cards[user_card_count]=(new ImageIcon(path+"AS_mod.png").getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
                // user_card_count++;
                // user_card_value+=get_card_value();
                // pickedCard="KC_mod.png";
                // user_cards[user_card_count]=(new ImageIcon(path+"KC_mod.png").getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
                // user_card_count++;
                // user_card_value+=get_card_value();

        }
    }

    //crucial part of the code to display all the images (for images use imageobserver as 'this', or else the image will not be displayed when needed)
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2D=(Graphics2D) g;
        g2D.drawImage(backgroundImage,-50,0,this);
        g2D.drawImage(card_behind,565,60,this);
        if(running)
        {
            g2D.setColor(Color.red);
            g2D.setFont(new Font("Impact",Font.PLAIN,20));
            g2D.drawString("Dealer's Cards:",30,30);
            g2D.drawString("Your Cards:",30,280);
            g2D.setColor(new Color(0, 158, 0));
            g2D.fillRoundRect(30, 50, 500, 170,30,30);
            g2D.fillRoundRect(30,300,500,170,30,30);
            g.setColor(Color.red);
            g.setFont(new Font("Impact",Font.PLAIN,15));
            //only after the user clicks stand button
            if(can_stand==false && user_card_count>=1)
            {
                g.drawString("Dealer Card Value: "+dealer_card_value,420,45);
                GameOver(g2D);
            }
            //display user cards
            for(int i=0;i<user_card_count;i++)
            {
                g2D.drawImage(user_cards[i],user_hit_cardX,user_hit_cardY,this);
                user_hit_cardX+=SHIFT_VALUE;
            }
            g.setFont(new Font("Impact",Font.PLAIN,15));
            g.drawString("Your Cards Value: "+user_card_value,420,295);

            //display dealer cards
            for(int i=0;i<dealer_card_count;i++)
            {
                g2D.drawImage(dealer_cards[i],dealer_hit_cardX,dealer_hit_cardY,this);
                dealer_hit_cardX+=SHIFT_VALUE;
            }

            //to reset the x coordinate for displaying the cards again in the same order
            dealer_hit_cardX=40;
            user_hit_cardX=40;
        }
        else
        {
            GameOver(g2D);
        }
    }

    //hits the dealer a card, can also be used when the user wishes to stand. 
    public void dealer_hit_card()
    {
        cardIndex=random.nextInt(All_Cards.length);
        pickedCard=All_Cards[cardIndex];
        System.out.println("Dealer Card Count: " + dealer_card_count);
        dealer_card_names[dealer_card_count]=pickedCard;
        dealer_card_value+=get_card_value();
        System.out.println("Dealer Card value: " + dealer_card_value);
        dealer_cards[dealer_card_count]=(new ImageIcon(path+""+pickedCard).getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        dealer_card_count++;
        
    }

    //hits the dealer a card, stops execution when the user wishes to stand.
    public void user_hit_card()
    {
        cardIndex=random.nextInt(All_Cards.length);
        pickedCard=All_Cards[cardIndex];
        System.out.println(pickedCard);
        user_card_value+=get_card_value();
        user_card_names[user_card_count]=pickedCard;
        user_cards[user_card_count]=(new ImageIcon(path+""+pickedCard).getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        user_card_count++;
        System.out.println("Card count: "+user_card_count);
        System.out.println("Card Value: "+user_card_value);
    }

    //Gets the value of the card which is randomly picked from the main list of cards, a common string variable pickedCard is used for
    //hitting both the user and the dealer with cards.
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

    //When the user wishes to stand, then the dealers first card(red_back.png) is switched with a random card to continue the game
    public void dealer_stand()
    {
        
        can_stand = false;
        can_compare=true;
        cardIndex=random.nextInt(All_Cards.length);
        pickedCard=All_Cards[cardIndex];
        dealer_cards[0]=dealer_cards[dealer_card_count]=(new ImageIcon(path+""+pickedCard).getImage()).getScaledInstance(100, 152, java.awt.Image.SCALE_SMOOTH);
        dealer_card_names[0]=pickedCard;
        dealer_card_value+=get_card_value();
        /* dealer_card_value=0;
        for(int i=0;i<dealer_card_count;i++)
        {
            pickedCard=dealer_card_names[i];
            System.out.println("\nNew Card: "+pickedCard);
            dealer_card_value+=get_card_value();
        } */
        System.out.println(dealer_card_value);
        System.out.println(dealer_card_count);
        while(dealer_card_value <= 17){
            dealer_hit_card();
        }
        repaint();
    }

    //The graphics object from the paint method is passed here to show game over and also the respective message
    //This method also deals with the situation when the user gets a blackjack(21 in the start of game before hitting)
    //some events are similar to the paint method, so maybe we can find a solution to merge the two methods into one(not needed for now)
    public void GameOver(Graphics g)
    {
        g.setColor(new Color(0, 158, 0));
        g.fillRoundRect(30, 50, 500, 170,30,30);
        g.fillRoundRect(30,300,500,170,30,30);
        g.setColor(Color.red);
        g.setFont(new Font("Calibri",Font.BOLD,60));
        FontMetrics GOmetrics = getFontMetrics(g.getFont());
        if(user_card_value>21)
        {
            //For printing the text in the center of the panel
            g.drawString("Game Over",(SCREEN_WIDTH-GOmetrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2+15);
            //For printing the text in the center of the panel
            g.setFont(new Font("Calibri",Font.BOLD,20));
            FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
            g.drawString("Your Cards Value is over 21",(SCREEN_WIDTH-ScoreMetrics.stringWidth("Your Cards Value is over 21"))/2,(SCREEN_HEIGHT/2)+35);
        }
        else if(user_card_value==21 && user_card_count==2)
        {
            //For printing the text in the center of the panel
            g.drawString("Black Jack!!",(SCREEN_WIDTH-GOmetrics.stringWidth("Black Jack!!"))/2,SCREEN_HEIGHT/2+15);
            //For printing the text in the center of the panel
            g.setFont(new Font("Calibri",Font.BOLD,20));
            FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
            g.drawString("You Win!!",(SCREEN_WIDTH-ScoreMetrics.stringWidth("You Win!!"))/2,SCREEN_HEIGHT/2+35);
        }
        // else if((user_card_value <= 21 && user_card_value > dealer_card_value) || dealer_card_value > 21)
        // {
        //     //For printing the text in the center of the panel
        //     g.setFont(new Font("Calibri",Font.BOLD,20));
        //     g.drawString("You Win!",(SCREEN_WIDTH-GOmetrics.stringWidth("You Win!"))/2,SCREEN_HEIGHT/2+15);
        // }
        // else if(!can_stand && user_card_count>=2 && dealer_card_count>=2 && !can_hit)
        else if(!can_stand)
        {
            if(dealer_card_value <= 21 && dealer_card_value > user_card_value)
            {
                g.drawString("Game Over",(SCREEN_WIDTH-GOmetrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2+15);
                //For printing the text in the center of the panel
                g.setFont(new Font("Calibri",Font.BOLD,20));
                FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
                g.drawString("Dealer Wins!",(SCREEN_WIDTH-ScoreMetrics.stringWidth("Dealer Wins!"))/2,(SCREEN_HEIGHT/2)+35);
            }
            else if((user_card_value <= 21 && user_card_value > dealer_card_value) || dealer_card_value > 21 && can_compare)
            {
                if(can_compare)
                {
                    g.setFont(new Font("Calibri",Font.BOLD,20));
                    FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
                    g.drawString("You Win!",(SCREEN_WIDTH-ScoreMetrics.stringWidth("You Win!"))/2,SCREEN_HEIGHT/2+15);
                }
                else
                {
                    g.setFont(new Font("Calibri",Font.BOLD,20));
                    FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
                    g.drawString("You have more chances of Winning",(SCREEN_WIDTH-ScoreMetrics.stringWidth("You have more chances of Winning"))/2,SCREEN_HEIGHT/2+15);
                    running=true;
                    can_stand=true;
                }
                //For printing the text in the center of the panel
            }
            else if(dealer_card_value == user_card_value)
            {
                //For printing the text in the center of the panel
                g.setFont(new Font("Calibri",Font.BOLD,20));
                FontMetrics ScoreMetrics=getFontMetrics(g.getFont());
                g.drawString("Tie!",(SCREEN_WIDTH-ScoreMetrics.stringWidth("Tie!"))/2,SCREEN_HEIGHT/2+15);
            }
        }
        // if(!can_stand && !can_compare)
        // {
        //     can_stand=true;
        //     running=true;
        // }
        
        for(int i=0;i<user_card_count;i++)
        {
            g.drawImage(user_cards[i],user_hit_cardX,user_hit_cardY,this);
            user_hit_cardX+=SHIFT_VALUE;
        }
        for(int i=0;i<dealer_card_count;i++)
        {
            g.drawImage(dealer_cards[i],dealer_hit_cardX,dealer_hit_cardY,this);
            dealer_hit_cardX+=SHIFT_VALUE;
        }
        //labeling of the cards set (dealers and users)
        g.setFont(new Font("Impact",Font.PLAIN,20));
        g.drawString("Dealer's Cards:",30,30);
        g.drawString("Your Cards:",30,280);
        //resetting the x coordinate to print the cards
        user_hit_cardX=40;
        dealer_hit_cardX=40;
    }

    //This method checks if the user card value is greater than 21 or equal to 21 (needs some tuning for further steps later.)
    public void checkConditions()
    {
        char if_ace;
        if(user_card_value>21)
        {
            for(int i=0;i<user_card_count;i++)
            {
                if_ace=user_card_names[i].charAt(0);
                if(if_ace=='A')
                {
                    user_card_names[i]='1'+user_card_names[i];
                    user_card_value=user_card_value-10;
                    return;
                }
            }
        }
        if(user_card_value >= 21){
            running=false;
            can_stand=false;
        }
        // if(user_card_value == 21){
        //     running=false;
        //     can_stand=false;
        // }
    }

    //This method is compulsory and checks which jbutton is clicked.
    public void actionPerformed(ActionEvent ae)
    {
        String command=ae.getActionCommand();
        if(command=="Hit" && running)
        { 
            if(can_hit)
            {
                user_hit_card();
                checkConditions();
                repaint();
            } 
        }
        else if(command=="Start Game")
        {
            startGame();
            can_stand=true;
            checkConditions();
            repaint();
        }
        else if(command=="Stand")
        {
            if(can_stand)
            {
                can_hit=false;
                dealer_stand();
                can_stand=false;
            }
        }
    }
}

//Comments for the next steps needed in the project.

/* - When the user cards is exactly 21 after hitting, there is no reaction.
   - After the stand, the dealer's cards value must be calculated,
        if the dealer cards value is less than 17(excluding), then cards are given until the 
        dealer's cards value is greater than or equal to 17 .

        else the dealer cards value must be checked.
            -if the dealers cards value is greater than the users cards value then user loses.
            -else if the dealers cards value is same as the users cards value then its a draw.
            -else the user wins.
*/


//Extra concept of split(need discussion since it is tough to implement, but is a crucial part of the game)(After the basic is done)

/*  -When the user gets two cards of the same value, he can either split or continue,
    -if the user continues then the game will go on as usual

    -else there will be 2 sets of cards which we need to deal with and the value of both must be calculated and displayed.
        --need discussion and can be implemented if the basic project is done--

    */


//Extra concept for animations

/*  -When the game has started, we can add a small animation that the cards are drawn from the deck of cards(red_back.png) in the top right corner 
        which is already added as image.        
*/
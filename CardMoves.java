import java.util.Scanner;
import java.util.*;
public class CardMoves {
  public static void main(String[] args) {
    System.out.println("You are playing a 'race' game with the computer to see who can get a sequence of face down cards all face up first, or get the deck's last remaining card on the rightmost side to be face down (in that case, you cannot make any more 'moves')\n");
    System.out.println("HOW THE GAME WORKS:");
    System.out.println("A sequence of random cards are initially face down (represented by 1).");
    System.out.println("\nA move consists of turning a face down card face up (represented by 0) and turning over the card to the right.");
    System.out.println("\nRegardless of the amount of cards and choice of the cards to turn, the algorithm sequence must terminate.\n");
    System.out.println("However, in this game, you will compete with the computer to see who can get all cards to a condition on where the sequence of moves must terminate.");
    System.out.println("\nFirst, we must flip a coin to decide who goes first.");
    Scanner s = new Scanner(System.in);
    System.out.println("\nEnter 'heads' or 'tails' to decide who goes first: ");
    String coin = s.nextLine();
    int chosen = 0;
    if(coin.equals("heads")){
      chosen = 1;
    }
    int actual = (int) Math.random() * 2;
    if(chosen == actual){
      System.out.println("You go first!");
      System.out.println("Before playing, you get to decide how many cards will be used.");
      System.out.println("The number of cards must be between 5 and 20.");
      System.out.print("How many cards do you want to use?   ");
      int numCards = s.nextInt();
      int trueCards = 0;
      if(numCards < 5){
        trueCards = 5;
      }
      else if (numCards > 20){
        trueCards = 20;
      }
      else{
        trueCards = numCards;
      }
      int [] player = createDeck(trueCards);
      int [] computer = createDeck(trueCards);
      boolean first = true;
      playGame(player, computer, first);
    }
    else{
      System.out.println("The computer goes first.");
      int randomCards = (int) (Math.random() * 14) + 5;
        int [] player = createDeck(randomCards);
        int [] computer = createDeck(randomCards);
        boolean first = false;
        System.out.print("Number of cards: " + computer.length);
        playGame(player, computer, first);
    }
  }

  //Methods are below:
  public static int[] createDeck(int numCards){
    int [] deck = new int [numCards];
    for(int i = 0; i < deck.length; i++){
      deck[i] = 1;
    }
    return deck;
  }
  
  public static void printDecks(int[] deckOne, int [] deckTwo){
    System.out.print("Player's deck → ");
    for (int i = 0; i < deckOne.length; i++){
      System.out.print(deckOne[i] + " ");
    }
    System.out.print("   ");
    for (int i = 0; i < deckTwo.length; i++){
      System.out.print(deckTwo[i] + " ");
    }
    System.out.print("← Opponent's deck");
  }

  public static void turn(int[] deck, int card){
    if(card >= 0 && card < deck.length){
      if(deck[card] == 1){
        deck[card] = 0;
      }
      else if(deck[card] == 0){
        deck[card] = 1;
      }
    }
  }

  public static void move(int[] deck, int card){
    if(deck[card] == 1){
      turn(deck, card);
      turn(deck, card + 1);
    }
  }

  public static void printDecimalfromDeck(int[] deck){
    long number = 0;
    int exp = deck.length - 1;
    int at = 0;
    while(exp >= 0 && at < deck.length){
      if(deck[at] == 1){
        number += Math.pow(2, exp);
      }
      exp--;
      at++;
    }
    System.out.print(number);
  }

  public static boolean checkifMustTerminate(int[] deck){
    for(int i = 0; i < deck.length - 1; i++){
      if(deck[i] == 1){
        return false;
      }
    }
    return true;
  }

  public static boolean checkifGameTerminates(int[] deckOne, int[] deckTwo){
    if(checkifMustTerminate(deckOne) || checkifMustTerminate(deckTwo)){
      return true;
    }
    return false;
  }

  //The game is below:
  public static void playGame(int[] player, int [] opponent, boolean first){
    while(checkifGameTerminates(player, opponent) == false){
      Scanner st = new Scanner(System.in);

      while (checkifGameTerminates(player, opponent) == false) {
          if (first) {
              System.out.print("\nSelect a card: ");
              int select = st.nextInt() - 1; // Adjust for 0-based index

              if (select >= 0 && select < player.length && player[select] == 1) {
                  move(player, select);
              } else {
                  System.out.println("You must select a valid face-down card.");
                  continue;
              }
          }
          // Computer's move
          int computerMove = (int) (Math.random() * opponent.length);
          move(opponent, computerMove);
          System.out.println();
          printDecks(player, opponent);
          first = true; // After the computer's turn, it's the player's turn
      }
    }
    System.out.println("\nGAME OVER.");
    System.out.print("\nThe winner is:  ");
    if(checkifMustTerminate(player)){
      System.out.println("You!");
    }
    else{
      System.out.println("The computer!");
    }
  }

}

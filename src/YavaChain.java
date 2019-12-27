/**
 * Created by gioperalto on 12/27/19.
 */
import java.util.Scanner;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class YavaChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to YavaChain\n--------------");
        System.out.println("How many blocks would you like to mine? (Default 3)");
        int blocks = in.nextInt();

        System.out.println("Choose your difficulty (Default 1)... Warning: Anything beyond 6 could take a while");
        difficulty = in.nextInt();

        for(int i = 0; i < blocks; i++) {
            if(i == 0) {
                blockchain.add(new Block(String.format("Block %d", i+1), "0"));
            } else {
                blockchain.add(
                    new Block(String.format("Block %d", i+1),
                    blockchain.get(blockchain.size()-1).hash)
                );
            }
            System.out.printf("\nTrying to Mine block %d... ", i+1);
            blockchain.get(i).mineBlock(difficulty);
        }

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nFull YavaChain: ");
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock, previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }

            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}

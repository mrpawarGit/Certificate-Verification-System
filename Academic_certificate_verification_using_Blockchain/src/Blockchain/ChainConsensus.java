package Blockchain;

import java.sql.Connection;


public class ChainConsensus {

    public static int block1, block2, block3, block4;
    public static String T_id1, T_id2, T_id3, T_id4;
    public static int Node2 = 0, Node3 = 0, FP = 0, Node4 = 0, Node1 = 0, num = 0;

    public static void Consensus(String data, Connection con,int id,String database) {
        try {

            block1 = Blockchain1.Blockchaindata(data, con,id,database);
            //System.out.println(id+"\tData=>"+block1);
            
            
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
//	    	Consensus("String plaindata");
    }
}

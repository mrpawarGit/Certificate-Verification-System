package Blockchain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class Blockchain1 {
	public static ArrayList<Block> blockchain1 = new ArrayList<Block>();
	public static int difficulty = 5;
        public static String Previsblk="";
        public static int Blockchaindata(String data,Connection con,int id,String database)
        {
        	try {
        
            String PrevHash=GetPreviousHash1(con);
            Dbconn.PrevHash1=PrevHash;
            
            blockchain1=GetChain1(con);
			// if genasis
            int size=blockchain1.size();
            if(PrevHash=="0")
            {
            blockchain1.add(new Block(data, "0"));}
            else
            {
            blockchain1.add(new Block(data, PrevHash));
            }
            System.out.println("System Mine the Current Transaction Block ");
            blockchain1.get(size).mineBlock1(difficulty);
            // validation block chain
            if(isChainValid1(con)){
           System.out.println(" BlockChain Valid "+id);
            Dbconn.blockchain1msg=1;
            String msg="Valid";
            Block.blocklist.put(id, msg);
            }
            else 
            {
            	 Dbconn.blockchain1msg=0;
            	 String msg="InValid";
                 Block.blocklist.put(id, msg);
                System.out.println("Blockchain is Invalid "+id);
            }
        	} catch (Exception e) {
				
				
			}
        	return Dbconn.blockchain1msg;
        }
         
	public static ArrayList<Block> GetChain1(Connection con) throws Exception
        {
            String previous="0";
            ArrayList<Block> wholeTransactionChain = new ArrayList<Block>();
          
            Statement stat = (Statement) con.createStatement();
            stat.executeQuery("select * from transhash");
            ResultSet rs = stat.getResultSet();
            int i=0;
            while (rs.next()) {
                if(i==0)
                wholeTransactionChain.add(new Block(rs.getString(2), "0"));
                else {
                wholeTransactionChain.add(new Block(rs.getString(2), previous));}
                previous=rs.getString(3);
                i++;
            }
            return wholeTransactionChain;
        }
        
      public static ArrayList<String> GetChainConsensus1(Connection  con) throws Exception
        {
            ArrayList<String> wholeTransactionChain = new ArrayList<>();
            
           
            Statement stat = (Statement) con.createStatement();
            stat.executeQuery("select * from transhash");
            ResultSet rs = stat.getResultSet();
             while (rs.next()) {
                 wholeTransactionChain.add(rs.getString(2)+","+rs.getString(3)+","+rs.getString(4));
            }
            return wholeTransactionChain;
        }
        // its give the state it is a genasis block or not
public static String GetPreviousHash1(Connection con)
{
            String finalhash="0";    
           try
           {
           
            Statement stat = (Statement) con.createStatement();
            stat.executeQuery("select * from transhash");
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
               finalhash= rs.getString(3);
              }
             }
catch(Exception ex)
{
}
   return finalhash;
}
	
// This is the strategy of consus algorithm, and its applicable for n nodes
        public static Boolean isChainValid1(Connection  con) throws Exception {
		 ArrayList<String> CompleteList =new ArrayList<>();
                 int flag=0;
                 String hashTarget = new String(new char[difficulty]).replace('\0', '0');
                 CompleteList =GetChainConsensus1(con);
                 for(int startpoint=0; startpoint<CompleteList.size(); startpoint++)
                 {
                 String [] parts= CompleteList.get(startpoint).toString().split(",");
                 String Chash=parts[2];
                 if(startpoint==0) Previsblk=parts[1];
                 else {
                    if(!Previsblk.equals(Chash))
                    {
                        flag=1;
                        System.out.println("Chash=>"+Chash);
                       // break;
                        
                    }
                  }
                     
               	if(!parts[1].substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				flag=1; break;
			}
                Previsblk=parts[1];
                 }
		if(flag==1)
                 return false;
                else
                    return true;
	}
}
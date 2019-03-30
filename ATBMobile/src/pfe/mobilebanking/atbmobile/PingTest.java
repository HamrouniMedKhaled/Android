package pfe.mobilebanking.atbmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class pingTest {
	
	public pingTest()
	{
		
	}

    public Boolean ping(){
        String ip = "127.0.0.1";
        String pingResult = "";
        
        boolean test = false;

        String pingCmd = "ping " + ip;
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                pingResult = inputLine;
            }
            in.close();
            
            if(pingResult.substring(pingResult.length()-5, pingResult.length()).equals("0%),"))
            	test = false;
            else
            	test = true;
            	

        } catch (IOException e) {
            System.out.println(e);
        }
        
        return test;

    }
}
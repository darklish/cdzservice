package com.ylk.datamineservice.client;


public class RC4Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputStr = "1234";
		/*byte[] bytes = new byte[5];
		bytes[0] = (byte)0xF5;
		bytes[1] = (byte)0x04;
		bytes[2] = (byte)0xB0;
		bytes[3] = (byte)0x2B;
		bytes[4] = (byte)0x7A;*/
	    String key = "345";
	    byte[] byteKey = {(byte) 0xFD,(byte) 0xE4,(byte) 0xDF,0x1B,(byte) 0xEE,0x77,(byte) 0xDC,0x7A,0x7A,0x6F,0x48,(byte) 0xBF,(byte) 0xE9,(byte) 0xFF,0x6F,(byte) 0xFA};
	    key = new String(byteKey);
	    byte[] str = HloveyRC4(inputStr.getBytes(),key);
	      
	    //打印加密后的字符串      
	    System.out.println(str);  
	      
	    //打印解密后的字符串 
	    System.out.println(new String(HloveyRC4(str,key)));
	}
	
	public static byte[] HloveyRC4(byte[] aInput,String aKey)   
    {   
        int[] iS = new int[256];
        byte[] iK = new byte[256];   
          
        for (int i=0;i<256;i++)   
            iS[i]=i;   
              
        int j = 1;   
          
        for (short i= 0;i<256;i++)   
        {   
            iK[i]=(byte)aKey.charAt((i % aKey.length()));   
        }   
          
        j=0;   
          
        for (int i=0;i<255;i++)   
        {   
            j=(j+iS[i]+iK[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
        }   
      
      
        int i=0;   
        j=0;   
        byte[] iInputChar = aInput;
        byte[] iOutputChar = new byte[iInputChar.length];   
        for(short x = 0;x<iInputChar.length;x++)   
        {   
            i = (i+1) % 256;   
            j = (j+iS[i]) % 256;
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
            int t = (iS[i]+(iS[j] % 256)) % 256;   
            int iY = iS[t];   
            char iCY = (char)iY;   
            iOutputChar[x] =(byte)( iInputChar[x] ^ iCY) ;      
        }   
          
        return iOutputChar;   
                  
    } 

}

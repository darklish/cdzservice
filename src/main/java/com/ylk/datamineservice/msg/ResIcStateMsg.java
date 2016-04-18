package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.util.FrameTypeUtil;



public class ResIcStateMsg extends ResBaseMsg {
	
	private int cardState;
	
	private int balance;
	
	private int price;
	
	public ResIcStateMsg() {
		
	}
	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)cardState);
		/*byte[] bytes = ByteUtil.getFloatBytes(balance, 0);
		dataBuf.put(bytes[3]);
		dataBuf.put(bytes[2]);
		dataBuf.put(bytes[1]);
		dataBuf.put(bytes[0]);*/
		/*byte[] bytes2 = ByteUtil.getFloatBytes(price, 0);
		dataBuf.put(bytes2[3]);
		dataBuf.put(bytes2[2]);
		dataBuf.put(bytes2[1]);
		dataBuf.put(bytes2[0]);*/
		dataBuf.putInt(balance);
		dataBuf.putInt(price);
		dataBuf.flip();
		this.fType = FrameTypeUtil.RES_IC_STATE;
	}

	public int getCardState() {
		return cardState;
	}


	public void setCardState(int cardState) {
		this.cardState = cardState;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}







	
	

}

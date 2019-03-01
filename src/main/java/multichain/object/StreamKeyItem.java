/*
 * Copyright (C) 2017 Worldline, Inc.
 *
 * MultiChainJavaAPI code distributed under the GPLv3 license, see COPYING file.
 * https://github.com/SimplyUb/MultiChainJavaAPI/blob/master/LICENSE
 *
 */
package multichain.object;

import java.util.List;

/**
 * @author Ub - H. MARTEAU
 * @version 4.14
 */
public class StreamKeyItem {
	List<String> publishers;
	
	Object keys = null;
	List<String> dataID = null;
	Object data = null;
	Long confirmations = null;
	String blockhash = null;
	Long blockindex = null;
	Long blocktime = null;
	String txid = null;
	Long vout = null;
	Boolean valid = null;
	Long time = null;
	Long timereceived = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StreamKeyItem [publishers=" + publishers.toString() + ",\n key=" + keys.toString() + ",\n data=" + data
				+ ",\n confirmations=" + confirmations + ",\n blockhash=" + blockhash + ",\n blockindex=" + blockindex
				+ ",\n blocktime=" + blocktime + ",\n txid=" + txid + ",\n vout=" + vout + ",\n valid=" + valid
				+ ",\n time=" + time + ",\n timereceived=" + timereceived + "]";
	}

	/**
	 * @return the publishers
	 */
	public List<String> getPublishers() {
		return publishers;
	}
	

	/**
	 * @param publishers
	 *            the publishers to set
	 */
	public void setPublishers(List<String> publishers) {
		this.publishers = publishers;
	}

	/**
	 * @return the key
	 */
	public Object getKey() {
		return keys;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Object keys) {
		this.keys = keys;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the confirmations
	 */
	public long getConfirmations() {
		return confirmations;
	}

	/**
	 * @param confirmations
	 *            the confirmations to set
	 */
	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}

	/**
	 * @return the blockhash
	 */
	public String getBlockhash() {
		return blockhash;
	}

	/**
	 * @param blockhash
	 *            the blockhash to set
	 */
	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}

	/**
	 * @return the blockindex
	 */
	public long getBlockindex() {
		return blockindex;
	}

	/**
	 * @param blockindex
	 *            the blockindex to set
	 */
	public void setBlockindex(long blockindex) {
		this.blockindex = blockindex;
	}

	/**
	 * @return the blocktime
	 */
	public long getBlocktime() {
		return blocktime;
	}

	/**
	 * @param blocktime
	 *            the blocktime to set
	 */
	public void setBlocktime(long blocktime) {
		this.blocktime = blocktime;
	}

	/**
	 * @return the txid
	 */
	public String getTxid() {
		return txid;
	}

	/**
	 * @param txid
	 *            the txid to set
	 */
	public void setTxid(String txid) {
		this.txid = txid;
	}

	/**
	 * @return the vout
	 */
	public long getVout() {
		return vout;
	}

	/**
	 * @param vout
	 *            the vout to set
	 */
	public void setVout(long vout) {
		this.vout = vout;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid
	 *            the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the timereceived
	 */
	public long getTimereceived() {
		return timereceived;
	}

	/**
	 * @param timereceived
	 *            the timereceived to set
	 */
	public void setTimereceived(long timereceived) {
		this.timereceived = timereceived;
	}
	
	
	
	
	
	/**
	 * @return hash in data
	 * 
	 */
	
public String getHash() {
		
		
		String tmp = data.toString();
		String parts[] = tmp.split(" ");
		String tmp2= parts[2];
		String hash = tmp2.substring(tmp2.lastIndexOf("hashFile")+9, tmp2.lastIndexOf(','));
		
		return hash;
	}
	
	/**
	 * 
	 * @return date from timestamp in data
	 * 
	 */
	public String getDate() {

		String tmp = data.toString();
		String parts[] = tmp.split(",");
		String tmp4= parts[4];
		String date = tmp4.substring(tmp4.lastIndexOf("timestamp")+10, tmp4.lastIndexOf('}')-10);
		return date;
	}
	
	public String getTimeTimestamp() {
		
		String tmp = data.toString();
		String parts[] = tmp.split(",");
		String tmp4= parts[4];
		String time = tmp4.substring(tmp4.lastIndexOf("timestamp")+21, tmp4.lastIndexOf('}')-1);
		return time;
	}
	
	public String getTimestamp() {
		String tmp = data.toString();
		String parts[] = tmp.split(",");
		String tmp4= parts[4];
		String timestamp = tmp4.substring(tmp4.lastIndexOf("timestamp")+10, tmp4.lastIndexOf('}')-1);
		return timestamp;
	}
	
	public String getPath() {
		
		String tmp = data.toString();
		String parts[] = tmp.split(" ");
		String tmp1= parts[1];
		String path = tmp1.substring(tmp1.lastIndexOf("path")+5, tmp1.lastIndexOf(','));
		
		return path;
	}
	
	public String getCategoria() {
		
		String tmp = data.toString();
		String parts[] = tmp.split(",");
		String tmp3= parts[3];
		String categ = tmp3.substring(tmp3.lastIndexOf("categoria")+10, tmp3.lastIndexOf(""));

		return categ;
		
	}
	
	public String getIDdocumentale() {
		
		
		String tmp = data.toString();
		String parts[] = tmp.split(" ");
		String tmp0= parts[0];
		String idDoc = tmp0.substring(tmp0.lastIndexOf("ID_documentale")+15, tmp0.lastIndexOf(','));
		return idDoc;
	}


}

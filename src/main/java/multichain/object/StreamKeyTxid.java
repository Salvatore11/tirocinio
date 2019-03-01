package multichain.object;

public class StreamKeyTxid {

	String txid = null;
	Object data = null;
	
	
	
	@Override
	public String toString() {
		return txid;
	}
	
	
	/**
	 * @return the txid
	 */
	public String getTxid() {
		return txid;
	}
	
	public Object getData() {
		return data;
	}

	/**
	 * @param txid
	 * the txid to set
	 */
	public void setTxid(String txid) {
		this.txid = txid;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getKey() {
		String k = data.toString();
		String key = k.substring((k.lastIndexOf("path") + 41), k.lastIndexOf("hash") - 2);
		return key;
	}
	
	public String getCategoria() {
		String c = data.toString();
		String categ = c.substring((c.lastIndexOf("categoria")+10), c.lastIndexOf("time")-2);
		return categ;
	}
	
	public String getHash() {
		String hash = data.toString();
		String subHash = hash.substring((hash.lastIndexOf("hashFile") + 8), hash.lastIndexOf(',') - 1);
		return subHash;
	}
	
	public String getTimestamp() {
		String timestamp = data.toString();
		String subDate = timestamp.substring((timestamp.lastIndexOf("timestamp")+ 10), timestamp.lastIndexOf("timestamp")+ 20);
		String subTime = timestamp.substring((timestamp.lastIndexOf("timestamp")+ 21), timestamp.lastIndexOf("timestamp")+ 29);
		return subDate + " " + subTime;
	}
	
}

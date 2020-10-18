package fleamarket;

public class Post {
	private int no;
	private String accountId;
	private String title;
	private String type;
	private String price;
	private String note;
	private String regist;

	@Override
	public String toString() {
		return "Post [no=" + no + ", accountId=" + accountId + ", title=" + title + ", type=" + type + ", price="
				+ price + ", note=" + note + ", regist=" + regist + "]";
	}
	
	public Post() {
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRegist() {
		return regist;
	}

	public void setRegist(String regist) {
		this.regist = regist;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
